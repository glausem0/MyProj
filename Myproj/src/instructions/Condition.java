package instructions;

import java.util.HashMap;

import registers.Register;

public class Condition {
	
	HashMap<Object, Object> regData, cpsr;
	
	public Condition(HashMap<Object, Object> regData, HashMap<Object, Object> cpsr){
		this.regData = regData;
		this.cpsr = cpsr;
	}
	
	//if eq == false -> NE=true; if eq == true -> EQ=true.
	private boolean condEq_Ne(String arg1, String arg2){
		boolean eq = false;
		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(regData.get(arg2).toString());
			
			if(val1 == val2){
				eq = true;
			}
		}
		else if(arg1.startsWith("r") && !arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(arg2);
			
			if(val1 == val2){
				eq = true;
			}
		}
		else if(!arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.get(arg2).toString());
	
			if(val1 == val2){
				eq = true;
			}
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);

			if(val1 == val2){
				eq = true;
			}
		}
		return eq;
	}
	
	//if mi == false Pl=true; mi == true -> MI=true. 
	private boolean condMi_Pl(String arg1, String arg2){
		boolean mi = false;
		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(regData.get(arg2).toString());
			
			if(val1 < val2){
				mi = true;
			}
		}
		else if(arg1.startsWith("r") && !arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(arg2);
			
			if(val1 <= val2){
				mi = true;
			}
		}
		else if(!arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.get(arg2).toString());
	
			if(val1 < val2){
				mi = true;
			}
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);

			if(val1 < val2){
				mi = true;
			}
		}
		return mi;
	}
	
	
	public boolean condAction(String cond, String arg1, String arg2){
		boolean condReturn = false;
		boolean tmp = false;
		switch (cond){
		
		case "eq": 
			tmp = condEq_Ne(arg1, arg2);
			if (tmp){
				condReturn = true;
				cpsr.put("Z", 1);
			}
			break;
		
		case "ne":
			tmp = condEq_Ne(arg1, arg2);
			if (!tmp){
				condReturn = true;
				cpsr.put("Z", 0);
			}
			break;
		
		case "cs":
			break;
			
		case "hs":
			break;
			
		case "cc":
			break;
			
		case "lo":
			break;
			
		case "mi":
			tmp = condMi_Pl(arg1, arg2);
			if (tmp){
				condReturn = true;
				cpsr.put("N", 1);
			}
			break;
		
		case "pl":
			tmp = condMi_Pl(arg1, arg2);
			if (!tmp){
				condReturn = true;
				cpsr.put("N", 0);
			}
			break;
		
		case "vs":
			break;
			
		case "vc":
			break;
			
		case "hi":
			break;
			
		case "ls":
			break;
			
		case "ge":
			break;
			
		case "lt":
			break;
			
		case "gt":
			break;
			
		case "le":
			break;
			
		case "nv":
			break;	
		}
		
		return condReturn;
	}
	
}
