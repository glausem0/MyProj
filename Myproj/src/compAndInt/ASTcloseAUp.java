/* Generated By:JJTree: Do not edit this line. ASTcloseAUp.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=DataNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTcloseAUp extends SimpleNode {
  public ASTcloseAUp(int id) {
    super(id);
  }

  public ASTcloseAUp(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=552c99e8068b246fbb0ccfcc073f8236 (do not edit this line) */
