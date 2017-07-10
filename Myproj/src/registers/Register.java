package registers;

import java.util.HashMap;
import java.util.Map;

public class Register {

	//TODO change Integer to Double
	private static Map<Object, Object> registers;
	
	public Register(){
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
		registers.put("r13", 0);
		registers.put("r14", 0);
	}
	
	public static void setRegister(Object arg1, Object arg2){
		if (registers.containsKey(arg1)){
			registers.put(arg1, arg2);
		}
	}
	
	public static Integer getRegister(Object arg1){
		Integer r = null;
		if(registers.containsKey(arg1)){
			r = (Integer) registers.get(arg1);
		}
		return r;
	}

	public Map<Object, Object> getRegisters() {
		return registers;
	}

	public void setRegisters(Map<Object, Object> registers) {
		this.registers = registers;
	}

}
