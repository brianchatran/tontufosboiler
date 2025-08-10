# 📝 CÓDIGO COMENTADO - TONTUFOS2 MOD

## 🎯 CLASE PRINCIPAL - Tontufos2.java

```java
// Paquete principal del mod
package com.tontufos2;

// Importaciones necesarias para el funcionamiento del mod
import com.tontufos2.blabber.HasFafaInInventoryCondition;
import com.tontufos2.blabber.HasTenEmeraldsInInventoryCondition;
import com.tontufos2.commands.PiramideTestCommand;
import com.tontufos2.entity.ModEntities;
import com.tontufos2.blocks.ModBlocks;
import com.tontufos2.items.ModItemGroups;
import com.tontufos2.items.ModItems;
import com.tontufos2.loot.AddItemLootModifier;
import com.tontufos2.sound.ModSounds;
import com.tontufos2.util.CharlySummonerHandler;
import com.tontufos2.util.ToxicityHandler;

// Importaciones de Fabric y Minecraft
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal del mod Tontufos2.
 * Implementa ModInitializer para ser el punto de entrada del mod.
 * Se ejecuta una sola vez cuando el mod se carga.
 */
public class Tontufos2 implements ModInitializer {
    // ID único del mod (usado en registros y recursos)
    public static final String MOD_ID = "tontufos2";
    
    // Logger para imprimir información y errores
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    // Referencia al servidor (útil para acceder desde otras clases)
    private static MinecraftServer SERVER = null;

    /**
     * Método principal que se ejecuta al inicializar el mod.
     * Aquí se registran todos los componentes del mod.
     */
    @Override
    public void onInitialize() {
        // Registro de ítems, bloques y handlers
        ModItems.registerItems();           // Registra todos los ítems personalizados
        ModItemGroups.registerItemGroups(); // Registra grupos de ítems en el inventario creativo
        ModBlocks.registerBlocks();         // Registra bloques personalizados
        ToxicityHandler.registerTickHandler(); // Inicia el sistema de toxicidad
        ModEntities.registerModEntities();  // Registra entidades personalizadas
        CharlySummonerHandler.registerStructureCheck(); // Inicia detección de pirámides
        PiramideTestCommand.register();     // Registra comandos personalizados
        ModSounds.registerSounds();         // Registra sonidos personalizados
        
        // Registro de modificadores de loot
        com.tontufos2.loot.AddChestLootModifier.register();  // Modifica loot de cofres
        com.tontufos2.loot.AddMobLootModifier.register();    // Modifica loot de mobs
        com.tontufos2.loot.AddItemLootModifier.register();   // Modifica loot de ítems
        
        // Registro de condiciones personalizadas para diálogos
        HasFafaInInventoryCondition.register();      // Condición: jugador tiene fafa
        HasTenEmeraldsInInventoryCondition.register(); // Condición: jugador tiene 10 esmeraldas

        // Eventos para gestionar la instancia del servidor
        // Se ejecuta cuando el servidor inicia
        ServerLifecycleEvents.SERVER_STARTING.register(server -> SERVER = server);
        // Se ejecuta cuando el servidor se detiene
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> SERVER = null);

        // Mensaje de confirmación de que el mod se cargó correctamente
        LOGGER.info("Hello Fabric world!");
    }
}
```

---

## 🌿 ÍTEM FAFA - FafaItem.java

