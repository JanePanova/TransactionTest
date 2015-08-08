package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Wallet;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by JaneJava on 5/3/15.
 */
public class WalletDAOImpl implements WalletDAO{
    static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    public WalletDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Wallet get(int id) {
        String sql = "SELECT id, money, lastUpdate FROM wallets WHERE id = " + id + " LIMIT 1;";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getInt("id"));
                wallet.setMoney(resultSet.getDouble("money"));
                wallet.setLastUpdate(resultSet.getLong("lastUpdate"));
                return wallet;
            }
            return null;
        });
    }

    @Override
    public void put(int id) {
        String sql = "INSERT INTO wallets(id) VALUE(?);";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Wallet wallet) {
        String sql = "UPDATE wallets SET money = ?, lastUpdate = ? WHERE id = ?";
        jdbcTemplate.update(sql, wallet.getMoney(), wallet.getLastUpdate(), wallet.getId());
    }
}
