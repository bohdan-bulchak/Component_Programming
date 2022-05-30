package gamestudio.service;


import gamestudio.entity.Rating;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws GamestudioException {
        if (getRating(rating.getGame(), rating.getPlayer()) == 0) {
            entityManager.persist(rating);
        } else {
            entityManager.createQuery("UPDATE Rating r SET r.rating=: rating, r.rated_on=:ratedon " +
                    "WHERE r.player =:player  AND r.game =: game")
                    .setParameter("rating", rating.getRating()).setParameter("ratedon", rating.getRatedOn())
                    .setParameter("player", rating.getPlayer()).setParameter("game", rating.getGame()).executeUpdate();
        }
    }

    @Override
    public int getAverageRating(String game) throws GamestudioException {
        int sumRating = 0;
        int counter = 0;
        List ratings;
        try {
            ratings = entityManager.createQuery("SELECT r from Rating r where r.game=:game")
                    .setParameter("game", game).getResultList();

        } catch (PersistenceException e) {
            System.out.println("ERROR with database");
            return 0;
        }
        for (Object rat:ratings){
            Rating rating = (Rating)rat;
            counter++;
            sumRating += rating.getRating();
        }
        return sumRating/counter;
       /* Query q = entityManager.createNativeQuery("SELECT sum(rating)/count(*) FROM rating WHERE gamestudio.game = ?");
        int averageRating = q.getMaxResults();
        return averageRating;*/
    }

    @Override
    public int getRating(String game, String player) throws GamestudioException {
        List ratings;
        ratings = entityManager.createQuery("SELECT r from Rating r where r.game=:game and r.player=:player")
                .setParameter("game", game).setParameter("player", player).getResultList();
        for (Object r: ratings){
            Rating rating = (Rating) r;
            if(rating.getPlayer().equals(player)){
                return rating.getRating();
            }
        }
        return 0;
    }
}
