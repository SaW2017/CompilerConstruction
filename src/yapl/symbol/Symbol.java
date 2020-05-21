package yapl.symbol;

import yapl.lib.Type;

public class Symbol implements yapl.interfaces.Symbol {

    public Symbol(int kind, String name, int line, int column) {
        this.kind = kind;
        this.name = name;
        this.line = line;
        this.column = column;
    }

    int kind = -1;
    String name;
    Type type;
    boolean isReference;
    boolean isReadonly;
    boolean isGlobal;
    int offset;
    yapl.interfaces.Symbol nextSymbol;
    boolean returnSeen;

    private int line;
    private int column;



    public int getKind() {
        return this.kind;
    }


    public String getKindString() {
        switch (kind) {
            case 0:
                return "Program";
            case 1:
                return "Procedure";
            case 2:
                return "Variable";
            case 3:
                return "Constant";
            case 4:
                return "Typename";
            case 5:
                return "Field";
            case 6:
                return "Parameter";
            case 7:
                return "Predefined Procedure";
            default:
                return null;
        }
    }


    public void setKind(int kind) {
        this.kind = kind;
    }


    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean isReference() {
        return isReference;
    }

    @Override
    public void setReference(boolean isReference) {
        this.isReference = isReference;
    }

    @Override
    public boolean isReadonly() {
        return isReadonly;
    }

    @Override
    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    @Override
    public boolean isGlobal() {
        return isGlobal;
    }

    @Override
    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public yapl.interfaces.Symbol getNextSymbol() {
        return nextSymbol;
    }

    @Override
    public void setNextSymbol(yapl.interfaces.Symbol symbol) {
        this.nextSymbol = symbol;
    }

    @Override
    public boolean getReturnSeen() {
        return returnSeen;
    }

    @Override
    public void setReturnSeen(boolean seen) {
        this.returnSeen = seen;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
