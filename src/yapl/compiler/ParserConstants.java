/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package yapl.compiler;


/** 
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 5;
  /** RegularExpression Id. */
  int ident = 52;
  /** RegularExpression Id. */
  int number = 53;
  /** RegularExpression Id. */
  int string = 54;
  /** RegularExpression Id. */
  int digit = 55;
  /** RegularExpression Id. */
  int letter = 56;
  /** RegularExpression Id. */
  int otherchar = 57;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<MULTI_LINE_COMMENT>",
    "\"Program\"",
    "\"Begin\"",
    "\"End\"",
    "\".\"",
    "\"Declare\"",
    "\"Const\"",
    "\"=\"",
    "\";\"",
    "\",\"",
    "\"Record\"",
    "\"EndRecord\"",
    "\"int\"",
    "\"bool\"",
    "\"[\"",
    "\"]\"",
    "\"void\"",
    "\"Procedure\"",
    "\"(\"",
    "\")\"",
    "\"Write\"",
    "\"Return\"",
    "\"While\"",
    "\"Do\"",
    "\"EndWhile\"",
    "\"If\"",
    "\"Then\"",
    "\"Else\"",
    "\"EndIf\"",
    "\":=\"",
    "\"Or\"",
    "\"new\"",
    "\"And\"",
    "\"#\"",
    "\"True\"",
    "\"False\"",
    "\"<\"",
    "\"<=\"",
    "\">=\"",
    "\">\"",
    "\"==\"",
    "\"!=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "<ident>",
    "<number>",
    "<string>",
    "<digit>",
    "<letter>",
    "<otherchar>",
  };

}
