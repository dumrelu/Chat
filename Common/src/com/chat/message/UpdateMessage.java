package com.chat.message;

import com.chat.packet.PacketType;
import com.chat.user.UserData;

/**
 *  A user updated its data.
 * 
 * @author Relu
 */
public class UpdateMessage extends Message
{
    private final String m_oldUsername;
    private final UserData m_userData;
    
    public UpdateMessage(String oldUsername, UserData userData)
    {
        super(PacketType.MESSAGE_UPDATE);
        m_oldUsername = oldUsername;
        m_userData = userData;
    }
    
    public String getOldUsername()
    {
        return m_oldUsername;
    }
    
    public UserData getUserData()
    {
        return m_userData;
    }
}
