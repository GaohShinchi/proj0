package com.internousdev.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.internousdev.ecsite.dto.MyPageDTO;
import com.internousdev.ecsite.util.DBConnector;

public class MyPageDAO {

	public ArrayList<MyPageDTO> getMyPageUserInfo(String item_transaction_id,String user_master_id) throws SQLException{
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		ArrayList<MyPageDTO> myPageDTO = new ArrayList<MyPageDTO>();
//userbuyitem(ubit)のプライマリーid(別でitemidがある)、iteminfo(iit)のアイテム名、ubitの合計価格(iteminfoの価格、個数で自動計算)、ubitの合計個数、ubitの支払い方法、ubitの時間
//FROM(場所指定)、userbuyitem(ubit)で定義、iteminfo(iit)で定義、left join(ubit優先表示)
//結合(ON ubit id,iit id)、WHEREで条件絞込(変数と同一のubit:itemidとubit:userマスターid)、降順表示
		String sql="SELECT ubit.id,iit.item_name,ubit.total_price,ubit.total_count,ubit.pay,ubit.insert_date FROM user_buy_item_transaction ubit LEFT JOIN item_info_transaction iit ON ubit.item_transaction_id=iit.id WHERE ubit.item_transaction_id = ? AND ubit.user_master_id = ? ORDER BY insert_date DESC";

	try{
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, item_transaction_id);
		preparedStatement.setString(2, user_master_id);

		ResultSet result = preparedStatement.executeQuery();

		while(result.next()){
			MyPageDTO dto=new MyPageDTO();
			dto.setId(result.getString("id"));
			dto.setItemName(result.getString("item_name"));
			dto.setTotalPrice(result.getString("total_price"));
			dto.setTotalCount(result.getString("total_count"));
			dto.setPayment(result.getString("pay"));
			dto.setInsert_date(result.getString("insert_date"));
			myPageDTO.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			connection.close();
		}
		return myPageDTO;
	}
	public int buyItemHistoryDelete(
			String item_transaction_id,
			String user_master_id)
	throws SQLException{
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		String sql =
				"DELETE FROM user_buy_item_transaction WHERE item_transaction_id = ? AND user_master_id = ?";
		PreparedStatement preparedStatement;
		int result = 0;
		try{
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, item_transaction_id);
			preparedStatement.setString(2, user_master_id);

			result = preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			connection.close();
		}
		return result;
	}
}
