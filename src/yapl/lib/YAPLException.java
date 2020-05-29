package yapl.lib;

import yapl.interfaces.CompilerError;

public class YAPLException extends Exception implements CompilerError {

    String msg;
    int errorNumber;
    int line;
    int column;

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