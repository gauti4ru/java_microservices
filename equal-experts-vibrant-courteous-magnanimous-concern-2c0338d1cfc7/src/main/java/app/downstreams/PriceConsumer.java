package app.downstreams;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceConsumer implements Consumers {
    @Autowired
    RestTemplate restTemplate;
    @Value("${price.api.uri}")
    String priceApiUri;

    @Override
    public float getPrice(String item) {
        String uri = priceApiUri + "backend-take-home-test-data/" + item + ".json";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {
            // Make the HTTP GET request
            Price response = restTemplate.exchange(uri, HttpMethod.GET, entity, Price.class).getBody();
            return response.getPrice();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

@Data
class Price {
    private String tittle;
    private float price;
}
