package restclient;

public class ErrorInfo 
{
    public int ID  = 0;
    public String Name = null;
    public String Description = null;
    
    public ErrorInfo()
    {
        
    }
    
    public ErrorInfo(int ID, String Name, String Description)
    {
        super();
        
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
    }
}
