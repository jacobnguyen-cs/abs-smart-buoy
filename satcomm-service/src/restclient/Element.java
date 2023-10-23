package restclient;

public class Element 
{
    public Field[] Fields = null;
    public Integer Index;
    
    public Element()
    {
        
    }
    
    public Element(Field[] Fields, Integer Index)
    {
        super();
        
        this.Fields = Fields;
        this.Index = Index;
    }
}
