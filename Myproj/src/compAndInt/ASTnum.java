/* Generated By:JJTree: Do not edit this line. ASTnum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTnum extends SimpleNode {
  public ASTnum(int id) {
    super(id);
  }

  public ASTnum(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4758fc838b7853a85670738c4b440df4 (do not edit this line) */
