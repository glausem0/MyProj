/* Generated By:JJTree: Do not edit this line. ASTstrSHPostNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrSHPostNeg extends SimpleNode {
  public ASTstrSHPostNeg(int id) {
    super(id);
  }

  public ASTstrSHPostNeg(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=33785caf851fa9ab79a2ad6106661862 (do not edit this line) */
