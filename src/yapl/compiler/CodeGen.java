package yapl.compiler;

import yapl.compiler.Attrib;
import yapl.impl.BackendMJ;
import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CodeGen {

    String label;
    //generates the code
    BackendMJ be = new BackendMJ();
    //name of the code file
    String filename = "";
    String outputFileName;

    public CodeGen() {
    }
    
    public String newLabel() {
        return UUID.randomUUID().toString();
    }
    
    public void assignLabel(String label) {

    }

    public byte loadValue(Attrib attr) throws YAPLException {
        return 0;
    }
    
    public byte loadAddress(Attrib attr) throws YAPLException {
        return 0;
    }
    
    public void freeReg(Attrib attr) {

    }

    /**
     * Allocates a variable in the static data array or on the stack
     * @param sym
     * @throws YAPLException
     */
    public void allocVariable(Symbol sym) throws YAPLException {
        //if variable is global => allocate it in the static data area
        if (sym.isGlobal())
            sym.setOffset(be.allocStaticData(1));
        else
            sym.setOffset(be.allocStack(1));
    }

    
    public void setFieldOffsets(RecordType record) {

    }

    
    public void storeArrayDim(int dim, Attrib length) throws YAPLException {

    }

    
    public Attrib allocArray(ArrayType arrayType) throws YAPLException {
        return null;
    }

    
    public Attrib allocRecord(RecordType recordType) throws YAPLException {
        return null;
    }

    
    public void setParamOffset(Symbol sym, int pos) {

    }

    
    public void arrayOffset(Attrib arr, Attrib index) throws YAPLException {

    }

    
    public void recordOffset(Attrib record, Symbol field) throws YAPLException {

    }

    
    public Attrib arrayLength(Attrib arr) throws YAPLException {
        return null;
    }

    
    public void assign(Attrib lvalue, Attrib expr) throws YAPLException {

    }

    /**
     * For signed expressions.
     * E.g. -7 or -(5+3)
     * @param op
     * @param x
     * @return
     * @throws YAPLException
     */
    public Attrib op1(Token op, Attrib x) throws YAPLException {
        if(op == null) return x;
        //sign must be plus or minus
        if(!(op.image.equals("+") || op.image.equals("-"))) throw new YAPLException("Internal error.", CompilerError.Internal, op.beginLine, op.beginColumn);
        //the following attribute must be an integer
        if(!(x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for unary operator " + op.image, CompilerError.IllegalOp1Type, op.beginLine, op.beginColumn);

        //if sign is plus => do nothing
        //else negate the value of x
        if(op.image.equals("+")){
            return x;

        }else{
            ((IntegerType)x.getType()).setValue(Integer.parseInt("-" + String.valueOf(((IntegerType)x.getType()).getValue())));
            return x;
        }
    }

    /**
     * For expressions with two variables and arithmetic operator. This methode takes the two attributes and the operator and returns the result as a new attribute.
     * @param x
     * @param op
     * @param y
     * @return
     * @throws YAPLException
     */
    public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {

        //check if the two attributes are compatible
        if(!Type.typeIsCompatible(x.getType(), y.getType())) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op.beginLine, op.beginColumn);
        //check if the operator is a valid arithmetic operation
        if(op.image.equals("And") || op.image.equals("Or")){

            if(!(x.getType() instanceof BooleanType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op.beginLine, op.beginColumn);
        }

        //compute the new attribute depending on the operation
        //e.g. if operation == "+": compute the sum of the two attributes
        switch(op.image){
            case "+":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new IntegerType(((IntegerType)x.getType()).getValue() + ((IntegerType)y.getType()).getValue()));
                }
                break;
            case "-":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new IntegerType(((IntegerType)x.getType()).getValue() - ((IntegerType)y.getType()).getValue()));
                }
                break;
            case "*":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new IntegerType(((IntegerType)x.getType()).getValue() * ((IntegerType)y.getType()).getValue()));
                }
                break;
            case "/":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new IntegerType(((IntegerType)x.getType()).getValue() / ((IntegerType)y.getType()).getValue()));
                }
                break;
            case "%":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new IntegerType(((IntegerType)x.getType()).getValue() % ((IntegerType)y.getType()).getValue()));
                }
                break;

        }

        return y;
    }

    /**
     * For expressions with two variables and relation operator.
     * @param x
     * @param op
     * @param y
     * @return
     * @throws YAPLException
     */
    public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
        //check for valid operator
        //"<" | "<=" | ">=" | ">"
        if(!(op.image.equals("<") || op.image.equals("<=") || op.image.equals(">=") || op.image.equals(">"))) throw new YAPLException("Illegal operand type for relational operator " + op.image, CompilerError.Internal);

        //check if the attributes are integer values
        if(!(x.getType() instanceof IntegerType && y.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalRelOpType, op.beginLine, op.beginColumn);

        //compute result and generate new attribute
        switch (op.image){
            case "<":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new BooleanType(((IntegerType)x.getType()).getValue() < ((IntegerType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }
                break;
            case "<=":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new BooleanType(((IntegerType)x.getType()).getValue() <= ((IntegerType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }
                break;
            case ">":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new BooleanType(((IntegerType)x.getType()).getValue() > ((IntegerType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }
                break;
            case ">=":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new BooleanType(((IntegerType)x.getType()).getValue() >= ((IntegerType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }
                break;
        }

        return new Attrib(new BooleanType(), y.getLine(), y.getColumn());
    }

    /**
     * For expressions with equal operator. This is an extra relOp, because this can be used for boolean expressions too.
     * @param x
     * @param op
     * @param y
     * @return
     * @throws YAPLException
     */
    public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {

        //check if types are compatible
        if(!Type.typeIsCompatible(x.getType(), y.getType())) throw new YAPLException("Illegal operand type for equality operator " + op.image, CompilerError.IllegalEqualOpType, op.beginLine, op.beginColumn);
        //check if the type is integer or boolean
        if(!(x.getType() instanceof BooleanType || x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for equality operator " + op.image, CompilerError.IllegalEqualOpType, op.beginLine, op.beginColumn);

        //compute result and generate new attribute
        switch (op.image){
            case "==":
                if(y.getType().getClass() == IntegerType.class){
                    return new Attrib(new BooleanType(((IntegerType)x.getType()).getValue() == ((IntegerType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }else if(y.getType().getClass() == BooleanType.class){
                    return new Attrib(new BooleanType(((BooleanType)x.getType()).getValue() == ((BooleanType)y.getType()).getValue()), y.getLine(), y.getColumn());
                }
                break;
        }

        return new Attrib(new BooleanType());
    }

    
    public void enterProc(Symbol proc) throws YAPLException {

    }

    
    public void exitProc(Symbol proc) throws YAPLException {

    }

    
    public void returnFromProc(Symbol proc, Attrib returnVal) throws YAPLException {

    }

    
    public Attrib callProc(Symbol proc, Attrib[] args) throws YAPLException {
        return null;
    }

    /**
     * This stores a string in the static area field and prints it
     * @param string
     * @throws YAPLException
     */
    public void writeString(String string) throws YAPLException {
        //store string in static data
        int addr = be.allocStringConstant(string);
        //takes the addr of the string in the static data area and prints it
        be.writeString(addr);
    }

    
    public void branchIfFalse(Attrib condition, String label) throws YAPLException {
    }

    
    public void jump(String label) {

    }

    /**
     * Stores the generated code into the predefined file
     */
    public void end(){
        try{
            be.writeObjectFile(new FileOutputStream(outputFileName == null ? filename.substring(0, filename.length()-5) + ".mj" : outputFileName));
        }catch (Exception ex){};

        System.out.println("wrote object file to " + filename);
    }

    /**
     * Sets the name of the file, which contains the code
     * @param s
     */
    public void setFileName(String s){
        this.filename = s.substring(0, s.length());
    }

    public void setOutFileName(String s){
        this.outputFileName = s;
    }
}
