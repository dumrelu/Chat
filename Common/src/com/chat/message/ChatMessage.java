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
    private final String m_message;
    private final Date m_date;
    private final boolean m_broadcast;

    public ChatMessage(String source, String message, Date date, boolean broadcast) 
    {
        super(PacketType.MESSAGE_CHAT);
        m_source = source;
        m_message = message;
        m_date = date;
        m_broadcast = broadcast;
    }
    
    public ChatMessage(String source, String message, Date date)
    {
        this(source, message, date, false);
    }
    
    public String getSource()
    {
        return m_source;
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
        return m_broadcast;
    }
    
    public boolean isPrivate()
    {
        return !m_broadcast;
    }
}
