package com.internousdev.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.internousdev.ecsite.util.DBConnector;
import com.internousdev.ecsite.util.DateUtil;

public class BuyItemCompleteDAO {

	public void buyItemInfo(String item_transaction_id,String total_price,String total_count,String user_master_id,String pay)
			throws SQLException{
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		DateUtil dateUtil = new DateUtil();
		String sql ="INSERT INTO user_buy_item_transaction"
				+ "(item_transaction_id,total_price,total_count,user_master_id,pay,insert_date)"
				+ "VALUES(?,?,?,?,?,?)";

		try{
			PreparedStatement pre = connection.prepareStatement(sql);
			pre.setString(1, item_transaction_id);
			pre.setString(2, total_price);
			pre.setString(3, total_count);
			pre.setString(4, user_master_id);
			pre.setString(5, pay);
			pre.setString(6, dateUtil.getDate());
			pre.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			connection.close();
		}
	}

}
