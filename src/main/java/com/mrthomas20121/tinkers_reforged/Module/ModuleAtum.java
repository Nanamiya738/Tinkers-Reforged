package com.mrthomas20121.tinkers_reforged.Module;

import com.mrthomas20121.libs.OredictHelper;
import com.mrthomas20121.libs.RegistryLib;
import com.mrthomas20121.tinkers_reforged.Config.Config;
import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerTraits;

public class ModuleAtum extends ModuleBase {
    public RegistryLib limestone = new RegistryLib(Materials.limestone);
    public RegistryLib khnumite = new RegistryLib(Materials.khnumite);
    
    public ModuleAtum() {
        limestone.setCraftable(true).setCastable(false);
        limestone.addMaterialTrait(TinkerTraits.cheapskate, MaterialTypes.HEAD);
        limestone.addMaterialTrait(TinkerTraits.cheap);
        limestone.registerHeadStats(180, 3.1f, 2.2f, HarvestLevels.STONE);
        limestone.registerHandleStats(0.9f, 50);
        limestone.registerExtraStats(30);

        khnumite.setCraftable(true).setCastable(false);
        khnumite.addMaterialTrait(TinkerTraits.jagged, MaterialTypes.HEAD);
        khnumite.addMaterialTrait(TinkerTraits.poisonous);
        khnumite.registerHeadStats(200, 3.4f, 3.1f, HarvestLevels.IRON);
        khnumite.registerHandleStats(0.9f, 70);
        khnumite.registerExtraStats(50);
    }
    public void preInit(FMLPreInitializationEvent e) {
        if(Config.limestone) {
            limestone.addIngotItem("stoneLimestone");
            limestone.preInit("stoneLimestone");
        }
        if(Config.khnumite) {
            khnumite.addIngotItem("ingotKhnumite");
            khnumite.preInit("Khnumite");
        }
    }
    public void init(FMLInitializationEvent e) { 
        if(Config.khnumite) {
            khnumite.setRepresentativeItem("ingotKhnumite");
        }
    }

    public void postInit(FMLPostInitializationEvent e) {
        // compat to make limestone work
        OredictHelper.removeOredict(new ItemStack(AtumBlocks.LIMESTONE), "stone");
    }
}
