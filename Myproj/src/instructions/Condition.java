package instructions;

import java.util.HashMap;

import registers.Register;

public class Condition {
	
	HashMap<Object, Object> regData;

	public Condition(HashMap<Object, Object> regData){
		this.regData = regData;
	}
	
	public boolean eqCond(String arg1, String arg2){
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

}
