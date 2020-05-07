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
    //kann weg, wird nur einmal put ausgeführt und das wars
    public HashMap<String, Symbol> symbolTable;

    boolean debugEnabled = false;
    Symbol parentSymbol;

    public Symboltable() {
        scopes = new Stack<>();
        scopeLevel = 0;
        symbolTable = new HashMap<>();
        globalScopes = new ArrayList<>();
        parentSymbol = new yapl.Symbol();
        currentScope = new Scope(false, null, 0);
    }

    public int getStacksNum(){
        return scopes.size();
    }

    @Override
    public void openScope(boolean isGlobal) {
        currentScope = new Scope(isGlobal, (yapl.Symbol)parentSymbol, scopeLevel);

        if(currentScope.isGlobal()){
            globalScopes.add(currentScope);
        }else{
            scopes.push(currentScope);
        }
        scopeLevel++;
        if(debugEnabled) System.out.println("Open scope: " + currentScope.toString());
    }

    @Override
    public void closeScope() {

        Scope closedScope = currentScope;

        if(!currentScope.isGlobal()){
            //System.out.println("Closing local scope: " + currentScope.getScopeLevel());
            scopes.pop();
            if(scopes.size() > 0) {
                currentScope = scopes.peek();
            } else {
                currentScope = globalScopes.get(globalScopes.size()-1);
            }
        }else{
            //System.out.println("Closing global scope: " + currentScope.getScopeLevel());
            globalScopes.remove(currentScope);
            if(globalScopes.size() > 0) currentScope = globalScopes.get(globalScopes.size()-1);
        }
        scopeLevel--;
        if(debugEnabled) System.out.println("Close scope: " + closedScope.toString());
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {

        if(currentScope.getSymbols().containsValue(s)){
            throw new YAPLException("Symbol already defined in this scope");
        } else if(currentScope.getSymbols().containsKey(s.getName())){
            throw new YAPLException("Symbol with name [" + s.getName() +  "]already exists");
        } else if(s.getName() == null){
            throw new YAPLException("No name for symbol defined");
        }
        currentScope.setSymbol((yapl.Symbol)s);
        if(debugEnabled) System.out.println("Added Symbol: " + currentScope.toString());
        symbolTable.put(s.getName(), s);
    }

    @Override
    public Symbol lookup(String name) throws YAPLException {
        if(name == null) throw new YAPLException("Symbol must not be null");

        Symbol s = null;

        s = checkScope(currentScope, name);

        if(s == null)
        {
            //System.out.println("Checking global scope");
            for(Scope scope : globalScopes){
                if(scope.getSymbols().containsKey(name)){
                    s = scope.getParentSymbol();
                }
            }
        }

        if(s == null) throw new YAPLException("identifier " + name + " not declared");

        //System.out.println(s.getName() +  " " + s.getKind());

        return s;
    }

    public Symbol checkScope(Scope scope, String name) throws YAPLException{

        //System.out.println("Lookup for: " + name + " in Scope: " + scope.getParentSymbol().getName());

        Symbol returnSymbol = null;

        Symbol s = scope.getParentSymbol();

        //System.out.println("Symbol: " + s.getName());

        if(s != null){
            Scope newScope = getScopeViaSymbol(s);

            //System.out.println("Symbol: " + newScope.getParentSymbol().getName());

            if(newScope != null) {

                if (newScope.getSymbols().containsKey(name)) {
                    //System.out.println("Local symbol found for " + name);
                    returnSymbol = newScope.getSymbols().get(name);
                } else {
                    returnSymbol = checkScope(newScope, name);
                }
            }
        }

        return returnSymbol;
    }

    public Scope getScopeViaSymbol(Symbol symbol) {
        for(Scope s : scopes){
            if(s.getSymbols().containsValue(symbol)) {
                //System.out.println("Local symbol found for " + symbol.getName());
                return s;
            }
        }
        //System.out.println("Returning null");
        return null;
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
