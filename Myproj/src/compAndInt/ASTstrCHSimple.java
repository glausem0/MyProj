/* Generated By:JJTree: Do not edit this line. ASTstrCHSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTstrCHSimple extends SimpleNode {
  public ASTstrCHSimple(int id) {
    super(id);
  }

  public ASTstrCHSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9688133ac467ebbb8858b478047dabcb (do not edit this line) */
