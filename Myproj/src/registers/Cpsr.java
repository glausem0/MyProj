package registers;

import java.util.HashMap;

public class Cpsr {

	//simple CPSR
	private HashMap<Object, Object> cpsr;

	public HashMap<Object, Object> init(){
		this.cpsr = new HashMap();
		cpsr.put("N", 0);
		cpsr.put("Z", 0);
		cpsr.put("C", 0);
		cpsr.put("V", 0);
		
		cpsr.put("I", 0); //masque d'interruption
		cpsr.put("F", 0); //masque d'interruption
		
		cpsr.put("mode", 10000); //user mode by default (10011 for supervisor mode)
		
		//cpsr.put("T", 0); thumb state
		
		return cpsr;
	}
	
	public void clearCpsr(){
		cpsr.put("N", 0);
		cpsr.put("Z", 0);
		cpsr.put("C", 0);
		cpsr.put("V", 0);
		cpsr.put("I", 0); 
		cpsr.put("F", 0); 
		cpsr.put("mode", 10000); 
	}
	
	public void print(){
		for (Object key: cpsr.keySet()){
			System.out.println(key+" "+ cpsr.get(key).toString() );
		}
	}
	
}
