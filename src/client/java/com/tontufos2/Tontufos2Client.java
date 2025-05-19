package com.tontufos2;

import com.tontufos2.entity.ModEntities;
import com.tontufos2.entity.client.CharlyGarciaRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class Tontufos2Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.CHARLY_GARCIA, CharlyGarciaRenderer::new);

	}

}
