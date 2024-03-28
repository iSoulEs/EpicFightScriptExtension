package yesman.epicfight.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import noppes.npcs.containers.ContainerNpcInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;

@Mixin(value = ContainerNpcInterface.class)
public abstract class MixinContainerNpcInterface extends Container {


    protected MixinContainerNpcInterface(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_) {
        super(p_i50105_1_, p_i50105_2_);
    }

    /**
     * @author
     * @reason Fixed Battle status block changing to interactive
     * if (this.field_70170_p.field_72995_K) {
     *             return this.isAttacking() ? ActionResultType.SUCCESS : ActionResultType.FAIL;
     */
    @Overwrite(remap = false)
    public boolean func_75145_c(PlayerEntity player) {
        return !player.removed;
    }
}

