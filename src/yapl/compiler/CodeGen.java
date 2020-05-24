package yapl.compiler;

import yapl.compiler.Attrib;
import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CodeGen {

    String label;

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
        if(!(x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for unary operator.", CompilerError.IllegalOp1Type);

        return x;
    }

    
    public Attrib op2(Attrib x, Token op, Attrib y) throws YAPLException {
        //TODO: Check for valid binary operator;
        if(x.getType() != y.getType()) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type);
        if(op.image.equals("And") || op.image.equals("Or")){
            if(!(x.getType() instanceof BooleanType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalOp2Type);
        }
        return x;
    }

    
    public Attrib relOp(Attrib x, Token op, Attrib y) throws YAPLException {
        //"<" | "<=" | ">=" | ">"
        if(!(op.image.equals("<") || op.image.equals("<=") || op.image.equals(">=") || op.image.equals(">"))) throw new YAPLException("Illegal operand type for relational operator.", CompilerError.Internal);
        if(!(x.getType() instanceof IntegerType && y.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand types for binary operator " + op.image, CompilerError.IllegalRelOpType);

        return x;
    }

    
    public Attrib equalOp(Attrib x, Token op, Attrib y) throws YAPLException {
        if(!(x.getType() != y.getType())) throw new YAPLException("Illegal operand type for equality operator.", CompilerError.IllegalEqualOpType);
        if(!(x.getType() instanceof BooleanType || x.getType() instanceof IntegerType)) throw new YAPLException("Illegal operand type for equality operator.", CompilerError.IllegalEqualOpType);

        return new yapl.compiler.Attrib(new BooleanType());
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

    }

    
    public void branchIfFalse(Attrib condition, String label) throws YAPLException {

    }

    
    public void jump(String label) {

    }
}
