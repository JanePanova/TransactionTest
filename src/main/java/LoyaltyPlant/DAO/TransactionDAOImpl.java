package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.Transaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
public class TransactionDAOImpl implements TransactionDAO {

    Logger logger = Logger.getLogger(TransactionDAOImpl.class);

    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountDAO accountDAO;

    public TransactionDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void put(final Transaction transaction) {
        final String sql = "INSERT INTO transactions (idFrom, idTo, money, ts) VALUE (?, ?, ?, ?);";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, transaction.getFrom());
            ps.setInt(2, transaction.getTo());
            ps.setDouble(3, transaction.getMoney());
            ps.setLong(4, transaction.getTimestamp());
            return ps;
        });
    }

    @Override
    public void update(Transaction transaction) {
        final String sql = "UPDATE transactions SET idFrom = ?, idTo = ?, money = ? WHERE id = ?;";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transaction.getFrom());
            ps.setInt(2, transaction.getTo());
            ps.setDouble(3, transaction.getMoney());
            ps.setInt(4, transaction.getId());
            return ps;
        });
    }

    @Override
    public Transaction get(int id) {
        final String sql = "SELECT id, idFrom, idTo, money FROM transactions WHERE id = ? LIMIT 1;";
        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                return ps;
            }
        }, resultSet -> {
            if (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setFrom(resultSet.getInt("idFrom"));
                transaction.setTo(resultSet.getInt("idTo"));
                transaction.setMoney(resultSet.getDouble("money"));
                return transaction;
            }
            return null;
        });
    }

    @Override
    public List<Transaction> getAll(int start) {
        final String sql = "SELECT id, idFrom, idTo, money FROM transactions WHERE id < ? ORDER BY id DESC LIMIT 30;";
        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, start);
            return ps;
        }, (resultSet, i) -> {
            Transaction transaction = new Transaction();
            transaction.setId   (resultSet.getInt("id"));
            transaction.setFrom (resultSet.getInt("idFrom"));
            transaction.setTo   (resultSet.getInt("idTo"));
            transaction.setMoney(resultSet.getDouble("money"));
            return transaction;
        });
    }

    @Override
    public List<Transaction> getFromUser(int id, long from) {
        final String sql = "SELECT id, idFrom, idTo, money, ts, blocked FROM transactions WHERE idFrom = ? AND ts > ?;";
        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setLong(2, from);
            return ps;
        }, (resultSet, i) -> {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setFrom(resultSet.getInt("idFrom"));
            transaction.setTo(resultSet.getInt("idTo"));
            transaction.setMoney(resultSet.getDouble("money"));
            transaction.setBlocked(resultSet.getBoolean("blocked"));
            transaction.setTimestamp(resultSet.getLong("ts"));
            return transaction;
        });
    }

    @Override
    public List<Transaction> getToUser(int id, long from) {
        final String sql = "SELECT id, idFrom, idTo, money, ts, blocked FROM transactions WHERE idTo = ? AND ts > ?;";
        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setLong(2, from);
            return ps;
        }, (resultSet, i) -> {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setFrom(resultSet.getInt("idFrom"));
            transaction.setTo(resultSet.getInt("idTo"));
            transaction.setMoney(resultSet.getDouble("money"));
            transaction.setBlocked(resultSet.getBoolean("blocked"));
            transaction.setTimestamp(resultSet.getLong("ts"));
            return transaction;
        });
    }

    @Override
    public List<Transaction> getTransactionsForUser(int id, long from) {
        final String sql = "SELECT id, idFrom, idTo, money, ts, blocked FROM transactions WHERE (idTo = ? OR idFrom = ?) AND ts > ? ORDER BY ts ASC;";
        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setLong(3, from);
            return ps;
        }, (resultSet, i) -> {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setFrom(resultSet.getInt("idFrom"));
            transaction.setTo(resultSet.getInt("idTo"));
            transaction.setMoney(resultSet.getDouble("money"));
            transaction.setBlocked(resultSet.getBoolean("blocked"));
            transaction.setTimestamp(resultSet.getLong("ts"));
            return transaction;
        });
    }
}
