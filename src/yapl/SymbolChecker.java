package yapl;

import yapl.compiler.*;
import yapl.interfaces.CompilerError;
import yapl.lib.YAPLException;


public class SymbolChecker {

    private Symboltable table;

    public SymbolChecker(){
        table = new Symboltable();
    }

    public void check(Node node) throws YAPLException {

//TODO warum bei ASTProgram 2x  table.openScope(true); ???
        if(node instanceof ASTProgram) {
            table.openScope(true);
            Symbol s = new Symbol();
            s.setName(((ASTProgram)node).getName());
            s.setKind(Symbol.Program);
            table.addSymbol(s);
            table.openScope(true);

        }else if(node instanceof ASTEnd){
            ASTEnd endNode = (ASTEnd)node;
            if(!(endNode.getName().equals(endNode.getProgramName()))){
                Symbol s = new Symbol();
                s.setName(endNode.getName());
                throw new YAPLException(s, node, CompilerError.EndIdentMismatch, endNode.getProgramName());
            }
        }else if(node instanceof ASTConstDecl){
            ASTConstDecl n = (ASTConstDecl)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Constant);
            table.addSymbol(s);
        }else if(node instanceof ASTVarDecl){
            ASTVarDecl n = (ASTVarDecl)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Variable);
            table.addSymbol(s);
        }else if(node instanceof ASTTypeDecl){
            ASTTypeDecl n = (ASTTypeDecl)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Typename);
            table.addSymbol(s);
        }else if(node instanceof ASTProcedure){
            ASTProcedure n = (ASTProcedure)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Procedure);
            table.addSymbol(s);
        }else if(node instanceof ASTBlock){  //Block neuer Scope?
            ASTBlock n = (ASTBlock)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Procedure);
            table.addSymbol(s);
            table.openScope(false);
        }else if(node instanceof ASTFormalParam){
            ASTFormalParam n = (ASTFormalParam)node;
            Symbol s = new Symbol();
            s.setName(n.getIdent());
            s.setKind(Symbol.Procedure);
            table.addSymbol(s);
        }
        //todo steht in dem File vom Helmut, keine Ahnung ob wir das auch brauchen?
        /**
         * else if(node instanceof ASTAssignmentOrProcedurCall){
         *  ASTBlock n = (ASTAssignmentOrProcedurCall)node;
         *  Symbol s = (Symbol)table.lookup(n.getName());
         *  if(s != null && s.getKind() == yapl.interfaces.Symbol.Procedure && n.jjtGetNumChildren() == 0){
         *      throw new YAPLException(s, n, CompilerError.SymbolIllegalUse, n.getProgramName());
         *  }
         *  Symbol s2 = new Symbol(n.getName());
         *  if(s == null) throw new YAPLException(s2, n, CompilerError.IdentNotDecl, n.getProgramName());
         * }
         */
        for(int i = 0; i < (node).jjtGetNumChildren(); i++){
            check(node.jjtGetChild(i));
        }



    }


}
