/* Generated By:JJTree: Do not edit this line. ASTstrPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrPostPos extends SimpleNode {
  public ASTstrPostPos(int id) {
    super(id);
  }

  public ASTstrPostPos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4357c74d1b00b2619374ae81c52978c3 (do not edit this line) */
