package capstone.tcp.server.domain;

import java.io.Serializable;

public class SnapShotData implements Serializable {

    private static final long serialVersionUID = -5417854095335550050L;
    
    public SnapShotData() {}
    
    private int imageIndex;
    private byte[] imageData;
    private String imageDataHex;
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SnapShotData [imageIndex=");
        builder.append(imageIndex);
        builder.append(", imageData=");
        builder.append(imageData);
        builder.append(", imageDataHex=");
        builder.append(imageDataHex);
        builder.append("]");
        return builder.toString();
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageDataHex() {
        return imageDataHex;
    }

    public void setImageDataHex(String imageDataHex) {
        this.imageDataHex = imageDataHex;
    }


}
