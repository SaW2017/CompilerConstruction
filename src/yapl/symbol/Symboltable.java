package yapl.symbol;

import yapl.LoggerService;
import yapl.interfaces.CompilerError;
import yapl.interfaces.Symbol;
import yapl.lib.YAPLException;

import java.util.Stack;

public class Symboltable implements yapl.interfaces.Symboltable {

    public Symboltable() {
    }

    // Scopes are saved for a better handling on a stack within the symboltable
    Stack<Scope> scopeStack = new Stack<>();
    Scope currentScope = null;
    // parentsymbol of the currentscope = a Symbol in the Symboltable
    Symbol parentSymbol = null;
    boolean debugEnabled = false;

    // opens new Scope and push it on stack and set the currentScope without removing it from stack
    @Override
    public void openScope(boolean isGlobal) {
        Scope newScope = new Scope(isGlobal, (yapl.symbol.Symbol) parentSymbol, currentScope);
        scopeStack.push(newScope);
        currentScope = scopeStack.peek();
    }

    // close current Scope as long as there exists more then one scope and set current scope
    @Override
    public void closeScope() {
        if (scopeStack.size() > 1) {
            scopeStack.pop();
            currentScope = scopeStack.peek();
        }
    }

    // add Symbol to symboltable
    // therefore search through currentScope if symbol is already declared
    // if already declared --> throws new YaplException
    @Override
    public void addSymbol(Symbol s) throws YAPLException {
        LoggerService.log("Adding symbol: " + s.getName());
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

    // overloads lookup-method
    // checks if symbol is in current scope or in parentScope of currentScope or the next parentScope
    // if symbol is null and there exists no more parentscope, it creates a new Symbol with a new Scope
    public Symbol lookup(String name, int line, int column) throws YAPLException {
        Symbol s = null;

        LoggerService.log("Looking for '" + name + "' in line/col " + line + "/" + column);

        currentScope.hasSymbol(name);

        s = checkScope(currentScope, name);

        Scope newScope = currentScope.getParentScope();

        if (s == null && newScope != null) {
            s = checkScope(newScope, name);
        }

        return s;
    }

    // check if name is in currentScope and returns symbol with this name
    // if currentScope doesnt contains symbol with given name
    // it checks the parentScope of the CurrentScope as long as there exists
    // a parentScope if there exists no symbol it returns null
    public Symbol checkScope(Scope scope, String name) {
        Symbol s = null;

        LoggerService.log("Looking for '" + name + "' in Scope with symbols: " + scope.getSymbols().keySet());

        if (scope.hasSymbol(name)) return scope.getSymbols().get(name);

        if (scope.getParentScope() != null) {
            s = checkScope(scope.getParentScope(), name);
        }

        return s;
    }

    // returns Symbol with given name from currentScope or null if it doesnt exist
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
