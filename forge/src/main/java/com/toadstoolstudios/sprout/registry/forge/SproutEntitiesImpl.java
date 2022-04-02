package com.toadstoolstudios.sprout.registry.forge;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SproutEntitiesImpl {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Sprout.MODID);

    public static void addSpawnRules() {
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.create(factory, group).setDimensions(width, height).build(name));
    }
}
