package yapl;

import yapl.lib.Type;

import java.util.LinkedList;

public class Symbol implements yapl.interfaces.Symbol {

    int kind = -1;
    String name;
    Type type;
    boolean isReference = false;
    boolean isReadonly = false;
    boolean isGlobal = false;
    int offset = 0;
    boolean symbolSeen = false;

    public LinkedList<Symbol> symbolLinkedList = new LinkedList<Symbol>();

    public Symbol() {
    }

    @Override
    public int getKind() {
        return kind;
    }

    @Override
    public String getKindString() {
        switch (kind){
            case 0: return "Program";
            case 1: return "Procedure";
            case 2: return "Variable";
            case 3: return "Constant";
            case 4: return "Typename";
            case 5: return "Field";
            case 6: return "Parameter";
            default: return null;
        }
    }

    @Override
    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public Type getType() {
        return type;
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
        if (symbolLinkedList.isEmpty()) return null;
        return symbolLinkedList.pop();
    }

    public void setNextSymbol(yapl.interfaces.Symbol symbol) {
        symbolLinkedList.add((Symbol) symbol);
    }

    @Override
    public boolean getReturnSeen() {
        return symbolSeen;
    }

    @Override
    public void setReturnSeen(boolean seen) {
        symbolSeen = seen;
    }
}