```java
package com.tontufos2.items;

// Importaciones necesarias
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

/**
 * Ítem personalizado "Fafa" que extiende la funcionalidad básica de Item.
 * Implementa lógica personalizada cuando el jugador consume el ítem.
 */
public class FafaItem extends Item {

    /**
     * Constructor que recibe la configuración del ítem.
     * @param settings Configuración del ítem (comida, durabilidad, etc.)
     */
    public FafaItem(Settings settings) {
        super(settings);
    }

    /**
     * Método que se ejecuta cuando el jugador termina de consumir el ítem.
     * Aquí se implementa toda la lógica personalizada del ítem fafa.
     * 
     * @param stack El stack del ítem que se está consumiendo
     * @param world El mundo donde ocurre la acción
     * @param user La entidad que está consumiendo el ítem
     * @return El stack resultante (normalmente vacío si se consumió completamente)
     */
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // Solo ejecutar lógica si el usuario es un jugador
        if (user instanceof PlayerEntity player) {
            // 1. AUMENTAR TOXICIDAD
            // Llama al sistema de toxicidad para incrementar el contador del jugador
            ToxicityHandler.addToxicity(player);

            // 2. EFECTO DE VELOCIDAD TEMPORAL
            // Aplica efecto de velocidad por 200 ticks (10 segundos) con potencia 1
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));

            // 3. DAÑO POR SOBREDOSIS
            // Obtiene el nivel actual de toxicidad del jugador
            int toxicity = ToxicityHandler.getToxicity(player);
            
            // Si la toxicidad es 2 o mayor, aplica daño
            if (toxicity >= 2) {
                // Calcula el daño: (toxicidad - 1) * 1.0
                // Ejemplo: toxicidad 3 = 2.0 de daño
                float realDamage = (toxicity - 1) * 1.0f;

                // Obtiene el tipo de daño personalizado "fafa_overdose"
                // Correcto en 1.20.1: DamageType via RegistryKey
                RegistryEntry<DamageType> fafaOverdoseType =
                        player.getWorld().getRegistryManager()
                                .get(RegistryKeys.DAMAGE_TYPE)
                                .entryOf(RegistryKey.of(RegistryKeys.DAMAGE_TYPE, 
                                        new Identifier("tontufos2", "fafa_overdose")));

                // Crea la fuente de daño con el tipo personalizado
                DamageSource fafaOverdoseSource = new DamageSource(fafaOverdoseType, player);

                // Aplica el daño al jugador
                player.damage(fafaOverdoseSource, realDamage);
            }
        }

        // Llama al método padre para completar el consumo normal del ítem
        return super.finishUsing(stack, world, user);
    }
}
```

---

## ☠️ SISTEMA DE TOXICIDAD - ToxicityHandler.java

