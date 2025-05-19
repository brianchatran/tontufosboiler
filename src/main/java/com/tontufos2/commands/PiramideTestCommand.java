package com.tontufos2.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import com.tontufos2.blocks.ModBlocks;

public class PiramideTestCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("piramide_test")
                    .executes(context -> spawnPyramid(context.getSource()))
            );
        });
    }

    private static int spawnPyramid(ServerCommandSource source) {
        BlockPos pos = BlockPos.ofFloored(source.getPosition());

        // Antorcha arriba del todo
        BlockPos torchPos = pos;
        source.getWorld().setBlockState(torchPos, Blocks.TORCH.getDefaultState());

        // Pirámide hacia abajo
        for (int y = 1; y <= 3; y++) {
            int offset = y - 1;
            for (int x = -offset; x <= offset; x++) {
                for (int z = -offset; z <= offset; z++) {
                    BlockPos target = pos.down(y).add(x, 0, z);
                    source.getWorld().setBlockState(target, ModBlocks.FAFA_BLOCK.getDefaultState());
                }
            }
        }

        source.sendFeedback(() -> Text.literal("Pirámide de prueba generada (hacia abajo)"), false);
        return 1;
    }
}
