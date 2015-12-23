package com.chat.command;

import com.chat.packet.PacketType;

/**
 *  Tell the server to disconnect the user.
 *
 * @author Relu
 */
public class QuitCommand extends Command
{

    public QuitCommand()
    {
        super(PacketType.COMMAND_QUIT);
    }
    
}
