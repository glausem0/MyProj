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
		else if (obj.startsWith("#0x")){
			obj = obj.replace("#0x", "");
			value = Integer.parseInt(obj, 16);
		}
		else{
			value = Integer.parseInt(obj);
		}
		
		return value;
	}
	
	public int shiftInstr(String shiftType, String reg, String val){	
		int regVal = toInt(reg);
		int value = toInt(val);
					
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
		
		int result = 0;

		result = regVal-value;
		
		return result;
	}
	
	public int cmnInstr(Object reg, String arg1){
		int regVal = toInt(reg.toString());
		int value = toInt(arg1);
		
		int result = 0;

		result = regVal+value;
		
		return result;
	}

	public int addInstr(Object reg, String arg1, String arg2) {
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = 0;
		
		regData.put(reg, ( val1 + val2 ));
		result = val1+val2;

		return result;
	}

	public int subInstr(Object reg, String arg1, String arg2) {
		int val1 = toInt(arg1);
		int val2 = toInt(arg2);
		
		int result = 0;
		
		regData.put(reg, ( val1 - val2 ));
		result = ( val1 - val2 );
		
		return result;
	}
}
