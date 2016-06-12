package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.armory.common.tileentity.state.FirePitState;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.util.client.TranslationKeys;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Marc on 25.12.2015.
 */
public class FirePitGuiManager extends TileStorageBasedGUIManager {

    private static final DecimalFormat laf = new DecimalFormat("###.##");
    private TileEntityFirePit tileEntityFirePit;

    public FirePitGuiManager (TileEntityFirePit tileEntityFirePit) {
        this.tileEntityFirePit = tileEntityFirePit;
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue (IGUIComponent component) {

        if (!( component instanceof ComponentProgressBar ))
            return 0F;

        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        if (component.getID().toLowerCase().contains("flame")){
            Float burningTime = (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);

            if (burningTime < 1F)
                return 0F;


            return burningTime / (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);
        }

        if (component.getID().toLowerCase().contains("mixingprogress"))
        {
            Float mixingprogress = (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.MIXINGPROGRESS);

            if (component.getID().endsWith("In.Left.Horizontal") || component.getID().endsWith("In.Right.Horizontal"))
            {
                if (mixingprogress <= 0F)
                    return 0F;

                if (mixingprogress >= 1F)
                    return 1F;

                return mixingprogress;
            }

            if (component.getID().endsWith("In.Left.Vertical") || component.getID().endsWith("In.Right.Vertical"))
            {
                if (mixingprogress <= 1F)
                    return 0F;

                if (mixingprogress >= 2F)
                    return 1F;

                return mixingprogress - 1F;
            }

            if (component.getID().endsWith("Out.Left.Vertical") || component.getID().endsWith("Out.Right.Vertical"))
            {
                if (mixingprogress <= 2F)
                    return 0F;

                if (mixingprogress >= 3F)
                    return 1F;

                return mixingprogress - 2F;
            }

            if (component.getID().endsWith("Out.Left.Horizontal") || component.getID().endsWith("Out.Right.Horizontal"))
            {
                if (mixingprogress <= 3F)
                    return 0F;

                if (mixingprogress >= 4F)
                    return 1F;

                return mixingprogress - 3F;
            }
        }

        return 0F;
    }

    /**
     * Method used by components that support overriding tooltips to grab the new tooltip string.
     *
     * @param component The component requesting the override.
     *
     * @return A string displayed as tooltip for the IGUIComponent during rendering.
     */
    @Override
    public String getCustomToolTipDisplayString (IGUIComponent component) {
        if (!( component instanceof ComponentProgressBar ))
            return "";

        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        if (component.getID().toLowerCase().contains("mixingprogress"))
        {
            Float mixingprogress = (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.MIXINGPROGRESS);

            if (mixingprogress <= 0F)
                return I18n.translateToLocal(TranslationKeys.GUI.PROGRESS) + ": 0 %";

            if (mixingprogress >= 4F)
                return I18n.translateToLocal(TranslationKeys.GUI.PROGRESS) + ": 100 %";

            return I18n.translateToLocal(TranslationKeys.GUI.PROGRESS) + ": " + Math.round(mixingprogress / 4F) + " %";
        }

        return "";
    }

    @Override
    public int getTotalTankContents (IGUIComponent component) {
        return tileEntityFirePit.getTankSize();
    }

    /**
     * Method used by the rendering system to dynamically update a Label.
     *
     * @param component The component requesting the content.
     *
     * @return THe string that should be displayed.
     */
    @Override
    public String getLabelContents (IGUIComponent component) {
        if(component.getID().endsWith(".CurrentTemperature"))
        {
            return Math.floor(getCurrentTemperature()) + " C";
        }
        else if(component.getID().endsWith(".MaxTemperature"))
        {
            return Math.floor(getMaxTemperature()) + " C";
        }
        else if(component.getID().endsWith(".LastChange"))
        {
            return laf.format(getLastAddedHeat()) + " C";
        }

        return super.getLabelContents(component);
    }

    @Override
    public ArrayList<FluidStack> getTankContents (IGUIComponent component) {
        return tileEntityFirePit.getAllFluids();
    }

    public float getLastAddedHeat () {
        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        return state.getLastAddedHeat();
    }

    public float getLastTemperature () {
        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        return state.getLastTemperature();
    }

    public float getMaxTemperature () {
        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        return state.getMaxTemperature();
    }

    public float getCurrentTemperature () {
        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        return state.getCurrentTemperature();
    }

    public Float getMixingProgress()
    {
        FirePitState state = (FirePitState) tileEntityFirePit.getState();

        return state.getMixingProgress();
    }
}
