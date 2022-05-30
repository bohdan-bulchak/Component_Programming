package gamestudio.service;

import gamestudio.entity.Rating;

import static gamestudio.service.ScoreServiceJDBC.JDBC_USER;
import static gamestudio.service.ScoreServiceJDBC.JDBC_URL;
import static gamestudio.service.ScoreServiceJDBC.JDBC_PASSWORD;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {


    public static final String INSERT_RATING = "INSERT INTO rating(game, player, rating, rated_on) VALUES (?, ?, ?, ?)";

    public static final String SELECT_RATING = "SELECT gamestudio.game, player, rating, rated_on FROM rating WHERE gamestudio.game = ? ";

    public static final String SELECT_PLAYER = "SELECT game, player, rating, rated_on FROM rating WHERE game = ? AND player = ? ";

    public static final String UPDATE_RATING = "UPDATE rating SET rating = ?, rated_on = ? WHERE game = ? AND player = ? ";

    public static final String AVERAGE_RATING = "SELECT sum(rating)/count(*) AS average FROM rating WHERE game = ?";

    @Override
    public void setRating(Rating rating) {
        if (getRating(rating.getGame(), rating.getPlayer()) != 0) {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                try (PreparedStatement ps = connection.prepareStatement(UPDATE_RATING)) {
                    ps.setInt(1, rating.getRating());
                    ps.setDate(2, new Date(rating.getRatedOn().getTime()));
                    ps.setString(3, rating.getGame());
                    ps.setString(4, rating.getPlayer());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new GamestudioException("Error saving Rating as existing", e);
            }

        } else {
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                try (PreparedStatement ps = connection.prepareStatement(INSERT_RATING)) {
                    ps.setString(1, rating.getGame());
                    ps.setString(2, rating.getPlayer());
                    ps.setInt(3, rating.getRating());
                    ps.setDate(4, new Date(rating.getRatedOn().getTime()));

                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new GamestudioException("Error saving Rating", e);
            }
        }


    }

    @Override
    public int getAverageRating(String game) {

        int averageRating = 0;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

            try (PreparedStatement ps = connection.prepareStatement(AVERAGE_RATING)) {
                    ps.setString(1, game);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {

                    averageRating = rs.getInt(1);

                    } 
                }
            }
        } catch (SQLException e) {
            throw new GamestudioException("Error loading Rating", e);
        }


        return averageRating;
    }


    @Override
    public int getRating(String game, String player) {
        Rating rating = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_PLAYER)) {
                ps.setString(1, game);
                ps.setString(2, player);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rating = new Rating(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getTimestamp(4)
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GamestudioException("Error loading Rating of player", e);
        }

        if (rating == null)
            return 0;

        return rating.getRating();
    }

}
