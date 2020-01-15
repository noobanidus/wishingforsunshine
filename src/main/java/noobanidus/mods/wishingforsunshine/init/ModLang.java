package noobanidus.mods.wishingforsunshine.init;

import com.tterrag.registrate.providers.ProviderType;

import static noobanidus.mods.wishingforsunshine.WishingForSunshine.REGISTRATE;

public class ModLang {
  static {
    REGISTRATE.addDataGenerator(ProviderType.LANG, (ctx) -> {
      ctx.add("wishingforsunshine.message.rain", "%s wished for rain!");
      ctx.add("wishingforsunshine.message.thunder", "%s wished for thunderbolts & lightning!");
      ctx.add("wishingforsunshine.message.sunshine", "%s wished for clear skies!");
      ctx.add("wishingforsunshine.message.morning", "%s wished for the dawn of a new day!");
      ctx.add("wishingforsunshine.message.midday", "%s wished for the bright, noon-day sun!");
      ctx.add("wishingforsunshine.message.sunset", "%s wished for twilight's setting sun!");
      ctx.add("wishingforsunshine.message.midnight", "%s wished for the witching hour!");
      ctx.add("itemGroup.wishingforsunshine", "Wishing For Sunshine");
    });
  }

  public static void load () {

  }
}
