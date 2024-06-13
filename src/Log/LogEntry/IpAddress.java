package Log.LogEntry;

import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IpAddress {
    private String ip;
    private int numberOfRequests;
    private String countryName;
    private ImageView image;
    
    public IpAddress(String ip){
        this.ip = ip;
    }

    public IpAddress(String ip, int requests){
        this.ip = ip;
        this.numberOfRequests = requests;
    }

    public void setCountryName(String name){
        this.countryName = name;
    }

    public void setNumOfRequets(int requests){
        this.numberOfRequests = requests;
    }

    public void setImage(String path){
        try{
            InputStream stream = new FileInputStream(path);
            Image imag = new Image(stream);
            this.image = new ImageView();
            this.image.setImage(imag);
            this.image.setX(16);
            this.image.setY(12);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getCountryName(){
        return this.countryName;
    }

    public String getIp(){
        return this.ip;
    }

    public int getNumberOfRequests(){
        return this.numberOfRequests;
    }  
    
    public ImageView getImage(){
        return this.image;
    }
}
