/* Generated By:JJTree: Do not edit this line. ASTldrCPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCPostPos extends SimpleNode {
  public ASTldrCPostPos(int id) {
    super(id);
  }

  public ASTldrCPostPos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8abf2bf4370ec9413dd850ec27a51ea8 (do not edit this line) */
