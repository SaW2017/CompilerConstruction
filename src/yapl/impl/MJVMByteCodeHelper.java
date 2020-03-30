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
        /*
        totalCode.add((byte)(code.size()>>24));
        totalCode.add((byte)(code.size()>>16));
        totalCode.add((byte)(code.size()>>8));
        totalCode.add((byte)(code.size()));
        */
        totalCode.add((byte)((code.size()+1)>>24));
        totalCode.add((byte)((code.size()+1)>>16));
        totalCode.add((byte)((code.size()+1)>>8));
        totalCode.add((byte)(code.size()+1));

        //static data area in words
        //TODO: Muss womöglich durch 4 geteilt werden da s32!!!
        /*
        totalCode.add((byte)(sData.size()>>24));
        totalCode.add((byte)(sData.size()>>16));
        totalCode.add((byte)(sData.size()>>8));
        totalCode.add((byte)(sData.size()));
        */
        totalCode.add((byte)(0));
        totalCode.add((byte)(0));
        totalCode.add((byte)(0));
        totalCode.add((byte)(4));

        //startPC
        totalCode.add((byte)(pcStart>>24));
        totalCode.add((byte)(pcStart>>16));
        totalCode.add((byte)(pcStart>>8));
        totalCode.add((byte)(pcStart));

        //add code
        totalCode.addAll(code);

        totalCode.add((byte)0x2f);

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
