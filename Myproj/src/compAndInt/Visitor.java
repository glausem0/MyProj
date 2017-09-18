package compAndInt;

import java.util.HashMap;
import java.util.LinkedHashMap;

import instructions.AccessMemory;
import instructions.Condition;
import instructions.Instruction;
import instructions.Update_C_S_psr;
import memory.Memory;
import registers.Cpsr_Spsr;
import registers.Register;

public class Visitor implements MyParserVisitor{

	private Register regData;
	private HashMap<Object, Object> reg; 
	private Cpsr_Spsr C_S_psr; 
	private HashMap<Object, Object> C_S_psrReg; 
	private Memory memory; 
	private LinkedHashMap<Object, Object> memor; 
	private Condition condition;
	private Update_C_S_psr upCSpsr;
	private AccessMemory AMem;
	private Instruction inst;

	//node prog:
	private int pc; //r15
	private int child;
	Object[] progArray;
	HashMap<String, Integer> branches;

	public Visitor(Register regData, 
			HashMap<Object, Object> reg, 
			Cpsr_Spsr C_S_psr,  
			HashMap<Object, Object> C_S_psrReg, 
			Memory memory, 
			LinkedHashMap<Object, Object> memor, 
			Condition condition,
			Update_C_S_psr upCSpsr,
			AccessMemory AMem,
			Instruction inst){
		this.regData = regData;
		this.reg = reg;
		this.C_S_psr = C_S_psr;
		this.C_S_psrReg = C_S_psrReg; 
		this.memory = memory;
		this.memor = memor;
		this.condition = condition;
		this.upCSpsr = upCSpsr;
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
	 * first look if we're in user mode or supervisor mode, then map the 
	 * name of registers corresponding to the mode 
	 * example: in user mode r14 = r14 and sp = r14; in supervisor mode r14 = r14_svc and sp = r14_svc
	 * @param register
	 * @return
	 */
	private String mapRegisters(Object register){
		String returnStr = null;

		if( C_S_psrReg.get("mode").toString().equals("10000") ){ //user mode
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
		}
		else if( C_S_psrReg.get("mode").toString().equals("10011") ){ //supervisor mode 
			switch(register.toString()){
			case("r13"):
				returnStr = "r13_svc";
				break;
			
			case "sp":
				returnStr = "r13_svc";
				break;
				
			case("r14"):
				returnStr = "r14_svc";
				break;
				
			case "lr":
				returnStr = "r14_svc";
				break;

			case "pc":
				returnStr = "r15";
				break;
			default:
				returnStr = register.toString();
			}
		}

		return returnStr;
	}

	/**
	 * copy the spsr values into cpsr if we're in supervisor mode:
	 */
	private void copySpsrInCpsr(){
		C_S_psrReg.put("N", C_S_psrReg.get("N_svc"));
		C_S_psrReg.put("Z", C_S_psrReg.get("Z_svc"));
		C_S_psrReg.put("C", C_S_psrReg.get("C_svc"));
		C_S_psrReg.put("V", C_S_psrReg.get("V_svc"));
		C_S_psrReg.put("I", C_S_psrReg.get("I_svc")); 
		C_S_psrReg.put("F", C_S_psrReg.get("F_svc"));
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

			case("branch"):{//simple branch (juste "B")
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
							pc +=1;
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

			case("Cbranch"):{ //branch with condition (B<cond>)
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

								pc +=1;
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

			case("branchLink"):{ //simple branch link (BL)
				int lr = 0;
				String labelBranch = node.jjtGetChild(child).jjtAccept(this, data).toString();
				if(branches.containsKey("label"+labelBranch)){//if label before branch
					int newi = branches.get("label"+labelBranch);
					int newLine = branches.get("lineLabel"+labelBranch);

					pc = newLine + 2;
					lr = pc-1;
					child = newi;
				}
				else{//branch before label:
					boolean in = true;
					while(in){
						if(!node.jjtGetChild(child).toString().equals("label")){ //if node not label
							pc +=1;
							lr = pc-1;
							child +=1;
						}
						else if(    (node.jjtGetChild(child).toString().equals("label")) 
								&& (!node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){ //if node is a label but not desire one
							pc += 1; //because label.
							lr = pc-1;
							child +=1;
						}
						else if(    (node.jjtGetChild(child).toString().equals("label")) 
								&& (node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){
							int newi = child;
							int newLine = searchLineProg(progArray, node.jjtGetChild(child).jjtAccept(this, data).toString());

							pc = newLine + 2;
							lr = pc-1;
							child= newi;

							in = false;
						}
					}
				}
				reg.put("r15", pc);
				reg.put(mapRegisters("lr"), lr);
			}
			break;

			case("CbranchLink"):{ //branch link and condition (BL<cond>)
				int lr = 0;
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
						lr = pc-1;
						child = newi;
					}
					else{//branch before label:
						boolean in = true;
						while(in){
							if(!node.jjtGetChild(child).toString().equals("label")){ //if node not label
								pc +=1;
								lr = pc-1;
								child +=1;
							}
							else if(    (node.jjtGetChild(child).toString().equals("label")) 
									&& (!node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){ //if node is a label but not desire one
								pc += 1; //because label.
								lr = pc-1;
								child +=1;
							}
							else if(    (node.jjtGetChild(child).toString().equals("label")) 
									&& (node.jjtGetChild(child).jjtAccept(this, data).equals(labelBranch)) ){
								int newi = child;
								int newLine = searchLineProg(progArray, node.jjtGetChild(child).jjtAccept(this, data).toString());

								pc = newLine + 2;
								lr = pc-1;
								child= newi;

								in = false;
							}
						}
					}
				}
				else{
					node.jjtGetChild(child).jjtAccept(this, data);
					pc += 1;
					lr = pc-1;
					child += 1;
				}

				reg.put("r15", pc);
				reg.put(mapRegisters("lr"), lr);
			}
			break;
			
			case("swi"):{
				int lr = 0;
				String swi_h = node.jjtGetChild(child).jjtAccept(this, data).toString(); //get the swi number
				//number in swi_h is an swi instruction call in the program; not implemented here
				
				pc += 1;//to calculate lr, not definitive pc
				lr = pc-1;
				child += 1;
				//update before passing to supervisor mode
				reg.put("r15", 8);
				reg.put(mapRegisters("lr"), lr);
				
				//enter in supervisor mode -> change cpsr:
				C_S_psrReg.put("mode", 10011); //change mode in cpsr
				//save lr in lr_svc and cpsr in spsr:
				reg.put("r14_svc", lr);
				C_S_psrReg.put("N_svc", C_S_psrReg.get("N"));
				C_S_psrReg.put("Z_svc", C_S_psrReg.get("Z"));
				C_S_psrReg.put("C_svc", C_S_psrReg.get("C"));
				C_S_psrReg.put("V_svc", C_S_psrReg.get("V"));
				C_S_psrReg.put("I_svc", "1");
				C_S_psrReg.put("F_svc", C_S_psrReg.get("F"));
				
			}
			break;
			
			case("Cswi"):{
				String Cswi = node.jjtGetChild(child).jjtAccept(this, data).toString();
				String[] condAndHandler = Cswi.split(",");
				String cond = condAndHandler[0];
				String handler = condAndHandler[1];
				
				boolean c = condition.condAction(cond); 

				if(c){
					int lr = 0;
					String swi_h = node.jjtGetChild(child).jjtAccept(this, data).toString(); //get the swi number
					//number in swi_h is an swi instruction call in the programme; not implemented here
					
					pc += 1;//to calculate lr, not definitive pc
					lr = pc-1;
					child += 1;
					//update before passing to supervisor mode
					reg.put("r15", 8);
					reg.put(mapRegisters("lr"), lr);
					
					//enter in supervisor mode -> change cpsr:
					C_S_psrReg.put("mode", 10011); //change mode in cpsr
					//save lr in lr_svc and cpsr in spsr:
					reg.put("r14_svc", lr);
					C_S_psrReg.put("N_svc", C_S_psrReg.get("N"));
					C_S_psrReg.put("Z_svc", C_S_psrReg.get("Z"));
					C_S_psrReg.put("C_svc", C_S_psrReg.get("C"));
					C_S_psrReg.put("V_svc", C_S_psrReg.get("V"));
					C_S_psrReg.put("I_svc", "1");
					C_S_psrReg.put("F_svc", C_S_psrReg.get("F"));
				}
			}
			break;

			default:
				node.jjtGetChild(child).jjtAccept(this, data);
				pc += 1;
				child += 1;
				reg.put("r15", pc);
			}//end switch	
		}//end while
		return null;
	}

	//***Terminal node:***//

	//Register
	@Override
	public Object visit(ASTregister node, Object data) {
		String valStr = node.value.toString();

		return mapRegisters(valStr);
	}

	//Number
	@Override
	public Object visit(ASTnumber node, Object data) {
		String valStr = node.value.toString();
		valStr = valStr.replace("#", "");
		int valInt = Integer.parseInt(valStr);	

		return valInt;
	}

	//Hexa
	@Override
	public Object visit(ASThexa node, Object data) {
		String valStr = node.value.toString();
		valStr = valStr.replace("#0x", "");
		//to have signed integer from hex:
		long valLong = Long.parseLong(valStr, 16);
		int value = (int) valLong;

		return value;
	}
	
	//Hexadecimal
	@Override
	public Object visit(ASThexadecimal node, Object data) {
		String valStr = node.value.toString();
		valStr = valStr.replace("0x", "");
		//to have signed integer from hex:
		long valLong = Long.parseLong(valStr, 16);
		int value = (int) valLong;

		return value;
	}

	//num
	@Override
	public Object visit(ASTnum node, Object data) {
		String val = node.value.toString();
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

	//close and update
	@Override
	public Object visit(ASTcloseAUp node, Object data) {
		String cu = "CU"; //close and update
		return cu;
	}

	//close only
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
	public Object visit(ASTmov node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);

		inst.movInstr(reg1, val);
		
		//verify if reg1 = pc, then effects a jump to the calculated address
		if(reg1.toString().equals("r15")){
			setPc( Integer.parseInt(reg.get("r15").toString()) );
			setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
		}
		
		return null;
	}

	@Override
	public Object visit(ASTmovC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			inst.movInstr(reg1, val);
			
			if( reg1.toString().equals("r15") ){
				setPc( Integer.parseInt(reg.get("r15").toString()) );
				setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTmovS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);
		Object typeVal = node.jjtGetChild(1);
		
		inst.movInstr(reg1, val);
		
		if(typeVal.toString().equals("shiftLS")){
			upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, true, false);
		}
		else{
			upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, false, false);
		}
		
		//verify if reg1 = pc, then effects a jump to the calculated address
		if( reg1.toString().equals("r15") ){
			setPc( Integer.parseInt(reg.get("r15").toString()) );
			setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
		}

		//if in supervisor mode, copy the spsr into the cpsr, return to user mode:
		if( reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011") ){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTmovCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeVal = node.jjtGetChild(2);

		if ( condition.condAction(cond.toString()) ){
			inst.movInstr(reg1, val);
			
			if(typeVal.toString().equals("shiftLS")){
				upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, true, false);
			}
			else{
				upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, false, false);
			}
			
			if( reg1.toString().equals("r15") ){
				setPc( Integer.parseInt(reg.get("r15").toString()) );
				setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
			}

			//if in supervisor mode, copy the spsr into the cpsr, return to user mode:
			if( reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011") ){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//MVN
	@Override
	public Object visit(ASTmvn node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);

		inst.mvnInstr(reg1, val.toString());
		
		if( reg1.toString().equals("r15") ){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTmvnC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			inst.mvnInstr(reg1, val.toString());
			
			if(reg1.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTmvnS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object val = node.jjtGetChild(1).jjtAccept(this, data);
		Object typeVal = node.jjtGetChild(1);

		inst.mvnInstr(reg1, val.toString());
		
		if(typeVal.toString().equals("shiftLS")){
			upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, true, false);
		}
		else{
			upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, false, false);
		}
		
		if( reg1.toString().equals("r15") ){
			setPc( Integer.parseInt(reg.get("r15").toString()) );
			setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
		}

		//if in supervisor mode, copy the spsr into the cpsr, return to user mode:
		if( reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011") ){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTmvnCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeVal = node.jjtGetChild(2);

		if ( condition.condAction(cond.toString()) ){
			inst.mvnInstr(reg1, val.toString());
			
			if(typeVal.toString().equals("shiftLS")){
				upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, true, false);
			}
			else{
				upCSpsr.update( Integer.parseInt(reg.get(reg1).toString()),true, true, false, false);
			}
			
			if( reg1.toString().equals("r15") ){
				setPc( Integer.parseInt(reg.get("r15").toString()) );
				setChild( Integer.parseInt(reg.get("r15").toString()) - 2 );
			}

			//if in supervisor mode, copy the spsr into the cpsr, return to user mode:
			if( reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011") ){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//ADD
	@Override
	public Object visit(ASTadd node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());
		
		if( reg1.toString().equals("r15") ){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}

	@Override
	public Object visit(ASTaddC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());

			if( reg1.toString().equals("r15") ){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTaddS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());
		
		//update cpsr or spsr depending on the mode we are:
		upCSpsr.update(result, true, true, true, true);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTaddCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());
			
			//update cpsr or spsr depending on the mode we are:
			upCSpsr.update(result, true, true, true, true);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//ADC
	@Override
	public Object visit(ASTadc node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//add the two arguments:
		int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());
		
		String resultStr = String.valueOf(result);
		String CStr = C_S_psrReg.get("C").toString();
		//Then add the C (carry):
		result = inst.addInstr(reg1, resultStr, CStr);
		
		//if reg = pc then make a jump
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTadcC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//add the two arguments:
			int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());
			
			String resultStr = String.valueOf(result);
			String CStr = C_S_psrReg.get("C").toString();
			//Then add the C (carry):
			result = inst.addInstr(reg1, resultStr, CStr);
			
			//if reg = pc then make a jump
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTadcS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//add the two arguments:
		int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());

		String resultStr = String.valueOf(result);
		String CStr = C_S_psrReg.get("C").toString();
		//Then add the C (carry):
		result = inst.addInstr(reg1, resultStr, CStr);

		upCSpsr.update(result, true, true, true, true);

		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTadcCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//add the two arguments:
			int result = inst.addInstr(reg1, arg1.toString(), arg2.toString());

			String resultStr = String.valueOf(result);
			String CStr = C_S_psrReg.get("C").toString();
			//Then add the C (carry):
			result = inst.addInstr(reg1, resultStr, CStr);

			upCSpsr.update(result, true, true, true, true);

			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//SUB
	@Override
	public Object visit(ASTsub node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}

	@Override
	public Object visit(ASTsubC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTsubS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
		upCSpsr.update(result, true, true, true, true);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTsubCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
			upCSpsr.update(result, true, true, true, true);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//SBC
	@Override
	public Object visit(ASTsbc node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//Sub the two arguments first
		int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
		
		String resultStr = String.valueOf(result);
		String CStr;
		
		//because result - (~C)
		if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
		else CStr = "0";
		
		//Then sub the C (carry):
		result = inst.subInstr(reg1, resultStr, CStr);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTsbcC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first
			int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
			
			String resultStr = String.valueOf(result);
			String CStr;
			
			//because result - (~C)
			if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
			else CStr = "0";
			
			//Then sub the C (carry):
			result = inst.subInstr(reg1, resultStr, CStr);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTsbcS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//Sub the two arguments first
		int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
		
		String resultStr = String.valueOf(result);
		String CStr;
		
		//because result - (~C)
		if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
		else CStr = "0";

		//Then sub the C (carry):
		result = inst.subInstr(reg1, resultStr, CStr);

		upCSpsr.update(result, true, true, true, true);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTsbcCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first
			int result = inst.subInstr(reg1, arg1.toString(), arg2.toString());
			
			String resultStr = String.valueOf(result);
			String CStr;
			
			//because result - (~C)
			if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
			else CStr = "0";

			//Then sub the C (carry):
			result = inst.subInstr(reg1, resultStr, CStr);

			upCSpsr.update(result, true, true, true, true);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//RSB
	//same as sub, reverse arguments
	@Override
	public Object visit(ASTrsb node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());

		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}

	@Override
	public Object visit(ASTrsbC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());

			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}		
		}

