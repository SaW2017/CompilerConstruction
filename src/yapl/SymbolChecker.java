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
        }

        for(int i = 0; i < (node).jjtGetNumChildren(); i++){
            check(node.jjtGetChild(i));
        }



    }


}
