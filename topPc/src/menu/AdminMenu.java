package menu;

import java.util.Map.Entry;
import java.util.Scanner;

import account.Management;
import account.Member;

public class AdminMenu {
	Scanner sc = new Scanner(System.in);
	Management newUser = new Management();
	private String mod;
	private int mod2;
	private String delete;
	private boolean adMenu;
	private String inputId;
	
	public void adminMenu(Management inputUser) {
		adMenu = true;
		this.newUser = inputUser;
		while(adMenu) {
			System.out.println("1. 회원관리");
			System.out.println("2. 재고현황");
			System.out.println("3. 종료");
			mod = sc.next();
			switch (mod) {
			case "1":
				manageMember();
				break;
			case "2":
				manageProduct();
				break;
			case "3":
				adMenu = false;
				break;
			}
			
		}
	}
	private void manageProduct() {
		System.out.println();
	}
	public void manageMember(){
		for(Entry<String, Member> e : newUser.member.entrySet()) {
			System.out.println(e.getKey()+" "+e.getValue());
		}		
		System.out.println("1. 회원탈퇴");
		System.out.println("2. 종료");
		mod2 = sc.nextInt();
		if(mod2 == 1) {
			System.out.print("삭제할아이디 입력 : ");
			inputId = sc.next();
			if(newUser.member.containsKey(inputId)) {
				System.out.println("삭제하실 아이디는 : "+ inputId + " 입니다. 삭제하시겠습니까?(y/n)");
				delete = sc.next();
				switch(delete) {
				case "y":
				case "Y":
					newUser.member.remove(inputId);
					System.out.println("탈퇴가 완료되었습니다");
					manageMember();
					break;
				case "n":
				case "N":
					manageMember();
					break;
				}
			}else {
				System.out.println("해당 아이디의 사용자가 없습니다");
				
			}
		}else if(mod2 == 2){
			System.out.println("회원관리종료");
		}else {
			System.out.println("메뉴의 번호를 선택해주세요");
			manageMember();
		}
	}

}
