/* Generated By:JJTree: Do not edit this line. ASTsmlalC.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTsmlalC extends SimpleNode {
  public ASTsmlalC(int id) {
    super(id);
  }

  public ASTsmlalC(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8b77ba3ffc0a435abdd6cba93a637d4c (do not edit this line) */
