package com.chat.command;

import com.chat.packet.PacketType;

/**
 *  Command send by the user to connect to the server.
 * 
 * @author Relu
 */
public class ConnectCommand extends Command
{
    private final String m_username;
    
    public ConnectCommand(String username) 
    {
        super(PacketType.COMMAND_CONNECT);
        m_username = username;
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
