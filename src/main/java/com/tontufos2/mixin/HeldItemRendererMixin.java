package com.tontufos2.mixin;

import com.tontufos2.Tontufos2Client; // Importa tu clase cliente

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    // Identificador del item de teclado
    private static final Identifier KEYBOARD_ID = new Identifier("evenmoreinstruments", "keyboard");

    // Inyecta al principio del método renderItem
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void tontufos2$hideKeyboardDuringDialogue(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int seed, CallbackInfo info) {

        // Solo nos interesa el jugador principal
        if (entity != MinecraftClient.getInstance().player) {
            return;
        }

        // Verifica si el item es el teclado
        if (stack.getItem() == Registries.ITEM.get(KEYBOARD_ID)) {

            // Verifica si el cliente está actualmente en un estado de diálogo con Charly
            // Esto llama a la función que crearás o modificarás en Tontufos2Client
            if (Tontufos2Client.isCharlyDialogueActive()) {

                // Si es el teclado Y estamos en diálogo con Charly, cancela el renderizado
                info.cancel();
            }
        }
    }
}