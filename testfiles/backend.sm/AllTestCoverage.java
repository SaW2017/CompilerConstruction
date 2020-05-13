package yapl.test.backend.sm;

import java.io.IOException;

public class AllTestCoverage {

    public static void main(String[] args) throws IOException {
        new Test1().main(new String[]{"Test1.txt"});
        new Test2().main(new String[]{"Test2.txt"});
        new Test3().main(new String[]{"Test3.txt"});
        new Test4().main(new String[]{"Test4.txt"});
        new Test5().main(new String[]{"Test5.txt"});
        new Test6().main(new String[]{"Test6.txt"});
        new Test7().main(new String[]{"Test7.txt"});
        new Test8().main(new String[]{"Test8.txt"});
        new Test9().main(new String[]{"Test9.txt"});
        new Test10().main(new String[]{"Test10.txt"});
        new Test11().main(new String[]{"Test11.txt"});

    }
}
