/* Generated By:JJTree: Do not edit this line. ASTldrCHPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCHPreNeg extends SimpleNode {
  public ASTldrCHPreNeg(int id) {
    super(id);
  }

  public ASTldrCHPreNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=2226c343197e963d0990767191c7b1b1 (do not edit this line) */
