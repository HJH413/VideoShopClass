package model;

import model.vo.Video;

public interface VideoDao {
	public void insertVideo(Video vo, int count) throws Exception;
}
