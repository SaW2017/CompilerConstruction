package yapl.lib;

public class ArrayType extends Type {
    ArrayType declSymbol;
    Type arrayType;
    int length;

    public Type getArrayType() {
        return arrayType;
    }

    public void setArrayType(Type arrayType) {
        this.arrayType = arrayType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayType getDeclSymbol() {
        return declSymbol;
    }

    public void setDeclSymbol(ArrayType declSymbol) {
        this.declSymbol = declSymbol;
    }
}
