package tech.thatgravyboat.sprout.fabric;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import tech.thatgravyboat.sprout.Sprout;
import tech.thatgravyboat.sprout.common.configs.SproutConfig;

public class ModMenuConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ResourcefulConfig config = Sprout.CONFIGURATOR.getConfig(SproutConfig.class);
            if (config == null) {
                return null;
            }
            return new ConfigScreen(null, config);
        };
    }
}
