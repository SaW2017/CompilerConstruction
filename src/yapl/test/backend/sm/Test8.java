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

        //Test JEQ
        backend.loadConst(0);
        backend.loadConst(0);
        backend.isEqual();
        backend.writeInteger();

        backend.loadConst(0);
        backend.loadConst(1);
        backend.isEqual();
        backend.writeInteger();

        //Test JLT
        backend.loadConst(1);
        backend.loadConst(0);
        backend.isLess();
        backend.writeInteger();

        backend.loadConst(0);
        backend.loadConst(1);
        backend.isLess();
        backend.writeInteger();

        //Test JLE
        backend.loadConst(1);
        backend.loadConst(0);
        backend.isLessOrEqual();
        backend.writeInteger();

        backend.loadConst(0);
        backend.loadConst(1);
        backend.isLessOrEqual();
        backend.writeInteger();

        //Test JGT
        backend.loadConst(1);
        backend.loadConst(0);
        backend.isGreater();
        backend.writeInteger();

        backend.loadConst(0);
        backend.loadConst(1);
        backend.isGreater();
        backend.writeInteger();

        //Test JGE
        backend.loadConst(1);
        backend.loadConst(0);
        backend.isGreaterOrEqual();
        backend.writeInteger();

        backend.loadConst(0);
        backend.loadConst(1);
        backend.isGreaterOrEqual();
        backend.writeInteger();

        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }

}
