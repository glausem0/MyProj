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

	////Register, number, hexa, cond and Scond:////
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
	public Object[] visit(ASThexa node, Object data) {
		String valStr = (String) node.data.get("hexa");
		valStr = valStr.replace("#0x", "");
		
		String valHex = valStr;
		String valInt = String.valueOf(Integer.parseInt(valHex, 16));
		
		String[] obj = null;
		obj[0] = valHex;
		obj[1] = valInt;
		
		return obj;	
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

	///MVN///
	@Override
	public Object visit(ASTdecln node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);

		inst.mvnInstr(reg, val.toString());
		return null;
	}

	@Override
	public Object visit(ASTdeclnS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.mvnInstr(reg, val.toString());
		upCpsr.update( Integer.parseInt(val.toString()) );
		
		return null;
	}

	@Override
	public Object visit(ASTdeclnC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			inst.mvnInstr(reg, val.toString());
		}
		return null;
	}

	@Override
	public Object visit(ASTdeclnCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			inst.mvnInstr(reg, val.toString());
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

	///ADC///
	@Override
	public Object visit(ASTadc node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//add the two arguments:
		int re = inst.addInstr(reg, arg1.toString(), arg2.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then add the C (carry):
		inst.addInstr(reg, ret, C);

		return null;
	}

	@Override
	public Object visit(ASTadcS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		//add the two arguments:
		int re = inst.addInstr(reg, arg1.toString(), arg2.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then add the C (carry):
		int result = inst.addInstr(reg, ret, C);

		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTadcC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//add the two arguments:
			int re = inst.addInstr(reg, arg1.toString(), arg2.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then add the C (carry):
			inst.addInstr(reg, ret, C);
		}

		return null;
	}

	@Override
	public Object visit(ASTadcCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//add the two arguments:
			int re = inst.addInstr(reg, arg1.toString(), arg2.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then add the C (carry):
			int result = inst.addInstr(reg, ret, C);

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

	///SBC///
	@Override
	public Object visit(ASTsbc node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//Sub the two arguments first
		int re = inst.subInstr(reg, arg1.toString(), arg2.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then sub the C (carry):
		inst.subInstr(reg, ret, C);

		return null;
	}

	@Override
	public Object visit(ASTsbcS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		//Sub the two arguments first
		int re = inst.subInstr(reg, arg1.toString(), arg2.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then sub the C (carry):
		int result = inst.subInstr(reg, ret, C);

		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTsbcC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first
			int re = inst.subInstr(reg, arg1.toString(), arg2.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then sub the C (carry):
			inst.subInstr(reg, ret, C);
		}

		return null;
	}

	@Override
	public Object visit(ASTsbcCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first
			int re = inst.subInstr(reg, arg1.toString(), arg2.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then sub the C (carry):
			int result = inst.subInstr(reg, ret, C);

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

	///RSC///
	@Override
	public Object visit(ASTrsc node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//Sub the two arguments first:
		int re = inst.subInstr(reg, arg2.toString(), arg1.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then sub the C:
		inst.subInstr(reg, ret, C);

		return null;
	}

	@Override
	public Object visit(ASTrscS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		//Sub the two arguments first:
		int re = inst.subInstr(reg, arg2.toString(), arg1.toString());
		String ret = String.valueOf(re);
		String C = cpsrReg.get("C").toString();

		//Then sub the C:
		int result = inst.subInstr(reg, ret, C);

		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTrscC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int re = inst.subInstr(reg, arg2.toString(), arg1.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then sub the C:
			inst.subInstr(reg, ret, C);
		}
		return null;
	}

	@Override
	public Object visit(ASTrscCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first:
			int re = inst.subInstr(reg, arg2.toString(), arg1.toString());
			String ret = String.valueOf(re);
			String C = cpsrReg.get("C").toString();

			//Then sub the C:
			int result = inst.subInstr(reg, ret, C);

			upCpsr.update(result);
		}
		return null;
	}

/////Instruction that update automatically the cpsr:
	///CMP///
	@Override
	public Object visit(ASTcmp node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		
		//cpsr set on the result of reg-arg1:
		int result = inst.cmpInstr(reg, arg1.toString());
		
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTcmpC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg-arg1:
			int result = inst.cmpInstr(reg, arg1.toString());
			
			upCpsr.update(result);
		}
		
		return null;
	}

	///CMN///
	@Override
	public Object visit(ASTcmn node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		
		//cpsr set on the result of reg+arg1:
		int result = inst.cmnInstr(reg, arg1.toString());
		
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTcmnC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg-arg1:
			int result = inst.cmnInstr(reg, arg1.toString());
			
			upCpsr.update(result);
		}
		return null;
	}

	///TST///
	@Override
	public Object visit(ASTtst node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTtstC node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
