package vswe.stevesvehicles.old.Listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import vswe.stevesvehicles.old.Helpers.ResourceHelper;
import vswe.stevesvehicles.vehicle.entity.IVehicleEntity;

public class OverlayRenderer
{

    public OverlayRenderer() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {
            renderOverlay();
        }
    }


	@SideOnly(Side.CLIENT)
	private void renderOverlay() {
		net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getMinecraft();		
	    EntityPlayer player = minecraft.thePlayer;

        if (minecraft.currentScreen == null && player.ridingEntity != null && player.ridingEntity instanceof IVehicleEntity)
        {
            ((IVehicleEntity)player.ridingEntity).getVehicle().renderOverlay(minecraft);
        }
	}

	
}