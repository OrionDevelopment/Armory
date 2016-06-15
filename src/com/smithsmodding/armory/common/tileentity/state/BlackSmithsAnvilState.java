package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.common.tileentity.*;
import com.smithsmodding.smithscore.common.tileentity.state.*;
import net.minecraft.nbt.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlackSmithsAnvilState implements ITileEntityState {

    float craftingprogress;
    IAnvilMaterial material;
    TileEntityBlackSmithsAnvil anvil;

    /**
     * Method called when this state get attached to a TE. Allows it to store a reference or modify values of the TE.
     *
     * @param tileEntitySmithsCore The TE this state got attached to.
     */
    @Override
    public void onStateCreated (TileEntitySmithsCore tileEntitySmithsCore) {
        this.anvil = (TileEntityBlackSmithsAnvil) tileEntitySmithsCore;
    }

    /**
     * Called to indicate this TE that some of its values may have been updated. Use it to perform additional
     * calculation on this data.
     */
    @Override
    public void onStateUpdated () {

    }

    /**
     * Method called by the Attached TE to indicate that it is being detached and discarded by its TE. Allows you to
     * handle the disconnect from the state gracefully.
     */
    @Override
    public void onStateDestroyed () {

    }

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets
     * written to disk.
     *
     * @return True when the state needs storing, false when not.
     */
    @Override
    public boolean requiresNBTStorage () {
        return true;
    }

    /**
     * Method that allows this state to read its data from Disk, when the attached TE gets loaded.
     *
     * @param stateData The stored data of this state.
     */
    @Override
    public void readFromNBTTagCompound (NBTBase stateData) {
        try{
            this.material = AnvilMaterialRegistry.getInstance().getAnvilMaterial(((NBTTagCompound) stateData).getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL));
            this.craftingprogress = ((NBTTagCompound) stateData).getFloat(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS);
        }
        catch (Exception ex)
        {
            this.material = AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.IRON);
            this.craftingprogress = 0F;
        }
    }

    /**
     * Method that allows this state to writes its data to Disk, when the attached TE writes its data to disk.
     *
     * @return A NBTBase that describes this state.
     */
    @Override
    public NBTBase writeToNBTTagCompound () {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, material.getID());
        compound.setFloat(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS, craftingprogress);

        return compound;
    }

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets used
     * to synchronise the TE.
     *
     * @return True when the state needs storing, false when not.
     */
    @Override
    public boolean requiresSynchronization () {
        return true;
    }

    public TileEntityBlackSmithsAnvil getAnvil () {
        return anvil;
    }

    /**
     * Method that allows this state to read its data from the network, when the attached TE gets synchronized.
     *
     * @param stateData The stored data of this state.
     */
    @Override
    public void readFromSynchronizationCompound (NBTBase stateData) {
        readFromNBTTagCompound(stateData);
    }

    /**
     * Method that allows this state to writes its data to the network, when the attached TE gets synchronized.
     *
     * @return A NBTBase that describes this state.
     */
    @Override
    public NBTBase writeToSynchronizationCompound () {
        return writeToNBTTagCompound();
    }

    public IAnvilMaterial getMaterial () {
        return material;
    }

    public void setMaterial (IAnvilMaterial material) {
        this.material = material;
    }

    public float getCraftingprogress () {
        return craftingprogress;
    }

    public void setCraftingprogress (float craftingprogress) {
        this.craftingprogress = craftingprogress;
    }


}
