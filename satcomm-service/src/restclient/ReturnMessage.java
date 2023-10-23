package restclient;

public class ReturnMessage 
{
    public long ID = 0;
    public String MessageUTC = "";
    public String ReceiveUTC = "";
    public int SIN;
    public String MobileID;
    public byte[] RawPayload;
    public Message Payload = null;
    public String RegionName;
    public int OTAMessageSize;
    public int CustomerID;
    public int Transport;
    public int MobileOwnerID;
    
    public ReturnMessage()
    {
        
    }
    
    public ReturnMessage(long ID, String MessageUTC, String ReceiveUTC, int SIN, String MobileID, byte[] RawPayload,
            Message Payload, String RegionName, int OTAMessageSize, int CustomerID, int Transport, int MobileOwnerID)
    {
        super();
        
        this.ID = ID;
        this.MessageUTC = MessageUTC;
        this.ReceiveUTC = ReceiveUTC;
        this.SIN = SIN;
        this.MobileID = MobileID;
        this.RawPayload = RawPayload;
        this.Payload = Payload;
        this.RegionName = RegionName;
        this.OTAMessageSize = OTAMessageSize;
        this.CustomerID = CustomerID;
        this.Transport = Transport;
        this.MobileOwnerID = MobileOwnerID;
    }
}
