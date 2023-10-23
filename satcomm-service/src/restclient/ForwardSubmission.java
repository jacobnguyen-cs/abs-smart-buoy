package restclient;

public class ForwardSubmission 
{
    public int ForwardMessageID = 0;
    public String DestinationID = "";
    public int ErrorID = 0;
    public long UserMessageID  = 0;
    public String ScheduledSendUTC = "";
    public int TerminalWakeupPeriod = 0;
    public int OTAMessageSize = 0;
    
    public ForwardSubmission()
    {
        
    }
    
    public ForwardSubmission(int ForwardMessageID, String DestinationID, int ErrorID, long UserMessageID, String ScheduledSendUTC,
            int TerminalWakeupPeriod, int OTAMessageSize)
    {
        super();
        
        this.ForwardMessageID = ForwardMessageID;
        this.DestinationID = DestinationID;
        this.ErrorID = ErrorID;
        this.UserMessageID = UserMessageID;
        this.ScheduledSendUTC = ScheduledSendUTC;
        this.TerminalWakeupPeriod = TerminalWakeupPeriod;
        this.OTAMessageSize = OTAMessageSize;
    }
}
