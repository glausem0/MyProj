/* Generated By:JJTree: Do not edit this line. ASTstrCPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCPreNeg extends SimpleNode {
  public ASTstrCPreNeg(int id) {
    super(id);
  }

  public ASTstrCPreNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=25e56b0fc2d7d1660182a8d18025da9a (do not edit this line) */
