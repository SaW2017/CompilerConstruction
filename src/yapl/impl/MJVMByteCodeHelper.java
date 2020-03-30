package yapl.impl;

import java.util.ArrayList;
import java.util.List;

public class MJVMByteCodeHelper {

    private static final byte HEADER1 = 'M';
    private static final byte HEADER2 = 'J';


    public static byte[] createByteCode(List<Byte> code, List<Byte> sData, int pcStart){
        List<Byte> totalCode = new ArrayList<>();

        //marker
        totalCode.add(HEADER1);
        totalCode.add(HEADER2);

        //codeSize
        //+1 because we need to add 0x2F to the end of the code
        int codeSize = code.size() + 1;
        totalCode.add((byte)(codeSize>>24));
        totalCode.add((byte)(codeSize>>16));
        totalCode.add((byte)(codeSize>>8));
        totalCode.add((byte)(codeSize));

        //static data area in words
        int sDataSize = sData.size()/MJVMInstructions.WORD_SIZE;
        totalCode.add((byte)(sDataSize>>24));
        totalCode.add((byte)(sDataSize>>16));
        totalCode.add((byte)(sDataSize>>8));
        totalCode.add((byte)(sDataSize));

        //startPC
        totalCode.add((byte)(pcStart>>24));
        totalCode.add((byte)(pcStart>>16));
        totalCode.add((byte)(pcStart>>8));
        totalCode.add((byte)(pcStart));

        //add code
        totalCode.addAll(code);
        totalCode.add(MJVMInstructions.RETURN);

        //add static data
        totalCode.addAll(sData);
        totalCode.add((byte)0);
        totalCode.add((byte)0);
        totalCode.add((byte)0);
        return listToPrimitiveByte(totalCode);
    }

    private static byte[] listToPrimitiveByte(List<Byte> byteList){
        byte[] data = new byte[byteList.size()];

        for(int i = 0; i < byteList.size(); i++){
            data[i] = byteList.get(i);
        }

        return data;
    }

}
