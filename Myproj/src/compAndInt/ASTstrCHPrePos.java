/* Generated By:JJTree: Do not edit this line. ASTstrCHPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCHPrePos extends SimpleNode {
  public ASTstrCHPrePos(int id) {
    super(id);
  }

  public ASTstrCHPrePos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=61106763c5178bf030dde7e494336025 (do not edit this line) */
