/* Generated By:JJTree: Do not edit this line. ASTldrCHPrePos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCHPrePos extends SimpleNode {
  public ASTldrCHPrePos(int id) {
    super(id);
  }

  public ASTldrCHPrePos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=84157cea2a3fa327e677cc03f119cfdc (do not edit this line) */
