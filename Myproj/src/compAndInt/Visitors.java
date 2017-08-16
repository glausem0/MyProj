package compAndInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

import instructions.AccessMemory;
import instructions.Condition;
import instructions.Instruction;
import instructions.UpdateCPSR;
import memory.Memory;
import registers.Cpsr;
import registers.Register;
import view.View;

public class Visitors implements MyParserVisitor{

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

	//node prog:
	private int pc; //r15
	private int child;
	Object[] progArray;
	HashMap<String, Integer> branches;

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

	public int getPc(){
		return pc;
	}

	public void setPc(int val){
		pc = val;
	}

	public int getChild(){
		return child;
	}

	public void setChild(int val){
		child = val;
	}

	public Object[] getProgArray(){
		return progArray;
	}

	public void setProgArray(Object[] prog){
		progArray = prog;
	}

	public HashMap<String, Integer> getBranches(){
		return branches;
	}
	
	public void setBranches(HashMap<String, Integer> hash){
		branches = hash;
	}
	
	/**
	 * map register sp to r13, lr, to r14, pc to r15
	 * @param register
	 * @return
	 */
	private String mapRegisters(Object register){
		String returnStr;

		switch(register.toString()){
		case "sp":
			returnStr = "r13";
			break;

		case "lr":
			returnStr = "r14";
			break;

		case "pc":
			returnStr = "r15";
			break;

		default:
			returnStr = register.toString();
		}

		return returnStr;
	}

