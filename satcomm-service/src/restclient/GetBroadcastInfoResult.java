package restclient;

public class GetBroadcastInfoResult 
{
    public int ErrorID  = 0;
    public BroadcastInfo[] BroadcastInfos = null;
    
    public GetBroadcastInfoResult()
    {
        
    }
    
    public GetBroadcastInfoResult(int ErrorID, BroadcastInfo[] BroadcastInfos)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.BroadcastInfos = BroadcastInfos;
    }
}
