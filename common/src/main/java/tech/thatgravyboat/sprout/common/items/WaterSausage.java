package tech.thatgravyboat.sprout.common.items;

import tech.thatgravyboat.sprout.common.registry.SproutParticles;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WaterSausage extends Item {

    public WaterSausage(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity user) {
        super.finishUsingItem(stack, world, user);
        if (user instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(SproutParticles.SHOOTS.get(), user.getX(), user.getEyeY(), user.getZ(), 15, 0.2, 0.2, 0.2, 0);
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
