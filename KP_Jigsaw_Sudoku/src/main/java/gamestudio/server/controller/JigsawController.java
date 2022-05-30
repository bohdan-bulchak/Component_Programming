package gamestudio.server.controller;

import gamestudio.entity.Comment;
import gamestudio.entity.Score;
import gamestudio.game.webui.WebUI;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class JigsawController {

    private final WebUI webUI;

    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;

    public JigsawController(ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        this.webUI = new WebUI(scoreService,commentService, ratingService);

    }
    @RequestMapping("jigsaw/main")
    public String diff(){
        return "jigsaw";
    }

    @RequestMapping("jigsaw/game")
    public String JigsawSudoku(@RequestParam(name = "row", required = false) String rowString,
                               @RequestParam(name = "column", required = false) String columnString,
                               @RequestParam(name = "value", required = false) String valueString,
                               @RequestParam(name = "player", required = false) String playersName,
                               @RequestParam(name = "diff", required = false) String diffString,
                               @RequestParam(name = "comment", required = false) String commentString,
                               @RequestParam(name = "rating", required = false) String ratingString,

                               Model model)
    {
        webUI.setDifficulty(diffString);
        webUI.setName(playersName);
        webUI.setComment(commentString);
        webUI.setRating(ratingString);

        webUI.processCommand(rowString, columnString, valueString);
        model.addAttribute("webUI", webUI);

        return "jigsaw-game"; //same name as the template
    }

    @RequestMapping("jigsaw/comment")
    public String comment(Model model){
        List<Comment> comments = commentService.getComments("jigsaw");
        model.addAttribute("comments", comments);

        return "jigsaw-comment";
    }

    @RequestMapping("jigsaw/score")
    public String score(Model model){
        List<Score> bestScores = scoreService.getTopScores("jigsaw");
        model.addAttribute("scores", bestScores);
        return "jigsaw-score";
    }

    @RequestMapping("jigsaw/rating")
    public String rating(Model model){
        int avg = ratingService.getAverageRating("jigsaw");
        model.addAttribute("avgRating",avg);
        return "jigsaw-rating";
    }


}
