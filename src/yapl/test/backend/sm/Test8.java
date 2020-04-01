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

public class Test8
{

    /**
     * Usage: java yapl.test.backend.sm.Test6 object_file
     */
    public static void main(String[] args) throws IOException
    {
        BackendBinSM backend = new BackendMJ();
        // main program
        backend.enterProc("main", 0, true);
        backend.loadConst(16);
        backend.loadConst(0);
        backend.isEqual();
        backend.writeInteger();
        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }

}
