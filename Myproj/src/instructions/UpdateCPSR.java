package instructions;

import java.util.HashMap;

public class UpdateCPSR {

	HashMap<Object, Object> cpsr;

	public UpdateCPSR(HashMap<Object, Object> cpsr){
		this.cpsr = cpsr;
	}

	private boolean negative(int result){
		boolean neg = false;

		if(result < 0){
			neg = true;
		}
		return neg;
	}

	private boolean zero(int result){
		boolean zer = false;
		
		if(result == 0){
			zer = true;
		}
		return zer;
	}

	//TODO verify carry
	//unsigned overflow
	private boolean carry(int result){
		boolean car = false;
		if(Integer.MIN_VALUE >= result ){
			car = true;
		}
		return car;
	}	

	//signed overflow
	private boolean overflow(int result){
		boolean ove = false;
		if( result >= Integer.MAX_VALUE){
			ove = true;
		}
		return ove;
	}

	public void update(int result){
		
		if(negative(result)){
			cpsr.put("N", 1);
		}
		
		if(zero(result)){
			cpsr.put("Z", 1);
		}
		
		if(carry(result)){
			cpsr.put("C", 1);
		}
		
		if(overflow(result)){
			cpsr.put("V", 1);
		}
	}
}
