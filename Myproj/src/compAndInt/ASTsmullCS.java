/* Generated By:JJTree: Do not edit this line. ASTsmullCS.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTsmullCS extends SimpleNode {
  public ASTsmullCS(int id) {
    super(id);
  }

  public ASTsmullCS(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d3e79a3a444cfddd6fa3b54344cd1cc4 (do not edit this line) */
