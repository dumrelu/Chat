package com.chat.packet.io;

import com.chat.packet.Packet;

/**
 *  The onPacketReceived method is called when a packet
 * is received.
 * 
 * @author Relu
 */
public interface PacketReceiverSubscriber 
{
    public void onPacketReceived(Packet packet);
}