	/**
	 * @param node
	 * @return true if node is a non terminal node.
	 */
	private boolean principalNode(String node){
		switch(node){
		case("decl"): return true;
		case("declS"): return true;
		case("declC"): return true;
		case("declCS"): return true;

		case("decln"): return true;
		case("declnS"): return true;
		case("declnC"): return true;
		case("declnCS"): return true;

		case("add"): return true;
		case("addS"): return true;
		case("addC"): return true;
		case("addCS"): return true;

		case("adc"): return true;
		case("adcS"): return true;
		case("adcC"): return true;
		case("adcCS"): return true;

		case("sub"): return true;
		case("subS"): return true;
		case("subC"): return true;
		case("subCS"): return true;

		case("sbc"): return true;
		case("sbcS"): return true;
		case("sbcC"): return true;
		case("sbcCS"): return true;

		case("rsb"): return true;
		case("rsbS"): return true;
		case("rsbC"): return true;
		case("rsbCS"): return true;

		case("rsc"): return true;
		case("rscS"): return true;
		case("rscC"): return true;
		case("rscCS"): return true;

		case("mla"): return true;
		case("mlaS"): return true;
		case("mlaC"): return true;
		case("mlaCS"): return true;

		case("mul"): return true;
		case("mulS"): return true;
		case("mulC"): return true;
		case("mulCS"): return true;

		case("smlal"): return true;
		case("smlalS"): return true;
		case("smlalC"): return true;
		case("smlalCS"): return true;

		case("smull"): return true;
		case("smullS"): return true;
		case("smullC"): return true;
		case("smullCS"): return true;

		case("umlal"): return true;
		case("umlalS"): return true;
		case("umlalC"): return true;
		case("umlalCS"): return true;

		case("umull"): return true;
		case("umullS"): return true;
		case("umullC"): return true;
		case("umullCS"): return true;

		case("cmp"): return true;
		case("cmpC"): return true;

		case("cmn"): return true;
		case("cmnC"): return true;

		case("teq"): return true;
		case("teqC"): return true;

		case("tst"): return true;
		case("tstC"): return true;

		case("and"): return true;
		case("andS"): return true;
		case("andC"): return true;
		case("andCS"): return true;

		case("bic"): return true;
		case("bicS"): return true;
		case("bicC"): return true;
		case("bicCS"): return true;

		case("eor"): return true;
		case("eorS"): return true;
		case("eorC"): return true;
		case("eorCS"): return true;

		case("orr"): return true;
		case("orrS"): return true;
		case("orrC"): return true;
		case("orrCS"): return true;

		case("ldrPreNeg"): return true;
		case("ldrPrePos"): return true;
		case("ldrPostNeg"): return true;
		case("ldrPostPos"): return true;
		case("ldrSimple"): return true;

		case("ldrBPreNeg"): return true;
		case("ldrBPrePos"): return true;
		case("ldrBPostNeg"): return true;
		case("ldrBPostPos"): return true;
		case("ldrBSimple"): return true;

		case("ldrHPreNeg"): return true;
		case("ldrHPrePos"): return true;
		case("ldrHPostNeg"): return true;
		case("ldrHPostPos"): return true;
		case("ldrHSimple"): return true;

		case("ldrSBPreNeg"): return true;
		case("ldrSBPrePos"): return true;
		case("ldrSBPostNeg"): return true;
		case("ldrSBPostPos"): return true;
		case("ldrSBSimple"): return true;

		case("ldrSHPreNeg"): return true;
		case("ldrSHPrePos"): return true;
		case("ldrSHPostNeg"): return true;
		case("ldrSHPostPos"): return true;
		case("ldrSHSimple"): return true;

		case("ldrCPreNeg"): return true;
		case("ldrCPrePos"): return true;
		case("ldrCPostNeg"): return true;
		case("ldrCPostPos"): return true;
		case("ldrCSimple"): return true;

		case("ldrCBPreNeg"): return true;
		case("ldrCBPrePos"): return true;
		case("ldrCBPostNeg"): return true;
		case("ldrCBPostPos"): return true;
		case("ldrCBSimple"): return true;

		case("ldrCHPreNeg"): return true;
		case("ldrCHPrePos"): return true;
		case("ldrCHPostNeg"): return true;
		case("ldrCHPostPos"): return true;
		case("ldrCHSimple"): return true;

		case("ldrCSBPreNeg"): return true;
		case("ldrCSBPrePos"): return true;
		case("ldrCSBPostNeg"): return true;
		case("ldrCSBPostPos"): return true;
		case("ldrCSBSimple"): return true;

		case("ldrCSHPreNeg"): return true;
		case("ldrCSHPrePos"): return true;
		case("ldrCSHPostNeg"): return true;
		case("ldrCSHPostPos"): return true;
		case("ldrCSHSimple"): return true;

		case("strPreNeg"): return true;
		case("strPrePos"): return true;
		case("strPostNeg"): return true;
		case("strPostPos"): return true;
		case("strSimple"): return true;

		case("strBPreNeg"): return true;
		case("strBPrePos"): return true;
		case("strBPostNeg"): return true;
		case("strBPostPos"): return true;
		case("strBSimple"): return true;

		case("strHPreNeg"): return true;
		case("strHPrePos"): return true;
		case("strHPostNeg"): return true;
		case("strHPostPos"): return true;
		case("strHSimple"): return true;

		case("strCPreNeg"): return true;
		case("strCPrePos"): return true;
		case("strCPostNeg"): return true;
		case("strCPostPos"): return true;
		case("strCSimple"): return true;

		case("strCBPreNeg"): return true;
		case("strCBPrePos"): return true;
		case("strCBPostNeg"): return true;
		case("strCBPostPos"): return true;
		case("strCBSimple"): return true;

		case("strCHPreNeg"): return true;
		case("strCHPrePos"): return true;
		case("strCHPostNeg"): return true;
		case("strCHPostPos"): return true;
		case("strCHSimple"): return true;

		case("ldmSimple"): return true;
		case("ldmList"): return true;
		case("MldmSimple"): return true;
		case("MldmList"): return true;
		case("ldmCSimple"): return true;
		case("ldmCList"): return true;
		case("MldmCSimple"): return true;
		case("MldmCList"): return true;
		case("MldmEnum"): return true;
		case("ldmEnum"): return true;
		case("ldmCEnum"): return true;
		case("MldmCEnum"): return true;

		case("stmSimple"): return true;
		case("stmList"): return true;
		case("MstmSimple"): return true;
		case("MstmList"): return true;
		case("stmCSimple"): return true;
		case("stmCList"): return true;
		case("MstmCSimple"): return true;
		case("MstmCList"): return true;
		case("MstmEnum"): return true;
		case("stmEnum"): return true;
		case("stmCEnum"): return true;
		case("MstmCEnum"): return true;

		case("swp"): return true;
		case("swpb"): return true;
		case("swpC"): return true;
		case("swpCB"): return true;

		default: return false;

		}
	}

	/**
	 * @param progArray
	 * @param elementLine
	 * @return the line of the in the programme of the node 
	 */
	private int searchLineProg(Object[] progArray, String node){
		int returnI=0;
		for (int i=0; i < progArray.length; i++){
			if(progArray[i].toString().equals(node)){ //equals find immediately
				returnI = i;
			}
		}
		return returnI;
	}


	//********************************Nodes definition********************************//

