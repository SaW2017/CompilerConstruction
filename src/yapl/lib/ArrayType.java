package yapl.lib;

import yapl.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

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

    public boolean isCompatible(Type t){

        if(t instanceof ArrayType){
            if (this.getTypeOfArray().getClass() == ((ArrayType) t).getTypeOfArray().getClass() && ((ArrayType) this).getLength() == ((ArrayType) t).getLength())
                return true;
        } else if(t instanceof RecordType){

        } else if(t instanceof ProcedureType){
            if(this.getTypeOfArray().getClass() == ((ProcedureType) t).getReturnType().getClass()) return true;
        }

        return false;
    }

    public Type getArrayDiff(Type t){
        System.out.println("\n[START Getting difference of array]");
        Type.printType(this);
        Type.printType(t);
        if(t instanceof ArrayType){
            System.out.println(">Compared type is array");
            System.out.println(((ArrayType) t).length + " == " + this.length);
            if(((ArrayType) t).length == this.length) {
                System.out.println(">Returning type of array: " + this.typeOfArray);
                return this.typeOfArray;
            }
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(Math.abs(((ArrayType) t).getLength() - this.getLength()));
            System.out.println(">Returning array of type: " + this.typeOfArray + " with length: " + Math.abs(((ArrayType) t).getLength() - this.getLength()));
            System.out.println("[END Getting difference of array]");
            return arrayType;
        }else{
            System.out.println(">Compared type is not an array");
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(this.length);
            System.out.println("[END Getting difference of array]");
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

    public static ArrayType buildArrayTypeFromSelector(Type t){
        ArrayType at = new ArrayType();

        if(t instanceof ArrayType){
            at.setTypeOfArray(((ArrayType) t).typeOfArray);
            at.setLength(((ArrayType) t).getLength());
        }else{
            at.setLength(1);
            at.setTypeOfArray(t);
        }

        return at;
    }
}
