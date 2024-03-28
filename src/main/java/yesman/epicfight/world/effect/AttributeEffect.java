package yesman.epicfight.world.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.main.EpicFightMod;

public class AttributeEffect extends Effect {
    protected final ResourceLocation icon;

    public AttributeEffect(EffectType category, String potionName, int color) {
        super(EffectType.BENEFICIAL, color);
        this.icon = new ResourceLocation(EpicFightMod.MODID, "textures/mob_effect/" + potionName + ".png");
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }
}
