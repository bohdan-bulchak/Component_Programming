package gamestudio.service;

import gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws GamestudioException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws GamestudioException {
        return entityManager.createQuery("SELECT c from Comment c where c.game=:game order by c.commentedAt desc")
                .setParameter("game", game).getResultList();
    }
}
