package yapl.impl;

public class MJVMInstructions {

    public static final int WORD_SIZE = 4;


    public static final byte LOAD = 0x01;
    public static final byte LOAD0 = 0x02;
    public static final byte LOAD1 = 0x03;
    public static final byte LOAD2 = 0x04;
    public static final byte LOAD3 = 0x05;

    public static final byte STORE = 0x06;
    public static final byte STORE0 = 0x07;
    public static final byte STORE1 = 0x08;
    public static final byte STORE2 = 0x09;
    public static final byte STORE3 = 0x0A;

    public static final byte GET_STATIC = 0x0B;
    public static final byte PUT_STATIC = 0x0C;

    public static final byte GET_FIELD = 0x0D;
    public static final byte PUT_FIELD = 0x0E;

    public static final byte CONST0 = 0x0F;
    public static final byte CONST1 = 0x10;
    public static final byte CONST2 = 0x11;
    public static final byte CONST3 = 0x12;
    public static final byte CONST4 = 0x13;
    public static final byte CONST5 = 0x14;
    public static final byte CONST_M1 = 0x15;
    public static final byte CONST = 0x16;

    public static final byte ADD = 0x17;
    public static final byte SUB = 0x18;
    public static final byte MUL = 0x19;
    public static final byte DIV = 0x1A;
    public static final byte REM = 0x1B;
    public static final byte NEG = 0x1C;
    public static final byte SHL = 0x1D;
    public static final byte SHR = 0x1E;

    public static final byte NEW = 0x1F;
    public static final byte NEW_ARRAY = 0x20;
    public static final byte ALOAD = 0x21;
    public static final byte ASTORE = 0x22;
    public static final byte BALOAD = 0x23;
    public static final byte BASTORE = 0x24;
    public static final byte ARRAY_LENGTH = 0x25;

    public static final byte POP = 0x26;
    public static final byte JMP = 0x27;
    public static final byte JEQ = 0x28;
    public static final byte JNE = 0x29;
    public static final byte JLT = 0x2A;
    public static final byte JLE = 0x2B;
    public static final byte JGT = 0x2C;
    public static final byte JGE = 0x2D;

    public static final byte CALL = 0x2E;
    public static final byte RETURN = 0x2F;
    public static final byte ENTER = 0x30;
    public static final byte EXIT = 0x31;
    public static final byte READ = 0x32;
    public static final byte PRINT = 0x33;
    public static final byte BREAD = 0x34;
    public static final byte BPRINT = 0x35;
    public static final byte TRAP = 0x36;
    public static final byte SPRINT = 0x37;

}