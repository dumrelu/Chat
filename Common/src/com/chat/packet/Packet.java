package com.chat.packet;

import java.io.Serializable;

/**
 *  The interface the client and server use to 
 * send and receive data.
 * 
 * @author Relu
 */
public interface Packet extends Serializable
{
    // Returns the type of the packet.
    public PacketType getType();
}
