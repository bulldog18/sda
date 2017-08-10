package app;


import code.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private final static String BASEURL = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String APIID = "74ad9cd519f4b5605ed60e44cfd302ae";

    private static URI createFinalUrl() throws URISyntaxException {
        return new URIBuilder(BASEURL)
                .addParameter("appid", APIID)
                .addParameter("q", "lodz")
                .addParameter("units", "metric")
                .addParameter("lang", "pl")
                .build();
    }

    private static void printWeather(WeatherModel weatherModel) {
        System.out.println(weatherModel.getName());
        weatherModel.getWeather()
                .forEach(x -> System.out.println(x.getDescription()));
        System.out.println("temp: " + weatherModel.getMain().getTemp());
    }

    private static WeatherModel perseResponse(String responseBody) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(responseBody, WeatherModel.class);
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        URI url = createFinalUrl();

        while (true) {

            Thread.sleep(5000);
            String responseBody = new HttpGetRequest(url).getRequest();
            WeatherModel weatherModel = perseResponse(responseBody);
            printWeather(weatherModel);

        }
    }
}
