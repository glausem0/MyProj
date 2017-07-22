package compAndInt;

import java.util.HashMap;

import instructions.Condition;
import instructions.Instruction;
import instructions.UpdateCPSR;
import memory.Memory;
import registers.Cpsr;
import registers.Register;

public class Visitors implements MyTestVisitor{

	Register regData = new Register();
	private HashMap<Object, Object> reg = regData.init();

	Cpsr cpsr = new Cpsr();
	private HashMap<Object, Object> cpsrReg = cpsr.init();
	
	Memory memory = new Memory();
	private HashMap<Object, Object> mem = memory.init();

	Instruction inst = new Instruction(reg, mem, memory);
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

////Register, number, hexa, shift, cond and Scond:////
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
	public Object visit(ASThexa node, Object data) {
		String valStr = (String) node.data.get("hexa");
		valStr = valStr.replace("#0x", "");
		//to have signed integer from hex:
		long valLong = Long.parseLong(valStr, 16);
		int value = (int) valLong;
		
		return value;
	}
	
	@Override
	public Object visit(ASTnum node, Object data) {
		String val = (String) node.data.get("num");
		
		return val;
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
	
	@Override
	public Object visit(ASTlsl node, Object data) {
		String lsl = node.value.toString();

		return lsl;
	}

	@Override
	public Object visit(ASTlsr node, Object data) {
		String lsr = node.value.toString();

		return lsr;
	}
	
	@Override
	public Object visit(ASTshift node, Object data) {
		String fle = node.value.toString();

		return fle;		
	}

	
////Instructions:////

	//print la table des registres et cpsr
	public void print(){
		System.out.println("Register:\n");
		regData.print();
		System.out.println("CPSR\n");
		cpsr.print();
		System.out.println("Memory\n");
		memory.print();
	}

	///shift LSL/LSR ///
	@Override
	public Object visit(ASTshiftLS node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object shift = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		
		int shiftVal = inst.shiftInstr(shift.toString(), reg.toString(), val.toString());
		
		return shiftVal;	
	}
	
	///shift "<<" ///
	@Override
	public Object visit(ASTshiftF node, Object data) {
		Object number = node.jjtGetChild(0).jjtAccept(this, data);
		Object shift = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		
		int shiftVal = inst.shiftInstr(shift.toString(), number.toString(), val.toString());
		
		return shiftVal;
	}
	
    ////Arithmetique////	
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
		
		if (C.equals("1")) C = "0";
		else C="1";

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

		if (C.equals("1")) C = "0";
		else C="1";
		
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

			if (C.equals("1")) C = "0";
			else C="1";
			
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

			if (C.equals("1")) C = "0";
			else C="1";
			
			//Then sub the C:
			int result = inst.subInstr(reg, ret, C);

			upCpsr.update(result);
		}
		return null;
	}

    ////Comparaison////
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
	
	///TEQ///
	@Override
	public Object visit(ASTteq node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		
		//cpsr set on the result of reg^arg1:
		int result = inst.teqInstr(reg, arg1.toString());
		
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTteqC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg^arg1:
			int result = inst.teqInstr(reg, arg1.toString());
			
			upCpsr.update(result);
		}
		
		return null;
	}
	
	///TST
	@Override
	public Object visit(ASTtst node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		
		//cpsr set on the result of reg&arg1:
		int result = inst.tstInstr(reg, arg1.toString());
		
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTtstC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg&arg1:
			int result = inst.tstInstr(reg, arg1.toString());
			
			upCpsr.update(result);
		}
		
		return null;
	}

    ////Logique////
	///AND///
	@Override
	public Object visit(ASTand node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.andInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTandS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.andInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTandC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			inst.andInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTandCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.andInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}

	///BIC///
	@Override
	public Object visit(ASTbic node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.bicInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTbicS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.bicInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTbicC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			inst.bicInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTbicCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.bicInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}

	///EOR///
	@Override
	public Object visit(ASTeor node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.eorInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTeorS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.eorInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTeorC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			inst.eorInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTeorCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.eorInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}

	///ORR///
	@Override
	public Object visit(ASTorr node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.orrInstr(reg, arg1.toString(), arg2.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTorrS node, Object data) {
		Object sCond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		int result = inst.orrInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);
		
		return null;
	}

	@Override
	public Object visit(ASTorrC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			inst.orrInstr(reg, arg1.toString(), arg2.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTorrCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object sCond = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(3).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(4).jjtAccept(this, data);
		
		if ( condition.condAction(cond.toString()) ){
			int result = inst.orrInstr(reg, arg1.toString(), arg2.toString());
			upCpsr.update(result);
		}
		
		return null;
	}

	
///LDR/STR///	
	
	@Override
	public Object visit(ASTcloseAUp node, Object data) {
		String cu = "CU"; //close and update
		return cu;
	}

	@Override
	public Object visit(ASTclose node, Object data) {
		String c = "C";
		return c;
	}
	
	@Override
	public Object visit(ASTldrSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(2).jjtAccept(this, data);
		
		String val="null";
		
		inst.preLdrIntr(regLdr.toString(), regV.toString(), val, close, "p");
		
		return null;
	}

	@Override
	public Object visit(ASTldrPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.preLdrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "n");
		
		return null;
	}

	@Override
	public Object visit(ASTldrPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.preLdrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "p");
		
		return null;
	}
	
	@Override
	public Object visit(ASTldrPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.postLdrIntr(regLdr.toString(), regV.toString(), val.toString(), "n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTldrPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		
		inst.postLdrIntr(regLdr.toString(), regV.toString(), val.toString(), "p");
		
		return null;
	}

}
