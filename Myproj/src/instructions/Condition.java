package instructions;

import java.util.HashMap;

public class Condition {
	
	HashMap<Object, Object> cpsr;
	
	public Condition(HashMap<Object, Object> cpsr){
		this.cpsr = cpsr;
	}
	
	private boolean condEq(){
		boolean response = false;
		if (cpsr.get("Z").toString().equals("1")){
			response = true;
		}
		return response;
	}
	
	private boolean condNe(){
		boolean response = false;
		if (cpsr.get("Z").toString().equals("0")){
			response = true;
		}
		return response;
	}
	
	private boolean condCs(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("1")){
			response = true;
		}
		return response;
	}
	//same as Cs
	private boolean condHs(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("1")){
			response = true;
		}
		return response;
	}
	
	private boolean condCc(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("0")){
			response = true;
		}
		return response;
	}
	//same as CC
	private boolean condLo(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("0")){
			response = true;
		}
		return response;
	}
	
	private boolean condMi(){
		boolean response = false;
		if (cpsr.get("N").toString().equals("1")){
			response = true;
		}
		return response;
	}
	
	private boolean condPl(){
		boolean response = false;
		if (cpsr.get("N").toString().equals("0")){
			response = true;
		}
		return response;
	}
	
	private boolean condVs(){
		boolean response = false;
		if (cpsr.get("V").toString().equals("1")){
			response = true;
		}
		return response;
	}
	
	private boolean condVc(){
		boolean response = false;
		if (cpsr.get("V").toString().equals("0")){
			response = true;
		}
		return response;
	}
	
	private boolean condHi(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("1") && cpsr.get("Z").toString().equals("0") ){
			response = true;
		}
		return response;
	}
	
	private boolean condLs(){
		boolean response = false;
		if (cpsr.get("C").toString().equals("0") || cpsr.get("Z").toString().equals("1") ){
			response = true;
		}
		return response;
	}
	
	private boolean condGe(){
		boolean response = false;
		if ( cpsr.get("N").toString().equals(cpsr.get("V").toString()) ){
			response = true;
		}
		return response;
	}
	
	private boolean condLt(){
		boolean response = false;
		if ( !cpsr.get("N").toString().equals(cpsr.get("V").toString()) ){
			response = true;
		}
		return response;
	}
	
	private boolean condGt(){
		boolean response = false;
		if ( cpsr.get("N").toString().equals(cpsr.get("V").toString()) && cpsr.get("Z").toString().equals("0") ){
			response = true;
		}
		return response;
	}
	
	private boolean condLe(){
		boolean response = false;
		if (  !(cpsr.get("N").toString().equals(cpsr.get("V").toString())) || cpsr.get("Z").toString().equals("1") ){
			response = true;
		}
		return response;
	}
	
	
	public boolean condAction(String cond){
		boolean condReturn = false;
		switch (cond){
		
		case "eq":
			condReturn = condEq();
			break;
		
		case "ne":
			condReturn = condNe();
			break;
		
		case "cs":
			condReturn = condCs();
			break;
			
		case "hs":
			condReturn = condHs();
			break;
			
		case "cc":
			condReturn = condCc();
			break;
			
		case "lo":
			condReturn = condLo();
			break;
			
		case "mi":
			condReturn = condMi();
			break;
		
		case "pl":
			condReturn = condPl();
			break;
		
		case "vs":
			condReturn = condVs();
			break;
			
		case "vc":
			condReturn = condVc();
			break;
			
		case "hi":
			condReturn = condHi();
			break;
			
		case "ls":
			condReturn = condLs();
			break;
			
		case "ge":
			condReturn = condGe();
			break;
			
		case "lt":
			condReturn = condLt();
			break;
			
		case "gt":
			condReturn = condGt();
			break;
			
		case "le":
			condReturn = condLe();
			break;	
		}
		
		return condReturn;
	}
	
}
