package noobanidus.mods.wishingforsunshine.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import noobanidus.mods.wishingforsunshine.WishingForSunshine;

import java.util.List;
import java.util.Random;

public class ComponentWell extends StructureVillagePieces.Village {
  private static final ResourceLocation WELL_ID = new ResourceLocation(WishingForSunshine.MODID, "village_well");

  public ComponentWell() {
  }

  public ComponentWell(StructureVillagePieces.Start start, int type, StructureBoundingBox boundingBox, EnumFacing facing) {
    super(start, type);
    this.boundingBox = boundingBox;
    setCoordBaseMode(facing);
  }

  @Override
  public boolean addComponentParts(World world, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
    if (averageGroundLvl < 0) {
      averageGroundLvl = getAverageGroundLevel(world, structureBoundingBoxIn);
      if (averageGroundLvl < 0) {
        return true;
      }

      this.boundingBox.offset(0, averageGroundLvl - this.boundingBox.minY, 0);
    }

    BlockPos pos = new BlockPos(this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ);
    TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
    PlacementSettings settings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID).setBoundingBox(structureBoundingBoxIn);
    Template template = templateManager.getTemplate(world.getMinecraftServer(), WELL_ID);
    template.addBlocksToWorldChunk(world, pos, settings);
    return true;
  }

  @SuppressWarnings("ConstantConditions")
  public static StructureVillagePieces.Village buildComponent (StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int type) {
    StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 5, 3, 5, facing);
    if (canVillageGoDeeper(boundingBox) && findIntersecting(pieces, boundingBox) == null) {
      return new ComponentWell(startPiece, type, boundingBox, facing);
    }

    return null;
  }
}
