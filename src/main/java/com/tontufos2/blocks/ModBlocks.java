// Declaración del paquete donde se encuentra esta clase
package com.tontufos2.blocks;

// Importaciones necesarias de clases y métodos de Minecraft y de tu mod
import com.tontufos2.Tontufos2;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.block.Blocks;

// Clase encargada de registrar los bloques personalizados de tu mod
public class ModBlocks {

    // Definición de un bloque personalizado llamado FAFA_BLOCK
    // Se registra tanto el bloque como su item correspondiente en un Pair
    public static final Block FAFA_BLOCK = registerBlock(
            "fafa_block",
            new Block(AbstractBlock.Settings.copy(Blocks.TERRACOTTA))
    );
    /**
     * Método privado para registrar un bloque y su correspondiente BlockItem en los registros de Minecraft.
     * @param name Nombre del bloque (sin prefijo de mod ID)
     * @param block Instancia del bloque a registrar
     * @return Un Pair que contiene el bloque registrado y su item registrado
     */
    private static Block registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, new Identifier(Tontufos2.MOD_ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(Tontufos2.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        return block;
    }

    /**
     * Método público para inicializar el registro de bloques.
     * (Actualmente solo imprime un mensaje de log, pero puede ampliarse en el futuro).
     */
    public static void registerBlocks() {
        Tontufos2.LOGGER.info("Registrando bloques para " + Tontufos2.MOD_ID);
    }

}
