package instructions;

import java.util.HashMap;

import memory.Memory;

public class Instruction {

	private HashMap<Object, Object> regData;
	private HashMap<Object, Object> memory;
	private Memory mem;

	public Instruction(HashMap<Object, Object> regData, HashMap<Object, Object> memory, Memory mem){
		this.regData = regData;
		this.memory = memory;
		this.mem = mem;
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

	//TODO change ldr for unaligned instr
	public void ldrIntr(String regL, String regV, String val1, String close, String PrePost, String PosNeg ){

		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val1.equals("null")){
						int add = regVInt;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16); 

						regData.put(regL, valMemInt);
						regData.put(regV, add);
					}
					else{
						int val1Int = toInt(val1);

						int add = regVInt - val1Int;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val1.equals("null")){
						int add = regVInt;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
					}
					else{
						int val1Int = toInt(val1);

						int add = regVInt - val1Int;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val1.equals("null")){
						int add = regVInt;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16); 

						regData.put(regL, valMemInt);
						regData.put(regV, add);
					}
					else{
						int val1Int = toInt(val1);

						int add = regVInt + val1Int;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val1.equals("null")){
						int add = regVInt;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
					}
					else{
						int val1Int = toInt(val1);

						int add = regVInt + val1Int;

						String valMem = mem.getMemoryElement(add).toString();
						valMem = valMem.replace("0x", "");
						long valMemInt = Long.parseLong(valMem, 16);

						regData.put(regL, valMemInt);
					}
				}
				break;
			}
		}
		break;

		case "post":
		{
			//récupère donneés:
			int regVInt = toInt(regV);
			int valInt = toInt(val1);

			int add; String valMem; long valMemInt; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;

				valMem = mem.getMemoryElement(add).toString();
				valMem = valMem.replace("0x", "");
				valMemInt = Long.parseLong(valMem, 16);

				regData.put(regL, valMemInt);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;

				valMem = mem.getMemoryElement(add).toString();
				valMem = valMem.replace("0x", "");
				valMemInt = Long.parseLong(valMem, 16);

				regData.put(regL, valMemInt);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}	
	}

	public void ldrSInstr(){
		//TODO
	}
	
	public void ldrBInstr(){
		//TODO
	}
	
	public void ldrHInstr(){
		//TODO
	}
	
	public void ldrSBInstr(){
		//TODO
	}
	
	public void ldrSHInstr(){
		//TODO
	}
	
	public void strIntr(String regL, String regV, String val1, String close, String PrePost, String PosNeg ){

		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val1.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val1);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val1.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);
					}
					else{
						int valInt = toInt(val1);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val1.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val1);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val1.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);
					}
					else{
						int valInt = toInt(val1);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val1);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}
	
	public void strSInstr(){
		//TODO
	}
	
	public void strBInstr(){
		//TODO
	}
	
	public void strHInstr(){
		//TODO
	}
	
	public void strSBInstr(){
		//TODO
	}
	
	public void strSHInstr(){
		//TODO
	}

	
}
