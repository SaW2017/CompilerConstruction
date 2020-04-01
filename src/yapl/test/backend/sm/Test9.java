package yapl.test.backend.sm;

import java.io.FileOutputStream;
import java.io.IOException;

import yapl.impl.BackendMJ;
import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

public class Test9 {

    public static void main(String[] args) throws IOException {

        BackendBinSM backend = new BackendMJ();
        // procedure print out length of array
        backend.enterProc("printarraylength", 4, false);
        backend.loadWord(MemoryRegion.HEAP, backend.paramOffset(0));  // load start address of a
        backend.arrayLength();
        backend.writeInteger();
        backend.exitProc("printarraylength");

        int addrA = backend.allocStaticData(4);

        // main program
        backend.enterProc("main", 0, true);
        // allocate 1-dimensional array of length 4
        backend.loadConst(4);
        backend.storeArrayDim(0);
        backend.allocArray();
        backend.storeWord(MemoryRegion.STATIC, addrA);    // store array start address in a
        // a[0] = 3
        backend.loadWord(MemoryRegion.STATIC, addrA);     // load start address of a
        backend.loadConst(0);                             // load index
        backend.loadConst(3);                             // load element value
        backend.storeArrayElement();
        // a[1] = 1
        backend.loadWord(MemoryRegion.STATIC, addrA);     // load start address of a
        backend.loadConst(1);                             // load index
        backend.loadConst(1);                             // load element value
        backend.storeArrayElement();
        // a[2] = 4
        backend.loadWord(MemoryRegion.STATIC, addrA);     // load start address of a
        backend.loadConst(2);                             // load index
        backend.loadConst(4);                             // load element value
        backend.storeArrayElement();
        // a[3] = 2
        backend.loadWord(MemoryRegion.STATIC, addrA);     // load start address of a
        backend.loadConst(3);                             // load index
        backend.loadConst(2);                             // load element value
        backend.storeArrayElement();

        backend.loadWord(MemoryRegion.STACK, backend.paramOffset(0));  // load start address of a
        backend.arrayLength();
        backend.writeInteger();

        // call "printarraylength"
        backend.callProc("printarraylength");
        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }
}
