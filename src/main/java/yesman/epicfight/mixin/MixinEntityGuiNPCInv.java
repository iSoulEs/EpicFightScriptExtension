package yesman.epicfight.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import noppes.npcs.client.gui.mainmenu.GuiNPCInv;
import noppes.npcs.client.gui.util.*;
import noppes.npcs.containers.ContainerNPCInv;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Mixin(value = GuiNPCInv.class)
public abstract class MixinEntityGuiNPCInv  extends GuiContainerNPCInterface2<ContainerNPCInv> implements ISliderListener, IGuiData {
    @Shadow private HashMap<Integer, Integer> chances;

    public MixinEntityGuiNPCInv(EntityNPCInterface npc, ContainerNPCInv cont, PlayerInventory inv, ITextComponent titleIn) {
        super(npc, cont, inv, titleIn);
    }

    /**
     * @author
     * @reason Fixed Battle status block changing to interactive
     * if (this.field_70170_p.field_72995_K) {
     *             return this.isAttacking() ? ActionResultType.SUCCESS : ActionResultType.FAIL;
     */
    @Overwrite(remap = false)
    public void func_231160_c_() {
        super.func_231160_c_();
        this.addLabel(new GuiLabel(0, "inv.minExp", this.guiLeft + 118, this.guiTop + 18));
        this.addTextField(new GuiTextFieldNop(0, this, this.guiLeft + 108, this.guiTop + 29, 60, 20, this.npc.inventory.getExpMin() + ""));
        this.getTextField(0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, 32767, 0);
        this.addLabel(new GuiLabel(1, "inv.maxExp", this.guiLeft + 118, this.guiTop + 52));
        this.addTextField(new GuiTextFieldNop(1, this, this.guiLeft + 108, this.guiTop + 63, 60, 20, this.npc.inventory.getExpMax() + ""));
        this.getTextField(1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, 32767, 0);
        this.addButton(new GuiButtonNop(this, 10, this.guiLeft + 88, this.guiTop + 88, 80, 20, new String[]{"stats.normal", "inv.auto"}, this.npc.inventory.lootMode));
        this.addLabel(new GuiLabel(2, "inv.npcInventory", this.guiLeft + 191, this.guiTop + 5));
        this.addLabel(new GuiLabel(3, "inv.inventory", this.guiLeft + 8, this.guiTop + 101));

        for(int i = 0; i < 9; ++i) {
            int chance = 100;
            if (this.npc.inventory.dropchance.containsKey(i)) {
                chance = (Integer)this.npc.inventory.dropchance.get(i);
            }

            if (chance < 0 || chance > 100) {
                chance = 100;
            }

            this.chances.put(i, chance);
            this.addSlider(new GuiSliderNop(this, i, this.guiLeft + 211, this.guiTop + 14 + i * 21, (float)chance / 100.0F));
        }

    }
}
