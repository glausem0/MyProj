/* Generated By:JJTree: Do not edit this line. ASTstrSPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrSPrePos extends SimpleNode {
  public ASTstrSPrePos(int id) {
    super(id);
  }

  public ASTstrSPrePos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c79231721b80d6243ca1acbdc26c31fa (do not edit this line) */
