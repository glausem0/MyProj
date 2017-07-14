package compAndInt;

import java.util.HashMap;

import instructions.Condition;
import instructions.Instruction;
import registers.Cpsr;
import registers.Register;

public class Visitors implements MyTestVisitor{

	Register regData = new Register();
	private HashMap<Object, Object> reg = regData.init();
	Cpsr cpsr = new Cpsr();
	private HashMap<Object, Object> cpsrReg = cpsr.init();
	Instruction inst = new Instruction(reg);
	Condition condition = new Condition(reg, cpsrReg);
	
	
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
	public Object visit(ASTregister node, Object data) {
		String valStr = (String) node.data.get("reg");
		
		return valStr;
	}

	@Override
	public Object visit(ASTnumber node, Object data) {
		String valStr = (String) node.data.get("value");
		valStr = valStr.replace("#", "");
		int valInt = Integer.parseInt(valStr);	
		
		return valInt;
	}
	
	@Override
	public Object visit(ASTcond node, Object data) {
		String cond = node.value.toString();
		
		return cond;
	}
		
	//print la table des registres et cpsr
	public void print(){
		regData.print();
		System.out.println("\n");
		cpsr.print();
	}
	
	//Instructions:
	@Override
	public Object visit(ASTdecl node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);
		
		inst.movInstr(reg, val);
		
		return "decl";
	}
	
	@Override
	public Object visit(ASTdeclC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		//TODO change if
		if (condition.condAction(cond.toString())){
			inst.movInstr(reg, val);
		}
		return "decl";
	}
	
	@Override
	public Object visit(ASTadd node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.addInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTaddC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		//TODO change if
		if ( condition.condAction(cond.toString()) ){
			inst.addInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTsub node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.subInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTsubC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if (condition.condAction(cond.toString())){
			inst.subInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}

	//same as sub, reverse arguments
	@Override
	public Object visit(ASTrsb node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.subInstr(reg, arg2.toString(), arg1.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTrsbC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if (condition.condAction(cond.toString())){
			inst.subInstr(reg, arg2.toString(), arg1.toString());
		}
		
		return null;
	}
	
}
