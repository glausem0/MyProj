package memory;

import java.util.LinkedHashMap;


public class Memory {

	private LinkedHashMap<Object, Object> memory;
	
	public LinkedHashMap<Object, Object> init(){
		this.memory = new LinkedHashMap();
		memory.put("0x00000000", "0x00000000"); //RESET
		memory.put("0x00000004", "0x00000004");	//"UNDEF" 
		memory.put("0x00000008", "0x00000008"); //"SWI" 
		memory.put("0x0000000c", "0x0000000c"); //"PABT"
		memory.put("0x00000010", "0x00000010"); //"DABT"
		memory.put("0x00000014", "0x00000014"); //"RESERVED"
		memory.put("0x00000018", "0x00000018"); //"IRQ"
		memory.put("0x0000001c", "0x0000001c"); //"FIQ"
		
		return memory;
	}
	
	/**
	 * erase all the value of the memory and restored base values
	 */
	public void clearMemory(){
		memory.clear();
		memory.put("0x00000000", "0x00000000"); //RESET
		memory.put("0x00000004", "0x00000004");	//"UNDEF" 
		memory.put("0x00000008", "0x00000008"); //"SWI" 
		memory.put("0x0000000c", "0x0000000c"); //"PABT"
		memory.put("0x00000010", "0x00000010"); //"DABT"
		memory.put("0x00000014", "0x00000014"); //"RESERVED"
		memory.put("0x00000018", "0x00000018"); //"IRQ"
		memory.put("0x0000001c", "0x0000001c"); //"FIQ"
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