	//***Program and simple node:***//	
	@Override
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	@Override
	public Object visit(ASTprog node, Object data) {
		//init elements:
		int children = node.jjtGetNumChildren();

		//program, children and lines:
		while(child < children){
			switch(node.jjtGetChild(child).toString()){
			case("label"):{//store children number and line of label in Hashtable:
				if( !branches.containsKey("label"+node.jjtGetChild(child).jjtAccept(this, data).toString()) ){
					branches.put("label"+node.jjtGetChild(child).jjtAccept(this, data).toString(), child);
					branches.put("lineLabel"+node.jjtGetChild(child).jjtAccept(this, data).toString(), searchLineProg(progArray, node.jjtGetChild(child).jjtAccept(this, data).toString()) );
				}

				child += 1;
				pc += 1; 

				reg.put("r15", pc);
			}
			break;

			case("branch"):{
				String labelBranch = node.jjtGetChild(child).jjtAccept(this, data).toString();
				if(branches.containsKey("label"+labelBranch)){//if label before branch
					int newi = branches.get("label"+labelBranch);
					int newLine = branches.get("lineLabel"+labelBranch);

					pc = newLine + 2;
					child = newi;
				}
				else{//branch before label:
					boolean in = true;
					while(in){
						if(!node.jjtGetChild(child).toString().equals("label")){ //if node not label
							if(principalNode(node.jjtGetChild(child).toString())){
								pc +=1;
							}
							child +=1;
						}
						else if(    (node.jjtGetChild(child).toString().equals("label")) 
								&& (!node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){ //if node is a label but not desire one
							pc += 1; //because label.
							child +=1;
						}
						else if(    (node.jjtGetChild(child).toString().equals("label")) 
								&& (node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){
							int newi = child;
							int newLine = searchLineProg(progArray, node.jjtGetChild(child).jjtAccept(this, data).toString());

							pc = newLine + 2;
							child= newi;

							in = false;
						}
					}
				}
				reg.put("r15", pc);
			}
			break;

			case("Cbranch"):{
				String labelBranchC = node.jjtGetChild(child).jjtAccept(this, data).toString();
				String[] condAndLab = labelBranchC.split(",");
				String cond = condAndLab[0];
				String labelBranch = condAndLab[1];

				boolean c = condition.condAction(cond); 

				if(c){
					if(branches.containsKey("label"+labelBranch)){//if label before branch
						int newi = branches.get("label"+labelBranch);
						int newLine = branches.get("lineLabel"+labelBranch);

						pc = newLine + 2;
						child = newi;
					}
					else{//branch before label:
						boolean in = true;
						while(in){
							if(!node.jjtGetChild(child).toString().equals("label")){ //if node not label
								if(principalNode(node.jjtGetChild(child).toString())){
									pc +=1;
								}
								child +=1;
							}
							else if(    (node.jjtGetChild(child).toString().equals("label")) 
									&& (!node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){ //if node is a label but not desire one
								pc += 1; //because label.
								child +=1;
							}
							else if(    (node.jjtGetChild(child).toString().equals("label")) 
									&& (node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){
								int newi = child;
								int newLine = searchLineProg(progArray, node.jjtGetChild(child).jjtAccept(this, data).toString());

								pc = newLine + 2;
								child= newi;

								in = false;
							}
						}
					}
				}
				else{
					node.jjtGetChild(child).jjtAccept(this, data);
					pc += 1;
					child += 1;
				}
				
				reg.put("r15", pc);
			}
			break;

			default:
				node.jjtGetChild(child).jjtAccept(this, data);
				if(principalNode(node.jjtGetChild(child).toString())){
					pc += 1;
				}
				child += 1;
				reg.put("r15", pc);
			}//end switch	
		}//end while
		return "Program";
	}

	//***Terminal node:***//

	//Register
	@Override
	public Object visit(ASTregister node, Object data) {
		String valStr = (String) node.data.get("reg");

		return mapRegisters(valStr);
	}

	//Number
	@Override
	public Object visit(ASTnumber node, Object data) {
		String valStr = (String) node.data.get("value");
		valStr = valStr.replace("#", "");
		int valInt = Integer.parseInt(valStr);	

		return valInt;
	}

	//Hexadecimal
	@Override
	public Object visit(ASThexa node, Object data) {
		String valStr = (String) node.data.get("hexa");
		valStr = valStr.replace("#0x", "");
		//to have signed integer from hex:
		long valLong = Long.parseLong(valStr, 16);
		int value = (int) valLong;

		return value;
	}

	//num
	@Override
	public Object visit(ASTnum node, Object data) {
		String val = (String) node.data.get("num");

		return val;
	}

	//amode
	@Override
	public Object visit(ASTamode node, Object data) {
		String amode = node.value.toString();

		return amode;
	}

	//condition
	@Override
	public Object visit(ASTcond node, Object data) {
		String cond = node.value.toString();

		return cond;
	}

	//lsl
	@Override
	public Object visit(ASTlsl node, Object data) {
		String lsl = node.value.toString();

		return lsl;
	}

	//lsr
	@Override
	public Object visit(ASTlsr node, Object data) {
		String lsr = node.value.toString();

		return lsr;
	}

	//asr
	@Override
	public Object visit(ASTasr node, Object data) {
		String asr = node.value.toString();

		return asr;
	}

	//ror
	@Override
	public Object visit(ASTror node, Object data) {
		String ror = node.value.toString();

		return ror;
	}

	//shift(<< and >>)
	@Override
	public Object visit(ASTshift node, Object data) {
		String fle = node.value.toString();

		return fle;		
	}

	//condition and update
	@Override
	public Object visit(ASTcloseAUp node, Object data) {
		String cu = "CU"; //close and update
		return cu;
	}

	//condition only
	@Override
	public Object visit(ASTclose node, Object data) {
		String c = "C";
		return c;
	}

	//***Non terminal node***//
	//*Shift*//

	//shift LSL/LSR/ASR/ROR
	@Override
	public Object visit(ASTshiftLS node, Object data) {
		Object reg = node.jjtGetChild(0).jjtAccept(this, data);
		Object shift = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		int shiftVal = inst.shiftInstr(shift.toString(), reg.toString(), val.toString());

		return shiftVal;	
	}

	//shift "<<" 
	@Override
	public Object visit(ASTshiftF node, Object data) {
		Object number = node.jjtGetChild(0).jjtAccept(this, data);
		Object shift = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		int shiftVal = inst.shiftInstr(shift.toString(), number.toString(), val.toString());

		return shiftVal;
	}

	//*Arithmetic*//	
	//MOV
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

	//MVN
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

	//ADD
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

	//ADC
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

	//SUB
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

	//SBC
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


	//RSB
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

	//RSC
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

	//MLA
	@Override
	public Object visit(ASTmla node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		inst.mlaInstr(reg1, reg2.toString(), reg3.toString(), reg4.toString());

		return null;
	}

	@Override
	public Object visit(ASTmlaS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		int result = inst.mlaInstr(reg1, reg2.toString(), reg3.toString(), reg4.toString());

		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTmlaC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.mlaInstr(reg1, reg2.toString(), reg3.toString(), reg4.toString());
		}
		return null;
	}


	@Override
	public Object visit(ASTmlaCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			int result = inst.mlaInstr(reg1, reg2.toString(), reg3.toString(), reg4.toString());
			upCpsr.update(result);
		}
		return null;
	}

	//MUL
	@Override
	public Object visit(ASTmul node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		inst.mulInstr(reg1, reg2.toString(), reg3.toString());

		return null;
	}

	@Override
	public Object visit(ASTmulS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.mulInstr(reg1, reg2.toString(), reg3.toString());

		upCpsr.update(result);

		return null;
	}

	@Override
	public Object visit(ASTmulC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.mulInstr(reg1, reg2.toString(), reg3.toString());
		}
		return null;
	}

	@Override
	public Object visit(ASTmulCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			int result = inst.mulInstr(reg1, reg2.toString(), reg3.toString());
			upCpsr.update(result);
		}
		return null;
	}

