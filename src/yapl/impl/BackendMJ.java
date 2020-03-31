package yapl.impl;

import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackendMJ implements BackendBinSM {


    private List<Byte> code = new ArrayList<>();
    private List<Byte> sData = new ArrayList<>();
    private HashMap<String, Integer> labels = new HashMap<>();
    private int pcStart = 0;


    @Override
    public int wordSize() {
        return MJVMInstructions.WORD_SIZE;
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
        outStream.write(MJVMByteCodeHelper.createByteCode(code, sData, pcStart));
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

        //fill the rest of the remaining word with zeros
        int remWord = sData.size()%MJVMInstructions.WORD_SIZE;
        if(remWord != 0){
            for(int i = wordSize()-remWord; i > 0; i--){
                sData.add((byte) 0x00);
            }
        }


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
        code.add(MJVMInstructions.CONST);
        code.add((byte) (value>>24));
        code.add((byte) (value>>16));
        code.add((byte) (value>>8));
        code.add((byte) value);
    }

    @Override
    public void loadWord(MemoryRegion region, int offset) {
        if(region == MemoryRegion.STACK){
            code.add(MJVMInstructions.LOAD);
            code.add((byte)offset);
        }else if(region == MemoryRegion.STATIC){

        }else{

        }
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
        //anscheinend wurde im testcase davon ausgegangen, dass hier immer ohne anführende leerzeichen geprinted wird
        code.add(MJVMInstructions.CONST0); //deshalb muss diese konstante auf den stack weil print t0 und t1 braucht
        code.add(MJVMInstructions.PRINT);

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
        int address = labels.get(label);
        code.add(MJVMInstructions.CALL);
        code.add((byte)(address>>8));
        code.add((byte)address);
    }

    @Override
    public void enterProc(String label, int nParams, boolean main) {
        labels.put(label, code.size());
        if(main){
            pcStart = code.size();
        }
        code.add(MJVMInstructions.ENTER);
        code.add((byte)nParams); //s8 nparams
        code.add((byte)nParams);  //s8 framesize ?!
    }

    @Override
    public void exitProc(String label) {
        code.add(MJVMInstructions.EXIT);
        code.add(MJVMInstructions.RETURN);
    }

    @Override
    public int paramOffset(int index) {
        return index;
    }
}
