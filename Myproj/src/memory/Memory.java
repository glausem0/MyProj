package memory;

import java.util.HashMap;

public class Memory {
	
	private HashMap<Object, Object> memory;
	
	public HashMap<Object, Object> init(){
		this.memory = new HashMap();
		return memory;
	}
	
public Object getMemoryElement(int address){
		
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
	
	public void setMemoryElement(Object address, int element){
		//if address exist, change the value of the address.
		//if address doesn't exist, create it with the value of element.
		
		String hex = Integer.toHexString(element);
		
		memory.put(address, hex);
	}

	public void print(){
		for (Object key : memory.keySet()) {
			System.out.println(key + " " + memory.get(key).toString());
		}
	}

}
