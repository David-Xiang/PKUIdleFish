package cn.edu.pku.course.database.idlefish.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.edu.pku.course.database.idlefish.mapper.TransactionResponseRowMapper;
import cn.edu.pku.course.database.idlefish.response.TransactionResponse;

@Repository
public class TransactionBasic {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	TransactionResponseRowMapper transactionResponseRowMapper;

	/**
	 * fetch all transactions <br>
	 */
	public TransactionResponse fetchAll() {
		String sql = "SELECT * FROM transaction";
		return jdbcTemplate.queryForObject(sql, transactionResponseRowMapper);
	}

}
