package gamestudio.server.webservice;

import gamestudio.entity.Comment;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/comment/
    @PostMapping
    public void addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
    }

    //http://localhost:8080/api/comment/jigsaw
    @GetMapping("/{game}")
    List<Comment> getComments(@PathVariable String game){
        return commentService.getComments(game);
    }



}

