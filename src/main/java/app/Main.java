package app;


import code.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.http.client.utils.URIBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Main {
    private final static String BASEURL = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String APIID = "74ad9cd519f4b5605ed60e44cfd302ae";

    private static Optional<URI> createFinalUrl() {
        try {
            URI uriBuild = new URIBuilder(BASEURL)
                    .addParameter("appid", APIID)
                    .addParameter("q", "lodz")
                    .addParameter("units", "metric")
                    .addParameter("lang", "pl")
                    .build();
            return Optional.of(uriBuild);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static void printWeather(WeatherModel weatherModel) {
        System.out.println(weatherModel.getName());
        weatherModel.getWeather()
                .forEach(x -> System.out.println(x.getDescription()));
        System.out.println("temp: " + weatherModel.getMain().getTemp());
    }

    private static Optional<WeatherModel> perseResponse(String responseBody) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            WeatherModel weatherModel = gson.fromJson(responseBody, WeatherModel.class);
            return Optional.of(weatherModel);
        } catch (JsonSyntaxException jE) {
            System.err.println(jE.getMessage());
            return Optional.empty();
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Optional<URI> url = createFinalUrl();

        while (true) {
            if (!url.isPresent()) {
                return;
            }
            Optional<String> responseBody = new HttpGetRequest(url.get()).getRequest();
            if (responseBody.isPresent()) {
                Optional<WeatherModel> weatherModel = perseResponse(responseBody.get());
                if (weatherModel.isPresent()) {
                    printWeather(weatherModel.get());
                }
            }
            sleep();
        }
    }
}
