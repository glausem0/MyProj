/* Generated By:JJTree: Do not edit this line. ASTldrCPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCPreNeg extends SimpleNode {
  public ASTldrCPreNeg(int id) {
    super(id);
  }

  public ASTldrCPreNeg(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=80d73f6c5a5fb0b87352500af29e767c (do not edit this line) */
