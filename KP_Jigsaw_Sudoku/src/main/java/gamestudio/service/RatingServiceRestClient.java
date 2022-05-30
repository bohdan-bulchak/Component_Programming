package gamestudio.service;

import com.fasterxml.classmate.GenericType;
import gamestudio.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.GenericArrayType;
import java.util.Arrays;

public class RatingServiceRestClient implements RatingService {

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating){
        restTemplate.postForEntity(url + "/rating",rating,Rating.class);
    }

    @Override
     public int getAverageRating(String game){
        return restTemplate.getForEntity(url + "/rating" + "/average" + game,Rating.class).getBody().getRating();
    }

    @Override
    public int getRating(String game, String player){
        return restTemplate.getForEntity(url + "/rating/" + game + player,Rating.class).getBody().getRating();
    }


    /*@Override
    public void setRating(Rating rating) throws RatingException {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(rating, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new ScoreException("Error saving rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<Integer>() {
                    });
        } catch (Exception e) {
            throw new ScoreException("Error loading average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .path("/" + game + "/" + player)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<Integer>() {
                    });
        } catch (Exception e) {
            throw new ScoreException("Error loading rating", e);
        }
    }*/
}