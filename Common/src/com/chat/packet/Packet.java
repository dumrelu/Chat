package com.chat.packet;

/**
 *  The interface the client and server use to 
 * send and receive data.
 * 
 * @author Relu
 */
public interface Packet 
{
    // Returns the type of the packet.
    public PacketType getType();
}
