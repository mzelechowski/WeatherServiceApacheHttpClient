import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=lomianki&units=metric&appid=d44f5a7fde40da42b930937fa5849c84";
//        ConnectionUtils connectionUtils = new ConnectionUtils(url);
//        HttpResponse response
//                = connectionUtils.getHttpResponse();
//        String responseBody = connectionUtils.getBodyFromHttpResponses(response);
//        System.out.println(responseBody);
        WeatherService  weatherService = new WeatherService(url);
        System.out.println(weatherService.mapWeatherApiResponse());

        System.out.println(getTimeStringFromModel(weatherService.mapWeatherApiResponse().getSunset()));

    }
    private static String getTimeStringFromModel(long unix_seconds){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
//        Date currentDate = new Date(inputDate*1000);
//        return dateFormat.format(currentDate);
        Date date = new Date(unix_seconds*1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        //jdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return  dateFormat.format(date);

    }
}
