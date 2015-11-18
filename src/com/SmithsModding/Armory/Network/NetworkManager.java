package com.SmithsModding.Armory.Network;
/*
 *   NetworkManager
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import com.SmithsModding.Armory.Network.Handlers.*;
import com.SmithsModding.Armory.Network.Messages.*;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkManager {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.General.MOD_ID.toLowerCase());

    public static void Init() {
        INSTANCE.registerMessage(MessageHandlerTileEntityFirePit.class, MessageTileEntityFirePit.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerTileEntityHeater.class, MessageTileEntityHeater.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerTileEntityArmorsAnvil.class, MessageTileEntityArmorsAnvil.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerCustomInput.class, MessageCustomInput.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MessageHandlerKnowledgeUpdate.class, MessageKnowledgeUpdate.class, 4, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerTileEntityBookBinder.class, MessageTileEntityBookBinder.class, 5, Side.CLIENT);
    }
}