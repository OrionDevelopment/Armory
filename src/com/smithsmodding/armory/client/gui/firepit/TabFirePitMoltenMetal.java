package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.armory.util.client.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.client.gui.tabs.implementations.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 25.01.2016.
 */
public class TabFirePitMoltenMetal extends CoreTab {

    GuiFirePit firePit;

    public TabFirePitMoltenMetal (String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiFirePit) root;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, host, new Coordinate2D(0, 0), GuiFirePit.GUI.getWidth(), 111, com.smithsmodding.armory.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 107), com.smithsmodding.armory.util.client.Colors.DEFAULT, ( (ContainerSmithsCore) firePit.inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALSLEFT, host, new CoreComponentState(), new Coordinate2D(7, 7), 20, 80, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALSRIGHT, host, new CoreComponentState(), new Coordinate2D(GuiFirePit.GUI.getWidth() - 7 - 20, 7), 20, 80, ComponentOrientation.VERTICALBOTTOMTOTOP));

        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.INFUSIONSTACKSBACKGROUND, host, new Coordinate2D(53, 33), 10 + 3 * 18 + 6, 18 + 10, new MinecraftColor(MinecraftColor.WHITE), ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));

        for (int tSlotIndex = 0; tSlotIndex < ( TileEntityFirePit.INFUSIONSTACK_AMOUNT ); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ( (ContainerSmithsCore) firePit.inventorySlots ).getContainerInventory().getIInventoryWrapper(), null), host, new Coordinate2D(slot.xDisplayPosition - 1, slot.yDisplayPosition - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.util.client.Colors.DEFAULT));
        }

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGINLEFTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(29, 7), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGINLEFTVERTICAL, host, new CoreComponentState(), new Coordinate2D(70, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGINRIGHTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(98, 7), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGINRIGHTVERTICAL, host, new CoreComponentState(), new Coordinate2D(91, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGOUTLEFTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(29, 71), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.HORIZONTALHEADRIGHTTOLEFTEMPTY, Textures.Gui.Basic.Components.HORIZONTALHEADRIGHTTOLEFTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGOUTLEFTVERTICAL, host, new CoreComponentState(), new Coordinate2D(76, 61), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMLEFTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMLEFTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGOUTRIGHTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(98, 71), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.HORIZONTALHEADLEFTTORIGHTEMPTY, Textures.Gui.Basic.Components.HORIZONTALHEADLEFTTORIGHTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGOUTRIGHTVERTICAL, host, new CoreComponentState(), new Coordinate2D(97, 61), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTORFULL));
    }
}