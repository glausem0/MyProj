/* Generated By:JJTree: Do not edit this line. ASTMstmSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTMstmSimple extends SimpleNode {
  public ASTMstmSimple(int id) {
    super(id);
  }

  public ASTMstmSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6f0068e0cf42946de94c4ba6c4b7fb72 (do not edit this line) */
