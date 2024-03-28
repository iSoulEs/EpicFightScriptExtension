package tkk.epic.key.key;

import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import tkk.epic.gui.hud.hotbar.HotBarManager;
import tkk.epic.key.TkkKeyBinding;
import tkk.epic.network.CPTkkSpellPress;
import tkk.epic.network.CPTkkSpellUp;
import tkk.epic.network.TkkEpicNetworkManager;

public class SpellKeybinding extends TkkKeyBinding {
    public int spellId;
    public SpellKeybinding(String description, IKeyConflictContext keyConflictContext, KeyModifier keyModifier, int keyCode, String category, int spellId) {
        super(description, keyConflictContext, keyModifier, keyCode, category);
        this.spellId=spellId;
    }

    @Override
    public void pressStart() {
        if(HotBarManager.spells[spellId].isDisable || !HotBarManager.spells[spellId].doRender || HotBarManager.spells[spellId].mana>0 || HotBarManager.spells[spellId].cooldown>0){return;}
        TkkEpicNetworkManager.sendToServer(new CPTkkSpellPress(spellId, HotBarManager.spells[spellId].isEnable));
    }

    @Override
    public void pressTick() {

    }

    @Override
    public void pressOver() {
        if(HotBarManager.spells[spellId].isDisable || !HotBarManager.spells[spellId].doRender || HotBarManager.spells[spellId].mana>0 || HotBarManager.spells[spellId].cooldown>0){return;}
        TkkEpicNetworkManager.sendToServer(new CPTkkSpellUp(spellId, HotBarManager.spells[spellId].isEnable));

    }
}
