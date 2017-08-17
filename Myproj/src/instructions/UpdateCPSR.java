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

	//unsigned overflow
	private boolean carry(int result){
		boolean car = false;
		long tmpInt = Integer.MAX_VALUE;
		if( ((2*tmpInt)<result) || (result<0) ){
			car = true;
		}
		return car;
	}	

	//signed overflow
	private boolean overflow(int result){
		boolean ove = false;
		if( (result<Integer.MIN_VALUE) || (result>Integer.MAX_VALUE)){
			ove = true;
		}
		return ove;
	}

	public void update(int result, boolean Nflag, boolean Zflag, boolean Cflag, boolean Vflag){

		if(Nflag){
			if(negative(result)){
				cpsr.put("N", 1);
			}else{
				cpsr.put("N", 0);
			}
		}

		if(Zflag){
			if(zero(result)){
				cpsr.put("Z", 1);
			}else{
				cpsr.put("Z", 0);
			}
		}

		if(Cflag){
			if(carry(result)){
				cpsr.put("C", 1);
			}else{
				cpsr.put("C", 0);
			}
		}

		if(Vflag){
			if(overflow(result)){
				cpsr.put("V", 1);
			}else{
				cpsr.put("V", 0);
			}
		}
	}
}
