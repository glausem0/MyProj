/* Generated By:JJTree: Do not edit this line. ASTldrSPostNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrSPostNeg extends SimpleNode {
  public ASTldrSPostNeg(int id) {
    super(id);
  }

  public ASTldrSPostNeg(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=689149a2a59a12e3d8ca6e14a7dbf156 (do not edit this line) */
