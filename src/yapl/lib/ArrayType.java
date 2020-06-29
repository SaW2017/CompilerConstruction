package yapl.lib;

import yapl.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public class ArrayType extends Type {

    Symbol declSymbol;  // it`s the name of the array at declaration
    Type typeOfArray;   // what kind of (primitive) Datatype the array stores
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

    // checks if given Type t is compatible with ArrayType or ProcedureType
    // if its Arraytype then it checks also if they have the same length
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

    // compares Arrays (length) and returns if they have the same length
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
            // if the length differs we create a new arrayType and set its Type to the type from this
            // and length with difference from this array and t.array
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(Math.abs(((ArrayType) t).getLength() - this.getLength()));
            System.out.println(">Returning array of type: " + this.typeOfArray + " with length: " + Math.abs(((ArrayType) t).getLength() - this.getLength()));
            System.out.println("[END Getting difference of array]");
            return arrayType;
        }else{
            // if compared Element is not an array, we create a new Arraytype and set its type to the given one
            System.out.println(">Compared type is not an array");
            ArrayType arrayType = new ArrayType();
            arrayType.setTypeOfArray(this.getTypeOfArray());
            arrayType.setLength(this.length);
            System.out.println("[END Getting difference of array]");
            return arrayType;
        }
    }

    // creates String with information about the Array
    @Override
    public String toString() {
        return "ArrayType{" +
                "declSymbol=" + declSymbol +
                ", typeOfArray=" + typeOfArray +
                ", length=" + length +
                '}';
    }

    // creates a new ArrayType with the same Attributes as t,
    // if t is an arrayType, else ArrayType has Type of t and length 1

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
