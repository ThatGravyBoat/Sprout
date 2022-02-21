package com.toadstoolstudios.sprout.registry;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.toadstoolstudios.sprout.Sprout.MOD_ID;

public class SproutEntities {
    public static final EntityType<ElephantEntity> ELEPHANT_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, ElephantEntity::new).dimensions(EntityDimensions.changing(1,1)).build();
    public static final EntityType<GlowflyEntity> GLOWFLY_ENTITY_TYPE = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, GlowflyEntity::new).dimensions(EntityDimensions.changing(1,1)).build();

    public static void registerEntities() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "elephant"), ELEPHANT_ENTITY_TYPE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "glowfly"), GLOWFLY_ENTITY_TYPE);
    }
}
