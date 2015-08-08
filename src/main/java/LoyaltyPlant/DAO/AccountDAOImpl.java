package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.AccountWithWallet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Created by JaneJava on 5/3/15.
 */
public class AccountDAOImpl implements AccountDAO {
    static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WalletDAO walletDAO;

    public AccountDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account get(int id) {
        String sql = String.format("SELECT id, name, blocked FROM users WHERE id = %d LIMIT 1;", id);
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Account account = new AccountWithWallet();
                account.setId(resultSet.getInt("id"));
                account.setName(resultSet.getString("name"));
                account.setBlocked(resultSet.getBoolean("blocked"));
                return account;
            }
            return null;
        });
    }

    @Override
    public List<Account> getAll(int start) {
        String sql = String.format("SELECT id, name, blocked FROM users WHERE blocked = FALSE AND id < %d ORDER BY id DESC LIMIT 30;", start);
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Account account = new Account();
            account.setId(resultSet.getInt("id"));
            account.setName(resultSet.getString("name"));
            account.setBlocked(resultSet.getBoolean("blocked"));
            return account;
        });
    }

    @Override
    public void put(final Account account) {
        final String sql = "INSERT INTO users (name) VALUE (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getName());
            return ps;
        }, keyHolder);
        walletDAO.put(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE users SET name = ?, blocked = ? WHERE id = ?;";
        jdbcTemplate.update(sql, account.getName(), account.isBlocked(), account.getId());
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE users SET blocked = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
