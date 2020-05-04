package yapl;

import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public ArrayList<Scope> globalScopes;
    public Stack<Scope> scopes;
    public Scope currentScope;
    public int scopeLevel;

    public HashMap<String, Symbol> symbolTable;

    boolean debugEnabled = false;
    Symbol parentSymbol;

    public Symboltable() {
        scopes = new Stack<>();
        scopeLevel = 0;
        symbolTable = new HashMap<>();
        globalScopes = new ArrayList<>();
    }

    @Override
    public void openScope(boolean isGlobal) {
        currentScope = new Scope(isGlobal, (yapl.Symbol)parentSymbol, scopeLevel);
        if(isGlobal){
            globalScopes.add(currentScope);
        }else{
            scopes.push(currentScope);
        }
        scopeLevel++;
    }

    @Override
    public void closeScope() {
        if(!currentScope.isGlobal()){
            scopes.pop();
            currentScope = scopes.pop();
        }
        scopeLevel--;
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {

        if(currentScope.getSymbols().containsValue(s)){
            throw new YAPLException("Symbol already defined in this scope");
        } else if(currentScope.getSymbols().containsKey(s.getName())){
            throw new YAPLException("Symbol with name already exists");
        } else if(s.getName() == null){
            throw new YAPLException("No name for symbol defined");
        }

        symbolTable.put(s.getName(), s);

    }

    @Override
    public Symbol lookup(String name) throws YAPLException {
        if(name == null) throw new YAPLException("Symbol not must not be null");

        Symbol s = null;

        s = checkScope(currentScope, name);

        if(s == null)
        {
            for(Scope scope : globalScopes){
                if(scope.getSymbols().containsKey(name)){
                    s = parentSymbol;
                }
            }
        }
        return s;
    }

    public Symbol checkScope(Scope scope, String name) throws YAPLException{

        Symbol returnSymbol = null;

        Symbol s = scope.getParentSymbol();

        if(s != null){
            Scope newScope = getScopeViaSymbol(s);
            if(newScope.getSymbols().containsKey(name)){
                returnSymbol = newScope.getSymbols().get(name);
            }else{
                returnSymbol = checkScope(newScope, name);
            }
        }

        return returnSymbol;
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