```java
package com.tontufos2.util;

// Importaciones necesarias
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

/**
 * Sistema que maneja la toxicidad de cada jugador.
 * Controla efectos progresivos basados en el consumo de fafa.
 */
public class ToxicityHandler {

    // Mapa que almacena la toxicidad de cada jugador usando su UUID como clave
    private static final Map<UUID, Integer> toxicityMap = new HashMap<>();
    
    // Mapa que registra qué niveles de toxicidad ya se anunciaron a cada jugador
    // Evita spam de mensajes repetidos
    private static final Map<UUID, Set<Integer>> announcedToxicityLevels = new HashMap<>();
    
    // UUID único para el modificador de velocidad (evita duplicados)
    private static final UUID FAFA_SPEED_BOOST_ID = UUID.fromString("5d7b8d8e-1cbb-4cbb-99b1-98d57b77fafa");

    /**
     * Aumenta la toxicidad de un jugador en 1 punto.
     * @param player El jugador al que aumentar la toxicidad
     */
    public static void addToxicity(PlayerEntity player) {
        UUID uuid = player.getUuid();
        // Obtiene la toxicidad actual (0 si no existe) y le suma 1
        toxicityMap.put(uuid, toxicityMap.getOrDefault(uuid, 0) + 1);
    }

    /**
     * Obtiene el nivel actual de toxicidad de un jugador.
     * @param player El jugador del que obtener la toxicidad
     * @return El nivel de toxicidad (0 si no tiene)
     */
    public static int getToxicity(PlayerEntity player) {
        return toxicityMap.getOrDefault(player.getUuid(), 0);
    }

    /**
     * Registra el sistema de tick que se ejecuta cada segundo.
     * Aquí se manejan todos los efectos de toxicidad.
     */
    public static void registerTickHandler() {
        // Se ejecuta al final de cada tick del mundo
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            // Solo ejecutar cada 20 ticks (1 segundo)
            if (world.getTime() % 20 == 0) {
                // Iterador para poder remover elementos mientras iteramos
                Iterator<Map.Entry<UUID, Integer>> iterator = toxicityMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<UUID, Integer> entry = iterator.next();
                    UUID uuid = entry.getKey();
                    int toxicity = entry.getValue();

                    // Obtiene el jugador del servidor
                    ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(uuid);
                    
                    // Si el jugador no está online, lo remueve del mapa
                    if (player == null) {
                        iterator.remove();
                        announcedToxicityLevels.remove(uuid);
                        continue;
                    }

                    // DECAY DE TOXICIDAD
                    // Cada 100 ticks (5 segundos), reduce la toxicidad en 1
                    if (world.getTime() % 100 == 0) {
                        toxicity--;
                        if (toxicity <= 0) {
                            // Si llega a 0, remueve al jugador del mapa
                            toxicityMap.remove(uuid);
                            announcedToxicityLevels.remove(uuid);
                        } else {
                            // Actualiza el valor en el mapa
                            toxicityMap.put(uuid, toxicity);
                        }
                    }

                    // BOOST DE VELOCIDAD
                    // Obtiene el atributo de velocidad del jugador
                    EntityAttributeInstance movementSpeed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                    if (movementSpeed != null) {
                        // Remueve el modificador anterior para evitar acumulación
                        movementSpeed.removeModifier(FAFA_SPEED_BOOST_ID);
                        
                        // Si tiene toxicidad ≥ 1, aplica boost de velocidad
                        if (toxicity >= 1) {
                            // Fórmula: 0.2 * raíz cuadrada de toxicidad
                            // Ejemplo: toxicidad 4 = 0.4 de velocidad extra
                            double speedBoost = 0.2 * Math.sqrt(toxicity);
                            movementSpeed.addPersistentModifier(new EntityAttributeModifier(
                                    FAFA_SPEED_BOOST_ID, 
                                    "Fafa Boost", 
                                    speedBoost, 
                                    EntityAttributeModifier.Operation.ADDITION
                            ));
                        }
                    }

                    // VENENO SI TOXICIDAD ≥ 2
                    if (toxicity >= 2) {
                        // Potencia del veneno = toxicidad - 2
                        // Ejemplo: toxicidad 4 = veneno potencia 2
                        int poisonAmplifier = toxicity - 2;
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, poisonAmplifier));
                    }

                    // MENSAJES SEGÚN TOXICIDAD
                    // Obtiene los niveles ya anunciados para este jugador
                    Set<Integer> announcedLevels = announcedToxicityLevels.computeIfAbsent(uuid, k -> new HashSet<>());

                    // Solo muestra mensaje si nunca se anunció este nivel
                    if (!announcedLevels.contains(toxicity)) {
                        if (toxicity == 2) {
                            player.sendMessage(Text.literal("Empiezas a sentirte rápido...")
                                    .formatted(Formatting.GREEN, Formatting.ITALIC), false);
                        } else if (toxicity == 4) {
                            player.sendMessage(Text.literal("Tus reflejos se descontrolan...")
                                    .formatted(Formatting.YELLOW, Formatting.BOLD), false);
                        } else if (toxicity == 6) {
                            player.sendMessage(Text.literal("¡¡¡Ya no sientes las piernas!!!")
                                    .formatted(Formatting.RED, Formatting.BOLD), false);
                        }
                        // Marca este nivel como ya anunciado
                        announcedLevels.add(toxicity);
                    }
                }
            }
        });

        // EVENTO DE RESPAWN
        // Se ejecuta cuando un jugador respawnea
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            UUID uuid = newPlayer.getUuid();

            // Limpia toda la toxicidad del jugador
            toxicityMap.remove(uuid);
            announcedToxicityLevels.remove(uuid);

            // Remueve el modificador de velocidad
            EntityAttributeInstance movementSpeed = newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (movementSpeed != null) {
                movementSpeed.removeModifier(FAFA_SPEED_BOOST_ID);
            }

            // Remueve el efecto de veneno
            newPlayer.removeStatusEffect(StatusEffects.POISON);

            // Reproduce sonido de wither spawn (efecto dramático)
            newPlayer.playSound(SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 1.0f, 1.0f);
        });
    }
}
```

