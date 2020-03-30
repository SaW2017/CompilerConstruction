package yapl.impl;

import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BackendMJ implements BackendBinSM {

    private final int wordSize = 4;
    private List<Byte> code = new ArrayList<>();
    private List<Byte> sData = new ArrayList<>();

    @Override
    public int wordSize() {
        return 4;
    }

    @Override
    public int boolValue(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void assignLabel(String label) {

    }

    @Override
    public void writeObjectFile(OutputStream outStream) throws IOException {

        byte[] byteCode = MJVMByteCodeHelper.createByteCode(code, sData, 0);

        for(byte b : byteCode){
            System.out.println(" -> " + b);
        }
        outStream.write(MJVMByteCodeHelper.createByteCode(code, sData, 0));
    }

    @Override
    public int allocStaticData(int words) {
        return 0;
    }

    @Override
    public int allocStringConstant(String string) {
        int startAddress = sData.size();

        for (byte b : string.getBytes()) {
            sData.add(b);
        }

        sData.add((byte) 0x00);

        return startAddress;
    }

    @Override
    public int allocStack(int words) {
        return 0;
    }

    @Override
    public void allocHeap(int words) {

    }

    @Override
    public void storeArrayDim(int dim) {

    }

    @Override
    public void allocArray() {

    }

    @Override
    public void loadConst(int value) {

    }

    @Override
    public void loadWord(MemoryRegion region, int offset) {

    }

    @Override
    public void storeWord(MemoryRegion region, int offset) {

    }

    @Override
    public void loadArrayElement() {

    }

    @Override
    public void storeArrayElement() {

    }

    @Override
    public void arrayLength() {

    }

    @Override
    public void writeInteger() {

    }

    @Override
    public void writeString(int addr) {
        code.add(MJVMInstructions.SPRINT);
        code.add((byte)(addr>>8));
        code.add((byte)(addr));
    }

    @Override
    public void neg() {

    }

    @Override
    public void add() {
        code.add(MJVMInstructions.ADD);
    }

    @Override
    public void sub() {
        code.add(MJVMInstructions.SUB);
    }

    @Override
    public void mul() {
        code.add(MJVMInstructions.MUL);
    }

    @Override
    public void div() {
        code.add(MJVMInstructions.DIV);
    }

    @Override
    public void mod() {
        code.add(MJVMInstructions.REM);
    }

    @Override
    public void and() {

    }

    @Override
    public void or() {

    }

    @Override
    public void isEqual() {

    }

    @Override
    public void isLess() {

    }

    @Override
    public void isLessOrEqual() {

    }

    @Override
    public void isGreater() {

    }

    @Override
    public void isGreaterOrEqual() {

    }

    @Override
    public void branchIf(boolean value, String label) {

    }

    @Override
    public void jump(String label) {

    }

    @Override
    public void callProc(String label) {

    }

    @Override
    public void enterProc(String label, int nParams, boolean main) {

    }

    @Override
    public void exitProc(String label) {

    }

    @Override
    public int paramOffset(int index) {
        return 0;
    }
}
