package com.smithsmodding.armory.common.material;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.materials.IMaterialRenderInfo;
import com.smithsmodding.armory.api.registries.IArmorPartRegistry;
import com.smithsmodding.armory.common.addons.MedievalAddonRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.util.client.color.ColorSampler;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

public class ArmorMaterial implements IArmorMaterial {
    private static int LASTUSEDID = 0;

    private int materialIndex;
    private String oreDictionaryIdentifier;
    private String uniqueIdentifier;
    private boolean isBaseMaterial;
    private int meltingTime;

    private HashMap<String, Float> partAbsorptionRatios = new HashMap<String, Float>();
    private HashMap<String, Integer> partDurabilities = new HashMap<String, Integer>();
    private HashMap<String, Integer> partModifierCounts = new HashMap<String, Integer>();

    private float meltingPoint;
    private float heatCoefficient;
    private ItemStack baseStack;

    private IMaterialRenderInfo renderInfo;
    private String translationKey;


    //Constructor
    public ArmorMaterial (String uniqueIdentifier, String oreDictionaryIdentifier, boolean isBaseMaterial, float meltingPoint, int meltingTime, float heatCoefficient, ItemStack baseStack) {
        this.oreDictionaryIdentifier = oreDictionaryIdentifier;
        this.uniqueIdentifier = uniqueIdentifier;

        if (Armory.side == Side.CLIENT && baseStack != null) {
            MinecraftColor metalColor = ColorSampler.getColorSampleFromItemStack(baseStack);

            renderInfo = new IMaterialRenderInfo.Metal(metalColor.getRGB());
        } else if (Armory.side == Side.CLIENT) {
            renderInfo = new IMaterialRenderInfo.Metal(MinecraftColor.WHITE.getRGB());
        }

        this.isBaseMaterial = isBaseMaterial;
        this.meltingPoint = meltingPoint;
        this.heatCoefficient = heatCoefficient;
        this.meltingTime = meltingTime;

        this.baseStack = baseStack;
        if (baseStack != null && !HeatableItemRegistry.getInstance().isHeatable(baseStack)) {
            HeatableItemRegistry.getInstance().addBaseStack(this, baseStack);
        }

        materialIndex = LASTUSEDID;
        LASTUSEDID++;

        Armory.getLogger().info("Initialized material: " + this.uniqueIdentifier);
    }


    @Override
    public String getUniqueID () {
        return uniqueIdentifier;
    }

    @Override
    public int getItemDamageMaterialIndex () {
        return materialIndex;
    }

    @Override
    public ItemStack getBaseItemStack () {
        return baseStack;
    }

    @Override
    public String getOreDicName () {
        return oreDictionaryIdentifier;
    }

    @Override
    public String getType () {
        return References.InternalNames.Tiers.MEDIEVAL;
    }

    public int getMeltingTime () {
        return meltingTime;
    }



    public void setBaseDamageAbsorption (String pTargetArmorInternalName, Float pBaseDamageAbsorption) {
        partAbsorptionRatios.put(pTargetArmorInternalName, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption (String pTargetArmorInternalName) {
        return partAbsorptionRatios.get(pTargetArmorInternalName);
    }

    @Override
    public HashMap<String, Float> getAllBaseDamageAbsorptionValues () {
        return partAbsorptionRatios;
    }




    public void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability) {
        partDurabilities.put(pTargetArmorInternalName, pBaseDurability);
    }

    public int getBaseDurability(String pTargetArmorInternalName) {
        if (partDurabilities.containsKey(pTargetArmorInternalName))
            return partDurabilities.get(pTargetArmorInternalName);

        return 100;
    }

    @Override
    public HashMap<String, Integer> getAllBaseDurabilityValues() {
        return partDurabilities;
    }


    public void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers) {
        partModifierCounts.put(pTargetArmorInternalName, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(String pTargetArmorInternalName) {
        return partModifierCounts.get(pTargetArmorInternalName);
    }

    @Override
    public HashMap<String, Integer> getAllMaxModifiersAmounts() {
        return partModifierCounts;
    }


    @Override
    public boolean getIsBaseArmorMaterial () {
        return isBaseMaterial;
    }

    @Override
    public float getMeltingPoint () {
        return meltingPoint;
    }


    @Override
    public float getHeatCoefficient () {
        return heatCoefficient;
    }

    @Override
    public IArmorPartRegistry getPartRegistry () {
        return MedievalAddonRegistry.getInstance();
    }


    @Override
    public IMaterialRenderInfo getRenderInfo () {
        return renderInfo;
    }

    @Override
    public IArmorMaterial setRenderInfo (IMaterialRenderInfo newInfo) {
        renderInfo = newInfo;
        return this;
    }

    @Override
    public String getTranslationKey () {
        return translationKey;
    }

    @Override
    public IArmorMaterial setTranslationKey (String key) {
        translationKey = key;
        return this;
    }

    @Override
    public TextFormatting getNameColor() {
        return ColorSampler.getChatMinecraftColorSample(renderInfo.getVertexColor());
    }
}
