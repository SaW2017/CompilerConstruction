package yapl;

import java.util.HashMap;
import java.util.LinkedList;

public class Scope {

    private boolean isGlobal;
    private Symbol parentSymbol;
    int scopeLevel;
    private HashMap<String, Symbol> symbols;

    public Scope(boolean isGlobal, Symbol parentSymbol, int scopeLevel) {
        this.isGlobal = isGlobal;
        this.parentSymbol = parentSymbol;
        this.scopeLevel = scopeLevel;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public Symbol getParentSymbol() {
        return parentSymbol;
    }

    public void setParentSymbol(Symbol parentSymbol) {
        this.parentSymbol = parentSymbol;
    }

    public HashMap<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(HashMap<String, Symbol> symbols) {
        this.symbols = symbols;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public void setScopeLevel(int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }
}
