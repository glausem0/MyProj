package mytest;

public class Visitors implements MyTestVisitor{

	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	@Override
	public Object visit(ASTprog node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return "Program";
	}

	@Override
	public Object visit(ASTdecl node, Object data) {
		return "decl";
	}

	@Override
	public Object visit(ASTregister node, Object data) {
		String valStr = node.value.toString();
		return valStr;
	}

	@Override
	public Object visit(ASTnumber node, Object data) {
		String valStr = node.value.toString();
		valStr.replace("#", "");
		int valInt = Integer.parseInt(valStr);
		
		return valInt;
	}

}
