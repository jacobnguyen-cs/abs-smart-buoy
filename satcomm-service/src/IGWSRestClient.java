import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import restclient.*;

public class IGWSRestClient 
{
	// replace the constants below with values appropriate for your account
	 
    // final static String gwHost = "isatdatapro.swlab.ca"; // isatdatapro.skywave.com
    // final static int gwPort = 8143; // 443
    // final static String gwRestBasePath = "/GLGW/2/RestMessages.svc/JSON/";

    // final static String myAccessID = "70000934";
    // final static String myPassword = "password";
	// final static String mobileID = "01097623SKY2C68";

    final static String gwHost = "isatdatapro.skywave.com";
    final static int gwPort = 443;
    final static String gwRestBasePath = "/GLGW/2/RestMessages.svc/JSON/";

    final static String myAccessID = "70003420";
    final static String myPassword = "MBWCNCKF";
	final static String mobileID = "02047634SKY6F17";    
    
    final static int pollingIntervalInSeconds = 30; // poll the IGWS once every 30 seconds
    final static int webServiceRequestTimeoutInSeconds = 300; // 5 minute request timeout
    
    // igwsUTC will contain current Gateway UTC time. We can use that as a starting 
    // high-watermark for message polling
    private static String igwsUTC;

    // wsErrorCodes dictionary can be used to get the name and description of
    // errors received from the Gateway. The next code snippet shows how to use it.
    HashMap<Integer, ErrorInfo> wsErrorCodes = new HashMap<Integer, ErrorInfo>();

    // Dictionary of all subaccounts associated to account <myAccessID>
    HashMap<String, SubaccountInfo> mySubaccounts = new HashMap<String, SubaccountInfo>();

    // Dictionary of all terminals associated to account <myAccessID>
    HashMap<String, TerminalInfo> myTerminals = new HashMap<String, TerminalInfo>();        
    
    // Dictionary of all broadcast IDs associated to account <myAccessID>
    HashMap<String, BroadcastInfo> myBroadcastIDs = new HashMap<String, BroadcastInfo>();        
    
    public void GetBasicGatewayAndAccountInfo()
    {
        getIGWSInformation();
        getSubaccountList();
        getTerminalList();
        getBroadcastIDList();
    }
	
	public void StartPolling()
    {
        igwsPolling();
    }

    public void StopPolling()
    {
        stopPolling();
    }   
    
