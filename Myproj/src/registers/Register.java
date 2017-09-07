package registers;

import java.util.HashMap;
import java.util.Map;

public class Register {

	private HashMap<Object, Object> registers;
	
	public HashMap<Object, Object> init(){
		this.registers = new HashMap();
		registers.put("r0", 0);
		registers.put("r1", 0);
		registers.put("r2", 0);
		registers.put("r3", 0);
		registers.put("r4", 0);
		registers.put("r5", 0);
		registers.put("r6", 0);
		registers.put("r7", 0);
		registers.put("r8", 0);
		registers.put("r9", 0);
		registers.put("r10", 0);
		registers.put("r11", 0);
		registers.put("r12", 0);
		registers.put("r13", 0); //sp
		registers.put("r14", 0); //lr
		registers.put("r15", 0); //pc 
								 //cpsr
		
		registers.put("r13_svc", 0); //sp r13 supervisor mode
		registers.put("r14_svc", 0); //lr r14 supervisor mode
		
		return registers;
	}
	
	public void clearRegister(){
		registers.put("r0", 0);
		registers.put("r1", 0);
		registers.put("r2", 0);
		registers.put("r3", 0);
		registers.put("r4", 0);
		registers.put("r5", 0);
		registers.put("r6", 0);
		registers.put("r7", 0);
		registers.put("r8", 0);
		registers.put("r9", 0);
		registers.put("r10", 0);
		registers.put("r11", 0);
		registers.put("r12", 0);
		registers.put("r13", 0); //sp
		registers.put("r14", 0); //lr
		registers.put("r15", 0); //pc 
	}
	
	public void print(){
		for (Object key : registers.keySet()) {
			System.out.println(key + " " + registers.get(key).toString());
		}
	}
	
}
