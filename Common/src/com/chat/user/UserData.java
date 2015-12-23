package com.chat.user;

import java.io.Serializable;

/**
 *  Holds data about a user.
 * 
 * @author Relu
 * @todo: Other user data.
 */
public class UserData implements Serializable
{
    private String m_username;

    public UserData(String username) 
    {
        m_username = username;
    }
    
    public void setUsername(String username)
    {
        m_username = username;
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
