package tech.thatgravyboat.sprout.entities.goals;

import tech.thatgravyboat.sprout.entities.Herbivore;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class SproutWanderGoal<T extends TamableAnimal & Herbivore> extends WaterAvoidingRandomStrollGoal {

    private final T elephant;

    public SproutWanderGoal(T pathAwareEntity, double d) {
        super(pathAwareEntity, d);
        this.elephant = pathAwareEntity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && elephant.isNotBusy();
    }
}
