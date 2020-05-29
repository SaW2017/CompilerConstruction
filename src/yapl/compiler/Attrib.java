package yapl.compiler;

import yapl.lib.Type;
import yapl.symbol.Symbol;

public class Attrib implements yapl.interfaces.Attrib {

    private byte kind;
    private Type type;
    private boolean isConstant;
    private boolean isReadonly;
    private boolean isGlobal;
    private int offset;
    private byte register;

    int line;
    int column;

    public Attrib() {
    }

    public Attrib(Symbol sym){

    }

    public Attrib(Type type){
        this.type = type;
    }

    public Attrib(Type type, int line, int column){
        this.type = type;
        this.line = line;
        this.column = column;
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

    @Override
    public byte getKind() {
        return this.kind;
    }

    @Override
    public void setKind(byte kind) {
        this.kind = kind;
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
    public boolean isConstant() {
        return isConstant;
    }

    @Override
    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
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
    public byte getRegister() {
        return register;
    }

    @Override
    public void setRegister(byte register) {
        this.register = register;
    }
}
