package tech.thatgravyboat.sprout.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record SpawnsBiomeModifier(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawns) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase.equals(Phase.ADD) && biomes().contains(arg)) {
            builder.getMobSpawnSettings().addSpawn(spawns().type.getCategory(), spawns());
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SproutForge.SPAWN_MODIFIER.get();
    }

    public static Codec<SpawnsBiomeModifier> makeCodec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(SpawnsBiomeModifier::biomes),
                MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawns").forGetter(SpawnsBiomeModifier::spawns)
        ).apply(instance, SpawnsBiomeModifier::new));
    }
}
