package yapl;

import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public Symboltable() {
    }

    public Stack<Scope>
    public HashMap<String, Symbol> symbolTable;

    boolean debugEnabled = false;
    Symbol parentSymbol;
    boolean scopeIsGlobal;

    @Override
    public void openScope(boolean isGlobal) {
        this.scopeIsGlobal = isGlobal;
    }

    @Override
    public void closeScope() {
        scopeIsGlobal = false;
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {
        symbolTable.put(s.getName(), s);
        s.setGlobal(scopeIsGlobal);
    }

    @Override
    public Symbol lookup(String name) throws YAPLException {
        if(name == null) throw new YAPLException();


    }

    @Override
    public void setParentSymbol(Symbol sym) {
        this.parentSymbol = sym;
    }

    @Override
    public Symbol getNearestParentSymbol(int kind) {
        if(parentSymbol.getKind() == kind) return parentSymbol;

    }

    @Override
    public void setDebug(boolean on) {
        debugEnabled = on;
    }

}
