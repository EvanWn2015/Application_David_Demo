package idv.evan.my_spotex8_1;

import java.io.Serializable;

/**
 * Created by 淳彥 on 2015/10/31.
 */
public class SpotVO implements Serializable {

    private static final long serialVersionUID = 3142967049066090897L;
    private int spot_id;
    private String spot_name;
    private String spot_web;
    private String spot_location;
    private byte[] spot_pic;

    public SpotVO(String name, String web, String location, byte[] pic) {}

    public SpotVO(int spot_id, String spot_name, String spot_web, String spot_location, byte[] spot_pic) {
        this.spot_id = spot_id;
        this.spot_name = spot_name;
        this.spot_web = spot_web;
        this.spot_location = spot_location;
        this.spot_pic = spot_pic;
    }

    public int getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(int spot_id) {
        this.spot_id = spot_id;
    }

    public String getSpot_name() {
        return spot_name;
    }

    public void setSpot_name(String spot_name) {
        this.spot_name = spot_name;
    }

    public String getSpot_web() {
        return spot_web;
    }

    public void setSpot_web(String spot_web) {
        this.spot_web = spot_web;
    }

    public String getSpot_location() {
        return spot_location;
    }

    public void setSpot_location(String spot_location) {
        this.spot_location = spot_location;
    }

    public byte[] getSpot_pic() {
        return spot_pic;
    }

    public void setSpot_pic(byte[] spot_pic) {
        this.spot_pic = spot_pic;
    }
}
