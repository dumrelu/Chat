package com.chat.server;

import com.chat.packet.Packet;
import com.chat.user.UserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Holds all the connected users.
 *
 * @author Relu
 */
public class UserConnectionTable 
{
    private Map<String, UserConnection> m_users;

    public UserConnectionTable() 
    {
        m_users = new HashMap<>();
    }
    
    public synchronized boolean addUser(UserData userData, UserConnection user)
    {
        if(userData == null || userData.getUsername() == null || "".equals(userData.getUsername()))
            return false;
        
        Object result = m_users.get(userData.getUsername());
        if(result != null)
            return false;
        
        m_users.put(userData.getUsername(), user);
        user.setUserData(userData);
        return true;
    }
    
    public synchronized List<UserConnection> getUsers()
    {
        return new ArrayList<>(m_users.values());
    }
    
    public synchronized boolean send(String username, Packet packet)
    {
        UserConnection user = m_users.get(username);
        if(user == null)
            return false;
        
        user.getPacketSender().send(packet);
        return true;
    }
    
    public synchronized void broadcast(Packet packet)
    {
        for (Map.Entry<String, UserConnection> entry : m_users.entrySet())
        {
            entry.getValue().getPacketSender().send(packet);
        }
    }
    
    public synchronized boolean update(UserData oldData, UserData newData, UserConnection user)
    {
        Object result = m_users.get(oldData.getUsername());
        if(result == null)
            return false;
        
        m_users.remove(oldData.getUsername());
        if(newData == null || newData.getUsername() == null || "".equals(newData.getUsername()))
            return false;
        
        m_users.put(newData.getUsername(), user);
        user.setUserData(newData);
        return true;
    }
    
    public synchronized boolean removeUser(UserData userData)
    {
        return m_users.remove(userData.getUsername()) != null;
    }
}
