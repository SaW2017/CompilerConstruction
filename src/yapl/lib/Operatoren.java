package yapl.lib;

public class Operatoren {
// it`s helper class for using the Operators

//    Arithmetic Operators
    public static final String Add       = "+";
    public static final String Sub       = "-";
    public static final String Mult      = "*";
    public static final String Div       = "/";
    public static final String Mod       = "%";

    public static final String[] ArithmeticOps = {Add, Sub, Mult, Div, Mod};

//    equalExp
    public static final String Equal            = "==";
    public static final String Unequal          = "!=";
    public static final String GreaterThan      = ">";
    public static final String SmallerThan      = "<";
    public static final String GreaterOrEqual   = ">=";
    public static final String LessOrEqual      = "<=";

    public static final String[] EqualExp = {Equal, Unequal, GreaterThan, SmallerThan, GreaterOrEqual, LessOrEqual};

//    booleOp
    public static final String And  = "And";
    public static final String Or   = "Or";

    public static final String[] BinaryExp = {Add, Sub, Mult, Div, Mod, Equal, Unequal, GreaterThan, SmallerThan, GreaterOrEqual, LessOrEqual, And, Or};
}
