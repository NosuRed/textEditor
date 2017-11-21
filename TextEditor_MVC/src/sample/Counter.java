package sample;

public class Counter {


    private String[] wordCounter;

    public void wordCounter(String counter){
        this.wordCounter = counter.split(" ");
    }

    public int getWordCounter() {
        if (wordCounter[0].equals("")) {
            return 0;
    }else{
        return wordCounter.length;
        }
    }

    private String charCounter;

    public void charCounter(String value){
        value = value.replaceAll("\n", System.getProperty("line.separator"));
        this.charCounter = value;

    }

    public int getCharCounter() {
        return charCounter.length();
    }
}