---

## 🎹 ENTIDAD CHARLY GARCÍA - CharlyGarciaEntity.java

```java
package com.tontufos2.entity.custom;

// Importaciones necesarias
import com.tontufos2.Tontufos2;
import com.tontufos2.dialogues.CharlyDialogueHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Entidad personalizada para Charly García.
 * NPC interactivo que permite diálogos y entrega de teclado.
 * Extiende PathAwareEntity para poder caminar y navegar.
 */
public class CharlyGarciaEntity extends PathAwareEntity {
    
    // Identificadores únicos para los diálogos
    public static final Identifier CHARLY_INTRO = new Identifier("tontufos2", "charly_intro");
    public static final Identifier CHARLY_SECOND = new Identifier("tontufos2", "charly_second");

    // Variable de instancia: propio de cada Charly, NO static!
    // Cada instancia de Charly recuerda si ya recibió el piano
    private boolean pianoDado = false;

    /**
     * Constructor de la entidad.
     * @param entityType El tipo de entidad
     * @param world El mundo donde se crea
     */
    public CharlyGarciaEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Define los atributos base de la entidad (vida, velocidad, etc.).
     * @return Builder con los atributos configurados
     */
    public static DefaultAttributeContainer.Builder createCharlyAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)  // 20 puntos de vida
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2); // Velocidad de movimiento
    }

    /**
     * Lógica de interacción con clic derecho.
     * Maneja tres casos principales:
     * 1. Si el jugador le da el teclado a Charly, se lo equipa
     * 2. Si Charly ya tiene el piano, se abre diálogo secundario
     * 3. Si aún no se lo diste, se abre diálogo introductorio
     * 
     * @param player El jugador que interactúa
     * @param hand La mano con la que interactúa
     * @return ActionResult indicando el resultado de la interacción
     */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // Solo ejecutar en el servidor y si es un ServerPlayerEntity
        if (!this.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack heldItem = player.getStackInHand(hand);  // Ítem en la mano del jugador
            ItemStack charlyItem = this.getMainHandStack();    // Ítem en la mano de Charly

            // CASO 1: DAR EL TECLADO A CHARLY
            // Verifica si el jugador tiene un teclado, Charly no tiene nada, y aún no se lo dio
            if (heldItem.getItem() == Registries.ITEM.get(new Identifier("evenmoreinstruments", "keyboard"))
                    && charlyItem.isEmpty() && !pianoDado) {

                // Crea una copia del teclado para Charly
                ItemStack keyboard = heldItem.copy();
                keyboard.setCount(1);
                this.setStackInHand(Hand.MAIN_HAND, keyboard);

                // Reduce el stack del jugador
                heldItem.decrement(1);
                player.setStackInHand(hand, heldItem);
                player.getInventory().markDirty();
                
                // Marca que ya se dio el piano
                pianoDado = true;
                Tontufos2.LOGGER.info("Charly recibió el teclado.");
                return ActionResult.FAIL; // Evita que se abra el diálogo
            }

            // CASO 2: CHARLY YA TIENE EL PIANO → DIÁLOGO SECUNDARIO
            // Si Charly ya recibió el piano y el jugador NO tiene teclado
            if (pianoDado && player.getMainHandStack().getItem() != 
                    Registries.ITEM.get(new Identifier("evenmoreinstruments", "keyboard"))) {
                CharlyDialogueHandler.startCharlyDialogue(serverPlayer, this, CHARLY_SECOND);
                Tontufos2.LOGGER.info("Inicia diálogo secundario con Charly.");
                return ActionResult.SUCCESS;
            }

            // CASO 3: DIÁLOGO INTRODUCTORIO
            // Si aún no se le dio el piano, muestra el diálogo inicial
            CharlyDialogueHandler.startCharlyDialogue(serverPlayer, this, CHARLY_INTRO);
            Tontufos2.LOGGER.info("Inicia diálogo principal con Charly.");
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    /**
     * Tick de entidad, se ejecuta cada tick.
     * Por ahora solo llama al método padre, pero se puede expandir.
     */
    @Override
    public void tick() {
        super.tick();
    }

    /**
     * Dropea lo que tenga en la mano principal al morir.
     * Asegura que el teclado se dropee si Charly muere.
     */
    @Override
    protected void dropEquipment(net.minecraft.entity.damage.DamageSource source, 
                                int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemStack mainHand = this.getMainHandStack();
        if (!mainHand.isEmpty() && allowDrops) {
            this.dropStack(mainHand.copy());
            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY); // Limpia la mano
        }
    }

    /**
     * Guarda la variable pianoDado en NBT para persistencia.
     * Se ejecuta cuando se guarda el mundo.
     */
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("PianoDado", this.pianoDado);
    }

    /**
     * Lee la variable pianoDado de NBT al cargar.
     * Se ejecuta cuando se carga el mundo.
     */
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.pianoDado = nbt.getBoolean("PianoDado");
    }

    /**
     * Verifica si el jugador es elegible para el diálogo principal.
     * @param player Jugador a verificar
     * @return true si pianoDado es true y el jugador sostiene el teclado
     */
    public boolean isPlayerEligibleForMainDialogue(PlayerEntity player) {
        if (!this.pianoDado) return false;

        ItemStack heldItem = player.getMainHandStack();
        return heldItem.getItem() == Registries.ITEM.get(new Identifier("evenmoreinstruments", "keyboard"));
    }
}
```

