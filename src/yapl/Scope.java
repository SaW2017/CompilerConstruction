package yapl;

import javax.xml.validation.SchemaFactoryConfigurationError;
import java.util.*;

public class Scope {

    private boolean isGlobal;
    private Symbol parentSymbol;
    int scopeLevel;
    private HashMap<String, Symbol> symbols;

    public Scope(boolean isGlobal, Symbol parentSymbol, int scopeLevel) {
        this.isGlobal = isGlobal;
        this.parentSymbol = parentSymbol;
        this.scopeLevel = scopeLevel;
        symbols = new HashMap<>();
        if(parentSymbol != null){
            parentSymbol.setScope(this);
        }

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

    public void setSymbol(Symbol s){
        symbols.put(s.name, s);
    }

    public String toString(){
        if(parentSymbol == null) return "";

        String scopeText = "";
        for(int i = 0; i < scopeLevel; i++) scopeText += "-";
        scopeText += "Parent symbol: " + parentSymbol.getName() + ", kind: " + parentSymbol.getKindString() + ", Scope Level: " + scopeLevel + ", isGLobal: " + isGlobal + ", Symbole: ";
        for(Symbol s : symbols.values()) scopeText += s.name + ", ";
        return scopeText;
    }

    public static Symbol getSymbolInScope(String str, Symbol parentSymbol){
        Iterator it = parentSymbol.getScope().getSymbols().entrySet().iterator();
        System.out.println("#symbols: " + parentSymbol.getScope().getSymbols().size());
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Symbol sym = (yapl.Symbol)pair.getValue();
            System.out.println("Symbol in scope: " + sym.getName() + " with key: " + pair.getKey().toString());
            if(sym.getName().equals(str)){
                //System.out.println("Symbol found in scope: " + pair.getKey() + " => " + pair.getValue());
                return (yapl.Symbol)pair.getValue();
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }
}
