package gamestudio.service;

import gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{


    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws GamestudioException {
        return entityManager.createQuery("select s from Score s where s.game = :game order by s.points desc")
                .setParameter("game",game)
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws GamestudioException{
        entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
