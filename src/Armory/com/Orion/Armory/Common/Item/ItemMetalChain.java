package com.Orion.Armory.Common.Item;
/*
 *   ItemMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.Orion.Armory.Common.Armor.TierMedieval.ArmorMaterialMedieval;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemMetalChain extends Item
{
    private HashMap<String, CustomResource> iResources = new HashMap<String, CustomResource>();

    public ItemMetalChain()
    {
        this.setMaxStackSize(16);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalChain);
    }

    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        Iterator tResourceIterator = iResources.entrySet().iterator();
        while (tResourceIterator.hasNext()) {
            Map.Entry<String, CustomResource> tResource = (Map.Entry<String, CustomResource>) tResourceIterator.next();
            tResource.getValue().addIcon(pIconRegister.registerIcon(tResource.getValue().getIconLocation()));
        }
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        String tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);
        return iResources.get(tMaterialID).getIcon();
    }

    //Function to get the amount of RenderPasses. Used in the vanilla rendering of the itemstack.
    @Override
    public int getRenderPasses(int pMeta) {
        return 1;
    }

    //Function that tells the renderer to use multiple layers
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    //Function that tells the renderer to use a certain color
    @Override
    public int getColorFromItemStack(ItemStack pStack, int pRenderPass) {
        String tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);
        return iResources.get(tMaterialID).getColor().getColor();
    }

    ///#############################################Resource control functions##########################################
    //Function for registering a new resource the Item may need to render it
    public void registerResource(CustomResource pResource) {
        iResources.put(pResource.getInternalName(), pResource);
    }

    //Returns the resource (if registered, else null) depending on the given InternalName (which is registered as its ID)
    public CustomResource getResource(String pResourceID) {
        return iResources.get(pResourceID);
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks)
    {
        for(ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values()){
            ItemStack tChainStack = new ItemStack(MedievalRegistry.iMetalChain, 1);

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.RingMaterial, tMaterial.iInternalName);
            tChainStack.setTagCompound(tStackCompound);

            pItemStacks.add(tChainStack);
        }
    }
}
