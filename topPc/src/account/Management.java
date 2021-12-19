package account;

import java.util.HashMap;

public class Management {
	
	public HashMap<String, Member> member = new HashMap<String, Member>();
	public void addMember(String id,Member mb) {
		member.put(id, mb);
	}
	
}
