package noobanidus.mods.wishingforsunshine.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.fluid.Fluids;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import noobanidus.mods.wishingforsunshine.init.ModBlocks;

public class ClientSetup {
  public static void init(FMLClientSetupEvent event) {
    Minecraft mc = Minecraft.getInstance();
    mc.getBlockColors().register((blockState, iEnviromentBlockReader, blockPos, i) -> Fluids.WATER.getAttributes().getColor(iEnviromentBlockReader, blockPos), ModBlocks.WELL.get());
    mc.getItemColors().register((itemStack, i) -> Fluids.WATER.getAttributes().getColor(), ModBlocks.WELL.get());
  }
}
