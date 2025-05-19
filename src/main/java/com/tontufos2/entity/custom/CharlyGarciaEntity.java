package com.tontufos2.entity.custom; // Paquete del mod

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
 * Permite interacción con diálogos y entrega de teclado.
 */
public class CharlyGarciaEntity extends PathAwareEntity {
    // Identificadores de diálogos
    public static final Identifier CHARLY_INTRO = new Identifier("tontufos2", "charly_intro");
    public static final Identifier CHARLY_SECOND = new Identifier("tontufos2", "charly_second");

    // Variable de instancia: propio de cada Charly, no static!
    private boolean pianoDado = false;

    // Constructor
    public CharlyGarciaEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    // Atributos base de la entidad (vida y velocidad)
    public static DefaultAttributeContainer.Builder createCharlyAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
    }

    /**
     * Lógica de interacción con clic derecho.
     * 1. Si el jugador le da el teclado a Charly, se lo equipa y termina ahí.
     * 2. Si el jugador tiene el mismo ítem que Charly, se abre diálogo principal.
     * 3. Si Charly ya tiene el teclado y el jugador no, se puede abrir otro diálogo secundario.
     */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack heldItem = player.getStackInHand(hand);
            ItemStack charlyItem = this.getMainHandStack();

            // 1. Dar el teclado a Charly si aún no lo tiene
            if (heldItem.getItem() == Registries.ITEM.get(new Identifier("evenmoreinstruments", "keyboard"))
                    && charlyItem.isEmpty() && !pianoDado) {

                ItemStack keyboard = heldItem.copy();
                keyboard.setCount(1);
                this.setStackInHand(Hand.MAIN_HAND, keyboard);

                heldItem.decrement(1);
                //aqui gemeni
                pianoDado = true;
                Tontufos2.LOGGER.info("Charly recibió el teclado.");
                return ActionResult.SUCCESS;
            }

            // 2. Si Charly ya tiene el piano → diálogo secundario SIEMPRE
            if (pianoDado) {
                CharlyDialogueHandler.startCharlyDialogue(serverPlayer, this, CHARLY_SECOND);
                Tontufos2.LOGGER.info("Inicia diálogo secundario con Charly.");
                return ActionResult.SUCCESS;
            }

            // 3. Si aún no se lo diste → diálogo introductorio
            CharlyDialogueHandler.startCharlyDialogue(serverPlayer, this, CHARLY_INTRO);
            Tontufos2.LOGGER.info("Inicia diálogo principal con Charly.");
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }


    // Tick de entidad, por si se quiere agregar lógica en el futuro
    @Override
    public void tick() {
        super.tick();
    }

    // Dropea lo que tenga en la mano principal al morir
    @Override
    protected void dropEquipment(net.minecraft.entity.damage.DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemStack mainHand = this.getMainHandStack();
        if (!mainHand.isEmpty() && allowDrops) {
            this.dropStack(mainHand.copy());
            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY); // Limpia la mano
        }
    }

    // Guardar variable en NBT (persistencia)
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("PianoDado", this.pianoDado);
    }

    // Leer variable de NBT al cargar
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.pianoDado = nbt.getBoolean("PianoDado");
    }
    /**
     * Verifica si el jugador tiene en la mano el ítem de teclado y si Charly ya recibió el piano.
     * @param player Jugador a verificar
     * @return true si pianoDado es true y el jugador sostiene el teclado
     */
    public boolean isPlayerEligibleForMainDialogue(PlayerEntity player) {
        if (!this.pianoDado) return false;

        ItemStack heldItem = player.getMainHandStack();
        return heldItem.getItem() == Registries.ITEM.get(new Identifier("evenmoreinstruments", "keyboard"));
    }

}
