/* Generated By:JJTree: Do not edit this line. ASTldrCBSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCBSimple extends SimpleNode {
  public ASTldrCBSimple(int id) {
    super(id);
  }

  public ASTldrCBSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ac212033d0f451f4b2816ac9dabff26a (do not edit this line) */
