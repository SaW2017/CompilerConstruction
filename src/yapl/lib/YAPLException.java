package yapl.lib;

import yapl.interfaces.CompilerError;

public class YAPLException extends Exception implements CompilerError {

    String msg;  // errormsg
    int errorNumber; // what kind of error it is
    // line & column in which error occured
    int line;
    int column;

    // differents overloaded constructors for different purposes
    public YAPLException(){}
    public YAPLException(String msg){ this.msg = msg; }
    public YAPLException(String msg, int errorNumber){
        this.msg = msg;
        this.errorNumber = errorNumber;
    }

    public YAPLException(String msg, int errorNumber, int line, int column) {
        this.msg = msg;
        this.errorNumber = errorNumber;
        this.line = line;
        this.column = column;
    }

    @Override
    public int errorNumber() {
        return errorNumber;
    }

    @Override
    public int line() {
        return line;
    }

    @Override
    public int column() {
        return column;
    }

    public String getMessage(){
        return this.msg;
    }
}
