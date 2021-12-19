package account;

public class Member {
	private String id;
	private String pw; 
	private String name;
	private int account;
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", pw=" + pw + ", name=" + name +", account="+account+ "]";
	}


	public Member(String id, String pw, String name, int account) {
		this.account = account;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	public Member() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	
}
