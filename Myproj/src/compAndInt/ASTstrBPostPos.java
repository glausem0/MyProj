/* Generated By:JJTree: Do not edit this line. ASTstrBPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrBPostPos extends SimpleNode {
  public ASTstrBPostPos(int id) {
    super(id);
  }

  public ASTstrBPostPos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4edc985bf312330f7517008f4f8fe27f (do not edit this line) */
