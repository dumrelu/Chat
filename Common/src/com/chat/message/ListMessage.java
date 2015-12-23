package com.chat.message;

import com.chat.packet.PacketType;
import com.chat.user.UserData;
import java.util.List;

/**
 *
 * @author Relu
 */
public class ListMessage extends Message
{
    private List<UserData> m_users;

    public ListMessage(List<UserData> users) 
    {
        super(PacketType.MESSAGE_LIST);
        m_users = users;
    }
    
    public List<UserData> getUsers()
    {
        return m_users;
    }
}
