package restclient;

public class ForwardSubmissionRequest 
{
    public String access_id = "";
    public String password = "";
    public ForwardMessage[] messages = null;
    
    public ForwardSubmissionRequest()
    {
        
    }
    
    public ForwardSubmissionRequest(String access_id, String password, ForwardMessage[] messages)
    {
        super();
        
        this.access_id = access_id;
        this.password = password;
        this.messages = messages;
    }
}
