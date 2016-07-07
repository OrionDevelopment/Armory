package com.smithsmodding.armory.common.event.config;

import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:25
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ConfigSyncCompletedEvent extends StandardNetworkableEvent {
    public ConfigSyncCompletedEvent() {
    }

    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        //NOOP Notification Event
    }

    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        //NOOP Notification Event
    }
}
