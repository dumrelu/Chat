package com.chat.packet.io;

import com.chat.packet.Packet;

/**
 *  The onPacketSent method is called when the packet
 * was sent.
 * 
 * @author Relu
 */
public interface PacketSenderSubscriber 
{
    public void onPacketSent(Packet packet);
}
