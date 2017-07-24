package instructions;

import java.util.HashMap;

public class AccessMemory {

	private HashMap<Object, Object> memory;

	public AccessMemory(HashMap<Object, Object> memory){
		this.memory = memory;
	}


	private String toHex32(int el){
		String hex = Integer.toHexString(el);

		if(hex.length() < 8){
			while (!(hex.length() == 8 )){
				hex = "0"+hex;
			}
		}

		hex = "0x"+hex;
		return hex;
	}

	private int toInt32(String el){
		el = el.replace("0x", "");

		Long tmp = Long.parseLong(el, 16);

		int t = (int) (long) tmp;

		return t;
	}

	private String toHalfWord(int el){
		el = el & ~(1 << 31);
		el = el & ~(1 << 30);
		el = el & ~(1 << 29);
		el = el & ~(1 << 28);
		el = el & ~(1 << 27);
		el = el & ~(1 << 26);
		el = el & ~(1 << 25);
		el = el & ~(1 << 24);
		el = el & ~(1 << 23);
		el = el & ~(1 << 22);
		el = el & ~(1 << 21);
		el = el & ~(1 << 20);
		el = el & ~(1 << 19);
		el = el & ~(1 << 18);
		el = el & ~(1 << 17);
		el = el & ~(1 << 16);

		String hw = toHex32(el);

		return hw;
	}

	private String toByte(int el){
		el = el & ~(1 << 31);
		el = el & ~(1 << 30);
		el = el & ~(1 << 29);
		el = el & ~(1 << 28);
		el = el & ~(1 << 27);
		el = el & ~(1 << 26);
		el = el & ~(1 << 25);
		el = el & ~(1 << 24);
		el = el & ~(1 << 23);
		el = el & ~(1 << 22);
		el = el & ~(1 << 21);
		el = el & ~(1 << 20);
		el = el & ~(1 << 19);
		el = el & ~(1 << 18);
		el = el & ~(1 << 17);
		el = el & ~(1 << 16);
		el = el & ~(1 << 15);
		el = el & ~(1 << 14);
		el = el & ~(1 << 13);
		el = el & ~(1 << 12);
		el = el & ~(1 << 11);
		el = el & ~(1 << 10);
		el = el & ~(1 << 9);
		el = el & ~(1 << 8);

		String b = toHex32(el);

		return b;
	}

	public int getMemoryElement32(int address){
		Object element = null;
		int elInt;
		String hexAdd;

		//aligned:
		if( (address % 4)==0 ){
			//address already exist:
			hexAdd = toHex32(address);
			if(memory.containsKey(hexAdd)){
				element = memory.get(hexAdd);
				elInt = toInt32(element.toString());
			}
			else{//if address doesn't exist: 
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(element.toString());
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//get the last byte:
			int b = Integer.parseInt(hexAdd.substring(hexAdd.length() - 1), 16);
			if (b != 4){b = 0;}

			//if address already exist:
			if(memory.containsKey(hexAdd)){
				if(b == 4){
					String tmp = memory.get(hexAdd).toString();
					String first = tmp.substring(0, 3);
					String second = tmp.substring(4, 7);

					tmp = second+first;
					element = tmp;
					elInt = toInt32(element.toString());
				}
				else{
					element = memory.get(hexAdd);
					elInt = toInt32(element.toString());
				}
			}
			else{//if address doesn't exist:
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(element.toString());
			}
		}

		return elInt;
	}

	public void setMemoryElement32(int address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		String hexAdd;
		String el = toHex32(element);

		//aligned:
		if( (address % 4)==0 ){
			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}	
		}
	}

	public int getMemoryElement16(int address){
		Object element = null;
		int elInt;
		String hexAdd;

		//aligned:
		if( (address % 4)==0 ){
			//address already exist:
			hexAdd = toHex32(address);
			if(memory.containsKey(hexAdd)){
				element = memory.get(hexAdd);
				elInt = toInt32(toHalfWord(toInt32(element.toString())));
			}
			else{//if address doesn't exist: 
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(toHalfWord(toInt32(element.toString())));
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//get the last byte:
			int b = Integer.parseInt(hexAdd.substring(hexAdd.length() - 1));
			if (b != 4){b = 0;}

			//if address already exist:
			if(memory.containsKey(hexAdd)){
				if(b == 4){
					String tmp = memory.get(hexAdd).toString();
					String first = tmp.substring(0, 3);
					String second = tmp.substring(4, 7);

					tmp = second+first;
					element = tmp;
					elInt = toInt32(toHalfWord(toInt32(element.toString())));
				}
				else{
					element = memory.get(hexAdd);
					elInt = toInt32(toHalfWord(toInt32(element.toString())));
				}
			}
			else{//if address doesn't exist:
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(toHalfWord(toInt32(element.toString())));
			}
		}

		return elInt;
	}

	public void setMemoryElement16(int address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		String hexAdd;
		String el = toHalfWord(element);

		//aligned:
		if( (address % 4)==0 ){
			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}	
		}
	}

	public int getMemoryElement8(int address){
		Object element = null;
		int elInt;
		String hexAdd;

		//aligned:
		if( (address % 4)==0 ){
			//address already exist:
			hexAdd = toHex32(address);
			if(memory.containsKey(hexAdd)){
				element = memory.get(hexAdd);
				elInt = toInt32(toByte(toInt32(element.toString())));
			}
			else{//if address doesn't exist: 
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(toByte(toInt32(element.toString())));
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//get the last byte:
			int b = Integer.parseInt(hexAdd.substring(hexAdd.length() - 1));
			if (b != 4){b = 0;}

			//if address already exist:
			if(memory.containsKey(hexAdd)){
				if(b == 4){
					String tmp = memory.get(hexAdd).toString();
					String first = tmp.substring(0, 3);
					String second = tmp.substring(4, 7);

					tmp = second+first;
					element = tmp;
					elInt = toInt32(toByte(toInt32(element.toString())));
				}
				else{
					element = memory.get(hexAdd);
					elInt = toInt32(toByte(toInt32(element.toString())));
				}
			}
			else{//if address doesn't exist:
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt32(toByte(toInt32(element.toString())));
			}
		}

		return elInt;
	}

	public void setMemoryElement8(int address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		String hexAdd;
		String el = toByte(element);

		//aligned:
		if( (address % 4)==0 ){
			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex32(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}	
		}
	}

	public int[] getMultipleMemoryElement(int n, int address, String incrDecr){		
		int [] elements = new int[n];

		switch(incrDecr){
		case "incr":
		{
			for (int i=0; i<n; i++){
				elements[i] = getMemoryElement32(address + i*4);
			}	
		}
		break;

		case "decr":
		{
			for (int i=0; i<n; i++){
				elements[i] = getMemoryElement32(address - i*4);
			}
		}
		break;
		}

		return elements;	
	}

	public void setMultipleMemoryElement(){

	}


}
