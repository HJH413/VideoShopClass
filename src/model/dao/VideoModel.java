package model.dao;

import model.VideoDao;
import model.vo.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VideoModel implements VideoDao{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hjh";
	String pass = "0175";
	
	public VideoModel() throws Exception{
		// 1. 드라이버로딩
		Class.forName(driver);
	}

	public void insertVideo(Video vo, int count) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			// 3. sql 문장 만들기
			String sql = "INSERT INTO video (video_num, genre, title, director, actor, ex) VALUES ( video_number.nextval, ?, ?, ?, ?, ?)";
			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getGenre());
			ps.setString(2, vo.getVideoName());
			ps.setString(3, vo.getDirector());
			ps.setString(4, vo.getActor());
			ps.setString(5, vo.getExp());
			// 5. sql 전송'
			for (int i = 0; i<count; i++) {
				ps.executeUpdate();
			}
			// 6. 닫기
		} finally {
			ps.close();
			con.close();
		}
	}


	/*
	함수명 : searchVideo
	인자 : 검색 카테고리를 선택한 인덱스, 검색시 사용할 입력된 단어
	리턴값 :
	역할 :
	 */

	public ArrayList searchVideo(int idx, String word) throws Exception {
		//2.연결객체 가져오기
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Video v = new Video();

		try{

			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			//3. sql문자
			String [] columns = {"title", "director"};
			String sql = " SELECT video_num, genre, title, director, actor "
					+ " FROM video "
					+ " WHERE " + columns[idx] + " like '%" + word + "%' " ;  // 컬럼 배열, 입력 단어

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ArrayList list = new ArrayList();

			while(rs.next()) {
				ArrayList temp = new ArrayList();

				temp.add(rs.getString("video_num"));
				temp.add(rs.getString("genre"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("director"));
				temp.add(rs.getString("actor"));

				list.add(temp);
			}

			return list;

		}finally {
			rs.close();
			ps.close();
			con.close();

		}

		//System.out.println("sql : " + sql); // 내가 입력한 sql문장이 맞는지 테스트
	}
}
