package cn.edu.pku.course.database.idlefish.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.edu.pku.course.database.idlefish.entity.Transaction;
import cn.edu.pku.course.database.idlefish.response.TransactionResponse;

@Component
public class TransactionResponseRowMapper implements RowMapper<TransactionResponse> {

	@Override
	public TransactionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		while (rs.next()) {
			transactions.add(new Transaction(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					rs.getDouble(5), rs.getString(6)));
		}
		return new TransactionResponse(transactions);
	}
}