---

## 🏗️ INVOCACIÓN DE CHARLY - CharlySummonerHandler.java

```java
package com.tontufos2.util;

// Importaciones necesarias
import java.util.HashSet;
import java.util.Set;
import com.tontufos2.Tontufos2;
import com.tontufos2.blocks.ModBlocks;
import com.tontufos2.entity.ModEntities;
import com.tontufos2.entity.custom.CharlyGarciaEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Handler que detecta estructuras especiales (pirámides) y invoca a Charly García.
 * Previene duplicados usando un Set de posiciones ya utilizadas.
 */
public class CharlySummonerHandler {
    
    // Set que almacena las posiciones de pirámides ya utilizadas
    // Previene que se invoque a Charly múltiples veces en la misma estructura
    private static final Set<BlockPos> piramidesUsadas = new HashSet<>();

    /**
     * Registra el sistema de detección de estructuras.
     * Se ejecuta cada tick del mundo.
     */
    public static void registerStructureCheck() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            // Solo ejecutar en ServerWorld
            if (!(world instanceof ServerWorld)) return;
            ServerWorld serverWorld = (ServerWorld) world;

            // Itera sobre todos los jugadores en el mundo
            serverWorld.getPlayers().forEach(player -> {
                BlockPos playerPos = player.getBlockPos();

                // Busca en un área de 11x11x11 bloques alrededor del jugador
                BlockPos.iterate(playerPos.add(-5, -5, -5), playerPos.add(5, 5, 5)).forEach(pos -> {
                    // Verifica si hay una pirámide de Charly en esta posición
                    if (isCharlyPyramidAt(serverWorld, pos) && !piramidesUsadas.contains(pos)) {
                        // Invoca a Charly 1 bloque arriba de la antorcha
                        spawnCharlyWithEffects(serverWorld, pos.up(1));
                        // Marca esta posición como ya utilizada
                        piramidesUsadas.add(pos.toImmutable());
                    }
                });
            });
        });
    }

    /**
     * Verifica si existe una pirámide de Charly en la posición especificada.
     * La estructura debe ser:
     *    T   (Antorcha)
     *   FFF  (3x3 fafa blocks)
     *  FFFFF (5x5 fafa blocks)
     * FFFFFFF (7x7 fafa blocks)
     * 
     * @param world El mundo donde verificar
     * @param torchPos La posición de la antorcha
     * @return true si encuentra la estructura completa
     */
    private static boolean isCharlyPyramidAt(World world, BlockPos torchPos) {
        // Verifica que haya una antorcha en la posición especificada
        if (!world.getBlockState(torchPos).isOf(Blocks.TORCH)) {
            return false;
        }

        // La base de la pirámide está 3 bloques abajo de la antorcha
        BlockPos basePos = torchPos.down(3);

        // Itera sobre las 3 capas de la pirámide
        for (int y = 0; y < 3; y++) {
            int offset = 2 - y; // ✅ 2 (base grande) hasta 0 (pico pequeño)
            
            // Itera sobre el área de cada capa
            for (int x = -offset; x <= offset; x++) {
                for (int z = -offset; z <= offset; z++) {
                    BlockPos currentPos = basePos.up(y);  // ✅ Hacia arriba desde base
                    currentPos = currentPos.add(x, 0, z);
                    
                    // Verifica que cada bloque sea un bloque de fafa
                    if (!world.getBlockState(currentPos).isOf(ModBlocks.FAFA_BLOCK)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Invoca a Charly García con efectos visuales y sonoros.
     * @param world El mundo donde invocar
     * @param torchPos La posición de la antorcha (Charly aparece 1 bloque arriba)
     */
    private static void spawnCharlyWithEffects(ServerWorld world, BlockPos torchPos) {
        // Crea la entidad Charly García
        CharlyGarciaEntity charly = ModEntities.CHARLY_GARCIA.create(world);
        if (charly != null) {
            // Posiciona a Charly 1 bloque arriba de la antorcha
            charly.refreshPositionAndAngles(torchPos.up(), 0.0f, 0.0f);
            world.spawnEntity(charly);

            // Sonido al spawnear (sonido de wither spawn)
            world.playSound(null, torchPos, SoundEvents.ENTITY_WITHER_SPAWN, 
                           SoundCategory.BLOCKS, 1.0f, 1.0f);

            // Partículas de nube alrededor de la posición
            world.spawnParticles(ParticleTypes.CLOUD, 
                                torchPos.getX() + 0.5, torchPos.getY(), torchPos.getZ() + 0.5, 
                                20, 0.3, 0.3, 0.3, 0.01);
            
            // Partículas de villager feliz arriba
            world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, 
                                torchPos.getX() + 0.5, torchPos.getY() + 1, torchPos.getZ() + 0.5, 
                                10, 0.2, 0.5, 0.2, 0.02);

            // 🔥 Borra la pirámide después de invocar
            destroyPyramid(world, torchPos);

            Tontufos2.LOGGER.info("Charly García ha sido invocado en " + torchPos);
        }
    }

    /**
     * Destruye la pirámide después de invocar a Charly.
     * @param world El mundo donde destruir
     * @param torchPos La posición de la antorcha
     */
    private static void destroyPyramid(ServerWorld world, BlockPos torchPos) {
        // Borra la antorcha
        world.setBlockState(torchPos, Blocks.AIR.getDefaultState());

        // Base de la pirámide (3x3 hasta 1x1)
        BlockPos basePos = torchPos.down(3);

        // Itera sobre las 3 capas de la pirámide
        for (int y = 0; y < 3; y++) {
            int offset = 2 - y;
            for (int x = -offset; x <= offset; x++) {
                for (int z = -offset; z <= offset; z++) {
                    BlockPos targetPos = basePos.up(y).add(x, 0, z);
                    // Solo borra bloques de fafa para no afectar otras estructuras
                    if (world.getBlockState(targetPos).isOf(ModBlocks.FAFA_BLOCK)) {
                        world.setBlockState(targetPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        // 🔥 Capa extra debajo (4x4 cuadrado completo)
        BlockPos extraLayer = torchPos.down(4);
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos targetPos = extraLayer.add(x, 0, z);
                if (world.getBlockState(targetPos).isOf(ModBlocks.FAFA_BLOCK)) {
                    world.setBlockState(targetPos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }
}
```

