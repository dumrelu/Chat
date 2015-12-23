package com.chat.message;

import com.chat.packet.PacketType;

/**
 *  A user disconnected from the server.
 * 
 * @author Relu
 */
public class DisconnectMessage extends Message 
{
    private final String m_username;

    public DisconnectMessage(String username) 
    {
        super(PacketType.MESSAGE_DISCONNECT);
        m_username = username;
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
