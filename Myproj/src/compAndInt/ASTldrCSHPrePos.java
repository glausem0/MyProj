/* Generated By:JJTree: Do not edit this line. ASTldrCSHPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCSHPrePos extends SimpleNode {
  public ASTldrCSHPrePos(int id) {
    super(id);
  }

  public ASTldrCSHPrePos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=73b6393850848ef45353700c03004867 (do not edit this line) */
