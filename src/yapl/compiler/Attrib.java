package yapl.compiler;

import yapl.lib.Type;

public class Attrib implements yapl.interfaces.Attrib {
    @Override
    public byte getKind() {
        return 0;
    }

    @Override
    public void setKind(byte kind) {

    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType(Type type) {

    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public void setConstant(boolean isConstant) {

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
    public byte getRegister() {
        return 0;
    }

    @Override
    public void setRegister(byte register) {

    }
}
