/* Generated By:JJTree: Do not edit this line. ASTldrHSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrHSimple extends SimpleNode {
  public ASTldrHSimple(int id) {
    super(id);
  }

  public ASTldrHSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7afb1d3d2a1f707b7dcf74f45a716fe1 (do not edit this line) */
