/* Generated By:JJTree: Do not edit this line. ASTMldmSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTMldmSimple extends SimpleNode {
  public ASTMldmSimple(int id) {
    super(id);
  }

  public ASTMldmSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=59f8183e19e2506f5f3e982435e7808e (do not edit this line) */
