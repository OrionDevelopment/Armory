package com.smithsmodding.armory.common.item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;

public class ItemHeatedItem extends Item {

    public ItemHeatedItem () {
        setMaxStackSize(1);
        setCreativeTab(GeneralRegistry.CreativeTabs.heatedItemTab);
        setUnlocalizedName(References.InternalNames.Items.ItemHeatedIngot);
        this.setRegistryName(References.General.MOD_ID, References.InternalNames.Items.ItemHeatedIngot);
    }

    public boolean areStacksEqualExceptTemp (ItemStack pFirstStack, ItemStack pSecondStack) {
        if (!( pFirstStack.getItem() instanceof ItemHeatedItem )) {
            return false;
        }

        if (!( pSecondStack.getItem() instanceof ItemHeatedItem )) {
            return false;
        }

        if (!pFirstStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE).equals(pSecondStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE)))
            return false;

        return pFirstStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID).equals(pSecondStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));

    }

    @Override
    public boolean showDurabilityBar (ItemStack pStack) {
        return false;
    }

    @Override
    public double getDurabilityForDisplay (ItemStack pStack) {
        return ( pStack.getTagCompound().getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE) ) / ( MaterialRegistry.getInstance().getMaterial(pStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID)).getMeltingPoint() );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation (ItemStack pStack, EntityPlayer pPlayer, List pTooltipList, boolean pBoolean) {
        String tTemperatureLine = I18n.translateToLocal(TranslationKeys.Items.HeatedIngot.TemperatureTag);
        tTemperatureLine = tTemperatureLine + ": " + Math.round(HeatableItemRegistry.getInstance().getItemTemperature(pStack));

        pTooltipList.add(tTemperatureLine);
    }

    @Override
    public boolean getHasSubtypes () {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems (Item pItem, CreativeTabs pCreativeTab, List pItemStacks) {
        Iterator<ItemStack> tStackIter = HeatableItemRegistry.getInstance().getAllMappedItems().iterator();

        while (tStackIter.hasNext()) {
            ItemStack tCooledStack = tStackIter.next();
            ItemStack tHeatedStack = HeatedItemFactory.getInstance().convertToHeatedIngot(tCooledStack);

            if (tHeatedStack != null) {
                pItemStacks.add(tHeatedStack);
            } else {
                Armory.getLogger().info("Tried to create a HeatedIngot from: " + HeatableItemRegistry.getInstance().getMaterialFromCooledStack(tCooledStack).getUniqueID() + " and failed!");
            }
        }
    }

    @Override
    public String getItemStackDisplayName (ItemStack pStack) {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getItemStackDisplayName(tOriginalItemStack);
    }


    /**
     @Override public void onUpdate(ItemStack pStack, World pWorldObj, entity pEntity, int pSlotIndex, boolean pSelected) {

     if (!(pEntity instanceof EntityPlayer))
     return;

     if (!ArmoryConfig.enableTemperatureDecay)
     return;

     EntityPlayer tPlayer = (EntityPlayer) pEntity;

     float tCurrentTemp = getItemTemperature(pStack);
     float tNewTemp = tCurrentTemp - 0.2F;

     setItemTemperature(pStack, tNewTemp);

     if (tNewTemp < 20F) {
     tPlayer.inventory.mainInventory[pSlotIndex] = HeatedItemFactory.getInstance().convertToCooledIngot(pStack);
     } else {
     for (ItemStack tStack : tPlayer.inventory.mainInventory) {
     if (tStack == null)
     continue;

     if (tStack.getItem() instanceof ItemTongs)
     return;
     }

     tPlayer.setFire(1);
     }
     }

     */
}

