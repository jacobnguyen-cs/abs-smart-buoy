package restclient;

import java.util.ArrayList;

public class ForwardMessage 
{
    public String DestinationID = "";
    public long UserMessageID = 0;
    public ArrayList<Byte> RawPayload = null;
    public Message Payload = null;
    public String TransportType = "";
    public DelayedSend SendOptions  = null;
    
    public ForwardMessage()
    {
        
    }
    
    public ForwardMessage(String DestinationID, long UserMessageID, ArrayList<Byte> RawPayload,
            Message Payload, String TransportType, DelayedSend SendOptions)
    {
        super();
        
        this.DestinationID = DestinationID;
        this.UserMessageID = UserMessageID;
        this.RawPayload = RawPayload;
        this.Payload = Payload;
        this.TransportType = TransportType;
        this.SendOptions = SendOptions;
    }
}


