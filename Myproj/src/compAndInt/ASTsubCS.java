/* Generated By:JJTree: Do not edit this line. ASTsubCS.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTsubCS extends SimpleNode {
  public ASTsubCS(int id) {
    super(id);
  }

  public ASTsubCS(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b45bfc50e15d32e218ab6293be7acc31 (do not edit this line) */
