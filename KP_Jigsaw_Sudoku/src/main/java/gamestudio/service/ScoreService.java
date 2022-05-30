package gamestudio.service;

import gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getTopScores(String game);

    void reset();
}
