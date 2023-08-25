package tech.thatgravyboat.sprout.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlockAccessor {

    @Accessor("POTTED_BY_CONTENT")
    @Contract(pure = true)
    static Map<Block, Block> getFlowerPotMap() {
        throw new AssertionError();
    }
}
