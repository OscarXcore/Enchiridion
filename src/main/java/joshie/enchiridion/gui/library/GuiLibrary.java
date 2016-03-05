package joshie.enchiridion.gui.library;

import joshie.enchiridion.util.ELocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiLibrary extends GuiContainer {
    private static final ResourceLocation location = new ELocation("library");
    public final int xSize = 430;
    public final int ySize = 217;
    public IInventory library;
    public int x, y;

    public GuiLibrary(InventoryPlayer playerInventory, IInventory library) {
        super(new ContainerLibrary(playerInventory, library));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        drawImage(location, -10, -10, 440, 240);
    }

    //Helper
    private void drawImage(ResourceLocation resource, int left, int top, int right, int bottom) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double) (x + left), (double) (y + bottom), (double) zLevel).tex(0, 1).color(1F, 1F, 1F, 1F).endVertex();
        worldrenderer.pos((double) (x + right), (double) (y + bottom), (double) zLevel).tex(1, 1).color(1F, 1F, 1F, 1F).endVertex();
        worldrenderer.pos((double) (x + right), (double) (y + top), (double) zLevel).tex(1, 0).color(1F, 1F, 1F, 1F).endVertex();
        worldrenderer.pos((double) (x + left), (double) (y + top), (double) zLevel).tex(0, 0).color(1F, 1F, 1F, 1F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
