package tech.thatgravyboat.sprout;

import tech.thatgravyboat.sprout.common.registry.fabric.SproutParticlesImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import static tech.thatgravyboat.sprout.Sprout.MODID;

public class SproutFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) ->
                SproutParticlesImpl.TEXTURES.stream()
                        .map(id -> new ResourceLocation(MODID, "particle/" + id))
                        .forEachOrdered(registry::register)
        );
        SproutClient.init();
        SproutClient.initParticleFactories();
        SproutClient.initItemColors();
        SproutClient.initBlockColors();
    }
}
