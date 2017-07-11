package instructions;

import registers.Register;

public class Instruction {
	
	static Register regData = new Register();

	public void movInstr(Object reg, Object val){
		regData.setRegister(reg, val);
	}
	
	public void printRegisters(){
		regData.print();
	}

	public void addInstr(Object reg, String arg1, String arg2) {
		
		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.getRegister(arg1).toString());
			int val2 = Integer.parseInt(regData.getRegister(arg2).toString());
			
			regData.setRegister(reg, ( val1 + val2 ));
		}
		else if(arg1.startsWith("r") && !(arg2.startsWith("r"))){
			int val1 = Integer.parseInt(regData.getRegister(arg1).toString());
			int val2 = Integer.parseInt(arg2);
			
			regData.setRegister(reg, ( val1 + val2 ));
		}
		else if (!(arg1.startsWith("r")) && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.getRegister(arg2).toString());
			
			regData.setRegister(reg, ( val1 + val2 ));
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);
			
			regData.setRegister(reg, ( val1 + val2 ));
		}	
	}

	public void subInstr(Object reg, String arg1, String arg2) {
		
		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.getRegister(arg1).toString());
			int val2 = Integer.parseInt(regData.getRegister(arg2).toString());
			
			regData.setRegister(reg, ( val1 - val2 ));
		}
		else if(arg1.startsWith("r") && !(arg2.startsWith("r"))){
			int val1 = Integer.parseInt(regData.getRegister(arg1).toString());
			int val2 = Integer.parseInt(arg2);
			
			regData.setRegister(reg, ( val1 - val2 ));
		}
		else if (!(arg1.startsWith("r")) && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.getRegister(arg2).toString());
			
			regData.setRegister(reg, ( val1 - val2 ));
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);
			
			regData.setRegister(reg, ( val1 - val2 ));
		}	
	}
}
