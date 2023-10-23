package restclient;

public class TerminalInfo 
{
    public String PrimeID = "";
    public String Description = "";
    public String LastRegistrationUTC = "";
    public String RegionName = "";
    public String MTSN = "";
    public String IMEI = "";
    public String MEID = "";
    public String MAC = "";
    public String PairedTerminalPrimeID = "";
    public String LastMTBPUTC = "";
    public String LastMTWSUTC = "";
    
    public TerminalInfo()
    {
        
    }
    
    public TerminalInfo(String PrimeID, String Description, String LastRegistrationUTC, String RegionName, String MTSN, String IMEI, 
            String MEID, String MAC, String PairedTerminalPrimeID, String LastMTBPUTC, String LastMTWSUTC)
    {
        super();
        
        this.PrimeID = PrimeID;
        this.Description = Description;
        this.LastRegistrationUTC = LastRegistrationUTC;
        this.RegionName = RegionName;
        this.MTSN = MTSN;
        this.IMEI = IMEI;
        this.MEID = MEID;
        this.MAC = MAC;
        this.PairedTerminalPrimeID = PairedTerminalPrimeID;
        this.LastMTBPUTC = LastMTBPUTC;
        this.LastMTWSUTC = LastMTWSUTC;
    }
}
