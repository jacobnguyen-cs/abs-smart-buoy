
import java.util.concurrent.*;

// Tested using the following Java versions:
// java version "1.8.0_102"

public class Main 
{
	public static void main(String[] args) 
	{
		try
		{
			// REST IGWS client
			IGWSRestClient restClient = new IGWSRestClient();

			restClient.GetBasicGatewayAndAccountInfo();
			restClient.SubmitModemMesssagesSample();
			
			// Poll the IDP Gateway for 300 seconds
			restClient.StartPolling();
			
			TimeUnit.SECONDS.sleep(300);
			restClient.StopPolling();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}