package instructions;

import java.util.HashMap;

public class Condition {
	
	HashMap<Object, Object> C_S_psr;
	
	public Condition(HashMap<Object, Object> C_S_psr){
		this.C_S_psr = C_S_psr;
	}
	
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
