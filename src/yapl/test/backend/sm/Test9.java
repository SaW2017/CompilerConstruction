package yapl.test.backend.sm;

import java.io.FileOutputStream;
import java.io.IOException;

import yapl.impl.BackendMJ;
import yapl.interfaces.BackendBinSM;
import yapl.interfaces.MemoryRegion;

/**
 * BackendMJ test: records.
 * @author Mario Taschwer
 * @version $Id$
 */

public class Test9
{

    /**
     * Usage: java yapl.test.backend.sm.Test6 object_file
     */
    public static void main(String[] args) throws IOException
    {
        BackendBinSM backend = new BackendMJ();
        int addrA = backend.allocStaticData(1);
        int addrNewline = backend.allocStringConstant("\n");
        // main program
        backend.enterProc("main", 0, true);

        backend.loadConst(7);
        backend.storeArrayDim(0);
        backend.allocArray();
        backend.arrayLength();
        backend.writeInteger();
        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }

}
