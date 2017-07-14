package compAndInt;

import java.util.HashMap;

import instructions.Condition;
import instructions.Instruction;
import instructions.UpdateCPSR;
import registers.Cpsr;
import registers.Register;

public class Visitors implements MyTestVisitor{

	Register regData = new Register();
	private HashMap<Object, Object> reg = regData.init();
	
	Cpsr cpsr = new Cpsr();
	private HashMap<Object, Object> cpsrReg = cpsr.init();
	
	Instruction inst = new Instruction(reg);
	Condition condition = new Condition(reg, cpsrReg);
	UpdateCPSR upCpsr = new UpdateCPSR(cpsrReg);
	
////Program and simple node:////	
	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	@Override
	public Object visit(ASTprog node, Object data) {
		node.childrenAccept(this, data);
		return "Program";
	}

////Register, number, cond and Scond:////
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
	
	@Override
	public Object visit(ASTscnd node, Object data) {
		String sCond = node.value.toString();

		return sCond;
	}
	
////Instructions:////
	
	//print la table des registres et cpsr
	public void print(){
		regData.print();
		System.out.println("\n");
		cpsr.print();
	}
	
///MOV///
	@Override
	public Object visit(ASTdecl node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);
		
		inst.movInstr(reg, val);
		
		return null;
	}
	
	@Override
	public Object visit(ASTdeclC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			inst.movInstr(reg, val);
		}
		return null;
	}
	
	@Override
	public Object visit(ASTdeclS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		inst.movInstr(reg, val);
		upCpsr.update( Integer.parseInt(val.toString()) );
		
		return null;
	}

	@Override
	public Object visit(ASTdeclCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			inst.movInstr(reg, val);
			upCpsr.update( Integer.parseInt(val.toString()) );
		}
		
		return null;
	}
	
	
///ADD///
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
		
		if ( condition.condAction(cond.toString()) ){
			inst.addInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTaddS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.addInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTaddCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.addInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}
	
	
///SUB///
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
		
		if ( condition.condAction(cond.toString()) ){
			inst.subInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTsubS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.subInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTsubCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}

	
///RSB///
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
		
		if ( condition.condAction(cond.toString()) ){
			inst.subInstr(reg, arg2.toString(), arg1.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTrsbS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.subInstr(reg, arg2.toString(), arg1.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTrsbCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg, arg2.toString(), arg1.toString());
			upCpsr.update(result);
		}
		
		return null;
	}


}
