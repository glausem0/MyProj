package instructions;

import java.util.HashMap;

public class Instruction {

	private HashMap<Object, Object> regData;
	private AccessMemory mem;

	public Instruction(HashMap<Object, Object> regData, AccessMemory mem){
		this.regData = regData;
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
	
	public int mlaInstr(Object reg1, String reg2, String reg3, String reg4){
		int val1 = toInt(reg2);
		int val2 = toInt(reg3);
		int val3 = toInt(reg4);
		
		int result = val3 + (val1 * val2);
		
		regData.put(reg1, result);
		
		return result;
	}
	
	public int mulInstr(Object reg1, String reg2, String reg3){
		int val1 = toInt(reg2);
		int val2 = toInt(reg3);
		
		int result = val1 * val2;
		
		regData.put(reg1, result);
		
		return result;
	}
	
	// equals to mla instruction, but result is in 64bit, higher 32 bit in reg1, lower in reg2
	public long smlalInstr (Object reg1, Object reg2, String reg3, String reg4){
		int reg1Int = toInt(reg1.toString());
		int reg2Int = toInt(reg2.toString());
		String reg1Hex = mem.toHex32(reg1Int);
		String reg2Hex = mem.toHex32(reg2Int);
		reg1Hex = reg1Hex.replace("0x", "");
		reg2Hex = reg2Hex.replace("0x", "");
		String valCompStr = reg1Hex+reg2Hex;
		long valHiLo = Long.parseLong(valCompStr, 16); 
		
		int val1 = toInt(reg3);
		int val2 = toInt(reg4);
		
		long result = valHiLo + (val1 * val2);
		
		String resultToHex = Long.toHexString(result);
		
		if(resultToHex.length() < 17){
			regData.put(reg1, 0);
			regData.put(reg2, result);
		}
		else{
			String hi = resultToHex.substring(0, 15);
			String lo = resultToHex.substring(0, resultToHex.length());
			
			regData.put(reg1, Long.parseLong(hi));
			regData.put(reg2, Long.parseLong(lo));
		}
		
		return result;
	}

	// equals to mul instruction, but result is in 64bit, higher 32 bit in reg1, lower in reg2
		public long smullInstr (Object reg1, Object reg2, String reg3, String reg4){
			int val1 = toInt(reg3);
			int val2 = toInt(reg4);
			
			long result = (val1 * val2);
			
			String resultToHex = Long.toHexString(result);
			
			if(resultToHex.length() < 17){
				regData.put(reg1, 0);
				regData.put(reg2, result);
			}
			else{
				String hi = resultToHex.substring(0, 15);
				String lo = resultToHex.substring(0, resultToHex.length());
				
				regData.put(reg1, Long.parseLong(hi));
				regData.put(reg2, Long.parseLong(lo));
			}
			
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

	public void ldmInst(String regL, String regStart, String regEnd, boolean update, String amode){
		int[] elements;
		int length;
		int address = (int) regData.get(regL);
		int start;
		int end;

		regStart = regStart.replace("r", "");
		start = Integer.parseInt(regStart);

		if(regEnd.equals("null")){
			length = 1;
		}
		else{

			regEnd = regEnd.replace("r", "");
			end = Integer.parseInt(regEnd);

			length = Math.abs(start-end)+1;
		}


		switch(amode){
		case "fd":
		case "ia":
		{
			elements = mem.getMultipleMemoryElement(length, address, "incr");

			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg="r"+reg;
				regData.put(tmpReg, elements[i]);
				reg += 1;
			}
			if(update){
				regData.put(regL, address + length*4 );
			}
		}	
		break;
		case "ed":
		case "ib":
		{
			elements = mem.getMultipleMemoryElement(length, address+4, "incr");

			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg="r"+reg;
				regData.put(tmpReg, elements[i]);
				reg += 1;
			}
			if(update){
				regData.put(regL, address + length*4 );
			}
		}
		break;

		case "fa":
		case "da":
		{
			elements = mem.getMultipleMemoryElement(length, address, "decr");

			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg="r"+reg;
				regData.put(tmpReg, elements[i]);
				reg += 1;
			}
			if(update){
				regData.put(regL, address - length*4 );
			}
		}	
		break;

		case "ea":
		case "db":
		{
			elements = mem.getMultipleMemoryElement(length, address - 4, "decr");

			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg="r"+reg;
				regData.put(tmpReg, elements[i]);
				reg += 1;
			}

			if(update){
				regData.put(regL, address - length*4 );
			}
		}
		break;
		}
	}

	public void stmInst(String regS, String regStart, String regEnd, boolean update, String amode){
		int length=0;
		int address = (int) regData.get(regS);
		int start;
		int end;

		regStart = regStart.replace("r", "");
		start = Integer.parseInt(regStart);

		if(regEnd.equals("null")){
			length = 1;
		}
		else{
			regEnd = regEnd.replace("r", "");
			end = Integer.parseInt(regEnd);

			length = Math.abs(end-start)+1;
		}

		int[] elements = new int[length];

		switch(amode){
		case "ea":
		case "ia":
		{
			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg = "r"+reg;
				elements[i] = (int) regData.get(tmpReg);
				reg += 1;
			}

			mem.setMultipleMemoryElement(length, address, elements, "incr");

			if(update){
				regData.put(regS, address + length*4);
			}
		}	
		break;

		case "fa":
		case "ib":
		{
			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg = "r"+reg;
				elements[i] = (int) regData.get(tmpReg);
				reg += 1;
			}

			mem.setMultipleMemoryElement(length, address+4, elements, "incr");

			if(update){
				regData.put(regS, address + length*4);
			}
		}	
		break;

		case "ed":
		case "da":
		{
			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg = "r"+reg;
				elements[i] = (int) regData.get(tmpReg);
				reg += 1;
			}

			mem.setMultipleMemoryElement(length, address, elements, "decr");

			if(update){
				regData.put(regS, address - length*4);
			}
		}	
		break;

		case "fd":
		case "db":
		{
			int reg = start;
			String tmpReg;

			for(int i=0; i<length; i++){
				tmpReg = "r"+reg;
				elements[i] = (int) regData.get(tmpReg);
				reg += 1;
			}

			mem.setMultipleMemoryElement(length, address - 4, elements, "decr");

			if(update){
				regData.put(regS, address - length*4);
			}
		}	
		break;
		}
	}

	
	
}
