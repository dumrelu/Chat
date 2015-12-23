package com.chat.client;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketSender;
import com.chat.user.UserData;

/**
 *
 * @author Relu
 */
public class CommandParser 
{
    private PacketSender m_sender;
    private PacketReceiver m_receiver;
    private String m_lastCommand;
    
    public CommandParser(PacketSender sender, PacketReceiver receiver)
    {
        m_sender = sender;
        m_receiver = receiver;
    }
    
    public void process(String line)
    {
        if(!line.startsWith("/"))
        {
            m_sender.send(new MessageCommand(line));
            m_lastCommand = "bcast";
            return;
        }
        
        if(line.length() < 2)
            return;
        
        int spaceIdx = line.indexOf(" ");
        if(spaceIdx < 0)
            spaceIdx = line.length();
        String command = line.substring(1, spaceIdx);
        switch(command)
        {
            case "connect":
                m_sender.send(new ConnectCommand(new UserData(line.substring(spaceIdx+1))));
                break;
            case "list":
                m_sender.send(new ListCommand());
                break;
            case "msg":
                int nextSpaceIdx = line.indexOf(" ", spaceIdx+1);
                m_sender.send(new MessageCommand(line.substring(spaceIdx+1, nextSpaceIdx), line.substring(nextSpaceIdx+1)));
                break;
        }
        
        m_lastCommand = command;
    }
    
    public String getLastCommand()
    {
        return m_lastCommand;
    }
}
