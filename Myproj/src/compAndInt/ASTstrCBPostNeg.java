/* Generated By:JJTree: Do not edit this line. ASTstrCBPostNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCBPostNeg extends SimpleNode {
  public ASTstrCBPostNeg(int id) {
    super(id);
  }

  public ASTstrCBPostNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=97d66e2d1f711a5716fb196fbc0dd37c (do not edit this line) */
