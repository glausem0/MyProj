package memory;

import java.util.HashMap;

public class Memory {

	//TODO change Memory to generate address mod 4, verify if exist, if yes, increment do not erase)

	private HashMap<Object, Object> memory;

	public HashMap<Object, Object> init(){
		this.memory = new HashMap();
		return memory;
	}

	public void print(){
		for (Object key : memory.keySet()) {
			System.out.println(key + " " + memory.get(key).toString());
		}
	}
	
}