	//SMLAL
	@Override
	public Object visit(ASTsmlal node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		inst.smlalInstr(reg1, reg2, reg3.toString(), reg4.toString());

		return null;
	}

	@Override
	public Object visit(ASTsmlalS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.smlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		upCpsr.update((int) result);

		return null;
	}

	@Override
	public Object visit(ASTsmlalC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.smlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		}

		return null;
	}

	@Override
	public Object visit(ASTsmlalCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			long result = inst.smlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
			upCpsr.update((int) result);
		}
		return null;
	}

	//SMULL
	@Override
	public Object visit(ASTsmull node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		inst.smullInstr(reg1, reg2, reg3.toString(), reg4.toString());

		return null;
	}


	@Override
	public Object visit(ASTsmullS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.smullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		upCpsr.update((int) result);

		return null;
	}


	@Override
	public Object visit(ASTsmullC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.smullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		}

		return null;
	}


	@Override
	public Object visit(ASTsmullCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			long result = inst.smullInstr(reg1, reg2, reg3.toString(), reg4.toString());
			upCpsr.update((int) result);

		}
		return null;
	}

	//UMLAL
	@Override
	public Object visit(ASTumlal node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		inst.umlalInstr(reg1, reg2, reg3.toString(), reg4.toString());

		return null;
	}


	@Override
	public Object visit(ASTumlalS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.umlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		upCpsr.update((int) result);

		return null;
	}


	@Override
	public Object visit(ASTumlalC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.umlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		}
		return null;
	}


	@Override
	public Object visit(ASTumlalCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			long result = inst.umlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
			upCpsr.update((int) result);
		}
		return null;
	}


	//UMULL
	@Override
	public Object visit(ASTumull node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		inst.umullInstr(reg1, reg2, reg3.toString(), reg4.toString());

		return null;
	}


	@Override
	public Object visit(ASTumullS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.umullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		upCpsr.update((int) result);

		return null;
	}


	@Override
	public Object visit(ASTumullC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.umullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		}

		return null;
	}


	@Override
	public Object visit(ASTumullCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(4).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			long result = inst.umullInstr(reg1, reg2, reg3.toString(), reg4.toString());
			upCpsr.update((int) result);
		}
		return null;
	}


	//*Comparaison*//
	//CMP
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

	//CMN
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

	//TEQ
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

	//TST
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

	//*Logique*//
	//AND
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

	//BIC
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

	//EOR
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

	//ORR
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


	//*LDR/STR*//	


	//LDR
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


	//STR
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

	/*
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
	 */

	//*LDM / STM*//

	//LDM
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

	@Override
	public Object visit(ASTldmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);

		String[] list = new String[children-2];

		for(int i=2; i<children; i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		inst.ldmInst(regL.toString(), list, false, amode.toString());

		return null;
	}


	@Override
	public Object visit(ASTldmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-3];

			for(int i=3; i<children; i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			inst.ldmInst(regL.toString(), list, false, amode.toString());
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

	@Override
	public Object visit(ASTMldmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);

		String[] list = new String[children-2];

		for(int i=2; i<children; i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		inst.ldmInst(regL.toString(), list, true, amode.toString());

		return null;
	}

	@Override
	public Object visit(ASTMldmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-3];

			for(int i=3; i<children; i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			inst.ldmInst(regL.toString(), list, true, amode.toString());
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

	//STM
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

	@Override
	public Object visit(ASTstmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);

		String[] list = new String[children-2];

		for(int i=2; i<children; i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		inst.stmInst(regL.toString(), list, false, amode.toString());

		return null;
	}

	@Override
	public Object visit(ASTstmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-3];

			for(int i=3; i<children; i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			inst.stmInst(regL.toString(), list, false, amode.toString());
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


	@Override
	public Object visit(ASTMstmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);

		String[] list = new String[children-2];

		for(int i=2; i<children; i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		inst.stmInst(regL.toString(), list, true, amode.toString());
		return null;
	}

	@Override
	public Object visit(ASTMstmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-3];

			for(int i=3; i<children; i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			inst.stmInst(regL.toString(), list, true, amode.toString());
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

	//*Swap*//
	//SWP
	@Override
	public Object visit(ASTswp node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		inst.swapInstr(reg1, reg2.toString(), reg3.toString(), false);

		return null;
	}


	@Override
	public Object visit(ASTswpb node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		inst.swapInstr(reg1, reg2.toString(), reg3.toString(), true);

		return null;
	}


	@Override
	public Object visit(ASTswpC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.swapInstr(reg1, reg2.toString(), reg3.toString(), false);
		}
		return null;
	}


	@Override
	public Object visit(ASTswpCB node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(3).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			inst.swapInstr(reg1, reg2.toString(), reg3.toString(), true);
		}
		return null;
	}

	//*Branch*//
	//branch
	@Override
	public Object visit(ASTbranch node, Object data) {
		String labelBranch = node.jjtGetChild(0).jjtAccept(this, data).toString();
		return labelBranch;
	}

	//label
	@Override
	public Object visit(ASTlabel node, Object data) {
		Object label = node.value.toString();
		return label;
	}

	//condition and label
	@Override
	public Object visit(ASTCbranch node, Object data) {
		String cond = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String label = node.jjtGetChild(1).jjtAccept(this, data).toString();

		String ret = cond+","+label;

		return ret;
	}
}
