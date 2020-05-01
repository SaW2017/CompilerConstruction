package yapl;

import java.util.HashMap;
import java.util.LinkedList;

public class Scope {

    private boolean isGlobal;
    private Symbol parentSymbol;
    private HashMap<String, Symbol> symbols;

    public Scope(boolean isGlobal, Symbol parentSymbol, HashMap<String, Symbol> symbols) {
        this.isGlobal = isGlobal;
        this.parentSymbol = parentSymbol;
        this.symbols = symbols;
    }
}
