package model.vo;

public class RentalVideo {
    int videoNum;
    String tel;

    public RentalVideo() {
    }

    public RentalVideo(int videoNum, String tel) {
        this.videoNum = videoNum;
        this.tel = tel;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
