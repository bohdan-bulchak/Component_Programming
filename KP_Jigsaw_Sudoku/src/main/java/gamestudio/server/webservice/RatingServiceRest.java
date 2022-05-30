package gamestudio.server.webservice;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    //http://localhost:8080/api/rating
    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    //http://localhost:8080/api/rating/average/jigsaw
    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable("game") String game) {
        return ratingService.getAverageRating(game);
    }

    //http://localhost:8080/api/rating/jigsaw/player
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable("game") String game, @PathVariable("player") String player) {
        return ratingService.getRating(game, player);
    }
}
