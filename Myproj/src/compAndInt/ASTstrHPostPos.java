/* Generated By:JJTree: Do not edit this line. ASTstrHPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrHPostPos extends SimpleNode {
  public ASTstrHPostPos(int id) {
    super(id);
  }

  public ASTstrHPostPos(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4bf59a5a9aaa499fd7bd221ee4b890db (do not edit this line) */
