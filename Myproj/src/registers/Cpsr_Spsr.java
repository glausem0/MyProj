package registers;

import java.util.HashMap;

public class Cpsr_Spsr {

	//simple CPSR
	private HashMap<Object, Object> C_S_psr;

	public HashMap<Object, Object> init(){

		this.C_S_psr = new HashMap();
		//Cpsr:
		C_S_psr.put("N", 0);
		C_S_psr.put("Z", 0);
		C_S_psr.put("C", 0);
		C_S_psr.put("V", 0);
		C_S_psr.put("I", 0); //masque d'interruption
		C_S_psr.put("F", 0); //masque d'interruption
		C_S_psr.put("mode", 10000); //user mode by default (10011 for supervisor mode)
		//cpsr.put("T", 0); thumb state

		//Spsr:
		C_S_psr.put("N_svc", 0);
		C_S_psr.put("Z_svc", 0);
		C_S_psr.put("C_svc", 0);
		C_S_psr.put("V_svc", 0);
		C_S_psr.put("I_svc", 0); //masque d'interruption
		C_S_psr.put("F_svc", 0); //masque d'interruption

		return C_S_psr;
	}

	public void clearC_S_psr(){
		//Cpsr
		C_S_psr.put("N", 0);
		C_S_psr.put("Z", 0);
		C_S_psr.put("C", 0);
		C_S_psr.put("V", 0);
		C_S_psr.put("I", 0); 
		C_S_psr.put("F", 0); 
		C_S_psr.put("mode", 10000);

		//Spsr:
		C_S_psr.put("N_svc", 0);
		C_S_psr.put("Z_svc", 0);
		C_S_psr.put("C_svc", 0);
		C_S_psr.put("V_svc", 0);
		C_S_psr.put("I_svc", 0); 
		C_S_psr.put("F_svc", 0); 
	}

	public void clearSPSR(){
		//Spsr:
		C_S_psr.put("N_svc", 0);
		C_S_psr.put("Z_svc", 0);
		C_S_psr.put("C_svc", 0);
		C_S_psr.put("V_svc", 0);
		C_S_psr.put("I_svc", 0); 
		C_S_psr.put("F_svc", 0);
	}

	public void print(){
		for (Object key: C_S_psr.keySet()){
			System.out.println(key+" "+ C_S_psr.get(key).toString() );
		}
	}

}
