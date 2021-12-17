package account;

import java.util.HashMap;

public class Management {
	
	public HashMap<String, String> member = new HashMap<String, String>();
	public void addMember(String id,String pw) {
		member.put(id, pw);
	}
	
}
