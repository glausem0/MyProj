/* Generated By:JJTree: Do not edit this line. ASTldrCSHPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCSHPostPos extends SimpleNode {
  public ASTldrCSHPostPos(int id) {
    super(id);
  }

  public ASTldrCSHPostPos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=16c57fd75d2dad265deaa8cf5e860b7b (do not edit this line) */
