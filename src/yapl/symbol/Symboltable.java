package yapl.symbol;

import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public Symboltable() {
        //TODO: Predefined Procedures in Scopes inkludieren
    }

    Stack<Scope> scopeStack = new Stack<>();
    Scope currentScope = null;
    Symbol parentSymbol = null;
    boolean debugEnabled = false;

    @Override
    public void openScope(boolean isGlobal) {
        Scope newScope = new Scope(isGlobal, (yapl.symbol.Symbol) parentSymbol, currentScope);
        scopeStack.push(newScope);
        currentScope = scopeStack.peek();
    }

    @Override
    public void closeScope() {
        if (scopeStack.size() > 1) {
            scopeStack.pop();
            currentScope = scopeStack.peek();
        }
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {
        System.out.println("Adding symbol: " + s.getName());
        if (currentScope.hasSymbol(s.getName()) || currentScope.hasSymbol((yapl.symbol.Symbol) s)) {
            Symbol existingSymbol = currentScope.getSymbols().get(s.getName());
            if(s.getKindString() != null ){ //&& s.getKind() == existingSymbol.getKind()) {
                throw new YAPLException("symbol '" + s.getName() + "' already declared in current scope (as " + existingSymbol.getKindString().toLowerCase() + ")", CompilerError.SymbolExists, ((yapl.symbol.Symbol) s).getLine(), ((yapl.symbol.Symbol) s).getColumn());
            }
        }
        currentScope.addSymbol(s.getName(), (yapl.symbol.Symbol) s);
    }

    @Override
    public Symbol lookup(String name) {
        return null;
    }

    public Symbol lookup(String name, int line, int column) throws YAPLException {
        Symbol s = null;

        System.out.println("Looking for '" + name + "' in line/col " + line + "/" + column);

        currentScope.hasSymbol(name);

        s = checkScope(currentScope, name);

        Scope newScope = currentScope.getParentScope();

        if (s == null && newScope != null) {
            s = checkScope(newScope, name);
        }

        return s;
    }

    public Symbol checkScope(Scope scope, String name) {
        Symbol s = null;

        System.out.println("Looking for '" + name + "' in Scope with symbols: " + scope.getSymbols().keySet());

        if (scope.hasSymbol(name)) return scope.getSymbols().get(name);

        if (scope.getParentScope() != null) {
            s = checkScope(scope.getParentScope(), name);
        }

        return s;
    }

    public Symbol getSymbolFromCurrentScope(String name){
        Symbol s = null;

        if(currentScope.hasSymbol(name)){
           s = currentScope.getSymbols().get(name);
        }

        return s;
    }

    @Override
    public void setParentSymbol(Symbol sym) {
        parentSymbol = sym;
    }

    @Override
    public Symbol getNearestParentSymbol(int kind) {
        return null;
    }

    @Override
    public void setDebug(boolean on) {
        debugEnabled = on;
    }
}
