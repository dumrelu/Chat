package com.chat.client;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.command.QuitCommand;
import com.chat.command.UpdateCommand;
import com.chat.packet.io.PacketSender;
import com.chat.user.UserData;

/**
 *
 * @author Relu
 */
public class CommandParser 
{
    private final PacketSender m_sender;
    private String m_lastCommand;
    
    public CommandParser(PacketSender sender)
    {
        m_sender = sender;
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
            case "bcast":
                m_sender.send(new MessageCommand(line.substring(spaceIdx+1)));
                break;
            case "nick":
                m_sender.send(new UpdateCommand(new UserData(line.substring(spaceIdx+1))));
                break;
            case "quit":
                m_sender.send(new QuitCommand());
                break;
        }
        
        m_lastCommand = command;
    }
    
    public String getLastCommand()
    {
        return m_lastCommand;
    }
}
