/* Generated By:JJTree: Do not edit this line. ASTldrBPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrBPreNeg extends SimpleNode {
  public ASTldrBPreNeg(int id) {
    super(id);
  }

  public ASTldrBPreNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7e9cb0148762afc99dc0b1d92a809081 (do not edit this line) */
