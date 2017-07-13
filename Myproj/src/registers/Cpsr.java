package registers;

import java.util.HashMap;

public class Cpsr {

	//simple CPSR
	private HashMap<Object, Object> cpsr;

	public Cpsr(){
		this.cpsr = new HashMap();
		cpsr.put("N", 0);
		cpsr.put("Z", 0);
		cpsr.put("C", 0);
		cpsr.put("V", 0);
		cpsr.put("J", 0);
		cpsr.put("I", 0);
		cpsr.put("F", 0);
		cpsr.put("T", 0);
	}
	
	public void print(){
		for (Object key: cpsr.keySet()){
			System.out.println(key+" "+ cpsr.get(key).toString() );
		}
	}
	
	public void setCpsr(Object arg1, Object arg2){
		if (cpsr.containsKey(arg1)){
			cpsr.put(arg1, arg2);
		}
	}
	
	public Object getCpsr(Object arg1){
		Object r = null;
		if(cpsr.containsKey(arg1)){
			r = cpsr.get(arg1);
		}
		return r;
	}

}
