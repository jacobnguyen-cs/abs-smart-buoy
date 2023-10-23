package restclient;

public class Field 
{
    public Element[] Elements = null;
    public Message Message = null;
    public String Name = "";
    public String Value = "";
    public String Type = "";
    
    public Field()
    {
        
    }
    
    public Field(Element[] Elements, Message Message, String Name, String Value, String Type)
    //public Field(String Name, String Value, String Type)
    {
        super();
        
        this.Elements = Elements;
        this.Message = Message;
        this.Name = Name;
        this.Value = Value;
        this.Type = Type;
    }
}
