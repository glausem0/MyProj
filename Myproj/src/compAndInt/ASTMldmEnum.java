/* Generated By:JJTree: Do not edit this line. ASTMldmEnum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package compAndInt;

public
class ASTMldmEnum extends SimpleNode {
  public ASTMldmEnum(int id) {
    super(id);
  }

  public ASTMldmEnum(MyParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(MyParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=93b5fb32cdbcc6ac4e5cfd920adbab26 (do not edit this line) */
