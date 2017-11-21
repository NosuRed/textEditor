package sample;

public class CutButton {
    private String value;
    public void removeWhiteSpace(String value){
        this.value = value;
    }

    public String getValue() {
        return value.replaceAll("\\s+", " ");
    }
}
