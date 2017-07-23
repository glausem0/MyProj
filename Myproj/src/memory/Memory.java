package memory;

import java.util.HashMap;

public class Memory {
	
	//TODO change Memory to generate address mod 4, verify if exist, if yes, increment do not erase)
	
	private HashMap<Object, Object> memory;
	
	public HashMap<Object, Object> init(){
		this.memory = new HashMap();
		return memory;
	}
	
public Object getMemoryElement(int address){
		int comp = 0;
		
		Object element = null;
		String hexAdd = Integer.toHexString(address);
		hexAdd = "0x"+hexAdd;
		
		if (memory.containsKey(hexAdd)){
			element = memory.get(hexAdd);
		}
		else{
			memory.put(hexAdd, "0x00000000");
			element = memory.get(hexAdd);
		}
		
		return element;
	}
	
	public void setMemoryElement(int address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		
		String hexAdd = Integer.toHexString(address);
		hexAdd = "0x"+hexAdd;
		String hexEl = Integer.toHexString(element);
		hexEl = "0x"+hexEl;
		
		memory.put(hexAdd, hexEl);
	}

	public void print(){
		for (Object key : memory.keySet()) {
			System.out.println(key + " " + memory.get(key).toString());
		}
	}

}
