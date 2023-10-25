package restclient;

public class WaterTemperature
{
    public int id;
    public double temp;

    public WaterTemperature()
    {

    }

    public WaterTemperature(int id, int type, double temp)
    {
        super();

        this.id = id;
        this.temp = temp;
    }
}