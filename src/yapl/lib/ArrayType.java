package yapl.lib;

import yapl.symbol.Symbol;

public class ArrayType extends Type {
    Symbol declSymbol;
    Type typeOfArray;
    int length;

    public Type getTypeOfArray() {
        return typeOfArray;
    }

    public void setTypeOfArray(Type typeOfArray) {
        this.typeOfArray = typeOfArray;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Symbol getDeclSymbol() {
        return declSymbol;
    }

    public void setDeclSymbol(Symbol declSymbol) {
        this.declSymbol = declSymbol;
    }

    public Type getArrayDiff(Type t){
        System.out.println(">Difference of array");
        if(t instanceof ArrayType){
            System.out.println(">>Compared type is array");
            System.out.println(((ArrayType) t).length + " == " + this.length);
            if(((ArrayType) t).length == this.length) return this.typeOfArray;
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(((ArrayType) t).getLength() - this.getLength());
            return arrayType;
        }else{
            System.out.println(">>Compared type is not an array");
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(this.length);
            return arrayType;
        }
    }

    @Override
    public String toString() {
        return "ArrayType{" +
                "declSymbol=" + declSymbol +
                ", typeOfArray=" + typeOfArray +
                ", length=" + length +
                '}';
    }
}
