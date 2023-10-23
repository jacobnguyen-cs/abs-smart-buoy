package restclient;

public class SubmitMessagesResult
{
    public int ErrorID = 0;
    public ForwardSubmission[] Submissions = null;
    
    public SubmitMessagesResult()
    {
        
    }
    
    public SubmitMessagesResult(int ErrorID, ForwardSubmission[] Submissions)
    {
        super();
        
        this.ErrorID = ErrorID;
        this.Submissions = Submissions;
    }
}
