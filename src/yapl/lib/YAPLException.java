package yapl.lib;

import yapl.Symbol;
import yapl.compiler.Node;
import yapl.interfaces.CompilerError;

public class YAPLException extends Exception implements CompilerError {

    private Symbol s;
    private Node n;
    private int error;
    private String programmName;

    int errorNumber;
    int line;
    int column;

    public YAPLException(Exception e) {
        super(e.getMessage());
    }

    public YAPLException(Symbol s, Node n, int error, String programmName) {
        super();
        this.s = s;
        this.n = n;
        this.error = error;
        this.programmName = programmName;
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
    public String getMessage() {

        String msg = "Error has occured: ";

        switch (errorNumber) {
            case CompilerError.SymbolExists:
                msg += "Symbol " + s.getName() + " already declared in curent scope (as " + s.getKindString() + ")";
            case CompilerError.IdentNotDecl:
                msg += "Identifier " + s.getName() + "not declared.";
            case CompilerError.SymbolIllegalUse:
                msg += "Illegal use of " + s.getKindString() + " " + s.getName();
            case CompilerError.EndIdentMismatch:
                msg += "End " + s.getName() + " does not match " + s.getKindString() + programmName;
            default:
                msg += "-";
        }

        msg += " at Line: " + line;
        msg += " /Column: " + column;

        return msg;
    }
}
