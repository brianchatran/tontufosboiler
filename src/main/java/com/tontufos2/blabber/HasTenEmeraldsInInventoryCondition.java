package com.tontufos2.blabber;

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
 * LootCondition personalizada que verifica si el jugador tiene al menos 10 esmeraldas en su inventario.
 * Usada para condicionar diálogos o loot tables dentro del mod tontufos2.
 */
public class HasTenEmeraldsInInventoryCondition implements LootCondition {

    /**
     * Instancia del LootConditionType para registrar esta condición.
     * Se asocia al Serializer definido internamente.
     */
    public static final LootConditionType TYPE = new LootConditionType(new Serializer());

    /**
     * Verifica si el jugador tiene al menos 10 esmeraldas en su inventario.
     *
     * @param context El LootContext que contiene la entidad a evaluar.
     * @return true si el jugador tiene al menos 10 esmeraldas, false en caso contrario.
     */
    @Override
    public boolean test(LootContext context) {
        var entity = context.get(LootContextParameters.THIS_ENTITY);
        if (entity instanceof PlayerEntity player) {
            int emeraldCount = 0;
            for (ItemStack stack : player.getInventory().main) {
                if (!stack.isEmpty() && stack.getItem() == Registries.ITEM.get(new Identifier("minecraft", "emerald"))) {
                    emeraldCount += stack.getCount();
                    if (emeraldCount >= 10) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retorna el tipo de condición registrada.
     *
     * @return El LootConditionType correspondiente.
     */
    @Override
    public LootConditionType getType() {
        return TYPE;
    }

    /**
     * Registra esta LootCondition en el registry con el identificador "tontufos2:has_ten_emeralds_in_inventory".
     * Debe ser llamado en la inicialización del mod.
     */
    public static void register() {
        Registry.register(Registries.LOOT_CONDITION_TYPE, new Identifier("tontufos2", "has_ten_emeralds_in_inventory"), TYPE);
    }

    /**
     * Serializer responsable de la serialización y deserialización de esta LootCondition.
     * Como es una condición sin parámetros, no guarda ni carga datos.
     */
    public static class Serializer implements JsonSerializer<HasTenEmeraldsInInventoryCondition> {

        /**
         * Serializa la condición a JSON. No guarda datos porque es un chequeo simple.
         *
         * @param json Objeto JSON donde escribir.
         * @param object La instancia de la condición.
         * @param context Contexto de serialización.
         */
        @Override
        public void toJson(JsonObject json, HasTenEmeraldsInInventoryCondition object, JsonSerializationContext context) {
            // No hay datos que serializar para esta condición.
        }

        /**
         * Deserializa la condición desde JSON. Siempre retorna una nueva instancia.
         *
         * @param json Objeto JSON a leer.
         * @param context Contexto de deserialización.
         * @return Nueva instancia de HasTenEmeraldsInInventoryCondition.
         */
        @Override
        public HasTenEmeraldsInInventoryCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            return new HasTenEmeraldsInInventoryCondition();
        }
    }
}
