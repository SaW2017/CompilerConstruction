package yapl.lib;

public class Type {

    public static boolean typeIsCompatible(Type t1, Type t2){

        if(t1 instanceof ArrayType && t2 instanceof ArrayType) {
            //System.out.println("Both array types: " + ((ArrayType) t1).getTypeOfArray().getClass() + " | " + ((ArrayType) t2).getTypeOfArray().getClass());
            if (((ArrayType) t1).getTypeOfArray().getClass() == ((ArrayType) t2).getTypeOfArray().getClass() && ((ArrayType) t1).getLength() == ((ArrayType) t2).getLength())
                return true;
        }else if(t1 instanceof ArrayType){
            ArrayType at = ((ArrayType)t1);
            if(at.getDeclSymbol() != null){
                if(at.getArrayDiff(at.getDeclSymbol().getType()).getClass() == t2.getClass()) return true;
            }
        }else if(t2 instanceof ArrayType){
            ArrayType at = ((ArrayType)t2);
            if(at.getDeclSymbol() != null){
                if(at.getArrayDiff(at.getDeclSymbol().getType()).getClass() == t1.getClass()) return true;
            }
        }else if(t1.getClass() == t2.getClass()){
            return true;
        }else if(t2 instanceof ProcedureType){

        }

        return false;
    }

//    public static boolean typeIsCompatible(Type t1, Type t2){
//
//    }

    public static void printType(Type t){
        if(t instanceof ArrayType){
            System.out.println("PRINTED TYPE: ARRAY of type" + ((ArrayType)t).typeOfArray.getClass() + " with length " + ((ArrayType)t).getLength());
        }else{
            System.out.println("PRINTED TYPE: " + t.getClass());
        }
    }
}
