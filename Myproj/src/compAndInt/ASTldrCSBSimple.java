/* Generated By:JJTree: Do not edit this line. ASTldrCSBSimple.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCSBSimple extends SimpleNode {
  public ASTldrCSBSimple(int id) {
    super(id);
  }

  public ASTldrCSBSimple(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=596efe5db4a0a7f1c5330a11b4729bd5 (do not edit this line) */
