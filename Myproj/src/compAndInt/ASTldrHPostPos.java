/* Generated By:JJTree: Do not edit this line. ASTldrHPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrHPostPos extends SimpleNode {
  public ASTldrHPostPos(int id) {
    super(id);
  }

  public ASTldrHPostPos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=939d9970f9413aa2afb62db31bc652c6 (do not edit this line) */
