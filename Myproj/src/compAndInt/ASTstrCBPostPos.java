/* Generated By:JJTree: Do not edit this line. ASTstrCBPostPos.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCBPostPos extends SimpleNode {
  public ASTstrCBPostPos(int id) {
    super(id);
  }

  public ASTstrCBPostPos(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=419fba6dbb3a70fb7fd7cefbd833062e (do not edit this line) */
