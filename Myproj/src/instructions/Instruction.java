package instructions;

import java.util.HashMap;

public class Instruction {

	private HashMap<Object, Object> regData;

	public Instruction(HashMap<Object, Object> regData){
		this.regData = regData;
	}
	
	private int toInt(String obj){
		int value = 0;
		if (obj.startsWith("r")){
			value = Integer.parseInt(regData.get(obj).toString());
		}
		else{
			value = Integer.parseInt(obj);
		}
		
		return value;
	}
	
	public int shiftInstr(String shiftType, String val1, String val2){	
		int regOrVal = toInt(val1);
		int value = toInt(val2);
					
		int retVal = 0;
		switch(shiftType){
		case("lsl"):
			if(1<=value && value <=31){
				retVal = (int) (Math.pow(2, value) * regOrVal);
			}
			else if (value == 0) {
				retVal = regOrVal;
			}
			else {
				retVal = 0;
			}
			break;
		
		case("lsr"):
			if(1<=value && value <=31){
				retVal = (int) (Math.pow(2, value) / regOrVal);
			}
			else if (value == 0) {
				retVal = regOrVal;
			}
			else {
				retVal = 0;
			}
			break;
		
		case("<<"):
			retVal = regOrVal << value;
			break;
		
		case(">>"):
			retVal = regOrVal >> value;
			break;
		}
		
		return retVal;
		
	}
	
	
	public void movInstr(Object reg, Object val){
		int value = toInt(val.toString());
		regData.put(reg, value);
	}

	public void mvnInstr(Object reg, String val){
		int value = toInt(val);
		regData.put(reg, ~value);
	}

	public int cmpInstr(Object reg, String arg1){
		int regVal = toInt(reg.toString());
		int value = toInt(arg1);
		
		int result = regVal - value;

		return result;
	}
	
	public int cmnInstr(Object reg, String arg1){
		int regVal = toInt(reg.toString());
		int value = toInt(arg1);
		
		int result = regVal + value;

		return result;
	}
	
	public int teqInstr(Object reg, String arg1){
		int regVal = toInt(reg.toString());
		int value = toInt(arg1);
		
		int result = regVal ^ value;
		
		return result;
	}
	
	public int tstInstr(Object reg, String arg1){
		int regVal = toInt(reg.toString());
		int value = toInt(arg1);
		
		int result = regVal & value;
		
		return result;
	}

	public int addInstr(Object reg, String arg1, String arg2) {
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = val1+val2;
		
		regData.put(reg, result);
		
		return result;
	}

	public int subInstr(Object reg, String arg1, String arg2) {
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = ( val1 - val2 );
		
		regData.put(reg, result);
		
		return result;
	}
	
	public int andInstr(Object reg, String arg1, String arg2){
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = val1 & val2;
		
		regData.put(reg, result);
		
		return result;
	}
	
	public int bicInstr(Object reg, String arg1, String arg2){
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = val1 & ~val2;
		
		regData.put(reg, result);
		
		return result;
	}
	
	public int eorInstr(Object reg, String arg1, String arg2){
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = val1 ^ val2;
		
		regData.put(reg, result);
		
		return result;
	}
	
	public int orrInstr(Object reg, String arg1, String arg2){
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = val1 | val2;
		
		regData.put(reg, result);
		
		return result;
	}
	
}
