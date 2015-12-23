package com.chat.command;

import com.chat.packet.PacketType;
import com.chat.user.UserData;

/**
 *  Tells the server to update the user data.
 *
 * @author Relu
 */
public class UpdateCommand extends Command
{
    private final UserData m_userData;

    public UpdateCommand(UserData userData) 
    {
        super(PacketType.COMMAND_UPDATE);
        m_userData = userData;
    }
    
    public UserData getUserData()
    {
        return m_userData;
    }
}
