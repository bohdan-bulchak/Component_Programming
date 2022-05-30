package gamestudio.server.webservice;

import gamestudio.entity.Score;
import gamestudio.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {
    @Autowired
    private ScoreService scoreService;

    //http://localhost:8080/api/score/
    @PostMapping
    public void addScore(@RequestBody Score score){
        scoreService.addScore(score);
    }

    //http://localhost:8080/api/score/jigsaw
    @GetMapping("/{game}")
    List<Score> getTopScores(@PathVariable String game){
        return scoreService.getTopScores(game);
    }

}
