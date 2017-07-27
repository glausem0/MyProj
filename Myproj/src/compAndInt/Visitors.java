package compAndInt;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import instructions.AccessMemory;
import instructions.Condition;
import instructions.Instruction;
import instructions.UpdateCPSR;
import memory.Memory;
import registers.Cpsr;
import registers.Register;

public class Visitors implements MyTestVisitor{

	private Register regData;
	private HashMap<Object, Object> reg; 
	private Cpsr cpsr; 
	private HashMap<Object, Object> cpsrReg; 
	private Memory memory; 
	private LinkedHashMap<Object, Object> memor; 
	private Condition condition;
	private UpdateCPSR upCpsr;
	private AccessMemory AMem;
	private Instruction inst;

	public Visitors(Register regData, 
			HashMap<Object, Object> reg, 
			Cpsr cpsr, 
			HashMap<Object, Object> cpsrReg, 
			Memory memory, 
			LinkedHashMap<Object, Object> memor, 
			Condition condition,
			UpdateCPSR upCpsr,
			AccessMemory AMem,
			Instruction inst){
		this.regData = regData;
		this.reg = reg;
		this.cpsr = cpsr;
		this.cpsrReg = cpsrReg;
		this.memory = memory;
		this.memor = memor;
		this.condition = condition;
		this.upCpsr = upCpsr;
		this.AMem = AMem;
		this.inst = inst;
	}
	
	
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
	public Object visit(ASTb_label node, Object data) {
		String b_label = node.value.toString();
		
		return b_label;
	}


	@Override
	public Object visit(ASTlabel_b node, Object data) {
		String label_b = node.value.toString();
		
		return label_b;
	}
	
	@Override
	public Object visit(ASTamode node, Object data) {
		String amode = node.value.toString();
		
		return amode;
	}

	@Override
	public Object visit(ASTcond node, Object data) {
		String cond = node.value.toString();

		return cond;
	}

	/*
	@Override
	public Object visit(ASTscnd node, Object data) {
		String sCond = node.value.toString();

		return sCond;
	}

	@Override
	public Object visit(ASTbCond node, Object data) {
		String bCond = node.value.toString();

		return bCond;
	}

	@Override
	public Object visit(ASThCond node, Object data) {
		String hCond = node.value.toString();

		return hCond;
	}
	 */

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
	public Object visit(ASTasr node, Object data) {
		String asr = node.value.toString();

		return asr;
	}

	@Override
	public Object visit(ASTror node, Object data) {
		String ror = node.value.toString();

		return ror;
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

	///shift LSL/LSR/ASR/ROR ///
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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);

		inst.movInstr(reg, val);
		upCpsr.update( Integer.parseInt(val.toString()) );

