package com.toadstoolstudios.sprout;

import net.fabricmc.api.ClientModInitializer;

public class SproutFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SproutClient.init();
    }
}
