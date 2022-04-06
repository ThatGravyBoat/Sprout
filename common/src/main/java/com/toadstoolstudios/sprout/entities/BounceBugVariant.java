package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

public enum BounceBugVariant {
    WARPED("textures/entity/warped_bug.png", Items.WARPED_FUNGUS, ParticleTypes.WARPED_SPORE),
    CRIMSON("textures/entity/crimson_bug.png", Items.CRIMSON_FUNGUS, ParticleTypes.CRIMSON_SPORE);

    public Identifier model, texture;
    public Item shroomChoice;
    public ParticleEffect particleEffect;

    BounceBugVariant(Identifier model, Identifier texture, Item shroomChoice, ParticleEffect particleEffect) {
        this.model = model;
        this.texture = texture;
        this.shroomChoice = shroomChoice;
        this.particleEffect = particleEffect;
    }

    BounceBugVariant(String texture, Item shroomChoice, ParticleEffect particleEffect) {
        this(new Identifier(Sprout.MODID, "geo/bounce_bug.geo.json"), new Identifier(Sprout.MODID, texture), shroomChoice, particleEffect);
    }
}
