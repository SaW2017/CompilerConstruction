package yapl.lib;

public class YAPLException extends Exception {

    String text;

    public YAPLException(String text){
        this.text = text;
    }
}
