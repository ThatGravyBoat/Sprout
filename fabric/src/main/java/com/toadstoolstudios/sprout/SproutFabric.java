package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.entities.GlowflyEntity;
import com.toadstoolstudios.sprout.entities.MammothEntity;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class SproutFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Sprout.init();
        FabricDefaultAttributeRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), ElephantEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(SproutEntities.MAMMOTH_ENTITY_TYPE.get(), MammothEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(SproutEntities.GLOWFLY_ENTITY_TYPE.get(), GlowflyEntity.createGlowflyAttributes());
    }
}
