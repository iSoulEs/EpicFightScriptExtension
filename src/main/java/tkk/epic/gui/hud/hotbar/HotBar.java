package tkk.epic.gui.hud.hotbar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import tkk.epic.key.KeybindingsManager;

import java.util.Locale;

public class HotBar extends AbstractGui {
    public static final int SKILL_SIZE=16;
    private static final ResourceLocation COOLDOWN_TEX = new ResourceLocation("tkkepic", "textures/gui/cooldown.png");
    private static final ResourceLocation FRAME = new ResourceLocation("tkkepic", "textures/gui/default_frame.png");
    private static final ResourceLocation FRAME_ENABLE = new ResourceLocation("tkkepic", "textures/gui/enable_frame.png");
    private static final ResourceLocation FRAME_DISABLE = new ResourceLocation("tkkepic", "textures/gui/disable_frame.png");
    private static final ResourceLocation FRAME_READY = new ResourceLocation("tkkepic", "textures/gui/ready_frame.png");

    Minecraft mc = Minecraft.getInstance();

    public void onHudRender(MatrixStack matrix, float v) {
        try {
            if (this.mc.options.renderDebug || this.mc.player.isSpectator())
                return;
            if (this.mc.player.isSpectator())
                return;
            int x = 0;
            int y = 0;
            if(HotBarManager.hotBarPos==0){
                y=this.mc.getWindow().getGuiScaledHeight()-20;
                x=0;

            }else if(HotBarManager.hotBarPos==1){
                x = 0;
                y = this.mc.getWindow().getGuiScaledHeight() / 2;
                for (int i=0;i<SKILL_SIZE;i++){
                    if(HotBarManager.spells[i].doRender){
                        y-=10;
                    }
                }

            }
            //renderHotbar(matrix, x, y);
            int temp=0;

            for (int i = 0; i < 16; i++) {
                RenderSystem.enableBlend();
                HotBarManager.Spell spell=HotBarManager.spells[i];
                if(spell!=null && spell.doRender){
                    int nowX=x;
                    int nowY=y;
                    if(HotBarManager.hotBarPos==0){
                        nowX+=temp*20;
                    }else if(HotBarManager.hotBarPos==1){
                        nowY+=temp*20;
                    }
                    String key;
                    if(i<8) {
                        key = cloc_translate(KeybindingsManager.getSpellKeybindingFromInt(i).keyBinding.getTranslatedKeyMessage()).toUpperCase(Locale.ROOT);
                    }else{
                        key="";
                    }
                    renderSpell(matrix,spell,nowX,nowY,key);
                    temp+=1;
                }

                //renderCurrentSpell(i, matrix);
                RenderSystem.disableBlend();
            }
            RenderSystem.disableBlend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void renderSpell(MatrixStack matrixStack,HotBarManager.Spell spell,int x,int y,String key){
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (spell == null){
            return;
        }
        if(!spell.doRender){
            return;
        }

        int xs = x;
        int ys = y;
        //渲染边框
        this.mc.getTextureManager().bind(FRAME);
        blit(matrixStack, xs, ys, 0.0F, 0.0F, 20, 20, 20, 20);
        //渲染激活
        if(spell.isEnable){
            this.mc.getTextureManager().bind(FRAME_ENABLE);
            blit(matrixStack, xs, ys, 0.0F, 0.0F, 20, 20, 20, 20);
        }

        //渲染图标
        this.mc.getTextureManager().bind(spell.getIcon());
        blit(matrixStack, xs+2, ys+2, 0.0F, 0.0F, 16, 16, 16, 16);
        //渲染充能
        if(spell.mana>0){
            drawCooldown(spell.getPercent(spell.mana,spell.maxMana), matrixStack, xs, ys);
        }
        //渲染冷却
        if(spell.cooldown>1){
            drawCooldown(spell.getPercent(spell.cooldown,spell.maxCooldow), matrixStack, xs, ys);
            float cooldown= ((float)spell.cooldown)/20;
            renderScaledTextB(matrixStack, xs + 2, ys + 2, 0.6D, cooldown+"s", spell.mana>0?TextFormatting.YELLOW:TextFormatting.GREEN);
        }
        //渲染可用状态提示
        if(spell.cooldown==1){
            if(spell.mana==0){
                this.mc.getTextureManager().bind(FRAME_READY);
                blit(matrixStack, xs, ys, 0.0F, 0.0F, 20, 20, 20, 20);
            }
        }
        //渲染按键
        if (key.length() > 3){
            key = key.substring(0, 2);
        }
        renderScaledText(matrixStack, xs + 16, ys + 16, 1.0D, key, spell.mana>0?TextFormatting.YELLOW:TextFormatting.GREEN);
        //渲染额外信息
        if(spell.text!=null){renderScaledTextC(matrixStack, xs + 14, ys + 0, 0.7D, spell.text, TextFormatting.GREEN);}
        //渲染禁用
        if(spell.isDisable){
            this.mc.getTextureManager().bind(FRAME_DISABLE);
            blit(matrixStack, xs, ys, 0.0F, 0.0F, 20, 20, 20, 20);
        }

    }
/*
    private void renderCurrentSpell(int num, MatrixStack matrix) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        HotBarManager.Spell spell = null;
        spell=HotBarManager.spells[num];
        if (spell == null){
            return;
        }
        if(!spell.doRender){
            return;
        }
        int x = 3;
        int y = this.mc.getWindow().getGuiScaledHeight() / 2 - HEIGHT / 2 + 3;
        y += num * 20;

        int xs = x;
        int ys = y;
        //渲染图标
        this.mc.getTextureManager().bind(spell.getIcon());
        blit(matrix, xs, ys, 0.0F, 0.0F, 16, 16, 16, 16);
        //渲染冷却
        drawCooldown(spell.getPercent(spell.cooldown,spell.maxCooldow), matrix, xs, ys);

        try {
            int xs = x;
            int ys = y;
            //blit(matrix, xs, ys, 0.0F, 0.0F, 16, 16, 16, 16);
            if (spell != null) {
                this.mc.getTextureManager()
                        .bind(spell.getIconLoc());
                blit(matrix, xs, ys, 0.0F, 0.0F, 16, 16, 16, 16);
                if (spell.config.charges > 0) {
                    int charges = (this.data.getCastingData()).charges.getCharges(spell.config.charge_name);
                    if (charges == 0) {
                        float needed = spell.config.charge_regen;
                        float currentticks = (this.data.getCastingData()).charges.getCurrentTicksChargingOf(spell.config.charge_name);
                        float ticksleft = needed - currentticks;
                        float percent = ticksleft / needed;
                        percent = MathHelper.clamp(percent, 0.0F, 1.0F);
                        drawCooldown(percent, matrix, xs, ys);
                    }
                    this.mc.getTextureManager()
                            .bind(CHARGE);
                    int chargex = x + 21;
                    for (int i = 0; i < charges; i++) {
                        blit(matrix, chargex, y + 5, 0.0F, 0.0F, this.CHARGE_SIZE, this.CHARGE_SIZE, this.CHARGE_SIZE, this.CHARGE_SIZE);
                        chargex += this.CHARGE_SIZE + 1;
                    }
                } else {
                    CooldownsData cds = Load.Unit((Entity)this.mc.player).getCooldowns();
                    float percent = cds.getCooldownTicks(spell.GUID()) / cds.getNeededTicks(spell.GUID());
                    if (cds.getCooldownTicks(spell.GUID()) > 1) {
                        percent = MathHelper.clamp(percent, 0.0F, 1.0F);
                        drawCooldown(percent, matrix, xs, ys);
                    }
                    int cdsec = cds.getCooldownTicks(spell.GUID()) / 20;
                    if (cdsec > 1) {
                        String stext = cdsec + "s";
                        renderScaledText(matrix, xs + 27, ys + 10, 0.75D, stext, TextFormatting.YELLOW);
                    }
                }
                //按键渲染
                String txt = translate(KeybindsRegister.getSpellHotbar(num).func_238171_j_()).toUpperCase(Locale.ROOT);
                if (txt.length() > 3)
                    txt = txt.substring(0, 2);
                renderScaledText(matrix, xs + 14, ys + 12, 1.0D, txt, TextFormatting.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

 */

    private void drawCooldown(float percent, MatrixStack matrix, int x, int y) {
        this.mc.getTextureManager().bind(COOLDOWN_TEX);
        blit(matrix, x+2, y+2, 0.0F, 0.0F, 16, (int)(16.0F * percent), 16, 16);
    }

    //com.robertx22.library_of_exile.utils.CLOC
    public static String translate(ITextComponent s) {
        return I18n.get(s.getString()
                .replaceAll("%", "PERCENT"), new Object[0])
                .replaceAll("PERCENT", "%");
    }
    //com.robertx22.library_of_exile.utils.GuiUtils
    public static void renderScaledText(MatrixStack matrix, int x, int y, double scale, String text, TextFormatting format) {
        double antiScale = 1.0D / scale;
        RenderSystem.scaled(scale, scale, scale);
        double textWidthMinus = ((Minecraft.getInstance()).font.width(text) / 2.0F) * scale;
        (Minecraft.getInstance()).font.getClass();
        double textHeightMinus = 9.0D * scale / 2.0D;
        float xp = (float)(x - textWidthMinus);
        float yp = (float)(y - textHeightMinus);
        float xf = (float)(xp * antiScale);
        float yf = (float)(yp * antiScale);
        (Minecraft.getInstance()).font.drawShadow(matrix, text, xf, yf, format.getColor().intValue());
        RenderSystem.scaled(antiScale, antiScale, antiScale);
    }
    //从左上算坐标
    public static void renderScaledTextB(MatrixStack matrix, int x, int y, double scale, String text, TextFormatting format) {
        double antiScale = 1.0D / scale;
        RenderSystem.scaled(scale, scale, scale);
        double textWidthMinus = ((Minecraft.getInstance()).font.width(text) / 2.0F) * scale;
        (Minecraft.getInstance()).font.getClass();
        double textHeightMinus = 9.0D * scale / 2.0D;
        //float xp = (float)(x - textWidthMinus);
        //float yp = (float)(y - textHeightMinus);
        float xf = (float)(x * antiScale);
        float yf = (float)(y * antiScale);
        (Minecraft.getInstance()).font.drawShadow(matrix, text, xf, yf, format.getColor().intValue());
        RenderSystem.scaled(antiScale, antiScale, antiScale);
    }
    //右往左
    public static void renderScaledTextC(MatrixStack matrix, int x, int y, double scale, String text, TextFormatting format) {
        double antiScale = 1.0D / scale;
        RenderSystem.scaled(scale, scale, scale);
        double textWidthMinus = ((Minecraft.getInstance()).font.width(text) / 2.0F) * scale;
        (Minecraft.getInstance()).font.getClass();
        double textHeightMinus = 9.0D * scale / 2.0D;
        float xp = (float)(x - textWidthMinus);
        //float yp = (float)(y - textHeightMinus);
        float xf = (float)(xp * antiScale);
        float yf = (float)(y * antiScale);
        (Minecraft.getInstance()).font.drawShadow(matrix, text, xf, yf, format.getColor().intValue());
        RenderSystem.scaled(antiScale, antiScale, antiScale);
    }
    //com.robertx22.library_of_exile.utils.CLOC
    public static String cloc_translate(ITextComponent s) {
        return I18n.get(s.getString()
                .replaceAll("%", "PERCENT"), new Object[0])
                .replaceAll("PERCENT", "%");
    }
}
