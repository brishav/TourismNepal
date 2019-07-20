package brishavshakya.com.tourismnepal.Model;

public class DistrictModel {
    private String id;
    private int pid;
    private  String name;
    private String imageurl;

    public DistrictModel(String id, String name, String imageurl,int pid) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.pid = pid;
    }
    public DistrictModel(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getPid() {
        return pid;
    }
}
