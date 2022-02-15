package model.dao;

import model.vo.RentalVideo;
import model.vo.Video;

import java.sql.*;
import java.util.ArrayList;


public class RentModel {
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hjh";
	String pass = "0175";

	// 1. 드라이버로딩
	public RentModel() throws Exception{

		OracleConn.getInstance();
	}

	//반납 목록 출력
	public ArrayList selectList() throws Exception {
		//2.연결객체 가져오기
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");
			//3. sql문자
			String sql = " SELECT rv.video_num vnum, v.title vtitle, c.name cname, c.tel ctel, rv.rental_date +3 rtd, rv.return_video tf "
			+ " FROM rental_video rv "
			+ " inner join customer c "
			+ " ON rv.tel = c.tel "
			+ " inner join video v "
			+ " ON rv.video_num = v.video_num "
			+ " WHERE rv.return_video = 'F' ";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ArrayList list = new ArrayList();

			while (rs.next()) {
				ArrayList temp = new ArrayList();

				temp.add(rs.getString("vnum"));
				temp.add(rs.getString("vtitle"));
				temp.add(rs.getString("cname"));
				temp.add(rs.getString("ctel"));
				temp.add(rs.getString("rtd"));
				temp.add(rs.getString("tf"));


				list.add(temp);
			}

			return list;


		} finally {
			rs.close();
			ps.close();
			con.close();

		}


		//System.out.println("sql : " + sql); // 내가 입력한 sql문장이 맞는지 테스트
	}

	//반납하기
	public void returnVideo(int videonum) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;

			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");

			String sql = "UPDATE rental_video set return_video = 'T', return_date = sysdate WHERE video_num = ? and return_video = 'F'";

			ps = con.prepareStatement(sql);
			ps.setInt(1, videonum );

			ps.executeUpdate();

			ps.close();
			con.close();
	}

	// 전화번호 엔터하면 고객명 나오기
	public String telByReturn (String tel) throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String name = "";

		con = DriverManager.getConnection(url, user, pass);

		String sql = "SELECT * FROM customer WHERE tel=?";

		ps = con.prepareStatement(sql);
		ps.setString(1, tel);

		rs = ps.executeQuery();

		if (rs.next()) {
			name = rs.getString("name");
		}

		rs.close();
		ps.close();
		con.close();

		return name;

	}

	//대여하기

	public int newRentalVideo(String tel, int videoNum) throws Exception {

		int result = 0;

		Connection con = null;
		PreparedStatement ps = null;

		con = DriverManager.getConnection(url, user, pass);
		System.out.println("DB연결 성공");

		String sql = "INSERT INTO RENTAL_VIDEO ( rental_num, tel,video_num, rental_date ,return_video)  "
				+ "VALUES ( rental_number.NEXTVAL ,?, ? ,SYSDATE,'F' )";

		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		ps.setInt(2, videoNum);

		result = ps.executeUpdate();

		ps.close();
		con.close();

		return result;

	}

	
}
