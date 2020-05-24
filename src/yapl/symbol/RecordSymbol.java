package yapl.symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordSymbol extends Symbol {

    public RecordSymbol(int kind, String name, int line, int column) {
        super(kind, name, line, column);
    }

    private HashMap<String, Symbol> symbols = new HashMap<>();

    public void addSymbol(Symbol s){
        symbols.put(s.getName(), s);
    }

    public boolean hasSymbol(String s){
        return symbols.containsKey(s);
    }
}
