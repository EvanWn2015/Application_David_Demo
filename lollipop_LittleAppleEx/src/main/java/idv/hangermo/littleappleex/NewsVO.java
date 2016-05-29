package idv.hangermo.littleappleex;

import java.io.Serializable;

public class NewsVO implements Serializable {
    private String title;
    private String time;
    private String url;

    public NewsVO() {
        super();
    }

    public NewsVO(String title, String time, String url) {
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
