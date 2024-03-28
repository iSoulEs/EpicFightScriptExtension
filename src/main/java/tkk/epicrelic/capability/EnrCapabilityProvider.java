package tkk.epicrelic.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnrCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IEnrCapability.class)
    public static final Capability<IEnrCapability> TKK_CAPABILITY = null;
    public IEnrCapability cap;
    public EnrCapabilityProvider(LivingEntity entity){
        cap=new EnrCapability(entity);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == EnrCapabilityProvider.TKK_CAPABILITY) {
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
