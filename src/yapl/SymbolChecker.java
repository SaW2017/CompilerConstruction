package yapl;

import yapl.compiler.*;
import yapl.interfaces.CompilerError;
import yapl.lib.YAPLException;


public class SymbolChecker {

    private Symboltable table;
    private String programName = "";
    private SimpleNode globalNode;

    public SymbolChecker(){
        table = new Symboltable();
        table.setDebug(true);
    }

    public void check(Node node) throws YAPLException {

        Symbol s = new Symbol();
        globalNode = (SimpleNode)node;
        //System.out.println("Entering node: " + "/" + ((SimpleNode) node).getKind());
        try {
//TODO warum bei ASTProgram 2x  table.openScope(true); ???
            if (node instanceof ASTProgram) {
                //table.openScope(true);
                programName = ((ASTProgram) node).getName();
                s = new Symbol();
                s.setName(((ASTProgram) node).getName());
                s.setKind(Symbol.Program);
                table.addSymbol(s);
                table.setParentSymbol(s);
                table.openScope(true);
            } else if (node instanceof ASTEnd) {
                ASTEnd endNode = (ASTEnd) node;
                if (!(endNode.getName().equals(endNode.getProgramName()))) {
                    s = new Symbol();
                    s.setName(endNode.getName());
                    throw new YAPLException(s, globalNode, CompilerError.EndIdentMismatch, endNode.getProgramName(), "");
                }
            } else if (node instanceof ASTConstDecl) {
                ASTConstDecl n = (ASTConstDecl) node;
                s = new Symbol();
                s.setName(n.getIdent());
                s.setKind(Symbol.Constant);
                table.addSymbol(s);
            } else if (node instanceof ASTVarDecl) {
                ASTVarDecl n = (ASTVarDecl) node;
                s = new Symbol();
                s.setName(n.getIdent());
                s.setKind(Symbol.Variable);
                table.addSymbol(s);
            } else if (node instanceof ASTTypeDecl) {
                ASTTypeDecl n = (ASTTypeDecl) node;
                s = new Symbol();
                s.setName(n.getIdent());
                s.setKind(Symbol.Typename);
                table.addSymbol(s);
            } else if (node instanceof ASTProcedure) {
                ASTProcedure n = (ASTProcedure) node;
                s = new Symbol();
                s.setName(n.getIdent());
                s.setKind(Symbol.Procedure);
                table.addSymbol(s);
                table.setParentSymbol(s);
                table.openScope(true);
            } else if (node instanceof ASTBlock) {  //Block neuer Scope?

                table.openScope(false);
            } else if (node instanceof ASTFormalParam) {
                ASTFormalParam n = (ASTFormalParam) node;
                s = new Symbol();
                s.setName(n.getIdent());
                s.setKind(Symbol.Parameter);
                table.addSymbol(s);
            }else if(node instanceof ASTAssignment){
                ASTAssignment n = (ASTAssignment) node;
                table.lookup(n.getIdent());
            }else if(node instanceof ASTPrimaryExpr){
                if((((ASTPrimaryExpr) node).getKind() == yapl.interfaces.Symbol.Procedure || (((ASTPrimaryExpr) node).getKind() == yapl.interfaces.Symbol.Variable))){
                    table.lookup(((ASTPrimaryExpr) node).getName());
                }
            }else if(node instanceof ASTArrayLen){
                table.lookup(((ASTArrayLen) node).getIdent());
            }else if(node instanceof ASTLiteral){

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
            for (int i = 0; i < (node).jjtGetNumChildren(); i++) {
                check(node.jjtGetChild(i));
            }

            if (node instanceof ASTProcedure || node instanceof ASTBlock || node instanceof ASTProgram) {
                /*System.out.println("Closing Node: " + node.getClass());
                System.out.println("# on Stack " + table.getStacksNum());*/
                table.closeScope();
            }
        }catch(YAPLException yex){
            System.out.println("Column: " + ((SimpleNode)globalNode).token.beginColumn + "Line: " + ((SimpleNode)globalNode).token.beginLine);
            throw new YAPLException(s, (SimpleNode)globalNode, yex.errorNumber(), programName, yex.getMessage());
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }


}
