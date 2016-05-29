package idv.david.viewpagerex;

import java.io.Serializable;

public class TeamVO implements Serializable {
    private String name;
    //drawable's ID
    private int logo;

    public TeamVO() {

    }

    public TeamVO(int logo, String name) {
        this.logo = logo;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

}
