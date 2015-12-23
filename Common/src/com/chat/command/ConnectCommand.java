package com.chat.command;

import com.chat.packet.PacketType;
import com.chat.user.UserData;

/**
 *  Command send by the user to connect to the server.
 * 
 * @author Relu
 */
public class ConnectCommand extends Command
{
    private final UserData m_userData;
    
    public ConnectCommand(UserData username) 
    {
        super(PacketType.COMMAND_CONNECT);
        m_userData = username;
    }
    
    public UserData getUsername()
    {
        return m_userData;
    }
}
