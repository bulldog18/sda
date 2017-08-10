package app;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;

public class HttpGetRequest {

    private HttpGet httpget;
    private StringResponseHandler responseHandler;

    public HttpGetRequest(URI url){
        httpget = new HttpGet(url);
        responseHandler = new StringResponseHandler();
    }

    public String getRequest() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient.execute(httpget, responseHandler);
    }
}
