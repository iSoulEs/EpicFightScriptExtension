package tkk.epic.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TkkCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(ITkkCapability.class)
    public static final Capability<ITkkCapability> TKK_CAPABILITY = null;
    public ITkkCapability cap;
    public TkkCapabilityProvider(ServerPlayerEntity p){
        cap=new TkkCapability(p);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == TkkCapabilityProvider.TKK_CAPABILITY) {
            return LazyOptional.of(() -> cap).cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return cap.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        cap.deserializeNBT(nbt);
    }
}
