package yapl.lib;

import yapl.Symbol;
import yapl.compiler.Node;
import yapl.interfaces.CompilerError;

public class YAPLException extends Exception implements CompilerError{

    int errorNumber;
    int line;
    int column;

    public YAPLException(Symbol s, Node n, int error, String programmName){

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

    @Override
    public String getMessage(){

        String msg = "Error has occured: ";

        switch (errorNumber){
            case CompilerError.SymbolExists: msg += "Symbol already declared.";
            case CompilerError.IdentNotDecl: msg += "Identifier not declared.";
            case CompilerError.SymbolIllegalUse: msg += "Illegal use of symbol.";
            case CompilerError.EndIdentMismatch: msg += "End identifier does not match program|procedure.";
            default: msg += "-";
        }

        msg += " at Line: " + line;
        msg += " /Column: " + column;

        return msg;
    }
}
