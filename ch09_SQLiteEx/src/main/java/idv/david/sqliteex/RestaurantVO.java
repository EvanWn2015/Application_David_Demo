package idv.david.sqliteex;

import java.io.Serializable;

public class RestaurantVO implements Serializable {
    //檢查序列化的版本ID (由Android Studio自動產生即可)
    private static final long serialVersionUID = 8514429432204663683L;
    private int rest_id;
    private String rest_name;
    private String rest_web;
    private String rest_phone;
    private String rest_speciality;
    private byte[] rest_pic;

    public RestaurantVO() {

    }

    public RestaurantVO(int rest_id, String rest_name, String rest_web, String rest_phone, String rest_speciality, byte[] rest_pic) {
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.rest_web = rest_web;
        this.rest_phone = rest_phone;
        this.rest_speciality = rest_speciality;
        this.rest_pic = rest_pic;
    }

    //id為資料庫自動產生，無需讓使用者輸入id資料
    public RestaurantVO(String rest_name, String rest_web, String rest_phone, String rest_speciality, byte[] rest_pic) {
        this(0, rest_name, rest_web, rest_phone, rest_speciality, rest_pic);
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getRest_web() {
        return rest_web;
    }

    public void setRest_web(String rest_web) {
        this.rest_web = rest_web;
    }

    public String getRest_phone() {
        return rest_phone;
    }

    public void setRest_phone(String rest_phone) {
        this.rest_phone = rest_phone;
    }

    public String getRest_speciality() {
        return rest_speciality;
    }

    public void setRest_speciality(String rest_speciality) {
        this.rest_speciality = rest_speciality;
    }

    public byte[] getRest_pic() {
        return rest_pic;
    }

    public void setRest_pic(byte[] rest_pic) {
        this.rest_pic = rest_pic;
    }
}
