package tkk.tkklib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tkk.tkklib.TkkGameLib;
import tkk.tkklib.network.SPOpenGui;
import tkk.tkklib.network.TkkNetworkManager;

public class TestGui extends Screen {
    public static void openGuiForPlayer(ServerPlayerEntity player){
        TkkNetworkManager.sendToPlayer(new SPOpenGui(),player);

    }
    TextFieldWidget textFieldWidget;
    Button button;
    ResourceLocation OBSIDIAN_FIRST_GUI_TEXTURE = new ResourceLocation(TkkGameLib.MOD_ID, "textures/gui/first_gui.png");
    TranslationTextComponent content = new TranslationTextComponent("gui." + TkkGameLib.MOD_ID + ".first");
    SliderPercentageOption sliderPercentageOption;
    Widget sliderBar;

    public TestGui(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.textFieldWidget = new TextFieldWidget(this.font, this.width / 2 - 100, 66, 200, 20, new TranslationTextComponent("gui." + TkkGameLib.MOD_ID + ".first.context"));
        this.children.add(this.textFieldWidget);

        this.button = new Button(this.width / 2 - 40, 96, 80, 20, new TranslationTextComponent("gui." + TkkGameLib.MOD_ID + ".first.save"), (button) -> {
        });
        this.addButton(button);

        this.sliderPercentageOption = new SliderPercentageOption(TkkGameLib.MOD_ID + ".sliderbar", 5, 100, 5, (setting) -> Double.valueOf(0), (setting, value) -> {
        }, (gameSettings, sliderPercentageOption1) -> new TranslationTextComponent("gui." + TkkGameLib.MOD_ID + ".first.test"));
        this.sliderBar = this.sliderPercentageOption.createButton(Minecraft.getInstance().options, this.width / 2 - 100, 120, 200);
        this.children.add(this.sliderBar);

        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(OBSIDIAN_FIRST_GUI_TEXTURE);
        int textureWidth = 208;
        int textureHeight = 156;
        this.blit(matrixStack, this.width / 2 - 150, 10, 0, 0, 300, 200, textureWidth, textureHeight);
        drawCenteredString(matrixStack, this.font, content, this.width / 2 - 10, 30, 0xeb0505);
        this.textFieldWidget.render(matrixStack, mouseX, mouseY, partialTicks);
        this.button.render(matrixStack, mouseX, mouseY, partialTicks);
        this.sliderBar.render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }




}
