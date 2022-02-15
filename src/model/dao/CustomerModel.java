package model.dao;

import model.CustomerDao;
import model.vo.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerModel implements CustomerDao{

	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hjh";
	String pass = "0175";

	public CustomerModel() throws Exception{
	 	// 1. 드라이버로딩
		OracleConn.getInstance();
	}
	
	public void insertCustomer(Customer vo) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			// 3. sql 문장 만들기
			String sql = "INSERT INTO customer(tel,name,sub_tel,adr,email) VALUES(?, ?, ?, ?, ?)";
			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getCustTel1());
			ps.setString(2, vo.getCustName());
			ps.setString(3, vo.getCustTel2());
			ps.setString(4,vo.getCustAddr());
			ps.setString(5,vo.getCustEmail());
			// 5. sql 전송
			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");
			// 6. 닫기
		} finally {
			ps.close();
			con.close();
		}
	}
	
	public Customer selectByTel(String tel) throws Exception{
		Customer dao = new Customer();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			String sql = "SELECT * FROM CUSTOMER WHERE tel = ? ";

			ps = con.prepareStatement(sql);
			ps.setString(1, tel);

			rs = ps.executeQuery();

			if(rs.next()) {
				dao.setCustName(rs.getString("NAME"));
				dao.setCustTel1(rs.getString("TEL"));
				dao.setCustTel2(rs.getString("SUB_TEL"));
				dao.setCustAddr(rs.getString("ADR"));
				dao.setCustEmail(rs.getString("EMAIL"));
			}

		}finally {
			rs.close();
			ps.close();
			con.close();
		}
		
		return dao;
		
	}
	
	public int updateCustomer(Customer vo) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			String sql = "UPDATE customer set name = ?,  sub_tel = ?, adr = ?, email = ? WHERE tel = ? ";

			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getCustName());
			ps.setString(2, vo.getCustTel2());
			ps.setString(3, vo.getCustAddr());
			ps.setString(4, vo.getCustEmail());
			ps.setString(5, vo.getCustTel1());

			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");

		} finally {
			ps.close();
			con.close();
		}

		return 0;
	}
}
