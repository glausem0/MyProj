/* Generated By:JJTree: Do not edit this line. ASTstrCBPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCBPrePos extends SimpleNode {
  public ASTstrCBPrePos(int id) {
    super(id);
  }

  public ASTstrCBPrePos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5b2d3bae4044553d3432c1b3099e4eb3 (do not edit this line) */
