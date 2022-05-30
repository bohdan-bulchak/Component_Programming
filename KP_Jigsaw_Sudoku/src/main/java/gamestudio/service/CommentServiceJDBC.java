package gamestudio.service;

import gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static gamestudio.service.ScoreServiceJDBC.JDBC_USER;
import static gamestudio.service.ScoreServiceJDBC.JDBC_URL;
import static gamestudio.service.ScoreServiceJDBC.JDBC_PASSWORD;


public class CommentServiceJDBC implements CommentService{

    public static final String INSERT_COMMENT = "INSERT INTO comment (game, player, comment, commentedAt) VALUES (?, ?, ?, ?)";

    public static final String SELECT_COMMENT =
            "SELECT game, player, comment, commentedAt FROM comment WHERE game = ? ORDER BY commentedAt DESC LIMIT 10;";


    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(INSERT_COMMENT);
        ) {
            ps.setString(1, comment.getGame());
            ps.setString(2, comment.getPlayer());
            ps.setString(3, comment.getComment());
            ps.setDate(4, new Date(comment.getCommentedAt().getTime()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new GamestudioException("Error saving comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT_COMMENT);
        ) {
            ps.setString(1, game);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getTimestamp(4)
                    );
                    comments.add(comment);
                }
            }

        } catch (SQLException e) {
            throw new GamestudioException("Error loading comment", e);
        }
        return comments;
    }


}
