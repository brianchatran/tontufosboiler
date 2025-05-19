package com.tontufos2.loot;

import com.tontufos2.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class AddMobLootModifier {

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(new Identifier("minecraft", "entities/skeleton"))) {
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(0.1f))
                        .with(ItemEntry.builder(ModItems.FAFA))
                        .build();

                tableBuilder.pool(pool);
            }
        });
    }
}