---

## 💬 SISTEMA DE DIÁLOGOS - CharlyDialogueHandler.java

```java
package com.tontufos2.dialogues;

// Importaciones necesarias
import com.tontufos2.Tontufos2;
import com.tontufos2.entity.custom.CharlyGarciaEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.ladysnake.blabber.impl.common.PlayerDialogueTracker;

/**
 * Handler centralizado para lanzar diálogos de Charly García con Blabber.
 * Wrapper simple para el sistema de diálogos de Blabber.
 */
public class CharlyDialogueHandler {

    /**
     * Inicia un diálogo de Charly con el jugador.
     * 
     * @param player Jugador que inicia el diálogo
     * @param charly Instancia de la entidad CharlyGarciaEntity
     * @param dialogueId Identificador único del diálogo a iniciar
     */
    public static void startCharlyDialogue(ServerPlayerEntity player, CharlyGarciaEntity charly, Identifier dialogueId) {
        try {
            // Inicia el diálogo usando el identificador pasado como argumento
            // Blabber maneja toda la lógica de diálogos internamente
            PlayerDialogueTracker.get(player).startDialogue(dialogueId, charly);

            // Logs para debugging
            Tontufos2.LOGGER.info("Piano dialogo.");
            System.out.println("Iniciando diálogo '" + dialogueId + "' con Charly para " + player.getName().getString());
            
        } catch (CommandSyntaxException e) {
            // Manejo y logging de posibles errores al iniciar diálogo
            System.err.println("Error al iniciar diálogo con Charly: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## 🎮 COMANDO DE PRUEBA - PiramideTestCommand.java

```java
package com.tontufos2.commands;

