/* Generated By:JJTree: Do not edit this line. ASTldrSHSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrSHSimple extends SimpleNode {
  public ASTldrSHSimple(int id) {
    super(id);
  }

  public ASTldrSHSimple(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1688e422eb550916c957a7e20c09d0a4 (do not edit this line) */
