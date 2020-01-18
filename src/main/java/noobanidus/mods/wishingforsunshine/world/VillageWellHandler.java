package noobanidus.mods.wishingforsunshine.world;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import noobanidus.mods.wishingforsunshine.config.WishingConfig;

import java.util.List;
import java.util.Random;

public class VillageWellHandler implements VillagerRegistry.IVillageCreationHandler {
  @Override
  public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
    if (random.nextFloat() < WishingConfig.VILLAGE_CHANCE) {
      return new StructureVillagePieces.PieceWeight(getComponentClass(), 3, 1);
    }

    return new StructureVillagePieces.PieceWeight(getComponentClass(), 0, 0);
  }

  @Override
  public Class<? extends StructureVillagePieces.Village> getComponentClass() {
    return ComponentWell.class;
  }

  @Override
  public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int type) {
    return ComponentWell.buildComponent(villagePiece, startPiece, pieces, random, x, y, z, facing, type);
  }
}
