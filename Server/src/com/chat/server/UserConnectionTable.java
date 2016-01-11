package com.chat.server;

import com.chat.message.DisconnectMessage;
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
        System.out.println("User " + userData.getUsername() + " authenticated.");
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
        
        
        if(addUser(newData, user))
        {
            m_users.remove(oldData.getUsername());
            return true;
        }
        return false;
    }
    
    public synchronized boolean removeUser(UserData userData)
    {
        UserConnection user = m_users.remove(userData.getUsername());
        if(user != null)
        {
            broadcast(new DisconnectMessage(userData.getUsername()));
            System.out.println("User " + user.getUserData().getUsername() + " disconnected");
            return true;
        }
        return false;
    }
}
