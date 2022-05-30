package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private int points;

    private Date playedAt;

    public Score(){

    }

    /*
        CREATE TABLE score (gamestudio.game VARCHAR(32) NOT NULL, player VARCHAR(32) NOT NULL, points INT NOT NULL, played TIMESTAMP NOT NULL)
     */

    public Score(String game, String player, int points, Date playerAt) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedAt = playerAt;
    }

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPlayedAt(Date playedAt) {
    }

    @Override
    public String toString() {
        return "Score{" +
                "gamestudio.game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedAt=" + playedAt +
                '}';
    }
}
