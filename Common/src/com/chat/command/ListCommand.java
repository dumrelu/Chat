package com.chat.command;

import com.chat.packet.PacketType;

/**
 *  Request a list of all the connected users.
 * 
 * @author Relu
 */
public class ListCommand extends Command
{

    public ListCommand()
    {
        super(PacketType.COMMAND_LIST);
    }
    
}
