package restclient;

public class DelayedSend 
{
    public boolean SatelliteSendOnReceive = false;
    public String MessageExpireUTC = "";
    
    public DelayedSend()
    {
        
    }
    
    public DelayedSend(boolean SatelliteSendOnReceive, String MessageExpireUTC )
    {
        super();
        
        this.SatelliteSendOnReceive = SatelliteSendOnReceive;
        this.MessageExpireUTC = MessageExpireUTC;
    }
}
