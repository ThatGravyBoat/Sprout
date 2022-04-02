package com.toadstoolstudios.sprout.entities;

import com.toadstoolstudios.sprout.Sprout;
import net.minecraft.util.Identifier;

public enum BounceBugVariant {
    WARPED("textures/entity/warped_bounce_bug.png"),
    CRIMSON("textures/entity/crimson_bounce_bug.png");

    public Identifier model, texture;

    BounceBugVariant(Identifier model, Identifier texture) {
        this.model = model;
        this.texture = texture;
    }

    BounceBugVariant(String texture) {
        this.model = new Identifier(Sprout.MODID, "geo/bounce_bug.geo.json");
        this.texture = new Identifier(Sprout.MODID, texture);
    }
}
