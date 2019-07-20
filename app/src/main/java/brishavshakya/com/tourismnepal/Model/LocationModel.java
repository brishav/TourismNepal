package brishavshakya.com.tourismnepal.Model;

public class LocationModel {
    private String id;
    private int pid;
    private int did;
    private  String name;
    private String imageurl;
    private String description;

    public LocationModel(String id, String name, String imageurl,String description,int pid,int did) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.pid = pid;
        this.did = did;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationModel(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDid() {
        return did;
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
