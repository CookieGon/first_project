package application;

import java.util.HashMap;

class TestList{
	private String list1;
	private String list2;
	private int list3;
	public String getList1() {
		return list1;
	}
	public void setList1(String list1) {
		this.list1 = list1;
	}
	public String getList2() {
		return list2;
	}
	public void setList2(String list2) {
		this.list2 = list2;
	}
	public int getList3() {
		return list3;
	}
	public void setList3(int list3) {
		this.list3 = list3;
	}
	public TestList(String list1, String list2, int list3) {
		super();
		this.list1 = list1;
		this.list2 = list2;
		this.list3 = list3;
	}
	@Override
	public String toString() {
		return "TestList [list1=" + list1 + ", list2=" + list2 + ", list3=" + list3 + "]";
	}
	
	
	
}

public class Test {

	public static void main(String[] args) {
		HashMap<String, TestList> map = new HashMap<>();
		TestList tl = new TestList("1","2",3);
		map.put("1", tl);
		System.out.println(map.get("1").getList1());
	}
}
