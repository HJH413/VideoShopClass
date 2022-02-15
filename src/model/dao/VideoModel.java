package model.dao;

import model.VideoDao;
import model.vo.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VideoModel implements VideoDao {

	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "hjh";
	String pass = "0175";

	public VideoModel() throws Exception {
		// 1. 드라이버로딩
		OracleConn.getInstance();
	}

	//비디오 입고
	public void insertVideo(Video vo, int count) throws Exception {
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
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
			// 5. sql 전송
			for (int i = 0; i < count; i++) {
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

		try {

			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");
			//3. sql문자
			String[] columns = {"title", "director"};
			String sql = " SELECT video_num, genre, title, director, actor "
					+ " FROM video "
					+ " WHERE " + columns[idx] + " like '%" + word + "%' ";  // 컬럼 배열, 입력 단어

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ArrayList list = new ArrayList();

			while (rs.next()) {
				ArrayList temp = new ArrayList();

				temp.add(rs.getString("video_num"));
				temp.add(rs.getString("genre"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("director"));
				temp.add(rs.getString("actor"));

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

	//비디오 검색
	public Video selectByVideoNum(int vNum) throws Exception {
		Video v = new Video();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");

			String sql = "SELECT * FROM video WHERE video_num = ? ";

			ps = con.prepareStatement(sql);
			ps.setInt(1, vNum);

			rs = ps.executeQuery();

			if (rs.next()) {
				v.setVideoNo(rs.getInt("video_num"));
				v.setGenre(rs.getString("genre"));
				v.setVideoName(rs.getString("title"));
				v.setDirector(rs.getString("director"));
				v.setActor(rs.getString("actor"));
				v.setExp(rs.getString("ex"));
			}

		} finally {
			rs.close();
			ps.close();
			con.close();
		}

		return v;
	}

	//비디오 수정
	public int modifyVideo(Video v) throws Exception { // 수정해야함
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");

			String sql = "UPDATE video SET genre = ? , title  = ? , director  = ? , actor  = ? , ex  = ?  WHERE video_num = ?";

			ps = con.prepareStatement(sql);

			ps.setString(1, v.getGenre());
			ps.setString(2, v.getVideoName());
			ps.setString(3, v.getDirector());
			ps.setString(4, v.getActor());
			ps.setString(5, v.getExp());
			ps.setInt(6, v.getVideoNo());

			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");

		} finally {
			ps.close();
			con.close();
		}
		return 0;
	}

	// 비디오 삭제
	public int delete(int video_num) throws Exception {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");
			String sql = "DELETE FROM video WHERE video_num = ? ";

			ps = con.prepareStatement(sql);
			ps.setInt(1, video_num);

			result = ps.executeUpdate();
			System.out.println(result + "행을 실행");
		} finally {
			ps.close();
			con.close();
		}
		return 0;
	}

} // end of video model
