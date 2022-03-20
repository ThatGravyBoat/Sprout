package net.examplemod.forge;

import net.examplemod.ExampleMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MOD_ID)
public class ExampleModForge {
    public ExampleModForge() {
        ExampleMod.init();
    }
}
