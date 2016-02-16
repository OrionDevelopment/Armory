package com.smithsmodding.armory.common.inventory.slots;
/*
 *   SlotHeatable
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.smithsmodding.armory.common.item.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.smithscore.common.slots.*;
import com.smithsmodding.smithscore.common.tileentity.Capabilities.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class SlotHeatable extends SlotSmithsCore {

    public SlotHeatable (ISmithsCoreItemHandler pInventory, int pSlotIndex, int pXLocation, int pYLocation) {
        super(pInventory, pSlotIndex, pXLocation, pYLocation);
    }

    @Override
    public boolean isItemValid (ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            return true;
        }
        return HeatableItemRegistry.getInstance().isHeatable(pItemStack);
    }

    @Override
    public int getSlotStackLimit () {
        return 1;
    }
}
