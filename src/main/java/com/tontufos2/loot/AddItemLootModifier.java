package com.tontufos2.loot;

import com.tontufos2.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class AddItemLootModifier {

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(new Identifier("minecraft", "chests/simple_dungeon")) ||
                    id.equals(new Identifier("minecraft", "chests/ruined_portal")) ||
                    id.equals(new Identifier("minecraft", "chests/village/village_weaponsmith"))) {

                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.FAFA))
                        .build();

                tableBuilder.pool(pool);
            }
        });
    }
}
