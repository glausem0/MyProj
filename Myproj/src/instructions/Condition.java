package instructions;

import java.util.HashMap;

public class Condition {
	
	HashMap<Object, Object> C_S_psr;
	
	public Condition(HashMap<Object, Object> C_S_psr){
		this.C_S_psr = C_S_psr;
	}
	
	/**
	 * verify equal condition
	 * @param mode
	 * @return true if Z==1, false otherwise
	 */
	private boolean condEq(String mode){
		boolean response = false;

		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("Z").toString().equals("1")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("Z_svc").toString().equals("1")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify not equal condition
	 * @param mode
	 * @return true if Z==0, false otherwise
	 */
	private boolean condNe(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("Z").toString().equals("0")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("Z_svc").toString().equals("0")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify Carry set condition
	 * @param mode
	 * @return true id C==1, false otherwise
	 */
	private boolean condCs(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("1")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("1")){
				response = true;
			}
		}
		
		return response;
	}
	
	//same as Cs
	/**
	 * verify unsigned Higher or Same condition
	 * @param mode
	 * @return true C==1, false otherwise
	 */
	private boolean condHs(String mode){
		boolean response = false;

		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("1")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("1")){
				response = true;
			}
		}

		return response;
	}
	
	/**
	 * verify Carry Clear condition
	 * @param mode
	 * @return true if C==0, false otherwise
	 */
	private boolean condCc(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("0")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("0")){
				response = true;
			}
		}
		
		return response;
	}
	
	//same as CC
	/**
	 * verify unsigned lower
	 * @param mode
	 * @return true if C==0, false otherwise
	 */
	private boolean condLo(String mode){
		boolean response = false;

		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("0")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("0")){
				response = true;
			}
		}

		return response;
	}
	
	/**
	 * verify minus condition
	 * @param mode
	 * @return true if N==1, false otherwise
	 */
	private boolean condMi(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("N").toString().equals("1")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("N_svc").toString().equals("1")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify plus condition
	 * @param mode
	 * @return true if N==0, false otherwise
	 */
	private boolean condPl(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("N").toString().equals("0")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("N_svc").toString().equals("0")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify V flag set
	 * @param mode
	 * @return true if V==1, false otherwise
	 */
	private boolean condVs(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("V").toString().equals("1")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("V_svc").toString().equals("1")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify V flag clear
	 * @param mode
	 * @return true if V==0, false otherwise
	 */
	private boolean condVc(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("V").toString().equals("0")){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("V_svc").toString().equals("0")){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify unsigned Higher
	 * @param mode
	 * @return true if C==1 && Z==0, false otherwise
	 */
	private boolean condHi(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("1") && C_S_psr.get("Z").toString().equals("0") ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("1") && C_S_psr.get("Z_svc").toString().equals("0") ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify lower or same condition
	 * @param mode
	 * @return true if C==0||Z==1, false otherwise
	 */
	private boolean condLs(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (C_S_psr.get("C").toString().equals("0") || C_S_psr.get("Z").toString().equals("1") ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (C_S_psr.get("C_svc").toString().equals("0") || C_S_psr.get("Z_svc").toString().equals("1") ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify signed greater than or equal condition
	 * @param mode
	 * @return true if N==V, false otherwise
	 */
	private boolean condGe(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if ( C_S_psr.get("N").toString().equals(C_S_psr.get("V").toString()) ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if ( C_S_psr.get("N_svc").toString().equals(C_S_psr.get("V_svc").toString()) ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify signed less than condition
	 * @param mode
	 * @return true if N!=V, false otherwise 
	 */
	private boolean condLt(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if ( !C_S_psr.get("N").toString().equals(C_S_psr.get("V").toString()) ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if ( !C_S_psr.get("N_svc").toString().equals(C_S_psr.get("V_svc").toString()) ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify greater than condition
	 * @param mode
	 * @return true if N==V && Z==0, false otherwise
	 */
	private boolean condGt(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if ( C_S_psr.get("N").toString().equals(C_S_psr.get("V").toString()) && C_S_psr.get("Z").toString().equals("0") ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if ( C_S_psr.get("N_svc").toString().equals(C_S_psr.get("V_svc").toString()) && C_S_psr.get("Z_svc").toString().equals("0") ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * verify Less than or equal condition
	 * @param mode
	 * @return true if N!=V || Z==1
	 */
	private boolean condLe(String mode){
		boolean response = false;
		
		if(mode.equals("10000")){//user mode
			if (  !(C_S_psr.get("N").toString().equals(C_S_psr.get("V").toString())) || C_S_psr.get("Z").toString().equals("1") ){
				response = true;
			}
		}
		else if(mode.equals("10011")){ //supervisor mode:
			if (  !(C_S_psr.get("N_svc").toString().equals(C_S_psr.get("V_svc").toString())) || C_S_psr.get("Z_svc").toString().equals("1") ){
				response = true;
			}
		}
		
		return response;
	}
	
	/**
	 * look if condition is respected 
	 * @param cond
	 * @return true when condition is respected
	 */
	public boolean condAction(String cond){
		boolean condReturn = false;
		
		String mode = C_S_psr.get("mode").toString();
		
		switch (cond){
		
		case "eq":
			condReturn = condEq(mode);
			break;
		
		case "ne":
			condReturn = condNe(mode);
			break;
		
		case "cs":
			condReturn = condCs(mode);
			break;
			
		case "hs":
			condReturn = condHs(mode);
			break;
			
		case "cc":
			condReturn = condCc(mode);
			break;
			
		case "lo":
			condReturn = condLo(mode);
			break;
			
		case "mi":
			condReturn = condMi(mode);
			break;
		
		case "pl":
			condReturn = condPl(mode);
			break;
		
		case "vs":
			condReturn = condVs(mode);
			break;
			
		case "vc":
			condReturn = condVc(mode);
			break;
			
		case "hi":
			condReturn = condHi(mode);
			break;
			
		case "ls":
			condReturn = condLs(mode);
			break;
			
		case "ge":
			condReturn = condGe(mode);
			break;
			
		case "lt":
			condReturn = condLt(mode);
			break;
			
		case "gt":
			condReturn = condGt(mode);
			break;
			
		case "le":
			condReturn = condLe(mode);
			break;	
		}
		
		return condReturn;
	}
	
}
