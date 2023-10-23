package restclient;

public class Message 
{
    public String Name = "";
    public int SIN = 0;
    public int MIN = 0;
    public String IsForward = "";
    //public ArrayOfField Fields = null;
    public Field[] Fields = null;
    
    public Message()
    {
        
    }
    
    //public Message(String Name, int SIN, int MIN, /*String IsForward,*/ ArrayOfField Fields)
    public Message(String Name, int SIN, int MIN, String IsForward, Field[] Fields)
    {
        super();
        
        this.Fields = Fields;
        this.Name = Name;
        this.SIN = SIN;
        this.MIN = MIN;
        this.IsForward = IsForward;
    }
}
