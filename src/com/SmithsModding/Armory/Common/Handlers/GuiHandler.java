package com.smithsmodding.Armory.Common.Handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */


import com.smithsmodding.Armory.Client.Gui.*;
import com.smithsmodding.Armory.Common.Inventory.*;
import com.smithsmodding.Armory.Common.TileEntity.*;
import com.smithsmodding.Armory.Util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.*;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement (int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID) {
            return new ContainerFirepit(pPlayer, (TileEntityFirePit) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement (int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID) {
            return new GuiFirePit(new ContainerFirepit(pPlayer, (TileEntityFirePit) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        return null;
    }
}
