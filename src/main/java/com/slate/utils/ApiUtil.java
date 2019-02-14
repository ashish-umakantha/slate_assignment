package com.slate.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class ApiUtil {

    /**
     * This Method can be used for post call.
     * @param url      -  url from relevant configs
     * @param apiQuery      -  Optional parameter to pass query
     * @param headerMap      -  Map with key-value pairs can be passed as headers
     * @param payload 		-  Payload for post call
     */
    public static String post(String url, String apiQuery, HashMap<String, String> headerMap, String payload) throws URISyntaxException, IOException{
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = null;
        if(apiQuery!=null) {
        	uriBuilder = new URIBuilder(url+""+apiQuery+"");
        }else {
        	uriBuilder = new URIBuilder(url);
        }
        try
        {
            //Define a postRequest request
            HttpPost postRequest = new HttpPost(uriBuilder.build());

            //Set the API media type in http content-type header
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                postRequest.addHeader(entry.getKey(), entry.getValue());
            }
            
            StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);
            postRequest.setEntity(entity);
            
            //Send the request; It will immediately return the response in HttpResponse object if any
            CloseableHttpResponse response = httpclient.execute(postRequest);
            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            LoggerUtils.info(responseString);
            return responseString;
        }
        finally
        {
            if (httpclient != null) {
                //Important: Close the connect
                httpclient.close();
            }
        }
    }
}
