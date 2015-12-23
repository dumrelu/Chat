package com.chat.test;

import com.chat.packet.Packet;
import com.chat.packet.PacketType;


public class TestPacket implements Packet
{
    private final String m_testMessage;
    
    public TestPacket(String testMessage)
    {
        m_testMessage = testMessage;
    }
    
    @Override
    public PacketType getType() 
    {
        return PacketType.MESSAGE_CHAT;
    }
    
    public String getTestMessage()
    {
        return m_testMessage;
    }
}
