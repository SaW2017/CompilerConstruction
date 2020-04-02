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

public class Test7
{

    /**
     * Usage: java yapl.test.backend.sm.Test7 object_file
     */
    public static void main(String[] args) throws IOException
    {
        BackendBinSM backend = new BackendMJ();
        // main program
        backend.enterProc("main", 0, true);

        //Test or
        backend.loadConst(0);
        backend.loadConst(0);
        backend.or();
        backend.writeInteger();
        backend.loadConst(0);
        backend.loadConst(1);
        backend.or();
        backend.writeInteger();
        backend.loadConst(1);
        backend.loadConst(0);
        backend.or();
        backend.writeInteger();
        backend.loadConst(1);
        backend.loadConst(1);
        backend.or();
        backend.writeInteger();

        //Test and
        backend.loadConst(0);
        backend.loadConst(0);
        backend.and();
        backend.writeInteger();
        backend.loadConst(0);
        backend.loadConst(1);
        backend.and();
        backend.writeInteger();
        backend.loadConst(1);
        backend.loadConst(0);
        backend.and();
        backend.writeInteger();
        backend.loadConst(1);
        backend.loadConst(1);
        backend.and();
        backend.writeInteger();

        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }

}
