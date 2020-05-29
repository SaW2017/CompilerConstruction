package yapl.lib;

public class Type {

    public static boolean typeIsCompatible(Type t1, Type t2){

        if(t1 instanceof ArrayType && t2 instanceof ArrayType) {
            //System.out.println("Both array types: " + ((ArrayType) t1).getTypeOfArray().getClass() + " | " + ((ArrayType) t2).getTypeOfArray().getClass());
            if (((ArrayType) t1).getTypeOfArray().getClass() == ((ArrayType) t2).getTypeOfArray().getClass() && ((ArrayType) t1).getLength() == ((ArrayType) t2).getLength())
                return true;
        }else if(t1 instanceof ArrayType){

        }else if(t2 instanceof ArrayType){

        }else if(t1.getClass() == t2.getClass()){
            return true;
        }

        return false;
    }

//    public static boolean typeIsCompatible(Type t1, Type t2){
//
//    }
}
