package memory;

import java.util.HashMap;

public class Memory {

	//TODO change Memory to generate address mod 4, verify if exist, if yes, increment do not erase)

	private HashMap<Object, Object> memory;

	public HashMap<Object, Object> init(){
		this.memory = new HashMap();
		return memory;
	}

	private String toHex(int el){
		String hex = Integer.toHexString(el);
		
		if(hex.length() < 8){
			while (!(hex.length() == 8 )){
				hex = "0"+hex;
			}
		}
		
		hex = "0x"+hex;
		return hex;
	}
	
	private int toInt(String el){
		el = el.replace("0x", "");
		
		Long tmp = Long.parseLong(el, 16);
		
		int t = (int) (long) tmp;
		
		return t;
	}
	
	public int getMemoryElement(int address){
		Object element = null;
		int elInt;
		String hexAdd;

		//aligned:
		if( (address % 4)==0 ){
			//address already exist:
			hexAdd = toHex(address);
			if(memory.containsKey(hexAdd)){
				element = memory.get(hexAdd);
				elInt = toInt(element.toString());
			}
			else{//if address doesn't exist: 
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt(element.toString());
			}
		}
		else{//unaligned:
			//clear two last bits
			address = address & ~(1 << 0);
			address = address & ~(1 << 1);

			hexAdd = toHex(address);
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
					elInt = toInt(element.toString());
				}
				else{
					element = memory.get(hexAdd);
					elInt = toInt(element.toString());
				}
			}
			else{//if address doesn't exist:
				memory.put(hexAdd, "0x00000000");
				element = memory.get(hexAdd);
				elInt = toInt(element.toString());
			}
		}

		return elInt;
	}
	
	public void setMemoryElement(int address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		String hexAdd;
		String el = toHex(element);

		//aligned:
		if( (address % 4)==0 ){
			hexAdd = toHex(address);
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

			hexAdd = toHex(address);
			//address already exist:
			if(memory.containsKey(hexAdd)){
				memory.put(hexAdd, el);
			}
			else{
				memory.put(hexAdd, el);
			}	
		}
	}
	 
	public void print(){
		for (Object key : memory.keySet()) {
			System.out.println(key + " " + memory.get(key).toString());
		}
	}
}