		return null;
	}

	@Override
	public Object visit(ASTdeclCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.addInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTaddCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg, arg1.toString(), arg2.toString());
		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTsubCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg, arg2.toString(), arg1.toString());
		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTrsbCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

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
		Object reg = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

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

	///LDR///
	@Override
	public Object visit(ASTldrSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(2).jjtAccept(this, data);

		String val="null";

		inst.ldrIntr(regLdr.toString(), regV.toString(), val, close,"pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		String val="null";

		if(condition.condAction(cond.toString())){	
			inst.ldrIntr(regLdr.toString(), regV.toString(), val, close,"pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(),"pre", "n");

		return null;
	}

	@Override
	public Object visit(ASTldrCPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(),"pre", "n");
		}

		return null;
	}

	@Override
	public Object visit(ASTldrPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}


	@Override
	public Object visit(ASTldrPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrBPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCBPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrBPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");

		return null;

	}

	@Override
	public Object visit(ASTldrCBPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTldrBPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "n");

		return null;
	}

	@Override
	public Object visit(ASTldrCBPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "n");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTldrBPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "p");

		return null;
	}

	@Override
	public Object visit(ASTldrCBPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTldrBSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.ldrBInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCBSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrBInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}


	@Override
	public Object visit(ASTldrHPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCHPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrHPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCHPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrHPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCHPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrHPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCHPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrHSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.ldrHInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCHSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrHInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSHPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSHPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSHPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSHPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSHPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	

	@Override
	public Object visit(ASTldrCSHPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSHPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	
	@Override
	public Object visit(ASTldrCSHPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrSHInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSHSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.ldrSHInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSHSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrSHInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTldrSBPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSBPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSBPrePos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSBPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close.toString(), "pre", "p");
		}
		return null;
	}


	@Override
	public Object visit(ASTldrSBPostNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSBPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";
		
		if(condition.condAction(cond.toString())){
		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSBPostPos node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSBPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrSBInstr(regLdr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTldrSBSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.ldrSBInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTldrCSBSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.ldrSBInstr(regLdr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}


	///STR///
	@Override
	public Object visit(ASTstrSimple node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(2).jjtAccept(this, data);

		String val="null";

		inst.strIntr(regStr.toString(), regV.toString(), val, close, "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		String val="null";
		
		if(condition.condAction(cond.toString())){
			inst.strIntr(regStr.toString(), regV.toString(), val, close, "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrPreNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrPrePos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrPostNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrPostPos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strIntr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrBPreNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCBPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrBPrePos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCBPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTstrBPostNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCBPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrBPostPos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCBPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";
	
		if(condition.condAction(cond.toString())){
			inst.strBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTstrBSimple node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.strBInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCBSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";
		if(condition.condAction(cond.toString())){
			inst.strBInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrHPreNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCHPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrHPrePos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCHPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrHPostNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCHPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";
		
		if(condition.condAction(cond.toString())){
			inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}


	@Override
	public Object visit(ASTstrHPostPos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCHPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}


	@Override
	public Object visit(ASTstrHSimple node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.strHInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCHSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.strHInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSHPreNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSHPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSHPrePos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSHPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSHPostNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSHPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSHPostPos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSHPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strSHInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSHSimple node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.strSHInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCSHSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.strSHInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}
		return null;
	}
	
	@Override
	public Object visit(ASTstrSBPreNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");

		return null;
	}
	
	@Override
	public Object visit(ASTstrCSBPreNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSBPrePos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(3).jjtAccept(this, data);

		inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCSBPrePos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);
		String close = (String) node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "pre", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSBPostNeg node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");

		return null;
	}

	@Override
	public Object visit(ASTstrCSBPostNeg node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";

		if(condition.condAction(cond.toString())){
			inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "n");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSBPostPos node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		String close = "null";

		inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCSBPostPos node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object val = node.jjtGetChild(3).jjtAccept(this, data);

		String close = "null";
		if(condition.condAction(cond.toString())){
			inst.strSBInstr(regStr.toString(), regV.toString(), val.toString(), close, "post", "p");
		}
		return null;
	}

	@Override
	public Object visit(ASTstrSBSimple node, Object data) {
		Object regStr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val = "null";

		inst.strSBInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");

		return null;
	}

	@Override
	public Object visit(ASTstrCSBSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regStr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val = "null";

		if(condition.condAction(cond.toString())){
			inst.strSBInstr(regStr.toString(), regV.toString(), val, close.toString(), "pre", "p");
		}

		return null;
	}

///LDM///
	@Override
	public Object visit(ASTldmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		
		String regEnd = "null";
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTldmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		
		String regEnd = "null";
		
		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString());
		}
		return null;
	}

	//TODO Verify enum.
	@Override
	public Object visit(ASTldmEnum node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		
		return null;
	}
	
	//TODO Verify enum
	@Override
	public Object visit(ASTldmCEnum node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTldmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		
		return null;
	}
	

	@Override
	public Object visit(ASTldmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		}
		return null;
	}

	@Override
	public Object visit(ASTMldmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		
		String regEnd = "null";
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTMldmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		
		String regEnd = "null";
		
		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString());
		}
		
		return null;
	}

	//TODO Verify enum.
	@Override
	public Object visit(ASTMldmEnum node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTMldmCEnum node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTMldmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTMldmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		}

		return null;
	}

///STM///
	@Override
	public Object visit(ASTstmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		
		String regEnd = "null";
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTstmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		
		String regEnd = "null";
		
		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString());
		}
		return null;
	}

	//TODO verify enum.
	@Override
	public Object visit(ASTstmEnum node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTstmCEnum node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTstmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTstmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString());
		}
		return null;
	}

	@Override
	public Object visit(ASTMstmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		
		String regEnd = "null";
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTMstmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		
		String regEnd = "null";

		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString());
		}
		return null;
	}

	//TODO verify enum
	@Override
	public Object visit(ASTMstmEnum node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		
		return null;
	}
	
	@Override
	public Object visit(ASTMstmCEnum node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		}
		return null;
	}

	@Override
	public Object visit(ASTMstmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		
		inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		
		return null;
	}

	@Override
	public Object visit(ASTMstmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(condition.condAction(cond.toString())){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString());
		}
		return null;
	}


	@Override
	public Object visit(ASTBLBlock node, Object data) {
		Object labelEnd = node.jjtGetChild(2).jjtAccept(this, data);
		//Do not accept other nodes, otherwise they will be executed
		
		return null;
	}


	@Override
	public Object visit(ASTBCLBlock node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}



}
