/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.message;

import com.chat.packet.Packet;
import com.chat.packet.PacketType;

/**
 *  Base class for messages send from server to client.
 *
 * @author Relu
 */
public class Message implements Packet
{
    private PacketType m_type;
    
    public Message(PacketType type)
    {
        m_type = type;
    }
    
    @Override
    public PacketType getType()
    {
        return m_type;
    }
}
