package memory;

import java.util.LinkedHashMap;


public class Memory {

	private LinkedHashMap<Object, Object> memory;
	
	public LinkedHashMap<Object, Object> init(){
		this.memory = new LinkedHashMap();
		return memory;
	}

	public void print(){
		for (Object key : memory.keySet()) {
			System.out.println(key + " " + memory.get(key).toString());
		}
	}
	
	public void clearMemory(){
		memory.clear();
	}
	
	public String printView(){
		String ent = "Address              Value\n \n";
		String ret = "";
		String line;
		
		for (Object key : memory.keySet()) {
			line = key + "       " + memory.get(key).toString();
			ret = ret+line+"\n";
		}
		
		return ent+ret;
	}
	
}
