package yesman.epicfight.world.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.main.EpicFightMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KatanaNewItem extends WeaponItem {
	@OnlyIn(Dist.CLIENT)
	private List<ITextComponent> tooltipExpand;

	public KatanaNewItem(Item.Properties build) {
		super(EpicFightItemTier.KATANA, 0, -2.0F, build);
		if (EpicFightMod.isPhysicalClient()) {
			this.tooltipExpand = new ArrayList<ITextComponent> ();
			this.tooltipExpand.add(new StringTextComponent(""));
			this.tooltipExpand.add(new TranslationTextComponent("item." + EpicFightMod.MODID + ".katana.tooltip"));
		}
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return toRepair.getItem() == Items.IRON_BARS;
	}
    
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		for (ITextComponent txtComp : tooltipExpand) {
			tooltip.add(txtComp);
		}
	}
}