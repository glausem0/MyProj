/* Generated By:JJTree: Do not edit this line. ASTcmn.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTcmn extends SimpleNode {
  public ASTcmn(int id) {
    super(id);
  }

  public ASTcmn(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4e66a5bf1ed42e7246d1dd7a0c8266f2 (do not edit this line) */
