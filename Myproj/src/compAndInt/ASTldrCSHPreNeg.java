/* Generated By:JJTree: Do not edit this line. ASTldrCSHPreNeg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTldrCSHPreNeg extends SimpleNode {
  public ASTldrCSHPreNeg(int id) {
    super(id);
  }

  public ASTldrCSHPreNeg(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b56e3a5c225be67c73db95e8b9541a43 (do not edit this line) */
