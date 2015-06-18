package enhancedbiomes.world.gen.layer;

import enhancedbiomes.EnhancedBiomesMod;
import enhancedbiomes.handlers.BiomeGenManager;
import enhancedbiomes.world.biomestats.BiomeCategorisation;
import static enhancedbiomes.world.biome.EnhancedBiomesArchipelago.*;
import static enhancedbiomes.world.biome.EnhancedBiomesBiome.*;
import static enhancedbiomes.world.biome.EnhancedBiomesGrass.*;
import static enhancedbiomes.world.biome.EnhancedBiomesPlains.*;
import static enhancedbiomes.world.biome.EnhancedBiomesRock.*;
import static enhancedbiomes.world.biome.EnhancedBiomesSand.*;
import static enhancedbiomes.world.biome.EnhancedBiomesSandstone.*;
import static enhancedbiomes.world.biome.EnhancedBiomesSnow.*;
import static enhancedbiomes.world.biome.EnhancedBiomesTropical.*;
import static enhancedbiomes.world.biome.EnhancedBiomesWetland.*;
import static enhancedbiomes.world.biome.EnhancedBiomesWoodland.*;
import static net.minecraft.world.biome.BiomeGenBase.*;
import static enhancedbiomes.world.biomestats.BiomeCategorisation.*;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;

public class GenLayerEBBiome extends GenLayer
{
	public static BiomeGenBase[] bl_hot = new BiomeGenBase[] {};
	public static BiomeGenBase[] bl_warm = new BiomeGenBase[] {};
	public static BiomeGenBase[] bl_cool = new BiomeGenBase[] {};
	public static BiomeGenBase[] bl_frozen = new BiomeGenBase[] {};
	public static BiomeGenBase[] bl_backup = new BiomeGenBase[] {};

	public GenLayerEBBiome(long par1, GenLayer par3GenLayer, WorldType par4WorldType) {
		super(par1);
		bl_hot = BiomeGenManager.getHotBiomes(bl_hot);
		bl_warm = BiomeGenManager.getWarmBiomes(bl_warm);
		bl_cool = BiomeGenManager.getCoolBiomes(bl_cool);
		bl_frozen = BiomeGenManager.getFrozenBiomes(bl_frozen);
		bl_backup = bl_hot.length >= 1 ? bl_hot : bl_warm.length >= 1 ? bl_warm : bl_cool.length >= 1 ? bl_cool : bl_frozen.length >= 1 ? bl_frozen : new BiomeGenBase[] {BiomeGenBase.plains};
		if(bl_hot.length == 0) bl_hot = bl_backup;
		if(bl_warm.length == 0) bl_warm = bl_backup;
		if(bl_cool.length == 0) bl_cool = bl_backup;
		if(bl_frozen.length == 0) bl_frozen = bl_backup;
		//FIXME Remove for release
		bl_frozen = /*getAllBiomesOfCat(SEA);*/new BiomeGenBase[]{biomeShield, biomeDrifts};
		bl_hot = bl_warm = bl_cool = bl_frozen;/**/
		/*bl_warm = bl_cool = bl_frozen = bl_hot;/**/
		/*bl_hot = bl_frozen = bl_cool = bl_warm;/**/
		/*bl_hot = bl_warm = bl_frozen = bl_cool;/**/
		this.parent = par3GenLayer;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	public int[] getInts(int par1, int par2, int par3, int par4) {
		int[] aint = this.parent.getInts(par1, par2, par3, par4);
		int[] aint1 = IntCache.getIntCache(par3 * par4);

		for(int i1 = 0; i1 < par4; ++i1) {
			for(int j1 = 0; j1 < par3; ++j1) {
				this.initChunkSeed((long) (j1 + par1), (long) (i1 + par2));
				int k1 = aint[j1 + i1 * par3];
				int l1 = (k1 & 3840) >> 8;
				k1 &= -3841;

				if(isBiomeOceanic(k1)) aint1[j1 + i1 * par3] = k1;
				else if(k1 == BiomeGenBase.mushroomIsland.biomeID) aint1[j1 + i1 * par3] = k1;
				else if(k1 == 1) {
					if(l1 > 0) {
						if(this.nextInt(3) == 0) aint1[j1 + i1 * par3] = BiomeGenBase.mesaPlateau.biomeID;
						else aint1[j1 + i1 * par3] = BiomeGenBase.mesaPlateau_F.biomeID;
					}
					else aint1[j1 + i1 * par3] = this.bl_hot[this.nextInt(this.bl_hot.length)].biomeID;
				}
				else if(k1 == 2) aint1[j1 + i1 * par3] = this.bl_warm[this.nextInt(this.bl_warm.length)].biomeID;
				else if(k1 == 3) aint1[j1 + i1 * par3] = this.bl_cool[this.nextInt(this.bl_cool.length)].biomeID;
				else if(k1 == 4) aint1[j1 + i1 * par3] = this.bl_frozen[this.nextInt(this.bl_frozen.length)].biomeID;
				else aint1[j1 + i1 * par3] = BiomeGenBase.mushroomIsland.biomeID;
			}
		}

		return aint1;
	}
}