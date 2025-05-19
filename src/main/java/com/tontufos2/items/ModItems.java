package com.tontufos2.items;

import com.tontufos2.Tontufos2;
import com.tontufos2.entity.ModEntities;
import com.tontufos2.sound.ModSounds;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {

    // Registro del item "fafa" con alwaysEdible y lógica personalizada.
    public static final Item FAFA = registerItem("fafa", new FafaItem(new Item.Settings().food(
            new FoodComponent.Builder()
                    .hunger(4)
                    .saturationModifier(0.3f)
                    .alwaysEdible() // <-- permite comer con muslos llenos
                    .build()
    )));
    public static final Item CHARLY_SPAWN_EGG = registerItem("charly_garcia_spawn_egg",
            new SpawnEggItem(ModEntities.CHARLY_GARCIA, 0x000000, 0xffffff, new Item.Settings()));

    public static final Item CHARLY_MUSIC_DISC = registerItem("charly_music_disc",
            new MusicDiscItem(15, ModSounds.CHARLY_SONG, new Item.Settings().maxCount(1).rarity(Rarity.RARE), 2400));


    private static Item registerItem(String itemID, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Tontufos2.MOD_ID, itemID), item);
    }

    public static void registerItems() {
        Tontufos2.LOGGER.info("Registrando items para " + Tontufos2.MOD_ID);
    }
}
