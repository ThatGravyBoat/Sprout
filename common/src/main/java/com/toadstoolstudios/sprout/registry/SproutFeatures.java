package com.toadstoolstudios.sprout.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class SproutFeatures {

    @ExpectPlatform
    public static void registerFeatures() {
        throw new AssertionError();
    }
}
