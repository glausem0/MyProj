/* Generated By:JJTree: Do not edit this line. ASTsbcCS.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTsbcCS extends SimpleNode {
  public ASTsbcCS(int id) {
    super(id);
  }

  public ASTsbcCS(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0fd396518dc827b3aaa8c660bd72fc6d (do not edit this line) */
