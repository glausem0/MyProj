/* Generated By:JJTree: Do not edit this line. ASTldrCHSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCHSimple extends SimpleNode {
  public ASTldrCHSimple(int id) {
    super(id);
  }

  public ASTldrCHSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=f7fbed5bfa99c0c1e6d8c5958849ee74 (do not edit this line) */
