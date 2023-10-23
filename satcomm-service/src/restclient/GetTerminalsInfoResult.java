package restclient;

public class GetTerminalsInfoResult 
{
    public int ErrorID  = 0;
    public TerminalInfo[] Terminals = null;
    
    public GetTerminalsInfoResult()
    {
        
    }
    
    public GetTerminalsInfoResult(int ErrorID, TerminalInfo[] Terminals)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.Terminals = Terminals;
    }
}
