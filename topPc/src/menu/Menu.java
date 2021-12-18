package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import account.Management;
import account.Member;

public class Menu {
	Management newUser = new Management();
	Member memberInfo = new Member();
	private String mod;
	private String switchYN;
	private boolean runnig = true;
	Scanner sc = new Scanner(System.in);

	public void run() {
		while(runnig) {
			System.out.println("1. login");
			System.out.println("2. join");
			System.out.println("4. exit");
			System.out.println("메뉴의 번호를 입력해 주세요");
			try {
				mod = sc.next();
				switch (mod) {
				case "1":
					login();
					break;
				case "2":
					join();
					break;
				case "3":
					control();
					break;
				case "4":
					exit();
					break;
				}
			} catch (InputMismatchException e) { // 숫자 외 입력 오류 방지
				System.out.println("숫자를 입력해 주세요");
			}
		}
	}

	private void exit() {
		System.out.println("시스템을 종료합니다.");
		runnig = false;
	}

	private void join() { // 회원가입
		System.out.println("ID를 입력하세요");
		memberInfo.setId(sc.next());
		System.out.println("비밀번호를 입력 하세요");
		memberInfo.setPw(sc.next());
		System.out.println("이름을 입력 하세요");
		memberInfo.setName(sc.next());
		memberInfo.setAccount(0);
		newUser(memberInfo.getId(), memberInfo.getPw());
		System.out.println(memberInfo.toString());
		run();
	}

	private void newUser(String id, String pw) {
		newUser.addMember(id, pw);
	}

	private void login() { // 로그인
		System.out.println("ID를 입력하세요");
		String id = sc.next();
		if (newUser.member.containsKey(id)) {
			System.out.println("비밀번호를 입력 하세요");
			String pw = sc.next();
			if (newUser.member.get(id).equals(pw)) {
				if (memberInfo.getAccount() < 1200) {
					System.out.println("남은 사용시간이 부족합니다. 충전창으로 이동합니다");
					charging();
				} else {
					System.out.println("로그인에 성공했습니다");
					printUserMenu();
				}
			}
		}

	}

	private void printUserMenu() {
		System.out.println("id : " + memberInfo.getId() + " 이름 : " + memberInfo.getName());
		System.out.println("1. 잔액 충전");
		System.out.println("2. 상품 주문");
		System.out.println("3. 잔액 조회");
		System.out.println("4. 사용 종료");
		mod = sc.next();
		switch (mod) {
		case "1":
			charging();
			break;
		case "2":
			order();
			break;
		case "3":
			checkAccount();
			break;
		case "4":
			logOut();
			run();
			break;
		default:
			System.out.println();
			System.out.println("메뉴의 번호를 입력해 주세요!");
			System.out.println();
			break;
		}
	}

	private void logOut() {
		System.out.println("사용을 종료 하시겠습니까?(y,n)");
		switchYN = sc.next();
		if(switchYN.equals("y") || switchYN.equals("Y")) {
			run();
		}else if(switchYN.equals("n")||switchYN.equals("N")) {
			printUserMenu();
		}else {
			System.out.println("y또는 n을 입력해 주세요");
			logOut();
		}
	}

	private void checkAccount() {
		System.out.println(memberInfo.getName()+"님의 잔액은 "+memberInfo.getAccount()+"입니다.");
		printUserMenu();
	}

	private void order() {
		// TODO Auto-generated method stub

	}

	private void charging() {
		System.out.println("1. 1000원 충전");
		System.out.println("2. 5000원 충전");
		System.out.println("3. 10000원 충전");
		System.out.println("4. 50000원 충전");
		System.out.println("5. 충전 종료");
		mod = sc.next();
		switch (mod) {
		case "1":
			memberInfo.setAccount(memberInfo.getAccount() + 1000);
			charging();
			break;
		case "2":
			memberInfo.setAccount(memberInfo.getAccount() + 5000);
			charging();
			break;
		case "3":
			memberInfo.setAccount(memberInfo.getAccount() + 10000);
			charging();
			break;
		case "4":
			memberInfo.setAccount(memberInfo.getAccount() + 50000);
			charging();
			break;
		case "5":
			System.out.println("충전을 종료합니다");
			printUserMenu();
			break;
		}
	}

	private void control() { // admin 컨트롤
		System.out.println("ID를 입력하세요");
		System.out.println("비밀번호를 입력 하세요");
	}

}
