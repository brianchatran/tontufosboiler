package com.tontufos2.loot;

import com.tontufos2.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class AddChestLootModifier {

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (isTargetChest(id)) {
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.FAFA))
                        .build();

                tableBuilder.pool(pool);
            }
        });
    }

    private static boolean isTargetChest(Identifier id) {
        return id.equals(new Identifier("minecraft", "chests/simple_dungeon")) ||
                id.equals(new Identifier("minecraft", "chests/ruined_portal")) ||
                id.getPath().contains("village") && id.getPath().contains("houses");
    }
}
