package tkk.epic.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public enum SoundUtil {
    INSTANCE;

    public void playSound(String name, BlockPos pos, float volume, float pitch){
        Minecraft mc = Minecraft.getInstance();
        SimpleSound rec = new SimpleSound(new ResourceLocation(name), SoundCategory.PLAYERS, volume, pitch, false, 0, ISound.AttenuationType.LINEAR, (pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), false);
        mc.getSoundManager().play((ISound) rec);

    }

}
