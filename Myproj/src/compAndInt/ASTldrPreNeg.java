/* Generated By:JJTree: Do not edit this line. ASTldrPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrPreNeg extends SimpleNode {
  public ASTldrPreNeg(int id) {
    super(id);
  }

  public ASTldrPreNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=56cfdc1dce34a8d7cdcd782e95345b9d (do not edit this line) */
