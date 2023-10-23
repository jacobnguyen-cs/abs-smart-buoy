package restclient;

public class ForwardStatus
{
    public int ForwardMessageID = 0;
    public boolean IsClosed = false;
    public int State = 0;
    public String StateUTC = null;
    public int ErrorID = 0;
    public int ReferenceNumber=0;
    public String Transport;
    
    public ForwardStatus()
    {
        
    }
    
    public ForwardStatus(int ForwardMessageID, boolean IsClosed, int State, String StateUTC, int ErrorID, int ReferenceNumber, String Transport )
    {
        super();
        
        this.ErrorID = ErrorID;
        this.ForwardMessageID = ForwardMessageID;
        this.IsClosed = IsClosed;
        this.State = State;
        this.StateUTC = StateUTC;
        this.Transport = Transport;
        this.ReferenceNumber = ReferenceNumber;
    }
} 