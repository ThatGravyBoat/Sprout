package net.examplemod;

public class ExampleMod {
    public static final String MOD_ID = "examplemod";
    
    public static void init() {
        System.out.println(ExampleExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
