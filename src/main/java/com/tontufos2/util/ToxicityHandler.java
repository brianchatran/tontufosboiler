package com.tontufos2.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ToxicityHandler {

    private static final Map<UUID, Integer> toxicityMap = new HashMap<>();
    private static final Map<UUID, Set<Integer>> announcedToxicityLevels = new HashMap<>();
    private static final UUID FAFA_SPEED_BOOST_ID = UUID.fromString("5d7b8d8e-1cbb-4cbb-99b1-98d57b77fafa");

    public static void addToxicity(PlayerEntity player) {
        UUID uuid = player.getUuid();
        toxicityMap.put(uuid, toxicityMap.getOrDefault(uuid, 0) + 1);
    }

    public static int getToxicity(PlayerEntity player) {
        return toxicityMap.getOrDefault(player.getUuid(), 0);
    }

    public static void registerTickHandler() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world.getTime() % 20 == 0) {
                Iterator<Map.Entry<UUID, Integer>> iterator = toxicityMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<UUID, Integer> entry = iterator.next();
                    UUID uuid = entry.getKey();
                    int toxicity = entry.getValue();

                    ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(uuid);
                    if (player == null) {
                        iterator.remove();
                        announcedToxicityLevels.remove(uuid);
                        continue;
                    }

                    // Bajar toxicidad cada 5 segundos
                    if (world.getTime() % 100 == 0) {
                        toxicity--;
                        if (toxicity <= 0) {
                            toxicityMap.remove(uuid);
                            announcedToxicityLevels.remove(uuid);
                        } else {
                            toxicityMap.put(uuid, toxicity);
                        }
                    }

                    // Velocidad boost
                    EntityAttributeInstance movementSpeed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                    if (movementSpeed != null) {
                        movementSpeed.removeModifier(FAFA_SPEED_BOOST_ID);
                        if (toxicity >= 1) {
                            double speedBoost = 0.2 * Math.sqrt(toxicity);
                            movementSpeed.addPersistentModifier(new EntityAttributeModifier(FAFA_SPEED_BOOST_ID, "Fafa Boost", speedBoost, EntityAttributeModifier.Operation.ADDITION));
                        }
                    }

                    // Veneno si toxicidad ≥ 2
                    if (toxicity >= 2) {
                        int poisonAmplifier = toxicity - 2;
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, poisonAmplifier));
                    }

                    // Mensajes según toxicidad (solo si nunca lo anunciamos antes)
                    Set<Integer> announcedLevels = announcedToxicityLevels.computeIfAbsent(uuid, k -> new HashSet<>());

                    if (!announcedLevels.contains(toxicity)) {
                        if (toxicity == 2) {
                            player.sendMessage(Text.literal("Empiezas a sentirte rápido...").formatted(Formatting.GREEN, Formatting.ITALIC), false);
                        } else if (toxicity == 4) {
                            player.sendMessage(Text.literal("Tus reflejos se descontrolan...").formatted(Formatting.YELLOW, Formatting.BOLD), false);
                        } else if (toxicity == 6) {
                            player.sendMessage(Text.literal("¡¡¡Ya no sientes las piernas!!!").formatted(Formatting.RED, Formatting.BOLD), false);
                        }
                        announcedLevels.add(toxicity);
                    }
                }
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            UUID uuid = newPlayer.getUuid();

            toxicityMap.remove(uuid);
            announcedToxicityLevels.remove(uuid);

            EntityAttributeInstance movementSpeed = newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (movementSpeed != null) {
                movementSpeed.removeModifier(FAFA_SPEED_BOOST_ID);
            }

            newPlayer.removeStatusEffect(StatusEffects.POISON);

            newPlayer.playSound(SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 1.0f, 1.0f);
        });
    }
}
