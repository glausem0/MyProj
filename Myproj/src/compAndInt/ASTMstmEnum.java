/* Generated By:JJTree: Do not edit this line. ASTMstmEnum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=dataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTMstmEnum extends SimpleNode {
  public ASTMstmEnum(int id) {
    super(id);
  }

  public ASTMstmEnum(MyTest p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyTestVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=54fbec7cf61c5936d706d8edfbc12da2 (do not edit this line) */
