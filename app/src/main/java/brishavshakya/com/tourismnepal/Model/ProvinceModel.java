package brishavshakya.com.tourismnepal.Model;

public class ProvinceModel {
    public String ProvinceName, Color;
    int ProvinceId;

    public ProvinceModel(Object o){

    }
    public ProvinceModel(int ProvinceId, String ProvinceName, String Color){
        this.ProvinceId = ProvinceId;
        this.ProvinceName = ProvinceName;
        this.Color = Color;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public String getColor(){
        return Color;
    }
}
