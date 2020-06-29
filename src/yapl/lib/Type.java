package yapl.lib;

public class Type {

    /**
     * Checks if the two types t1 and t2 are compatible.
     * @param t1
     * @param t2
     * @return
     */
    public static boolean typeIsCompatible(Type t1, Type t2){

        //if both are arrays, the type and the length must be the same
        if(t1 instanceof ArrayType && t2 instanceof ArrayType) {

            if (((ArrayType) t1).getTypeOfArray().getClass() == ((ArrayType) t2).getTypeOfArray().getClass() && ((ArrayType) t1).getLength() == ((ArrayType) t2).getLength())
                return true;
        //if only one is an array, the value at the index of the array must be compatible with the non array type
        }else if(t1 instanceof ArrayType){
            ArrayType at = ((ArrayType)t1);
            if(at.getDeclSymbol() != null){
                if(at.getArrayDiff(at.getDeclSymbol().getType()).getClass() == t2.getClass()) return true;
            }
        //Same: if only one is an array, the value at the index of the array must be compatible with the non array type
        }else if(t2 instanceof ArrayType){
            ArrayType at = ((ArrayType)t2);
            if(at.getDeclSymbol() != null){
                if(at.getArrayDiff(at.getDeclSymbol().getType()).getClass() == t1.getClass()) return true;
            }
        //if both non array types, the type must be the same
        }else if(t1.getClass() == t2.getClass()){
            return true;
        //if return type of a procedure -> not implmented
        }else if(t2 instanceof ProcedureType){

        }

        return false;
    }


    public static void printType(Type t){
        if(t instanceof ArrayType){
            System.out.println("PRINTED TYPE: ARRAY of type" + ((ArrayType)t).typeOfArray.getClass() + " with length " + ((ArrayType)t).getLength());
        }else{
            System.out.println("PRINTED TYPE: " + t.getClass());
        }
    }
}
