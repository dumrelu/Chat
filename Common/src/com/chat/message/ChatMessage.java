package com.chat.message;

import com.chat.packet.PacketType;
import java.util.Date;

/**
 *  Message for the user.
 *
 * @author Relu
 */
public class ChatMessage extends Message
{
    private final String m_source;
    private final String m_destination;
    private final String m_message;
    private final Date m_date;

    public ChatMessage(String source, String destination, String message, Date date) 
    {
        super(PacketType.MESSAGE_CHAT);
        m_source = source;
        m_destination = destination;
        m_message = message;
        m_date = date;
    }
    
    public ChatMessage(String source, String message, Date date)
    {
        this(source, null, message, date);
    }
    
    public String getSource()
    {
        return m_source;
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
    
    public boolean isBroadcast()
    {
        return m_destination == null;
    }
    
    public boolean isPrivate()
    {
        return !isBroadcast();
    }
}
