package net.zestyblaze.wishingforsunshine;

import net.fabricmc.api.ModInitializer;
import net.zestyblaze.wishingforsunshine.registry.WFSBlockEntityInit;
import net.zestyblaze.wishingforsunshine.registry.WFSBlockInit;
import net.zestyblaze.wishingforsunshine.registry.WFSItemInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WishingForSunshine implements ModInitializer {
	public static final String MODID = "wishingforsunshine";
	public static final String MODNAME = "WishingForSunshine";
	public static final Logger LOGGER = LogManager.getLogger(MODNAME);

	@Override
	public void onInitialize() {
		LOGGER.info("WishingForSunshine is installed correctly, loading now! Thanks for installing! <3");
		WFSBlockEntityInit.registerBlockEntities();
		WFSBlockInit.registerBlocks();
		WFSItemInit.registerItems();
	}
}
