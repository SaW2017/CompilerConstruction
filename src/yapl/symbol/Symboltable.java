package yapl.symbol;

import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public Symboltable(){
        //TODO: Predefined Procedures in Scopes inkludieren
    }

    Stack<Scope> scopeStack = new Stack<>();
    Scope currentScope = null;
    Symbol parentSymbol = null;
    boolean debugEnabled = false;

    @Override
    public void openScope(boolean isGlobal) {
        Scope newScope = new Scope(isGlobal, (yapl.symbol.Symbol)parentSymbol, currentScope);
        scopeStack.push(newScope);
        currentScope = scopeStack.peek();
    }

    @Override
    public void closeScope() {
        scopeStack.pop();
        currentScope = scopeStack.peek();
    }

    @Override
    public void addSymbol(Symbol s) throws YAPLException {
        if(currentScope.hasSymbol(s.getName()) || currentScope.hasSymbol((yapl.symbol.Symbol)s)) {
            throw new YAPLException("Symbol already declared.", CompilerError.SymbolExists, ((yapl.symbol.Symbol)s).getLine(), ((yapl.symbol.Symbol)s).getColumn());
        }
        currentScope.addSymbol(s.getName(), (yapl.symbol.Symbol)s);
    }

    @Override
    public Symbol lookup(String name){return null;}

    public Symbol lookup(String name, int line, int column) throws YAPLException {
        currentScope.hasSymbol(name);

        Symbol s = checkScope(currentScope.getParentScope(), name);

        if (s == null) throw new YAPLException("", CompilerError.IdentNotDecl, line, column);

        return s;
    }

    public Symbol checkScope(Scope scope, String name){
        Symbol s = null;

        if(scope.hasSymbol(name)) return scope.getSymbols().get(name);

        if(scope.getParentScope() != null){
            s = checkScope(scope.getParentScope(), name);
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
