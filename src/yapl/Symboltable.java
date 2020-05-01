package yapl;

import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public Stack<Scope> scopes;
    public Scope currentScope;
    public int scopeLevel;

    public HashMap<String, Symbol> symbolTable;

    boolean debugEnabled = false;
    Symbol parentSymbol;

    public Symboltable() {
        scopes = new Stack<>();
        scopeLevel = 0;
    }

    @Override
    public void openScope(boolean isGlobal) {
        currentScope = new Scope(isGlobal, (yapl.Symbol)parentSymbol, scopeLevel);
        scopes.push(currentScope);
        scopeLevel++;
    }

    @Override
    public void closeScope() {
        scopes.pop();
        currentScope = scopes.pop();
        scopeLevel--;
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {
        symbolTable.put(s.getName(), s);
        s.setGlobal(currentScope.isGlobal());
    }

    @Override
    public Symbol lookup(String name) throws YAPLException {
        if(name == null) throw new YAPLException();

        return checkScope(currentScope, name);
    }

    public Symbol checkScope(Scope scope, String name) throws YAPLException{

        Symbol s = scope.getParentSymbol();

        if(s != null){
            Scope newScope = getScopeViaSymbol(s);
            if(newScope.getSymbols().containsKey(name)){
                return newScope.getSymbols().get(name);
            }else{
                return checkScope(newScope, name);
            }
        }

        return s;
    }

    public Scope getScopeViaSymbol(Symbol symbol) throws YAPLException {
        for(Scope s : scopes){
            if(s.getSymbols().containsValue(symbol)) return s;
        }
        throw new YAPLException("Symbol not found in symbol table");
    }

    @Override
    public void setParentSymbol(Symbol sym) {
        this.parentSymbol = sym;
    }

    @Override
    public Symbol getNearestParentSymbol(int kind) {
        if(parentSymbol.getKind() == kind) return parentSymbol;
        return null;
    }

    @Override
    public void setDebug(boolean on) {
        debugEnabled = on;
    }

}
