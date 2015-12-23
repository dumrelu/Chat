package com.chat.message;

import com.chat.packet.PacketType;

/**
 *  An error has occured when executing a command.
 *
 * @author Relu
 */
public class ErrorMessage extends Message 
{
    private String m_errorMessage;

    public ErrorMessage(String errorMessage) 
    {
        super(PacketType.MESSAGE_ERROR);
    }
    
    public String getErrorMessage()
    {
        return m_errorMessage;
    }
}
