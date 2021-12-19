package menu;

import java.util.Map.Entry;
import java.util.Scanner;

import account.Management;
import account.Member;

public class AdminMenu {
	Scanner sc = new Scanner(System.in);
	Management newUser = new Management();
	private String mod;

	public void adminMenu(Management inputUser) {
		this.newUser = inputUser;
		System.out.println("1. 회원조회");
		System.out.println("2. 재고현황");
		System.out.println("3. 회원관리");
		mod = sc.next();
		switch (mod) {
		case "1":
			for(Entry<String, Member> e : newUser.member.entrySet()) {
				System.out.println(e.getKey()+" "+e.getValue());
			}
			break;
		}
	}

}
