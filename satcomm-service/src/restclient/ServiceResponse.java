package restclient;

public class ServiceResponse
{
    public String type;
    public WaterTemperature[] data;

    public ServiceResponse()
    {

    }

    public ServiceResponse(String type, WaterTemperature[] data)
    {
        super();

        this.type = type;
        this.data = data;
    }
}