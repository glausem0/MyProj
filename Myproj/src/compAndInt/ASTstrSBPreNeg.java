/* Generated By:JJTree: Do not edit this line. ASTstrSBPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrSBPreNeg extends SimpleNode {
  public ASTstrSBPreNeg(int id) {
    super(id);
  }

  public ASTstrSBPreNeg(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0a6889aa453289c5bd36310fde38899c (do not edit this line) */
