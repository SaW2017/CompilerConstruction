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
    BackendMJ be = new BackendMJ();
    String filename = "";

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
    
    public void allocVariable(Symbol sym) throws YAPLException {
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

    
    public Attrib op1(Token op, Attrib x) throws YAPLException {
        if(op == null) return x;
        if(!(op.image.equals("+") || op.image.equals("-"))) throw new YAPLException("Internal error.", CompilerError.Internal, op.beginLine, op.beginColumn);
        if(!(x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for unary operator " + op.image, CompilerError.IllegalOp1Type, op.beginLine, op.beginColumn);

        if(op.image.equals("+")){
            return x;
        }else{
            ((IntegerType)x.getType()).setValue(Integer.parseInt("-" + String.valueOf(((IntegerType)x.getType()).getValue())));
            return x;
        }
    }

    
    public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {
        //TODO: Check for valid binary operator;

        if(!Type.typeIsCompatible(x.getType(), y.getType())) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op.beginLine, op.beginColumn);
        if(op.image.equals("And") || op.image.equals("Or")){

            if(!(x.getType() instanceof BooleanType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type, op.beginLine, op.beginColumn);
        }

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

    
    public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
        //"<" | "<=" | ">=" | ">"
        if(!(op.image.equals("<") || op.image.equals("<=") || op.image.equals(">=") || op.image.equals(">"))) throw new YAPLException("Illegal operand type for relational operator " + op.image, CompilerError.Internal);
        if(!(x.getType() instanceof IntegerType && y.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalRelOpType, op.beginLine, op.beginColumn);

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

    
    public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {
        if(!Type.typeIsCompatible(x.getType(), y.getType())) throw new YAPLException("Illegal operand type for equality operator " + op.image, CompilerError.IllegalEqualOpType, op.beginLine, op.beginColumn);
        if(!(x.getType() instanceof BooleanType || x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for equality operator " + op.image, CompilerError.IllegalEqualOpType, op.beginLine, op.beginColumn);

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

    
    public void writeString(String string) throws YAPLException {
        int addr = be.allocStringConstant(string);
        be.writeString(addr);
    }

    
    public void branchIfFalse(Attrib condition, String label) throws YAPLException {
    }

    
    public void jump(String label) {

    }

    public void end(){
        try{
            be.writeObjectFile(new FileOutputStream(filename.substring(0, filename.length()-5) + ".mj"));
        }catch (Exception ex){};

        System.out.println("wrote object file to " + filename);
    }

    public void setFileName(String s){
        this.filename = s.substring(0, s.length());
    }
}
