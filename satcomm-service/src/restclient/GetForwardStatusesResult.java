package restclient;

public class GetForwardStatusesResult
{
    public int ErrorID = 0;
    public String NextStartUTC = null;
    public ForwardStatus[] Statuses = null;
    
    public GetForwardStatusesResult()
    {
        
    }
    
    public GetForwardStatusesResult(int ErrorID, String NextStartUTC, ForwardStatus[] Statuses)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.NextStartUTC = NextStartUTC;
        this.Statuses = Statuses;
    }
}