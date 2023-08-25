package tech.thatgravyboat.sprout;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import tech.thatgravyboat.sprout.common.registry.SproutParticles;

import static tech.thatgravyboat.sprout.Sprout.MODID;

public class SproutFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) ->
            SproutParticles.PARTICLES.stream()
            .map(RegistryEntry::getId)
            .map(ResourceLocation::getPath)
            .map(id -> new ResourceLocation(MODID, "particle/" + id))
            .forEachOrdered(registry::register)
        );
        SproutClient.init();
        SproutClient.initParticleFactories();
        SproutClient.initItemColors();
        SproutClient.initBlockColors();
    }
}
