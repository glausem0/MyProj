package instructions;

import java.util.HashMap;

public class Update_C_S_psr {

	HashMap<Object, Object> C_S_psr;

	public Update_C_S_psr(HashMap<Object, Object> C_S_psr){
		this.C_S_psr = C_S_psr;
	}

	/**
	 * @param result
	 * @return true if result is negative, false otherwise
	 */
	private boolean negative(int result){
		boolean neg = false;

		if(result < 0){
			neg = true;
		}
		return neg;
	}

	/**
	 * @param result
	 * @return true if result is zero, false otherwise 
	 */
	private boolean zero(int result){
		boolean zer = false;

		if(result == 0){
			zer = true;
		}
		return zer;
	}
	
	//unsigned overflow
	/**
	 * @param result
	 * @return true if result is unsigned, false otherwise
	 */
	private boolean carry(int result){
		boolean car = false;
		long tmpInt = Integer.MAX_VALUE;
		if( ((2*tmpInt)<result) || (result<0) ){
			car = true;
		}
		return car;
	}	

	//signed overflow
	/**
	 * @param result
	 * @return true if result is signed, false otherwise
	 */
	private boolean overflow(int result){
		boolean ove = false;
		if( (result<Integer.MIN_VALUE) || (result>Integer.MAX_VALUE)){
			ove = true;
		}
		return ove;
	}

	/**
	 * update the value of the cpsr or sprs depending on the mode
	 * @param result
	 * @param Nflag
	 * @param Zflag
	 * @param Cflag
	 * @param Vflag
	 */
	public void update(int result, boolean Nflag, boolean Zflag, boolean Cflag, boolean Vflag){

		if( C_S_psr.get("mode").toString().equals("10000") ){ //user mode
			if(Nflag){
				if(negative(result)){
					C_S_psr.put("N", 1);
				}else{
					C_S_psr.put("N", 0);
				}
			}

			if(Zflag){
				if(zero(result)){
					C_S_psr.put("Z", 1);
				}else{
					C_S_psr.put("Z", 0);
				}
			}

			if(Cflag){
				if(carry(result)){
					C_S_psr.put("C", 1);
				}else{
					C_S_psr.put("C", 0);
				}
			}

			if(Vflag){
				if(overflow(result)){
					C_S_psr.put("V", 1);
				}else{
					C_S_psr.put("V", 0);
				}
			}
		}
		else if( C_S_psr.get("mode").toString().equals("10011") ){ //supervisor mode
			if(Nflag){
				if(negative(result)){
					C_S_psr.put("N_svc", 1);
				}else{
					C_S_psr.put("N_svc", 0);
				}
			}

			if(Zflag){
				if(zero(result)){
					C_S_psr.put("Z_svc", 1);
				}else{
					C_S_psr.put("Z_svc", 0);
				}
			}

			if(Cflag){
				if(carry(result)){
					C_S_psr.put("C_svc", 1);
				}else{
					C_S_psr.put("C_svc", 0);
				}
			}

			if(Vflag){
				if(overflow(result)){
					C_S_psr.put("V_svc", 1);
				}else{
					C_S_psr.put("V_svc", 0);
				}
			}
		}
		
	}
}
