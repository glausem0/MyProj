package mytest;

import registers.Register;

public class Visitors implements MyTestVisitor{

	Register registerData = new Register();
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	@Override
	public Object visit(ASTprog node, Object data) {
		node.childrenAccept(this, data);
		return "Program";
	}

	@Override
	public Object visit(ASTdecl node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);
		
		registerData.setRegister(reg, val);
		
		return "decl";
	}

	@Override
	public Object visit(ASTregister node, Object data) {
		String valStr = (String) node.data.get("reg");
		
		System.out.println(valStr);
		return valStr;
	}

	@Override
	public Object visit(ASTnumber node, Object data) {
		String valStr = (String) node.data.get("value");
		valStr = valStr.replace("#", "");
		int valInt = Integer.parseInt(valStr);
				
		System.out.println(valInt);
		
		return valStr;
	}
	
	//print la table des registres
	public void print(){
		registerData.print();
	}

}
