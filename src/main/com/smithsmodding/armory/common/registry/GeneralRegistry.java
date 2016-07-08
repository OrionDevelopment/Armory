package com.smithsmodding.armory.common.registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.registries.*;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Properties;

public class GeneralRegistry implements IArmoryAPI {
    public static boolean isInDevEnvironment = false;
    private static GeneralRegistry instance = new GeneralRegistry();

    private GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        isInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("armory.Dev", "false"));
    }

    public static GeneralRegistry getInstance() {
        return instance;
    }

    @Override
    public IAnvilMaterialRegistry getAnvilMaterialRegistry() {
        return AnvilMaterialRegistry.getInstance();
    }

    @Override
    public IAnvilRecipeRegistry getAnvilRecipeRegistry() {
        return AnvilRecipeRegistry.getInstance();
    }

    @Override
    public IHeatableItemRegistry getHeatableItemRegistry() {
        return HeatableItemRegistry.getInstance();
    }

    @Override
    public IArmorPartRegistry getMedievalArmorPartRegistry() {
        return MedievalAddonRegistry.getInstance();
    }

    @Override
    public IMaterialRegistry getArmorMaterialRegistry() {
        return MaterialRegistry.getInstance();
    }
}