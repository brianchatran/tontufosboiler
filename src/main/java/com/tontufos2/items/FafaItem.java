package com.tontufos2.items;

import com.tontufos2.util.ToxicityHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.world.World;

public class FafaItem extends Item {

    public FafaItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            // Aumentar toxicidad
            ToxicityHandler.addToxicity(player);

            // Efecto SPEED momentáneo
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));

            // Daño real si toxicidad >= 2
            int toxicity = ToxicityHandler.getToxicity(player);
            if (toxicity >= 2) {
                float realDamage = (toxicity - 1) * 1.0f;

                // Correcto en 1.20.1: DamageType via RegistryKey
                RegistryEntry<DamageType> fafaOverdoseType =
                        player.getWorld().getRegistryManager()
                                .get(RegistryKeys.DAMAGE_TYPE)
                                .entryOf(RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("tontufos2", "fafa_overdose")));

                DamageSource fafaOverdoseSource = new DamageSource(fafaOverdoseType, player);

                player.damage(fafaOverdoseSource, realDamage);
            }
        }

        return super.finishUsing(stack, world, user);
    }
}