		return null;
	}

	@Override
	public Object visit(ASTrsbS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
		upCSpsr.update(result, true, true, true, true);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTrsbCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
			upCSpsr.update(result, true, true, true, true);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//RSC
	@Override
	public Object visit(ASTrsc node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		//Sub the two arguments first:
		int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
		
		String resultStr = String.valueOf(result);
		String CStr;
		
		//because result - (~C)
		if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
		else CStr = "0";

		//Then sub the C:
		result = inst.subInstr(reg1, resultStr, CStr);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTrscC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//Sub the two arguments first:
			int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
			
			String resultStr = String.valueOf(result);
			String CStr;
			
			//because result - (~C)
			if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
			else CStr = "0";

			//Then sub the C:
			result = inst.subInstr(reg1, resultStr, CStr);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTrscS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
		
		String resultStr = String.valueOf(result);
		String CStr;
		
		//because result - (~C)
		if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
		else CStr = "0";

		//Then sub the C:
		result = inst.subInstr(reg1, resultStr, CStr);

		upCSpsr.update(result, true, true, true, true);
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTrscCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.subInstr(reg1, arg2.toString(), arg1.toString());
			
			String resultStr = String.valueOf(result);
			String CStr;
			
			//because result - (~C)
			if(C_S_psrReg.get("C").toString().equals("0")) CStr = "1";
			else CStr = "0";

			//Then sub the C:
			result = inst.subInstr(reg1, resultStr, CStr);

			upCSpsr.update(result, true, true, true, true);
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}
		return null;
	}

	//MLA (doesn't effect jump if reg1 is pc, any precision for spsr)
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
	public Object visit(ASTmlaS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		int result = inst.mlaInstr(reg1, reg2.toString(), reg3.toString(), reg4.toString());

		upCSpsr.update(result, true, true, false, false);
		
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
			upCSpsr.update(result, true, true, false, false);
		}
		return null;
	}

	//MUL (doesn't effect jump if reg1 is pc, any precision for spsr)
	@Override
	public Object visit(ASTmul node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		inst.mulInstr(reg1, reg2.toString(), reg3.toString());

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
	public Object visit(ASTmulS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.mulInstr(reg1, reg2.toString(), reg3.toString());

		upCSpsr.update(result, true, true, false, false);

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
			upCSpsr.update(result, true, true, false, false);
		}
		return null;
	}

	//SMLAL (doesn't effect jump if reg1 is pc, any precision for spsr)
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
	public Object visit(ASTsmlalS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.smlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		int resultHi = (int) reg.get(reg2.toString());
		upCSpsr.update(resultHi, true, false, false, false);
		upCSpsr.update((int)result, false, true, false, false);

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
			int resultHi = (int) reg.get(reg2.toString());
			upCSpsr.update(resultHi, true, false, false, false);
			upCSpsr.update((int)result, false, true, false, false);
		}
		return null;
	}

	//SMULL (doesn't effect jump if reg1 is pc, any precision for spsr)
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
	public Object visit(ASTsmullS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.smullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		int resultHi = (int) reg.get(reg2.toString());
		upCSpsr.update(resultHi, true, false, false, false);
		upCSpsr.update((int)result, false, true, false, false);

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
			int resultHi = (int) reg.get(reg2.toString());
			upCSpsr.update(resultHi, true, false, false, false);
			upCSpsr.update((int)result, false, true, false, false);

		}
		return null;
	}

	//UMLAL (doesn't effect jump if reg1 is pc, any precision for spsr)
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
	public Object visit(ASTumlalS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.umlalInstr(reg1, reg2, reg3.toString(), reg4.toString());
		int resultHi = (int) reg.get(reg2.toString());
		upCSpsr.update(resultHi, true, false, false, false);
		upCSpsr.update((int)result, false, true, false, false);

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
			int resultHi = (int) reg.get(reg2.toString());
			upCSpsr.update(resultHi, true, false, false, false);
			upCSpsr.update((int)result, false, true, false, false);
		}
		return null;
	}

	//UMULL (doesn't effect jump if reg1 is pc, any precision for spsr)
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
	public Object visit(ASTumullS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);
		Object reg4 = node.jjtGetChild(3).jjtAccept(this, data);

		long result = inst.umullInstr(reg1, reg2, reg3.toString(), reg4.toString());
		int resultHi = (int) reg.get(reg2.toString());
		upCSpsr.update(resultHi, true, false, false, false);
		upCSpsr.update((int)result, false, true, false, false);

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
			int resultHi = (int) reg.get(reg2.toString());
			upCSpsr.update(resultHi, true, false, false, false);
			upCSpsr.update((int)result, false, true, false, false);
		}
		return null;
	}

	//*Comparaison*//
	//CMP (doesn't effect jump if reg1 is pc,)
	@Override
	public Object visit(ASTcmp node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);

		//cpsr set on the result of reg-arg1:
		int result = inst.cmpInstr(reg1, arg1.toString());

		upCSpsr.update(result, true, true, true, true);

		return null;
	}

	@Override
	public Object visit(ASTcmpC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg-arg1:
			int result = inst.cmpInstr(reg1, arg1.toString());

			upCSpsr.update(result, true, true, true, true);
		}

		return null;
	}

	//CMN (doesn't effect jump if reg1 is pc, any precision for spsr)
	@Override
	public Object visit(ASTcmn node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);

		//cpsr set on the result of reg+arg1:
		int result = inst.cmnInstr(reg1, arg1.toString());

		upCSpsr.update(result, true, true, true, true);

		return null;
	}

	@Override
	public Object visit(ASTcmnC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg+arg1:
			int result = inst.cmnInstr(reg1, arg1.toString());

			upCSpsr.update(result, true, true, true, true);
		}
		return null;
	}

	//TEQ (doesn't effect jump if reg1 is pc, any precision for spsr)
	@Override
	public Object visit(ASTteq node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object typeArg1 = node.jjtGetChild(1);

		//cpsr set on the result of reg^arg1:
		int result = inst.teqInstr(reg1, arg1.toString());

		if(typeArg1.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTteqC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg1 = node.jjtGetChild(2);

		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg^arg1:
			int result = inst.teqInstr(reg1, arg1.toString());

			if(typeArg1.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}
		}

		return null;
	}

	//TST (doesn't effect jump if reg1 is pc, any precision for spsr)
	@Override
	public Object visit(ASTtst node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object typeArg1 = node.jjtGetChild(1);

		//cpsr set on the result of reg&arg1:
		int result = inst.tstInstr(reg1, arg1.toString());

		if(typeArg1.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTtstC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg1 = node.jjtGetChild(2);

		if ( condition.condAction(cond.toString()) ){
			//cpsr set on the result of reg&arg1:
			int result = inst.tstInstr(reg1, arg1.toString());

			if(typeArg1.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}
		}

		return null;
	}

	//*Logique*//
	//AND
	@Override
	public Object visit(ASTand node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.andInstr(reg1, arg1.toString(), arg2.toString());
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTandC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.andInstr(reg1, arg1.toString(), arg2.toString());
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTandS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(2);
		
		int result = inst.andInstr(reg1, arg1.toString(), arg2.toString());

		if(typeArg2.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTandCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(3);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.andInstr(reg1, arg1.toString(), arg2.toString());

			if(typeArg2.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//BIC
	@Override
	public Object visit(ASTbic node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.bicInstr(reg1, arg1.toString(), arg2.toString());
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		return null;
	}
	
	@Override
	public Object visit(ASTbicC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.bicInstr(reg1, arg1.toString(), arg2.toString());
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}			
		}

		return null;
	}

	@Override
	public Object visit(ASTbicS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(2);

		int result = inst.bicInstr(reg1, arg1.toString(), arg2.toString());
		
		if(typeArg2.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}

		return null;
	}

	@Override
	public Object visit(ASTbicCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(3);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.bicInstr(reg1, arg1.toString(), arg2.toString());
			
			if(typeArg2.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//EOR
	@Override
	public Object visit(ASTeor node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.eorInstr(reg1, arg1.toString(), arg2.toString());

		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTeorC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.eorInstr(reg1, arg1.toString(), arg2.toString());

			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTeorS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(2);
		
		int result = inst.eorInstr(reg1, arg1.toString(), arg2.toString());
		
		if(typeArg2.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}

		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTeorCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(3);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.eorInstr(reg1, arg1.toString(), arg2.toString());
			
			if(typeArg2.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}

			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}

	//ORR
	@Override
	public Object visit(ASTorr node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);

		int result = inst.orrInstr(reg1, arg1.toString(), arg2.toString());

		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTorrC node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.orrInstr(reg1, arg1.toString(), arg2.toString());

			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTorrS node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(2).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(2);

		int result = inst.orrInstr(reg1, arg1.toString(), arg2.toString());

		if(typeArg2.toString().equals("shiftLS")){
			upCSpsr.update(result, true, true, true, false);
		}
		else{
			upCSpsr.update(result, true, true, false, false);
		}
		
		if(reg1.toString().equals("r15")){
			setPc(result);
			setChild(result-2);
		}

		//if in supervisor mode, copy the spsr into the cpsr:
		if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
			copySpsrInCpsr();//copy
			C_S_psrReg.put("mode", 10000);//return to user mode
			C_S_psrReg.put("I", 0);

			C_S_psr.clearSPSR(); //clear spsr
			//clear registers of spsr:
			reg.put("r13_svc", 0);
			reg.put("r14_svc", 0);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTorrCS node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg1 = node.jjtGetChild(1).jjtAccept(this, data);
		Object arg1 = node.jjtGetChild(2).jjtAccept(this, data);
		Object arg2 = node.jjtGetChild(3).jjtAccept(this, data);
		Object typeArg2 = node.jjtGetChild(3);

		if ( condition.condAction(cond.toString()) ){
			int result = inst.orrInstr(reg1, arg1.toString(), arg2.toString());

			if(typeArg2.toString().equals("shiftLS")){
				upCSpsr.update(result, true, true, true, false);
			}
			else{
				upCSpsr.update(result, true, true, false, false);
			}
			
			if(reg1.toString().equals("r15")){
				setPc(result);
				setChild(result-2);
			}

			//if in supervisor mode, copy the spsr into the cpsr:
			if(reg1.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}

		return null;
	}


	//*LDR/STR*//	
	//LDR
	@Override
	public Object visit(ASTldrSimple node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(2).jjtAccept(this, data);

		String val="null";

		inst.ldrIntr(regLdr.toString(), regV.toString(), val, close.toString() ,"pre", "p");
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

		return null;
	}

	@Override
	public Object visit(ASTldrCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLdr = node.jjtGetChild(1).jjtAccept(this, data);
		Object regV = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String val="null";

		if(condition.condAction(cond.toString())){	
			inst.ldrIntr(regLdr.toString(), regV.toString(), val, close.toString() ,"pre", "p");
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTldrPreNeg node, Object data) {
		Object regLdr = node.jjtGetChild(0).jjtAccept(this, data);
		Object regV = node.jjtGetChild(1).jjtAccept(this, data);
		Object val = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		inst.ldrIntr(regLdr.toString(), regV.toString(), val.toString(), close.toString() ,"pre", "n");
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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
		
		if(regLdr.toString().equals("r15")){
			setPc(Integer.parseInt(reg.get("r15").toString()));
			setChild(Integer.parseInt(reg.get("r15").toString())-2);
		}

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
			
			if(regLdr.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
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

	//*LDM / STM*//

	//LDM
	@Override
	public Object visit(ASTldmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);
		boolean isSVC;

		if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
		else isSVC = false;
		
		String regEnd = "null";

		if(close.toString().equals("C")){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString(), isSVC);

			if(regStart.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		else if(close.toString().equals("CU")){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString(), isSVC);

			if(regStart.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}

			if(regStart.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}
		
		return null;
	}

	@Override
	public Object visit(ASTldmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);
		boolean isSVC;
		
		if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
		else isSVC = false;

		String regEnd = "null";

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString(), isSVC);

				if(regStart.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
			else if(close.toString().equals("CU")){
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, false, amode.toString(), isSVC);

				if(regStart.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}

				if(regStart.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTldmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);
		
		boolean inList = false;

		String[] list = new String[children-3];

		for(int i=2; i<(children-1); i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		

		if(close.toString().equals("C")){
			inst.ldmInst(regL.toString(), list, false, amode.toString());
			
			for(int i=0; i<list.length; i++){
				if(list[i].equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
			
		}
		else if(close.toString().equals("CU")){
			
			for(int i=0; i<list.length; i++){
				if(list[i].equals("r15")){
					inList = true;
				}
			}
			
			if(!inList){
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r13_svc")){
						list[i] = "r13";
					}
					if(list[i].equals("r14_svc")){
						list[i] = "r14";
					}
				}
			}
			
			inst.ldmInst(regL.toString(), list, false, amode.toString());
			
			if(inList){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}

			if(inList && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}	
		}
		
		return null;
	}


	@Override
	public Object visit(ASTldmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-4];
			
			boolean inList = false;

			for(int i=3; i<(children-1); i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			
			if(close.toString().equals("C")){
				inst.ldmInst(regL.toString(), list, false, amode.toString());
				
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r15")){
						setPc(Integer.parseInt(reg.get("r15").toString()));
						setChild(Integer.parseInt(reg.get("r15").toString())-2);
					}
				}
				
			}
			else if(close.toString().equals("CU")){
				
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r15")){
						inList = true;
					}
				}
				
				if(!inList){
					for(int i=0; i<list.length; i++){
						if(list[i].equals("r13_svc")){
							list[i] = "r13";
						}
						if(list[i].equals("r14_svc")){
							list[i] = "r14";
						}
					}
				}
				
				inst.ldmInst(regL.toString(), list, false, amode.toString());
				
				if(inList){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}

				if(inList && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}	
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTldmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);
		boolean isSVC;
		
		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
			else isSVC = false;
			
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(), isSVC);
			
			if(regEnd.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		else if(close.toString().equals("CU")){
			if(regEnd.toString().equals("r15")) isSVC = true;
			else isSVC = false;
			
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(), isSVC);
			
			if(regEnd.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
			
			if(regEnd.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}	
		}
		
		return null;
	}


	@Override
	public Object visit(ASTldmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		Object close = node.jjtGetChild(5).jjtAccept(this, data);
		boolean isSVC;
		
		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
				else isSVC = false;
				
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(), isSVC);
				
				if(regEnd.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
			else if(close.toString().equals("CU")){
				if(regEnd.toString().equals("r15")) isSVC = true;
				else isSVC = false;
				
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(), isSVC);
				
				if(regEnd.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
				
				if(regEnd.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}	
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTMldmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);
		boolean isSVC;

		if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
		else isSVC = false;
		
		String regEnd = "null";

		if(close.toString().equals("C")){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString(), isSVC);

			if(regStart.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		else if(close.toString().equals("CU")){
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString(), isSVC);

			if(regStart.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}

			if(regStart.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}
		}
		
		return null;
	}

	@Override
	public Object visit(ASTMldmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);
		boolean isSVC;

		if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
		else isSVC = false;
		
		String regEnd = "null";

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString(), isSVC);

				if(regStart.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
			else if(close.toString().equals("CU")){
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd, true, amode.toString(), isSVC);

				if(regStart.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}

				if(regStart.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTMldmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		String[] list = new String[children-3];
		
		boolean inList = false;

		for(int i=2; i<(children-1); i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		
		if(close.toString().equals("C")){
			inst.ldmInst(regL.toString(), list, true, amode.toString());

			for(int i=0; i<list.length; i++){
				if(list[i].equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
		}
		else if(close.toString().equals("CU")){
			
			for(int i=0; i<list.length; i++){
				if(list[i].equals("r15")){
					inList = true;
				}
			}
			
			if(!inList){
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r13_svc")){
						list[i] = "r13";
					}
					if(list[i].equals("r14_svc")){
						list[i] = "r14";
					}
				}
			}
			
			inst.ldmInst(regL.toString(), list, true, amode.toString());
			
			
			if(inList){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
			
			if(inList && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}	
		}

		return null;
	}

	@Override
	public Object visit(ASTMldmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-4];
			
			boolean inList = false;

			for(int i=3; i<(children-1); i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			
			if(close.toString().equals("C")){
				inst.ldmInst(regL.toString(), list, true, amode.toString());

				for(int i=0; i<list.length; i++){
					if(list[i].equals("r15")){
						setPc(Integer.parseInt(reg.get("r15").toString()));
						setChild(Integer.parseInt(reg.get("r15").toString())-2);
					}
				}
			}
			else if(close.toString().equals("CU")){
				
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r15")){
						inList = true;
					}
				}
				
				if(!inList){
					for(int i=0; i<list.length; i++){
						if(list[i].equals("r13_svc")){
							list[i] = "r13";
						}
						if(list[i].equals("r14_svc")){
							list[i] = "r14";
						}
					}
				}
				
				inst.ldmInst(regL.toString(), list, true, amode.toString());
				
				
				if(inList){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
				
				if(inList && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}	
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTMldmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);
		boolean isSVC;
		
		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
			else isSVC = false;
			
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(), isSVC);
			
			if(regEnd.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
		}
		else if(close.toString().equals("CU")){
			if(regEnd.toString().equals("r15")) isSVC = true;
			else isSVC = false;
			
			inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(), isSVC);
			
			if(regEnd.toString().equals("r15")){
				setPc(Integer.parseInt(reg.get("r15").toString()));
				setChild(Integer.parseInt(reg.get("r15").toString())-2);
			}
			
			if(regEnd.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
				copySpsrInCpsr();//copy
				C_S_psrReg.put("mode", 10000);//return to user mode
				C_S_psrReg.put("I", 0);

				C_S_psr.clearSPSR(); //clear spsr
				//clear registers of spsr:
				reg.put("r13_svc", 0);
				reg.put("r14_svc", 0);
			}	
		}

		return null;
	}

	@Override
	public Object visit(ASTMldmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regLd = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		Object close = node.jjtGetChild(5).jjtAccept(this, data);
		boolean isSVC;

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")) isSVC = true;
				else isSVC = false;
				
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(), isSVC);
				
				if(regEnd.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
			}
			else if(close.toString().equals("CU")){
				if(regEnd.toString().equals("r15")) isSVC = true;
				else isSVC = false;
				
				inst.ldmInst(regLd.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(), isSVC);
				
				if(regEnd.toString().equals("r15")){
					setPc(Integer.parseInt(reg.get("r15").toString()));
					setChild(Integer.parseInt(reg.get("r15").toString())-2);
				}
				
				if(regEnd.toString().equals("r15") && C_S_psrReg.get("mode").toString().equals("10011")){
					copySpsrInCpsr();//copy
					C_S_psrReg.put("mode", 10000);//return to user mode
					C_S_psrReg.put("I", 0);

					C_S_psr.clearSPSR(); //clear spsr
					//clear registers of spsr:
					reg.put("r13_svc", 0);
					reg.put("r14_svc", 0);
				}	
			}

		}

		return null;
	}

	//STM
	@Override
	public Object visit(ASTstmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);
		
		String regEnd = "null";
		
		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), true);
			}
			else{
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), false);
			}
		}
		else if(close.toString().equals("CU")){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), false);
		}

		return null;
	}

	@Override
	public Object visit(ASTstmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		String regEnd = "null";

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")){
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), true);
				}
				else{
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), false);
				}
			}
			else if(close.toString().equals("CU")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, false, amode.toString(), false);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTstmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		String[] list = new String[children-3];
		
		for(int i=2; i<(children-1); i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}		
		
		if(close.toString().equals("C")){
			inst.stmInst(regL.toString(), list, false, amode.toString());	
		}
		else if(close.toString().equals("CU")){
			for(int i=0; i<list.length; i++){
				if(list[i].equals("r13_svc")){
					list[i] = "r13";
				}
				if(list[i].equals("r14_svc")){
					list[i] = "r14";
				}
			}
			inst.stmInst(regL.toString(), list, false, amode.toString());
		}

		return null;
	}

	@Override
	public Object visit(ASTstmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-4];

			for(int i=3; i<(children-1); i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}		
			
			if(close.toString().equals("C")){
				inst.stmInst(regL.toString(), list, false, amode.toString());	
			}
			else if(close.toString().equals("CU")){
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r13_svc")){
						list[i] = "r13";
					}
					if(list[i].equals("r14_svc")){
						list[i] = "r14";
					}
				}
				inst.stmInst(regL.toString(), list, false, amode.toString());
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTstmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),true);
			}
			else{
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),false);
			}
		}
		else if(close.toString().equals("CU")){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),false);
		}
	
		return null;
	}

	@Override
	public Object visit(ASTstmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		Object close = node.jjtGetChild(5).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")){
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),true);
				}
				else{
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),false);
				}
			}
			else if(close.toString().equals("CU")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), false, amode.toString(),false);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTMstmSimple node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(3).jjtAccept(this, data);

		String regEnd = "null";

		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), true);
			}
			else{
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), false);
			}
		}
		else if(close.toString().equals("CU")){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), false);
		}

		return null;
	}

	@Override
	public Object visit(ASTMstmCSimple node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);

		String regEnd = "null";

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")){
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), true);
				}
				else{
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), false);
				}
			}
			else if(close.toString().equals("CU")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd, true, amode.toString(), false);
			}
		}
		return null;
	}


	@Override
	public Object visit(ASTMstmEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regL = node.jjtGetChild(1).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		String[] list = new String[children-3];

		for(int i=2; i<(children-1); i++){
			list[i-2] = node.jjtGetChild(i).jjtAccept(this, data).toString();
		}	
		
		if(close.toString().equals("C")){
			inst.stmInst(regL.toString(), list, true, amode.toString());	
		}
		else if(close.toString().equals("CU")){
			for(int i=0; i<list.length; i++){
				if(list[i].equals("r13_svc")){
					list[i] = "r13";
				}
				if(list[i].equals("r14_svc")){
					list[i] = "r14";
				}
			}
			inst.stmInst(regL.toString(), list, true, amode.toString());
		}
		
		return null;
	}

	@Override
	public Object visit(ASTMstmCEnum node, Object data) {
		int children = node.jjtGetNumChildren();
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regL = node.jjtGetChild(2).jjtAccept(this, data);
		Object close = node.jjtGetChild(children-1).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			String[] list = new String[children-4];

			for(int i=3; i<(children-1); i++){
				list[i-3] = node.jjtGetChild(i).jjtAccept(this, data).toString();
			}	
			
			if(close.toString().equals("C")){
				inst.stmInst(regL.toString(), list, true, amode.toString());	
			}
			else if(close.toString().equals("CU")){
				for(int i=0; i<list.length; i++){
					if(list[i].equals("r13_svc")){
						list[i] = "r13";
					}
					if(list[i].equals("r14_svc")){
						list[i] = "r14";
					}
				}
				inst.stmInst(regL.toString(), list, true, amode.toString());
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTMstmList node, Object data) {
		Object amode = node.jjtGetChild(0).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(1).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(2).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(3).jjtAccept(this, data);
		Object close = node.jjtGetChild(4).jjtAccept(this, data);
		
		if(close.toString().equals("C")){
			if(C_S_psrReg.get("mode").toString().equals("10011")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),true);
			}
			else{
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),false);
			}
		}
		else if(close.toString().equals("CU")){
			inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),false);
		}

		return null;
	}

	@Override
	public Object visit(ASTMstmCList node, Object data) {
		Object cond = node.jjtGetChild(0).jjtAccept(this, data);
		Object amode = node.jjtGetChild(1).jjtAccept(this, data);
		Object regSt = node.jjtGetChild(2).jjtAccept(this, data);
		Object regStart = node.jjtGetChild(3).jjtAccept(this, data);
		Object regEnd = node.jjtGetChild(4).jjtAccept(this, data);
		Object close = node.jjtGetChild(5).jjtAccept(this, data);

		if(condition.condAction(cond.toString())){
			if(close.toString().equals("C")){
				if(C_S_psrReg.get("mode").toString().equals("10011")){
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),true);
				}
				else{
					inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),false);
				}
			}
			else if(close.toString().equals("CU")){
				inst.stmInst(regSt.toString(), regStart.toString(), regEnd.toString(), true, amode.toString(),false);
			}
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
	public Object visit(ASTswpb node, Object data) {
		Object reg1 = node.jjtGetChild(0).jjtAccept(this, data);
		Object reg2 = node.jjtGetChild(1).jjtAccept(this, data);
		Object reg3 = node.jjtGetChild(2).jjtAccept(this, data);

		inst.swapInstr(reg1, reg2.toString(), reg3.toString(), true);

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
	
	//*Software interrupt*//
	//SWI
	@Override
	public Object visit(ASTswi node, Object data) {
		String swi_Handler = node.jjtGetChild(0).jjtAccept(this, data).toString();
		return swi_Handler;
	}

	@Override
	public Object visit(ASTCswi node, Object data) {
		String cond = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String swi_Handler = node.jjtGetChild(1).jjtAccept(this, data).toString();
		
		String ret = cond+","+swi_Handler;
		
		return ret;
	}

	//*Branch*//
	//branch
	@Override
	public Object visit(ASTbranch node, Object data) {
		String labelBranch = node.jjtGetChild(0).jjtAccept(this, data).toString();
		return labelBranch;
	}

	//condition and label
	@Override
	public Object visit(ASTCbranch node, Object data) {
		String cond = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String label = node.jjtGetChild(1).jjtAccept(this, data).toString();

		String ret = cond+","+label;

		return ret;
	}

	//branchlink
	@Override
	public Object visit(ASTbranchLink node, Object data) {
		String labelBranchLink = node.jjtGetChild(0).jjtAccept(this, data).toString();
		return labelBranchLink;
	}

	@Override
	public Object visit(ASTCbranchLink node, Object data) {
		String cond = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String labelLink = node.jjtGetChild(1).jjtAccept(this, data).toString();

		String ret = cond+","+labelLink;

		return ret;
	}
	
	//label
		@Override
		public Object visit(ASTlabel node, Object data) {
			Object label = node.value.toString();
			return label;
		}
}
