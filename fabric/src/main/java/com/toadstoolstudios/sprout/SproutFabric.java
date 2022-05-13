package com.toadstoolstudios.sprout;

import com.toadstoolstudios.sprout.config.fabric.ConfigLoaderImpl;
import com.toadstoolstudios.sprout.entities.BounceBugEntity;
import com.toadstoolstudios.sprout.entities.ElephantEntity;
import com.toadstoolstudios.sprout.registry.SproutConfiguredFeatures;
import com.toadstoolstudios.sprout.registry.SproutEntities;
import com.toadstoolstudios.sprout.registry.SproutItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import java.io.IOException;

public class SproutFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Sprout.init();

        try {
            ConfigLoaderImpl.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SproutEntities.addSpawns();
        SproutConfiguredFeatures.registerFeatures();
        FabricDefaultAttributeRegistry.register(SproutEntities.ELEPHANT_ENTITY_TYPE.get(), ElephantEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(SproutEntities.BOUNCE_BUG_ENTITY.get(), BounceBugEntity.createMobAttributes());
        SproutItems.onComplete();
    }
}
