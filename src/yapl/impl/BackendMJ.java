package yapl.impl;

import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackendMJ implements BackendBinSM {

    //contains the generated code
    private List<Byte> code = new ArrayList<>();
    //contains the generated static data
    private List<Byte> sData = new ArrayList<>();
    //contains labels, which are declared
    private HashMap<String, Integer> labels = new HashMap<>();
    //contains labels, which are unknown at the first call
    private HashMap<String, Integer> unknownLabels = new HashMap<>();
    //start address of the program
    private int pcStart = 0;


    //for procedures
    private int fsAddress = 0;
    private int fsLocalVariables = 0;
    private int params = 0;

    public BackendMJ(){
        //write 2 words (8 byte) into the static data area at the beginning
        //this is important for later instructions
        for(int i = 0; i < 8; i++){
            sData.add((byte)0);
        }
    }

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
        labels.put(label, code.size());
    }

    @Override
    public void writeObjectFile(OutputStream outStream) throws IOException {

        //link unknown labels
        for(Map.Entry<String, Integer> entry : unknownLabels.entrySet()) {


            String label = entry.getKey();
            int replaceAddress = entry.getValue();
            int labelAddress = labels.get(label);

            System.out.println("Replace unknowlabel: " + label + " on address [" + replaceAddress + "] to address [" + labelAddress + "]");

            code.set(replaceAddress, (byte)(labelAddress>>8));
            code.set(replaceAddress+1, (byte)labelAddress);
        }
        //generate code by using the helper class for the header and static data area
        outStream.write(MJVMByteCodeHelper.createByteCode(code, sData, pcStart));
    }

    @Override
    public int allocStaticData(int words) {
        //writes for each word 4 empty bytes into the static data area and returns the address of the first word
        int startAddress = sData.size()/MJVMInstructions.WORD_SIZE;

        for(int i = 0; i < words*4; i++){
            sData.add((byte)0);
        }

        return startAddress;
    }

    @Override
    public int allocStringConstant(String string) {

        //calculate start address
        int startAddress = sData.size()/MJVMInstructions.WORD_SIZE;

        //store the chars of the string into the static area
        for (byte b : string.getBytes()) {
            sData.add(b);
        }

        //add the null termination of the string
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
        fsLocalVariables += words;
        return params;
    }

    @Override
    public void allocHeap(int words) {
        code.add(MJVMInstructions.NEW);
        code.add((byte)(words>>8)); //new requires the length of the data in words
        code.add((byte)words);
    }

    @Override
    public void storeArrayDim(int dim) {
        int newDim = dim+1;
        code.add(MJVMInstructions.PUT_STATIC);
        code.add((byte)(newDim>>8));
        code.add((byte)newDim);
    }

    @Override
    public void allocArray() {
        int newDim = 1;
        code.add(MJVMInstructions.GET_STATIC);
        code.add((byte)(newDim>>8));
        code.add((byte)newDim);

        code.add(MJVMInstructions.NEW_ARRAY);
        code.add((byte)16);
    }

    @Override
    public void loadConst(int value) {
        code.add(MJVMInstructions.CONST);
        //the 32bit constant
        code.add((byte) (value>>24));
        code.add((byte) (value>>16));
        code.add((byte) (value>>8));
        code.add((byte) value);
    }

    @Override
    public void loadWord(MemoryRegion region, int offset) {

        //check where the word needs to be loaded
        //each instruction needs the address followed
        if(region == MemoryRegion.STACK){
            code.add(MJVMInstructions.LOAD);
            code.add((byte)offset);
        }else if(region == MemoryRegion.STATIC){
            code.add(MJVMInstructions.GET_STATIC);
            code.add((byte)(offset>>8));
            code.add((byte)offset);
        }else{
            code.add(MJVMInstructions.GET_FIELD);
            code.add((byte)(offset>>8));
            code.add((byte)offset);
        }
    }

    @Override
    public void storeWord(MemoryRegion region, int offset) {

        //check where the word needs to be stored
        //each instruction needs the address followed
        if(region == MemoryRegion.STACK){
            code.add(MJVMInstructions.STORE);
            code.add((byte)offset);
        }else if(region == MemoryRegion.STATIC){
            code.add(MJVMInstructions.PUT_STATIC);
            code.add((byte)(offset>>8));
            code.add((byte)offset);
        }else{
            code.add(MJVMInstructions.PUT_FIELD);
            code.add((byte)(offset>>8));
            code.add((byte)offset);
        }
    }

    @Override
    public void loadArrayElement() {
        code.add(MJVMInstructions.ALOAD);
    }

    @Override
    public void storeArrayElement() {
        code.add(MJVMInstructions.ASTORE);
    }

    @Override
    public void arrayLength() {
        code.add(MJVMInstructions.ARRAY_LENGTH);
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
        //address of the string
        code.add((byte)(addr>>8));
        code.add((byte)(addr));
    }

    @Override
    public void neg() {
        code.add(MJVMInstructions.NEG);
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
        code.add(MJVMInstructions.MUL);
    }

    @Override
    public void or(){
        //or operation with arithmetic operations
        code.add(MJVMInstructions.ADD);
        code.add(MJVMInstructions.CONST1);
        code.add(MJVMInstructions.ADD);
        code.add(MJVMInstructions.CONST2);
        code.add(MJVMInstructions.DIV);
    }

    private void compareValues(byte instr){
        //the two variables to compare muste be already on the stack
        //if the compare is true => 1 is on the top of the stack
        //else => 0 is on the top of the stack
        code.add(instr);
        code.add((byte)((code.size()+5)>>8));   //address of load const1
        code.add((byte)(code.size()+5));
        code.add(MJVMInstructions.CONST0);      //if compare failes
        code.add(MJVMInstructions.JMP);
        code.add((byte)((code.size()+2)>>8));
        code.add((byte)(code.size()+2));
        code.add(MJVMInstructions.CONST1);
    }

    @Override
    public void isEqual() {
        compareValues(MJVMInstructions.JEQ);
    }

    @Override
    public void isLess() {
        compareValues(MJVMInstructions.JLT);
    }

    @Override
    public void isLessOrEqual() {
        compareValues(MJVMInstructions.JLE);
    }

    @Override
    public void isGreater() {
        compareValues(MJVMInstructions.JGT);
    }

    @Override
    public void isGreaterOrEqual() {
        compareValues(MJVMInstructions.JGE);
    }

    @Override
    public void branchIf(boolean value, String label) {
        if(value){
            code.add(MJVMInstructions.CONST1);    //for true
        }else{
            code.add(MJVMInstructions.CONST0);    //for false
        }

        code.add(MJVMInstructions.JEQ);
        //in most of the cases we dont know the label to jump
        //therefore, we store the name of the label and search for the address of the label at the end by calling
        //writeObjectFile...
        unknownLabels.put(label, code.size());
        code.add((byte)0);
        code.add((byte)0);
    }

    @Override
    public void jump(String label) {

        code.add(MJVMInstructions.JMP);
        //in most of the cases we dont know the label to jump
        //therefore, we store the name of the label and search for the address of the label at the end by calling
        //writeObjectFile...
        unknownLabels.put(label, code.size());
        code.add((byte)0);
        code.add((byte)0);
    }

    @Override
    public void callProc(String label) {

        code.add(MJVMInstructions.CALL);
        //in most of the cases we dont know the label to jump
        //therefore, we store the name of the label and search for the address of the label at the end by calling
        //writeObjectFile...
        unknownLabels.put(label, code.size());
        code.add((byte)0);
        code.add((byte)0);

    }

    @Override
    public void enterProc(String label, int nParams, boolean main) {

        fsAddress = 0;
        fsLocalVariables = 0;
        params = nParams;

        //at this point a label is specified and we store it for later usage
        labels.put(label, code.size());
        if(main){
            pcStart = code.size();
        }
        code.add(MJVMInstructions.ENTER);
        code.add((byte)nParams); //s8 nparams
        fsAddress = code.size();
        code.add((byte)0);  //s8 framesize ?!
    }

    @Override
    public void exitProc(String label) {

        code.set(fsAddress, (byte)(fsLocalVariables + params));

        labels.put(label, code.size());
        code.add(MJVMInstructions.EXIT);
        code.add(MJVMInstructions.RETURN);
    }

    @Override
    public int paramOffset(int index) {
        return index;
    }
}
