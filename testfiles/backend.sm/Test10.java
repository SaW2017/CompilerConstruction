package yapl.test.backend.sm;

import yapl.impl.BackendMJ;
import yapl.interfaces.BackendBinSM;

import java.io.FileOutputStream;
import java.io.IOException;

public class Test10 {

    /**
     * Usage: java yapl.test.backend.sm.Test10 object_file
     */
    public static void main(String[] args) throws IOException
    {
        BackendBinSM backend = new BackendMJ();

        // main program
        backend.enterProc("main", 0, true);
        int addrNewline = backend.allocStringConstant("\n");

        //Test add
        backend.loadConst(7);
        backend.loadConst(10);
        backend.add();
        backend.writeInteger();
        backend.writeString(addrNewline);

        //Test neg
        backend.loadConst(9);
        backend.neg();
        backend.writeInteger();
        backend.writeString(addrNewline);

        //Test div
        backend.loadConst(20);
        backend.loadConst(5);
        backend.div();
        backend.writeInteger();
        backend.writeString(addrNewline);

        //Test mod
        backend.loadConst(15);
        backend.loadConst(10);
        backend.mod();
        backend.writeInteger();
        backend.writeString(addrNewline);

        backend.exitProc("main_end");

        backend.writeObjectFile(new FileOutputStream(args[0]));
        System.out.println("wrote object file to " + args[0]);
    }


}
