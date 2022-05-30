package gamestudio.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private String comment;

    private Date commentedAt;

    public Comment(){

    }

    public Comment(String game, String player, String comment, Date commentedAt) {

        this.game = game;
        this.player = player;
        this.commentedAt = commentedAt;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "gamestudio.game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", commentedAt=" + commentedAt +
                '}';
    }
}
