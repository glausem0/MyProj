/* Generated By:JJTree: Do not edit this line. ASTstrCSBSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCSBSimple extends SimpleNode {
  public ASTstrCSBSimple(int id) {
    super(id);
  }

  public ASTstrCSBSimple(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ce2e4163b221b1849f3daf16de6337eb (do not edit this line) */
