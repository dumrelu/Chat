package com.chat.command;

import com.chat.packet.PacketType;
import java.util.Date;

/**
 * Command for sending messages.
 *
 * @author Relu
 */
public class MessageCommand extends Command
{
    private String m_destination;
    private String m_message;
    private Date m_date;

    public MessageCommand(String destination, String message) 
    {
        super(PacketType.COMMAND_MSG);
        m_destination = destination;
        m_message = message;
        m_date = new Date();
    }
    
    public MessageCommand(String message)
    {
        this(null, message);
    }
    
    public String getDestination()
    {
        return m_destination;
    }
    
    public String getMessage()
    {
        return m_message;
    }
    
    public Date getDate()
    {
        return m_date;
    }
    
    public boolean isPrivate()
    {
        return m_destination != null;
    }
    
    public boolean isBroadcast()
    {
        return m_destination == null;
    }
}