    private Thread pollingThread = null;
    private void igwsPolling()
    {
        Runnable runnable = () -> 
        {
            System.out.println("IGWS polling started.");

            String startReUTC = igwsUTC;
            String startFwStatusUTC = igwsUTC;

            while(!Thread.currentThread().isInterrupted())
            {
                try
                {
                      startReUTC = retrieveNewReturnLinkMessages(startReUTC);
                    startFwStatusUTC = retrieveNewForwardMessageStatusChanges(startFwStatusUTC);

                    TimeUnit.SECONDS.sleep(pollingIntervalInSeconds);
                }
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                catch(Exception e)
                {
                    System.out.println("igwsPolling exception: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("IGWS polling completed.");
        };

        pollingThread = new Thread(runnable);
        pollingThread.start();     
    }
    
    private void stopPolling()
    {
        pollingThread.interrupt();
    }
    
    // Logs error messages on the console
    private void igwsErrorHandler(int errorCode, String caller)
    {
        try
        {
            ErrorInfo ei = wsErrorCodes.get(errorCode);     
            if(ei != null)
            {
                System.out.println("Error calling " + caller + ": " +
                        errorCode + " - " +
                        ei.Name );
            }
            else
            {
                System.out.println("Error calling " + caller + ": " + errorCode );
            }
        }
        catch (Exception ex)
        {
            System.out.println("Logging exception: " + ex.getMessage() );
        }
    }
    
    // Execute a  GET REST web service request
    private StringBuilder executeRequest(String webServiceOperation, String parameters)
    {
        try
        {
            String authentication = "access_id=" + myAccessID + "&password=" + myPassword;
            
            URI getFwStatusesURI = new URI("https", null, gwHost, gwPort, gwRestBasePath+webServiceOperation,
                    authentication + parameters,
                    null);
					
			// The following line instructs the IDP Gateway to send compressed responses. If you don't want to use compression, use:
			//     HttpClientBuilder.create().disableContentCompression().build();
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(getFwStatusesURI);

            // Set the HTTP request timeout
            RequestConfig requestConfig = RequestConfig.custom()
              .setSocketTimeout(webServiceRequestTimeoutInSeconds*1000)
              .setConnectTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .setConnectionRequestTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .build();
            request.setConfig(requestConfig);
            
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String inputStr;
            StringBuilder responseStrBuilder = new StringBuilder();

            while ((inputStr = rd.readLine()) != null) 
                responseStrBuilder.append(inputStr);

            return responseStrBuilder;
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        return null;
    }
    
    // -- Get basic IGWS information
    private void getIGWSInformation()
    {
        System.out.println("Calling: igws_information");
        
        try
        {
            StringBuilder responseStrBuilder = executeRequest("igws_information/", "&get_error_codes=true");
            if(responseStrBuilder == null)
                return;
            
            // Object mapper instance
            ObjectMapper mapper = new ObjectMapper();
             
            // Convert JSON to a Java object
            String json = responseStrBuilder.toString();
            IGWSInformationResponse infoResponse = mapper.readValue(json, IGWSInformationResponse.class);
            if(infoResponse != null && infoResponse.ErrorID == 0)
            {
                igwsUTC = infoResponse.UTC;
                
                if(infoResponse.ErrorCodes != null)
                {
                    for (ErrorInfo errorInfo: infoResponse.ErrorCodes)
                        wsErrorCodes.put(new Integer(errorInfo.ID), errorInfo);   
                    
                    System.out.println("    Connected to IDP Gateway web service version: " + infoResponse.Version + 
                            " UTC: " +  igwsUTC + 
                            " Retrieved error codes: " + wsErrorCodes.size());
                }
            }
            else if (infoResponse != null)
            {
                System.out.println("getIGWSInformation error: " + infoResponse.ErrorID);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    // Get a list of subaccounts of your account
    // This call will fail with an error in case your account is not a super-account
    private void getSubaccountList()
    {
		int AUTHORIZATION_ERROR = 104;  // Error returned by the web service if the account is not authorized to call the web servic eoperation
		
        System.out.println("Calling: get_subaccount_info");
        try
        {
            StringBuilder responseStrBuilder = executeRequest("get_subaccount_info/", "");
            if(responseStrBuilder == null)
                return;
            
            // Object mapper instance
            ObjectMapper mapper = new ObjectMapper();
             
            // Convert JSON to a Java object
            String json = responseStrBuilder.toString();
            GetSubaccountInfoResult saResponse = mapper.readValue(json, GetSubaccountInfoResult.class);
            if(saResponse != null && saResponse.ErrorID == 0)
            {
                if( saResponse.Subaccounts != null && saResponse.Subaccounts.length > 0)
                {
                    for (SubaccountInfo subAccountInfo: saResponse.Subaccounts)
                    {
                        mySubaccounts.put(subAccountInfo.AccountID, subAccountInfo); 
                        System.out.println("    Retrieved subaccount: " + subAccountInfo.AccountID);
                    }
                }
            }
            else if (saResponse != null)
            {
				if(saResponse.ErrorID == AUTHORIZATION_ERROR)
                     System.out.println("Your account is not a super-account, therefore subaccount list cannot be retrieved.");
				else
					igwsErrorHandler(saResponse.ErrorID, "get_subaccount_info");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    // Get a list of terminals assigned to your account
    private void getTerminalList()
    {
        System.out.println("Calling: get_terminals_info");
        try
        {
            boolean terminalRetrievalCompleted = false;
            int queryPageSize = 500;
            String startTerminalID = ""; // Start from the first terminal
            do
            {
                String parameters = "&since_id=" + startTerminalID + "&page_size=" + queryPageSize;
                
                StringBuilder responseStrBuilder = executeRequest("get_terminals_info/", parameters);
                if(responseStrBuilder == null)
                    return;

                // Object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // Convert JSON to a Java object
                String json = responseStrBuilder.toString();
                GetTerminalsInfoResult terminalInfoResponse = mapper.readValue(json, GetTerminalsInfoResult.class);
                if(terminalInfoResponse != null && terminalInfoResponse.ErrorID == 0 && 
                        terminalInfoResponse.Terminals != null && terminalInfoResponse.Terminals.length > 0)
                {
                    for (TerminalInfo mtInfo: terminalInfoResponse.Terminals)
                    {
                        myTerminals.put(mtInfo.PrimeID, mtInfo);
                        
                        System.out.println("    Prime ID: " + mtInfo.PrimeID + " Description: " + mtInfo.Description);
                    }

                    // The next call should retrieve a next page of 500 terminals. Take the PrimeID of the 
                    // last received terminal and pass it as a sinceID parameter of the next GetTerminalsInfo call.
                    startTerminalID = terminalInfoResponse.Terminals[terminalInfoResponse.Terminals.length-1].PrimeID;
                }
                else
                {
                    terminalRetrievalCompleted = true;
                }
                
                if (terminalInfoResponse != null && terminalInfoResponse.ErrorID > 0)
                {
                    igwsErrorHandler(terminalInfoResponse.ErrorID, "get_terminals_info");
                }
            } while (!terminalRetrievalCompleted);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    // Get a list of broadcast IDs assigned to your account
    private void getBroadcastIDList()
    {
        System.out.println("Calling: get_broadcast_info");
        try
        {
            StringBuilder responseStrBuilder = executeRequest("get_broadcast_info/", "");
            if(responseStrBuilder == null)
                return;
            
            // Object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // Convert JSON to a Java object
            String json = responseStrBuilder.toString();
            GetBroadcastInfoResult broadcastInfoResponse = mapper.readValue(json, GetBroadcastInfoResult.class);
            if(broadcastInfoResponse != null && broadcastInfoResponse.ErrorID == 0 && 
                    broadcastInfoResponse.BroadcastInfos != null && broadcastInfoResponse.BroadcastInfos.length > 0)
            {
                for (BroadcastInfo broadcastInfo: broadcastInfoResponse.BroadcastInfos)
                {
                    myBroadcastIDs.put(broadcastInfo.ID, broadcastInfo);

                    System.out.println("    Broadcast ID: " + broadcastInfo.ID + " Description: " + broadcastInfo.Description);
                }
            }
            if (broadcastInfoResponse != null && broadcastInfoResponse.ErrorID > 0)
            {
                igwsErrorHandler(broadcastInfoResponse.ErrorID, "get_broadcast_info");
            }

        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public String retrieveNewForwardMessageStatusChanges(String startUTC)
    {
        System.out.println("Calling: get_forward_statuses");
        
        String nextStartUTC = startUTC;
        try
        {
            String parameters = "&start_utc=" + startUTC;
            StringBuilder responseStrBuilder = executeRequest("get_forward_statuses/", parameters);
            if(responseStrBuilder == null)
                return startUTC;
            
            // Object mapper instance
            ObjectMapper mapper = new ObjectMapper();
             
            // Convert JSON to a Java object
            String json = responseStrBuilder.toString();
            GetForwardStatusesResult restResponse = mapper.readValue(json, GetForwardStatusesResult.class);
            if(restResponse != null && restResponse.ErrorID == 0 && 
                    restResponse.Statuses != null && restResponse.Statuses.length > 0)
            {
                // Handle the response ...
                System.out.println("    Number of retrieved statuses: " + restResponse.Statuses.length);
                
                for(ForwardStatus st: restResponse.Statuses)
                    System.out.println("    Forward Message ID: " + st.ForwardMessageID + " Message state: " + 
                            forwardMessageStateName(st.State) + " State change time: " + st.StateUTC);
                
                // -- Get the next StartUTC, but only if Statuses array is not empty
                nextStartUTC = restResponse.NextStartUTC;
                return nextStartUTC;
            }
            else if(restResponse != null && restResponse.ErrorID == 0)
            {
                System.out.println("    Number of retrieved statuses: 0");
            }
            else if(restResponse != null && restResponse.ErrorID > 0)
            {
                igwsErrorHandler(restResponse.ErrorID, "get_forward_statuses");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        return nextStartUTC;
    }
    
    private String forwardMessageStateName(int state)
    {
        String stateName = "NA";
        switch(state)
        {
            case 0:
                stateName = "SUBMITTED";
                break;
            case 1:
                stateName = "RECEIVED";
                break;
            case 2:
                stateName = "ERROR";
                break;
            case 3:
                stateName = "DELIVERY FAILED";
                break;
            case 4:
                stateName = "TIMEOUT";
                break;
            case 5:
                stateName = "CANCELLED";
                break;
            case 6:
                stateName = "WAITING";
                break;
            case 8:
                stateName = "TRANSMITTED";
                break;
        }

        return stateName;
    }
    
    public String retrieveNewReturnLinkMessages(String startUTC)
    {
        System.out.println("Calling: get_return_messages");
        
        String nextStartUTC = startUTC;
        startUTC = "2023-11-15 00:52:04 UTC";
        try
        {
            String parameters = "&start_utc=" + startUTC + "&include_raw_payload=true";
            StringBuilder responseStrBuilder = executeRequest("get_return_messages/", parameters);
            if(responseStrBuilder == null)
                return startUTC;
            
            // Object mapper instance
            ObjectMapper mapper = new ObjectMapper();
             
            // Convert JSON to a Java object
            String json = responseStrBuilder.toString();
            GetReturnMessagesResult restResponse = mapper.readValue(json, GetReturnMessagesResult.class);
            if(restResponse != null && restResponse.ErrorID == 0 && 
                    restResponse.Messages != null && restResponse.Messages.length > 0)
            {
                // Handle the response ...
                System.out.println("    Number of retrieved messages: " + restResponse.Messages.length);
                
                for(restclient.ReturnMessage msg: restResponse.Messages)
                    sendData(msg);

                // -- Get the next StartUTC, but only if Messages array is not empty
                nextStartUTC = restResponse.NextStartUTC;
                return nextStartUTC;
            }
            else if(restResponse != null && restResponse.ErrorID == 0)
            {
                System.out.println("    Number of retrieved messages: 0");
            }
            else if(restResponse != null && restResponse.ErrorID > 0)
            {
                igwsErrorHandler(restResponse.ErrorID, "get_return_messages");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        return nextStartUTC;
    }
	
	public void SubmitModemMesssagesSample()
    {
        System.out.println("Calling: submit_messages");
        try
        {
            String webServiceOperation = "submit_messages/";
            
            URI getFwStatusesURI = new URI("https", null, gwHost, gwPort, gwRestBasePath+webServiceOperation, null, null);
            //RI getFwStatusesURI = new URI("http", null, gwHost, gwPort, gwRestBasePath+webServiceOperation, null, null);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(getFwStatusesURI);

            // Set the HTTP request timeout
            RequestConfig requestConfig = RequestConfig.custom()
              .setSocketTimeout(webServiceRequestTimeoutInSeconds*1000)
              .setConnectTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .setConnectionRequestTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .build();
            postRequest.setConfig(requestConfig);
            
            // Prepare a message submission request
            ForwardMessage []fwMessages = new ForwardMessage[1];
            
            ForwardSubmissionRequest fwRequest = new ForwardSubmissionRequest();
            fwRequest.access_id = myAccessID;
            fwRequest.password = myPassword;
            fwRequest.messages = fwMessages;
            
            fwMessages[0] = new ForwardMessage();
            fwMessages[0].DestinationID = mobileID;
            fwMessages[0].UserMessageID = 1;
            fwMessages[0].RawPayload =  new ArrayList<Byte>() {{
                add((byte)0x00);
                add((byte)0x48);
            }};

            fwMessages[0].Payload = null;
            
            // Convert a Java object to JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(fwRequest);
            
            StringEntity input = new StringEntity(json);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = client.execute(postRequest);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String responseStr;
            StringBuilder responseStrBuilder = new StringBuilder();

            while ((responseStr = rd.readLine()) != null) 
                responseStrBuilder.append(responseStr);
            
            // Convert JSON to a Java object
            String jsonResponse = responseStrBuilder.toString();
            SubmitMessagesResult restResponse = mapper.readValue(jsonResponse, SubmitMessagesResult.class);
            if(restResponse != null && restResponse.ErrorID == 0 && 
                    restResponse.Submissions != null && restResponse.Submissions.length > 0)
            {
                // Handle the response ...
                System.out.println("    Number of submitted messages: " + restResponse.Submissions.length);
                
                for(restclient.ForwardSubmission msg: restResponse.Submissions)
                    System.out.println("    FW Message ID: " + msg.ForwardMessageID + " for User ID " + msg.UserMessageID);
            }
            else if(restResponse != null && restResponse.ErrorID > 0)
            {
                igwsErrorHandler(restResponse.ErrorID, "submit_messages");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }


    // execute POST request to water temperature service
    public void sendData(restclient.ReturnMessage msg)
    {
        System.out.println("Attempting to send data to water temperature service");
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost("http://decryption-service-alb-2134574620.us-east-2.elb.amazonaws.com/decrypt");

            // Set the HTTP request timeout
            RequestConfig requestConfig = RequestConfig.custom()
              .setSocketTimeout(webServiceRequestTimeoutInSeconds*1000)
              .setConnectTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .setConnectionRequestTimeout(webServiceRequestTimeoutInSeconds * 1000)
              .build();
            postRequest.setConfig(requestConfig);

            // Convert a Java object to JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(msg);

            StringEntity input = new StringEntity(json);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = client.execute(postRequest);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String responseStr;
            StringBuilder responseStrBuilder = new StringBuilder();

            while ((responseStr = rd.readLine()) != null) 
                responseStrBuilder.append(responseStr);
            
            String jsonResponse = responseStrBuilder.toString();
            System.out.println(jsonResponse);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
