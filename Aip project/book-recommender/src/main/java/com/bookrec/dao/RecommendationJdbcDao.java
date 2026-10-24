package com.bookrec.dao;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/** Plain JDBC (no ORM): raw Connection / PreparedStatement / ResultSet. */
@Repository
public class RecommendationJdbcDao {

    private final DataSource dataSource;
    public RecommendationJdbcDao(DataSource dataSource) { this.dataSource = dataSource; }

    private static final String PERSONALISED_SQL =
        "SELECT b.id, b.title, b.author, b.genre, ROUND(AVG(r.score), 2) AS avg_score, COUNT(r.id) AS votes " +
        "FROM books b LEFT JOIN ratings r ON r.book_id = b.id " +
        "WHERE b.genre IN (SELECT b2.genre FROM ratings r2 JOIN books b2 ON b2.id = r2.book_id " +
        "                  WHERE r2.user_id = ? AND r2.score >= 4) " +
        "AND b.id NOT IN (SELECT book_id FROM ratings WHERE user_id = ?) " +
        "GROUP BY b.id, b.title, b.author, b.genre " +
        "ORDER BY avg_score DESC, votes DESC LIMIT 10";

    private static final String TOP_RATED_SQL =
        "SELECT b.id, b.title, b.author, b.genre, ROUND(AVG(r.score), 2) AS avg_score, COUNT(r.id) AS votes " +
        "FROM books b LEFT JOIN ratings r ON r.book_id = b.id " +
        "WHERE b.id NOT IN (SELECT book_id FROM ratings WHERE user_id = ?) " +
        "GROUP BY b.id, b.title, b.author, b.genre " +
        "ORDER BY avg_score DESC, votes DESC LIMIT 10";

    /** Genre-based recommendations from the user's 4-5 star ratings; falls back to top-rated books. */
    public List<Map<String, Object>> recommendFor(long userId) throws SQLException {
        List<Map<String, Object>> result = query(PERSONALISED_SQL, userId, userId);
        if (result.isEmpty()) result = query(TOP_RATED_SQL, userId);
        return result;
    }

    private List<Map<String, Object>> query(String sql, long... params) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setLong(i + 1, params[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", rs.getLong("id"));
                    m.put("title", rs.getString("title"));
                    m.put("author", rs.getString("author"));
                    m.put("genre", rs.getString("genre"));
                    m.put("avgScore", rs.getDouble("avg_score"));
                    m.put("votes", rs.getInt("votes"));
                    rows.add(m);
                }
            }
        }
        return rows;
    }

    /** Proves live database connectivity via JDBC metadata. */
    public Map<String, Object> connectionInfo() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            DatabaseMetaData md = con.getMetaData();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("connected", con.isValid(2));
            m.put("database", md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
            m.put("driver", md.getDriverName());
            m.put("url", md.getURL());
            m.put("user", md.getUserName());
            return m;
        }
    }
}
