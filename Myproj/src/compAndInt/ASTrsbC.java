/* Generated By:JJTree: Do not edit this line. ASTrsbC.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTrsbC extends SimpleNode {
  public ASTrsbC(int id) {
    super(id);
  }

  public ASTrsbC(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9f0d77c0817b72cb83cc17b18d47d242 (do not edit this line) */
