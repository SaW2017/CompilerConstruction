package yapl;

import yapl.lib.Type;

public class Symbol implements yapl.interfaces.Symbol {
    @Override
    public int getKind() {
        return 0;
    }

    @Override
    public String getKindString() {
        return null;
    }

    @Override
    public void setKind(int kind) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType(Type type) {

    }

    @Override
    public boolean isReference() {
        return false;
    }

    @Override
    public void setReference(boolean isReference) {

    }

    @Override
    public boolean isReadonly() {
        return false;
    }

    @Override
    public void setReadonly(boolean isReadonly) {

    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public void setGlobal(boolean isGlobal) {

    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public void setOffset(int offset) {

    }

    @Override
    public yapl.interfaces.Symbol getNextSymbol() {
        return null;
    }

    @Override
    public void setNextSymbol(yapl.interfaces.Symbol symbol) {

    }

    @Override
    public boolean getReturnSeen() {
        return false;
    }

    @Override
    public void setReturnSeen(boolean seen) {

    }
}
