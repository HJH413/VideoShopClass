package model.dao;

import model.VideoDao;
import model.vo.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
}
