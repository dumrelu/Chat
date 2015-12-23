package com.chat.command;

import com.chat.packet.Packet;
import com.chat.packet.PacketType;

/**
 *  Base class for all the command packets.
 * 
 * @author Relu
 */
public class Command implements Packet
{
    private final PacketType m_type;

    public Command(PacketType m_type) 
    {
        this.m_type = m_type;
    }

    @Override
    public PacketType getType() 
    {
        return m_type;
    }

}
