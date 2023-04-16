import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ConnectionUtils {
    private final String url;

    public ConnectionUtils(String url) {
        this.url = url;
    }

    public HttpResponse getHttpResponse() {
        HttpGet httpGet = new HttpGet(this.url);
        HttpResponse httpResponse;
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("error status code");
            return null;
        }
        return httpResponse;
    }

    public String getBodyFromHttpResponses(HttpResponse httpResponse) {
        String responseBody;
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
            responseBody = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        // na ładne drukowanie obiektu JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object json = mapper.readValue(responseBody, Object.class);
            responseBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }

        return responseBody;
    }
}
