/* Generated By:JJTree: Do not edit this line. ASTldrHPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrHPrePos extends SimpleNode {
  public ASTldrHPrePos(int id) {
    super(id);
  }

  public ASTldrHPrePos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7ca91246a5d727b5b9eba30cc0d1c36a (do not edit this line) */
