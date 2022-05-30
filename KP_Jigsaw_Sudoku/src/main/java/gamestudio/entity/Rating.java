package gamestudio.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private int rating;

    private Date rated_on;

    public Rating(){

    }

    public Rating(String game, String player, int rating, Date rated_on) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.rated_on = rated_on;
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return rated_on;
    }

    public void setRatedOn(Date ratedOn) {
        this.rated_on = rated_on;

    }

    @Override
    public String toString() {
        return "Rating{" +
                "player='" + player + '\'' +
                ", gamestudio.game='" + game + '\'' +
                ", rating=" + rating +
                ", rated_on=" + rated_on +
                '}';
    }
}
