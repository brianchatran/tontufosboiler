package com.tontufos2.entity;

import com.tontufos2.Tontufos2;
import com.tontufos2.entity.custom.CharlyGarciaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<CharlyGarciaEntity> CHARLY_GARCIA = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Tontufos2.MOD_ID, "charly_garcia"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CharlyGarciaEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
                    .build()
    );

    public static void registerModEntities() {
        Tontufos2.LOGGER.info("Registered Charly Entity for " + Tontufos2.MOD_ID);
        FabricDefaultAttributeRegistry.register(CHARLY_GARCIA, CharlyGarciaEntity.createCharlyAttributes());
    }
}
