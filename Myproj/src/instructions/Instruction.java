package instructions;

import java.util.HashMap;

public class Instruction {

	private HashMap<Object, Object> regData;

	public Instruction(HashMap<Object, Object> regData){
		this.regData = regData;
	}

	public String hexaToIntInstr(String hexa){
		String hexaInt = String.valueOf(Integer.parseInt(hexa, 16));	
		return hexaInt;
	}
	
	public String intToHexaInstr(int inte){
		String hexa = Integer.toHexString(inte);
		return hexa;
	}
	
	public int shiftInstr(String shiftType, String reg, String val){
		int value;
		int regVal;
		
		//val can be a value or a register:
		if(val.startsWith("r")){
			value = Integer.parseInt(regData.get(val).toString());
		}
		else{ 
			value = Integer.parseInt(val);
		}
		
		regVal = Integer.parseInt(regData.get(reg).toString()); 
		
		int retVal = 0;
		switch(shiftType){
		case("lsl"):
			if(1<=value && value <=31){
				retVal = (int) (Math.pow(2, value) * regVal);
			}
			else if (value == 0) {
				retVal = regVal;
			}
			else {
				retVal = 0;
			}
			break;
		
		case("lsr"):
			if(1<=value && value <=31){
				retVal = (int) (Math.pow(2, value) / regVal);
			}
			else if (value == 0) {
				retVal = regVal;
			}
			else {
				retVal = 0;
			}
			break;
		}
		
		return retVal;
		
	}
	
	
	public void movInstr(Object reg, Object val){
		regData.put(reg, val);
	}

	public void mvnInstr(Object reg, String val){
		int value = Integer.parseInt(val);
		regData.put(reg, ~value);
	}

	public int cmpInstr(Object reg, String arg1){
		int result = 0;

		int regVal = Integer.parseInt(regData.get(reg).toString());
		
		if(arg1.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			result = regVal - val1;
		}
		else{
			result = regVal - (Integer.parseInt(arg1));
		}
		
		return result;
	}
	
	public int cmnInstr(Object reg, String arg1){
		int result = 0;

		int regVal = Integer.parseInt(regData.get(reg).toString());
		
		if(arg1.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			result = regVal + val1;
		}
		else{
			result = regVal + (Integer.parseInt(arg1));
		}
		
		return result;
	}

	public int addInstr(Object reg, String arg1, String arg2) {

		int result = 0;

		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(regData.get(arg2).toString());

			regData.put(reg, ( val1 + val2 ));
			result = ( val1 + val2 );
		}
		else if(arg1.startsWith("r") && !(arg2.startsWith("r"))){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(arg2);

			regData.put(reg, ( val1 + val2 ));
			result = ( val1 + val2 );
		}
		else if (!(arg1.startsWith("r")) && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.get(arg2).toString());

			regData.put(reg, ( val1 + val2 ));
			result = ( val1 + val2 );
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);

			regData.put(reg, ( val1 + val2 ));
			result = ( val1 + val2 );
		}	

		return result;
	}

	public int subInstr(Object reg, String arg1, String arg2) {

		int result = 0;

		if(arg1.startsWith("r") && arg2.startsWith("r")){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(regData.get(arg2).toString());

			regData.put(reg, ( val1 - val2 ));
			result = ( val1 - val2 );
		}
		else if(arg1.startsWith("r") && !(arg2.startsWith("r"))){
			int val1 = Integer.parseInt(regData.get(arg1).toString());
			int val2 = Integer.parseInt(arg2);

			regData.put(reg, ( val1 - val2 ));
			result = ( val1 - val2 );
		}
		else if (!(arg1.startsWith("r")) && arg2.startsWith("r")){
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(regData.get(arg2).toString());

			regData.put(reg, ( val1 - val2 ));
			result = ( val1 - val2 );
		}
		else{
			int val1 = Integer.parseInt(arg1);
			int val2 = Integer.parseInt(arg2);

			regData.put(reg, ( val1 - val2 ));
			result = ( val1 - val2 );
		}	

		return result;
	}
}
