package restclient;

public class IGWSInformationResponse
{
    public int ErrorID = 0;
    public String UTC = "";
    public String Version  = "";
    public ErrorInfo[] ErrorCodes  = null;
    
    public IGWSInformationResponse()
    {
        
    }
    
    public IGWSInformationResponse(int ErrorID, String UTC, String Version, ErrorInfo[] ErrorCodes)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.UTC = UTC;
        this.Version = Version;
        this.ErrorCodes = ErrorCodes;
    }
}