// Importaciones necesarias
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import com.tontufos2.blocks.ModBlocks;

/**
 * Comando de prueba para generar pirámides de fafa.
 * Útil para testing y debugging del sistema de invocación.
 */
public class PiramideTestCommand {

    /**
     * Registra el comando en el sistema de comandos de Minecraft.
     */
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            // Registra el comando literal "piramide_test"
            dispatcher.register(CommandManager.literal("piramide_test")
                    .executes(context -> spawnPyramid(context.getSource()))
            );
        });
    }

    /**
     * Genera una pirámide de prueba en la posición del jugador.
     * @param source La fuente del comando (jugador que lo ejecuta)
     * @return 1 si se ejecutó correctamente
     */
    private static int spawnPyramid(ServerCommandSource source) {
        // Obtiene la posición del jugador como BlockPos
        BlockPos pos = BlockPos.ofFloored(source.getPosition());

        // Antorcha arriba del todo
        BlockPos torchPos = pos;
        source.getWorld().setBlockState(torchPos, Blocks.TORCH.getDefaultState());

        // Pirámide hacia abajo (3 capas)
        for (int y = 1; y <= 3; y++) {
            int offset = y - 1; // 0, 1, 2 para las 3 capas
            
            // Genera cada capa de la pirámide
            for (int x = -offset; x <= offset; x++) {
                for (int z = -offset; z <= offset; z++) {
                    BlockPos target = pos.down(y).add(x, 0, z);
                    source.getWorld().setBlockState(target, ModBlocks.FAFA_BLOCK.getDefaultState());
                }
            }
        }

        // Mensaje de confirmación
        source.sendFeedback(() -> Text.literal("Pirámide de prueba generada (hacia abajo)"), false);
        return 1;
    }
}
```

---

## 📦 SISTEMA DE LOOT - AddItemLootModifier.java

```java
package com.tontufos2.loot;

// Importaciones necesarias
import com.tontufos2.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

/**
 * Modificador de loot que añade fafa a cofres específicos.
 * Usa el sistema de Fabric para modificar las tablas de loot de Minecraft.
 */
public class AddItemLootModifier {

