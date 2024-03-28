package tkk.tkklib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import tkk.tkklib.TkkGameLib;

public class TestContainerGui extends ContainerScreen<TestContainer> {
    private final ResourceLocation OBSIDIAN_CONTAINER_RESOURCE = new ResourceLocation(TkkGameLib.MOD_ID, "textures/gui/container.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    public TestContainerGui(TestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = textureWidth;
        this.imageHeight = textureHeight;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        drawCenteredString(matrixStack, this.font, Integer.toString(this.getMenu().getIntArray().get(0)), 82, 20, 0xeb0505);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(OBSIDIAN_CONTAINER_RESOURCE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        blit(matrixStack, i, j, 0, 0, imageWidth, imageHeight, this.textureWidth, textureHeight);
    }
}
