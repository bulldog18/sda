package app;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class HttpGetRequest {

    private HttpGet httpget;
    private StringResponseHandler responseHandler;

    public HttpGetRequest(URI url){
        httpget = new HttpGet(url);
        responseHandler = new StringResponseHandler();
    }

    public Optional<String> getRequest()  {

        String response = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            String respnse = httpclient.execute(httpget, responseHandler);
            return Optional.of(respnse);
        }catch (IOException e){
            System.err.println(e.getMessage());
            return Optional.ofNullable(response);
        }
    }
}
