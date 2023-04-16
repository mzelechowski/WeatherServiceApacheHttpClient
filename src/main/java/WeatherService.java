import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherService {
    private ConnectionUtils connectionUtils;

    public WeatherService(String url) {
        this.connectionUtils = new ConnectionUtils(url);
    }

    public WeatherModel mapWeatherApiResponse(){
        String responseBody=connectionUtils
                .getBodyFromHttpResponses(connectionUtils.getHttpResponse());
        JsonNode jsonNode = null;
        WeatherModel weatherModel;
        try {
            jsonNode = new ObjectMapper().readTree(responseBody);
            weatherModel=new WeatherModel();
            weatherModel.setTemp(jsonNode.get("main").get("temp").asDouble());
            weatherModel.setTempMin(jsonNode.get("main").get("temp_min").asDouble());
            weatherModel.setTempMax(jsonNode.get("main").get("temp_max").asDouble());
            weatherModel.setPressure(jsonNode.get("main").get("pressure").asDouble());
            weatherModel.setFeelsLike(jsonNode.get("main").get("feels_like").asDouble());
            weatherModel.setWindSpeed(jsonNode.get("wind").get("speed").asDouble());
            weatherModel.setCountry(jsonNode.get("sys").get("country").asText());
            weatherModel.setSunrise(jsonNode.get("sys").get("sunrise").asLong());
            weatherModel.setSunset(jsonNode.get("sys").get("sunset").asLong());
            weatherModel.setCityName(jsonNode.get("name").asText());
            weatherModel.setTimeZone(jsonNode.get("timezone").asLong());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return null;
        }
        return  weatherModel;
    }
}
