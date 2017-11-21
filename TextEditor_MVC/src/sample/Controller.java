package sample;

import java.io.File;


public class Controller {
    private File textIn;

    private String value;

    private String parts;

    private String cutValue;



    public void setTextIn(File textIn) {
        this.textIn = textIn;
    }

    public File getTextIn() {
        return textIn;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getParts() {
        return parts;
    }


    public void setCutValue(String cutValue) {
        this.cutValue = cutValue;
    }

    public String getCutValue() {
        return cutValue;
    }
}
