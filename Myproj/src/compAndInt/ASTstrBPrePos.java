/* Generated By:JJTree: Do not edit this line. ASTstrBPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrBPrePos extends SimpleNode {
  public ASTstrBPrePos(int id) {
    super(id);
  }

  public ASTstrBPrePos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=717cbbdf4a82fe68169c1f7964d19838 (do not edit this line) */
