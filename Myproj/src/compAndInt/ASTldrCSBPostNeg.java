/* Generated By:JJTree: Do not edit this line. ASTldrCSBPostNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCSBPostNeg extends SimpleNode {
  public ASTldrCSBPostNeg(int id) {
    super(id);
  }

  public ASTldrCSBPostNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4624620770dac7dd22b0a9450858ea23 (do not edit this line) */
