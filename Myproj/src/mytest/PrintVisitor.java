package mytest;

public class PrintVisitor implements MyTestVisitor{

	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	@Override
	public Object visit(ASTprog node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(" \n");
		return (data);
	}

	@Override
	public Object visit(ASTdecl node, Object data) {
		System.out.println(node.jjtGetValue() + " ");
		node.jjtGetChild(0).jjtAccept(this, data);
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
	}

	@Override
	public Object visit(ASTregister node, Object data) {
		System.out.println(node.data.get("reg"));
		return data;
	}

	@Override
	public Object visit(ASTnumber node, Object data) {
		System.out.println(node.data.get("value"));
		return data;
	}

}
