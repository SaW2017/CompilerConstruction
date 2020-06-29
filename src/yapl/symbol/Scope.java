package yapl.symbol;

import java.util.HashMap;
import java.util.Map;

public class Scope {

    //every Scope has a Hasmap with symbols (name, symbol)
    private Map<String, Symbol> symbols = new HashMap<>();
    private boolean isGlobal;
    private Symbol parentSymbol;
    private Scope parentScope;

    // if a new scope is created we`re setting if its global, and the parentSymbol
    // and the parentScope --> its helpful for the symbol lookup
    public Scope(boolean isGlobal, Symbol parentSymbol, Scope parentScope){
        this.isGlobal = isGlobal;
        this.parentSymbol = parentSymbol;
        this.parentScope = parentScope;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Symbol> symbols) {
        this.symbols = symbols;
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

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public void addSymbol(String name, Symbol s){
        symbols.put(name, s);
    }

    // check if Scope contains symbol with given name
    public boolean hasSymbol(String name){
        return symbols.containsKey(name) ? true : false;
    }

    // check if Scope contains given symbol
    public boolean hasSymbol(Symbol symbol){
        return symbols.containsValue(symbol) ? true : false;
    }

    public static enum Type{
        PROGRAM,
        PROCEDURE,
        PARAMETERS,
    }
}
