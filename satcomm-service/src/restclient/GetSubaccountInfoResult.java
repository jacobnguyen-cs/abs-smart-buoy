package restclient;

public class GetSubaccountInfoResult 
{
    public int ErrorID = 0;
    public SubaccountInfo[] Subaccounts = null;
    
    public GetSubaccountInfoResult()
    {
        
    }
    
    public GetSubaccountInfoResult(int ErrorID, SubaccountInfo[] Subaccounts)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.Subaccounts = Subaccounts;
    }
}
