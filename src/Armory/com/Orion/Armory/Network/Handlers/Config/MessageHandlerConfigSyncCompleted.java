package com.Orion.Armory.Network.Handlers.Config;

import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Network.Messages.Config.MessageConfigSyncCompleted;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:26
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerConfigSyncCompleted implements IMessageHandler<MessageConfigSyncCompleted, IMessage> {

    @Override
    public IMessage onMessage(MessageConfigSyncCompleted message, MessageContext ctx) {
        TileEntityArmorsAnvil.clearAllStoredRecipes();
        ArmoryInitializer.MedievalInitialization.initializeAnvilRecipes();

        return null;
    }
}