    /**
     * Registra el modificador de loot.
     */
    public static void register() {
        // Se ejecuta cuando se modifican las tablas de loot
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // Verifica si el ID corresponde a un cofre específico
            if (id.equals(new Identifier("minecraft", "chests/simple_dungeon")) ||
                    id.equals(new Identifier("minecraft", "chests/ruined_portal")) ||
                    id.equals(new Identifier("minecraft", "chests/village/village_weaponsmith"))) {

                // Crea un pool de loot con 1 roll (siempre añade 1 fafa)
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.FAFA))
                        .build();

                // Añade el pool a la tabla de loot
                tableBuilder.pool(pool);
            }
        });
    }
}
```

---

## 🎵 SISTEMA DE SONIDOS - ModSounds.java

```java
package com.tontufos2.sound;

// Importaciones necesarias
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Sistema que registra los sonidos personalizados del mod.
 */
public class ModSounds {
    
    // Sonido de la canción de Charly
    public static final SoundEvent CHARLY_SONG = registerSoundEvent("charly_song");

    /**
     * Método auxiliar para registrar un sonido en el registro de Minecraft.
     * @param name Nombre del sonido (sin prefijo de mod ID)
     * @return El SoundEvent registrado
     */
    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("tontufos2", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    /**
     * Método para inicializar el registro de sonidos.
     * Actualmente solo imprime un mensaje de confirmación.
     */
    public static void registerSounds() {
        System.out.println("Registrando sonidos de tontufos2");
    }
}
```

---

## 🎯 CONDICIONES PERSONALIZADAS - HasFafaInInventoryCondition.java

```java
package com.tontufos2.blabber;

// Importaciones necesarias
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.tontufos2.Tontufos2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

/**
 * LootCondition personalizada que verifica si el jugador tiene un ítem "fafa" en su inventario.
 * Usada para condicionar diálogos o loot tables dentro del mod tontufos2.
 */
public class HasFafaInInventoryCondition implements LootCondition {

    /**
     * Instancia del LootConditionType para registrar esta condición.
     * Se asocia al Serializer definido internamente.
     */
    public static final LootConditionType TYPE = new LootConditionType(new Serializer());

    /**
     * Verifica si el jugador tiene al menos un ítem que contenga "fafa" en su translationKey.
     * @param context El LootContext que contiene la entidad a evaluar
     * @return true si el jugador tiene un ítem "fafa", false en caso contrario
     */
    @Override
    public boolean test(LootContext context) {
        var entity = context.get(LootContextParameters.THIS_ENTITY);
        if (entity instanceof PlayerEntity player) {
            // Itera sobre el inventario principal del jugador
            for (ItemStack stack : player.getInventory().main) {
                if (!stack.isEmpty() && 
                    stack.getItem().getTranslationKey().equals("item.tontufos2.fafa")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retorna el tipo de condición registrada.
     * @return El LootConditionType correspondiente
     */
    @Override
    public LootConditionType getType() {
        return TYPE;
    }

    /**
     * Registra esta LootCondition en el registry con el identificador "tontufos2:has_fafa_in_inventory".
     * Debe ser llamado en la inicialización del mod.
     */
    public static void register() {
        Registry.register(Registries.LOOT_CONDITION_TYPE, 
                         new Identifier("tontufos2", "has_fafa_in_inventory"), TYPE);
    }

    /**
     * Serializer responsable de la serialización y deserialización de esta LootCondition.
     * Como es una condición sin parámetros, no guarda ni carga datos.
     */
    public static class Serializer implements JsonSerializer<HasFafaInInventoryCondition> {

        /**
         * Serializa la condición a JSON. No guarda datos porque es un chequeo simple.
         */
        @Override
        public void toJson(JsonObject json, HasFafaInInventoryCondition object, JsonSerializationContext context) {
            // No hay datos que serializar para esta condición
        }

        /**
         * Deserializa la condición desde JSON. Siempre retorna una nueva instancia.
         */
        @Override
        public HasFafaInInventoryCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            return new HasFafaInInventoryCondition();
        }
    }
}
```

---

*Esta documentación de código debe mantenerse actualizada con cada cambio significativo en el código.*
