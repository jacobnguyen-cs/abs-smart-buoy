package restclient;

public class GetReturnMessagesResult 
{
    public int ErrorID = 0;
    public String NextStartUTC = null;
    public ReturnMessage[] Messages = null;
    
    public GetReturnMessagesResult()
    {
        
    }
    
    public GetReturnMessagesResult(int ErrorID, String NextStartUTC, ReturnMessage[] Messages)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.NextStartUTC = NextStartUTC;
        this.Messages = Messages;
    }
}
