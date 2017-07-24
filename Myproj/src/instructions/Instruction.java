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
				retVal = regOrVal << value;
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
				retVal = regOrVal >>> value;
			}
			else if (value == 0) {
				retVal = regOrVal;
			}
			else {
				retVal = 0;
			}
		break;

		case("asr"):
			if(1<=value && value <=31){
				retVal = regOrVal >> value;
			}
			else if (value == 0) {
				retVal = -regOrVal;
			}
			else {
				retVal = regOrVal;
			}
		break;

		case("ror"):
			if(1<=value && value <=31){
				retVal = regOrVal >>> value | regOrVal<<(32 - value) ;
			}
			else if (value == 0) {
				retVal = regOrVal;
			}
			else {
				regOrVal = regOrVal & 31;
				retVal = shiftInstr("ror", String.valueOf(retVal), String.valueOf(regOrVal));
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

	public void ldrIntr(String regL, String regV, String val, String close, String PrePost, String PosNeg ){

		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement32(add);

						regData.put(regL, valMem);
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
			int valInt = toInt(val);

			int add; int valMem; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;
				valMem = mem.getMemoryElement32(add);

				regData.put(regL, valMem);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;
				valMem = mem.getMemoryElement32(add);

				regData.put(regL, valMem);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}	
	}

	public void ldrBInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg ){

		val = val.replace("-", "");

		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
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
			int valInt = toInt(val);

			int add; int valMem; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;
				valMem = mem.getMemoryElement8(add);

				regData.put(regL, valMem);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;
				valMem = mem.getMemoryElement8(add);

				regData.put(regL, valMem);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}
	}

	public void ldrSBInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg ){
		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement8(add);

						regData.put(regL, valMem);
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
			int valInt = toInt(val);

			int add; int valMem; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;
				valMem = mem.getMemoryElement8(add);

				regData.put(regL, valMem);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;
				valMem = mem.getMemoryElement8(add);

				regData.put(regL, valMem);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}
	}

	public void ldrHInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg ){

		val = val.replace("-","");

		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
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
			int valInt = toInt(val);

			int add; int valMem; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;
				valMem = mem.getMemoryElement16(add);

				regData.put(regL, valMem);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;
				valMem = mem.getMemoryElement16(add);

				regData.put(regL, valMem);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}
	}

	public void ldrSHInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg){
		switch(PrePost){
		case "pre":
		{	
			int regVInt = toInt(regV);
			switch(PosNeg){
			case "n":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt - valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
				}	
				break;

			case "p":
				if(close.equals("CU")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
						regData.put(regV, add);	
					}
				}
				else if(close.equals("C")){
					if (val.equals("null")){
						int add = regVInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
					}
					else{
						int valInt = toInt(val);
						int add = regVInt + valInt;
						int valMem = mem.getMemoryElement16(add);

						regData.put(regL, valMem);
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
			int valInt = toInt(val);

			int add; int valMem; int tmp;

			switch(PosNeg){
			case "n":
				add = regVInt;
				valMem = mem.getMemoryElement16(add);

				regData.put(regL, valMem);

				tmp = regVInt - valInt;
				regData.put(regV, tmp);			
				break;

			case "p":
				add = regVInt;
				valMem = mem.getMemoryElement16(add);

				regData.put(regL, valMem);

				tmp = regVInt + valInt;
				regData.put(regV, tmp);			
				break;
			}
		}
		break;

		}

	}

	public void strIntr(String regL, String regV, String val, String close, String PrePost, String PosNeg ){

		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement32(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement32(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement32(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}

	public void strBInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg){

		val = val.replace("-", "");

		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement8(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement8(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}

	public void strSBInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg){
		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement8(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement8(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement8(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}

	public void strHInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg){

		val = val.replace("-", "");

		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement16(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement16(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}

	public void strSHInstr(String regL, String regV, String val, String close, String PrePost, String PosNeg){
		switch(PrePost){
		case "pre":
		{
			int regVInt = toInt(regV);
			switch(PosNeg){
			case("n"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt - valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
				}
			break;

			case("p"):
				if(close.equals("CU")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);

						regData.put(regV, add);
					}
				}
				else if (close.equals("C")){
					if(val.equals("null")){

						int add = regVInt;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
					else{
						int valInt = toInt(val);

						int add = regVInt + valInt ;
						int valueStr = toInt(regL);

						mem.setMemoryElement16(add, valueStr);
					}
				}
			break;
			}
		}
		break;

		case "post":
		{
			int regVInt = toInt(regV);
			int valInt = toInt(val);

			int add; int valueStr; int tmp;

			switch(PosNeg){
			case("n"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement16(add, valueStr);

			tmp = regVInt - valInt;
			regData.put(regV, tmp);
			break;

			case("p"):
				add = regVInt;
			valueStr = toInt(regL);

			mem.setMemoryElement16(add, valueStr);

			tmp = regVInt + valInt;
			regData.put(regV, tmp);
			break;
			}
		}
		break;

		}
	}


}
