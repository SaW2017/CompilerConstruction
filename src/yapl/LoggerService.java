package yapl;

import yapl.compiler.Parser;

public class LoggerService {

    public static void log(Object o){
        if(Parser.loggingIsEnabled){
            System.out.println(o.toString());

        }
    }
}
