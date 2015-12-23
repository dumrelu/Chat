package com.chat.message;

import com.chat.packet.PacketType;
import com.chat.user.UserData;

/**
 *  A user connected to the server.
 * 
 * @author Relu
 */
public class ConnectMessage extends Message
{
    private final UserData m_userData;
    
    public ConnectMessage(UserData userData) 
    {
        super(PacketType.MESSAGE_CONNECT);
        m_userData = userData;
    }
    
    public UserData getUserData()
    {
        return m_userData;
    }
}
