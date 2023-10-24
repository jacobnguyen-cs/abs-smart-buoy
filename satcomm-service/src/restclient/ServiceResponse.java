package restclient;

public class ServiceResponse
{
    public String type;
    public ServiceData[] data;

    public ServiceResponse()
    {

    }

    public ServiceResponse(String type, ServiceData[] data)
    {
        super();

        this.type = type;
        this.data = data;
    }
}