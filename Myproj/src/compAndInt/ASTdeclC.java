/* Generated By:JJTree: Do not edit this line. ASTdeclC.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTdeclC extends SimpleNode {
  public ASTdeclC(int id) {
    super(id);
  }

  public ASTdeclC(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e1b7e7ddaf37d3f38ab19d8cf2a0144f (do not edit this line) */
