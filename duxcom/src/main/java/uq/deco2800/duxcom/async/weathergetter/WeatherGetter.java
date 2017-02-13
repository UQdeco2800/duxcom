package uq.deco2800.duxcom.async.weathergetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Gets the current weather from Yahoo.
 *
 * Created by liamdm on 21/10/2016.
 */
public class WeatherGetter {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WeatherGetter.class);

    /**
     * The target URL
     */
    public static final String JSON_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition.text%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%27brisbane,%20au%27)&format=json";

    /**
     * If this class has the weather yet
     */
    private static boolean hasWeather = false;

    /**
     * If it failed
     */
    private static boolean failed = false;

    /**
     * The failure reason
     */
    private static String failureReason = "";

    /**
     * The raw JSON reply
     */
    private static String rawReply = "";

    /**
     * The current weather
     */
    private static String currentWeather = "";

    /**
     * Mark as failed
     * @param reason reason for failing
     */
    private static void fail(String reason){
        logger.warn("Failed to get current weather, reason [{}]", reason);
        failed = true;
        failureReason = reason;
    }

    /**
     * Get the current weather
     */
    public static void init(){
        logger.info("Trying to get current weather from [{}]", JSON_URL);
        Thread t = new Thread(() -> {
            URL url;
            try {
                url = new URL(JSON_URL);
            } catch (MalformedURLException e) {
                fail(e.getMessage());
                return;
            }

            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                fail(e.getMessage());
                return;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String data = br.readLine();
                rawReply = data;

                if(!data.contains("condition") || !data.contains("text")){
                    fail("Could not get data!");
                    return;
                }

                String[] segments = data.split(":");
                if(segments.length < 2){
                    fail("Data was invalid format!");
                    return;
                }

                String targetSegment = segments[segments.length - 1];
                String condition = targetSegment.substring(1, targetSegment.indexOf("\"", 1));
                currentWeather = condition;
                hasWeather = true;

                logger.info("Parsed current weather to get condition [{}]", condition);

            } catch (IOException e) {
                fail(e.getMessage());
                return;
            }



        });

        t.setName("WeatherThread");
        t.setDaemon(true);
        t.start();
    }


    public static boolean hasWeather(){
        return hasWeather;
    }

    public static String getCurrentWeather(){
        return currentWeather;
    }

    /**
     * If weather failed to get
     */
    public static boolean isFailed() {
        return failed;
    }

    /**
     * Gets the failure reason
     */
    public static String getFailureReason() {
        return failureReason;
    }

    /**
     * Gets the raw JSON reply
     */
    public static String getRawReply() {
        return rawReply;
    }
}
