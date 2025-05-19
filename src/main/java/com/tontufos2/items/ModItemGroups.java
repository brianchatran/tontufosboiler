package com.tontufos2.items;

import com.tontufos2.blocks.ModBlocks;
import com.tontufos2.Tontufos2;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup; // API de Fabric para crear grupos de items personalizados
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Clase encargada de registrar y definir los grupos de items personalizados del mod.
 */
public class ModItemGroups {

    // Declaración del grupo de items "TONTUFO"
    public static final ItemGroup TONTUFO = registerItemGroup("tontufos",
            FabricItemGroup.builder()
                    // Nombre traducible del grupo de items (para localización)
                    .displayName(Text.translatable("itemGroup.tontufos2.tontufos"))

                    // Ícono representativo del grupo en el inventario creativo
                    .icon(() -> new ItemStack(ModItems.FAFA))

                    // Items y bloques que se mostrarán en este grupo
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.FAFA); // Agrega el item FAFA
                        entries.add(Item.BLOCK_ITEMS.get(ModBlocks.FAFA_BLOCK));
                        entries.add(ModItems.CHARLY_SPAWN_EGG);
                        entries.add(ModItems.CHARLY_MUSIC_DISC);

                    })

                    // Construye el grupo de items
                    .build()
    );

    /**
     * Método auxiliar para registrar un grupo de items en el registro de Minecraft.
     *
     * @param itemGroupID ID único del grupo de items (namespace:path)
     * @param itemGroup Instancia del grupo a registrar
     * @return Grupo de items registrado
     */
    private static ItemGroup registerItemGroup(String itemGroupID, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(Tontufos2.MOD_ID, itemGroupID), itemGroup);
    }

    /**
     * Método para inicializar el registro de los grupos de items.
     * Llama este método en la inicialización de tu mod.
     */
    public static void registerItemGroups() {
        Tontufos2.LOGGER.info("Registrando GRUPO DE items para " + Tontufos2.MOD_ID);
        // La variable TONTUFO se registra en tiempo de carga de la clase,
        // por lo que no es necesario llamar nada más aquí.
    }
}
