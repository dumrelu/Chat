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

    public ChatMessage(String source, String message, Date date) 
    {
        super(PacketType.MESSAGE_CHAT);
        m_source = source;
        m_message = message;
        m_date = date;
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
}
