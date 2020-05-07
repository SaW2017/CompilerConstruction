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

        System.out.println("Working on Node = " + node.getClass());
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
            }else if(node instanceof ASTPrimaryExpr) {
                //alt
                /*if((((ASTPrimaryExpr) node).getKind() == yapl.interfaces.Symbol.Procedure ||
                        (((ASTPrimaryExpr) node).getKind() == yapl.interfaces.Symbol.Variable))){
                    Symbol sym = (yapl.Symbol)table.lookup(((ASTPrimaryExpr) node).getName());
                    if(sym.getKind() != ((ASTPrimaryExpr) node).getKind()) throw new YAPLException(s, (ASTPrimaryExpr)node, CompilerError.SymbolIllegalUse, programName, "");
                }*/

                //neu
                //check if variable exists
                String identStr = ((ASTPrimaryExpr) node).getIdent();
                System.out.println("~~~~~> " + identStr);
                if((identStr != null) && !(identStr.equals("null"))){
                    Symbol parentSym = (yapl.Symbol)table.lookup(identStr);
                    Symbol s1 = parentSym.getScope().getSymbols().get(identStr);
                    System.out.println("------> " + s1);
                    //System.out.println("Kind sym["+s1.getName()+"]");
                    //System.out.println("-> " + s1.getKind());
                    //System.out.println("--> ["+((ASTPrimaryExpr) node).getIdent()+"]");
                    //System.out.println("---> " + ((ASTPrimaryExpr) node).getKind());

                    //if(s1.getKind() != ((ASTPrimaryExpr) node).getKind()) throw new YAPLException(s, (ASTPrimaryExpr)node, CompilerError.SymbolIllegalUse, programName, "");
                }

            }else if(node instanceof ASTProcedureCall){
                //check if procedure is declared
            }else if(node instanceof ASTArrayLen){
                table.lookup(((ASTArrayLen) node).getIdent());
            }else if(node instanceof ASTLiteral){
                //brauch ma net
            }/*else if(node instanceof ASTProcedureCall){
                ASTProcedureCall n = (ASTProcedureCall) node;
                System.out.println("Looking for: " + n.getName());
                Symbol sym = (yapl.Symbol) table.lookup(n.getName());
                System.out.println("Symbol kind: " + s.getKindString() + " " + s.getName());
                System.out.println("Found Symbol: " + sym.getKindString());
                if(s.getKind() != sym.getKind()) throw new YAPLException(s, n, CompilerError.SymbolIllegalUse, programName, "");
            }*/
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
