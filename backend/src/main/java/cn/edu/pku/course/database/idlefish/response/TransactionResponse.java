package cn.edu.pku.course.database.idlefish.response;

import java.util.List;

import cn.edu.pku.course.database.idlefish.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransactionResponse {

	public List<Transaction> transaction;

}
