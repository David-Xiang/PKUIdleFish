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
	 * fetch transactions <br>
	 * result will be ordered using *order* and divided by page using pageNum and
	 * itemPerPage if pageNum > 0 <br>
	 */
	public TransactionResponse fetch(String order, int pageNum, int itemPerPage) {
		String limit = pageNum > 0 ? "LIMIT " + (pageNum - 1) * itemPerPage + ", " + itemPerPage : "";
		String sql = "SELECT * FROM transaction " + order + " " + limit;
		return jdbcTemplate.queryForObject(sql, transactionResponseRowMapper);
	}

}
