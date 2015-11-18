/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Knowledge;

import net.minecraft.item.ItemStack;

public interface IBluePrintItem {

    String getBlueprintID(ItemStack pStack);

    float getBluePrintQuality(ItemStack pStack);

    void setBluePrintQuality(ItemStack pStack, float pNewQuality);

    String getTranslatedBluePrintQuality(ItemStack pStack);
}