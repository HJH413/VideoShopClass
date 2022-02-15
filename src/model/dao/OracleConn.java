package model.dao;

public class OracleConn {

    static OracleConn oraCon = null; //변수 선언

    private OracleConn() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("드라이버 로딩은 단 한번");

    }

    public static OracleConn getInstance() throws Exception  {
        if(oraCon == null) {
           oraCon = new OracleConn();
        }
        return oraCon;
    }
}
// 싱글톤 패턴
// 여러 사용자가 동일한 클래스를 여러번 열개 할 필요가 없어서
// static 선언으로 메모리에 올려놓고
// 여러 사용자가 하나의 클래스를 사용하게 만드는 것