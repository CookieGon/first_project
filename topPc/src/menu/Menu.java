package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import account.Management;
import account.Member;

public class Menu {
	Management newUser = new Management();
	AdminMenu adminControl = new AdminMenu();
	private String mod;
	private String switchYN;
	
	private String nowUserId; // 로그인 중인 아이디
	private String nowUserName;
	private int nowUserAccount;
	
	private boolean runnig = true;	// 메인메뉴 동작 상태
	Scanner sc = new Scanner(System.in);

	public void run() {
		while (runnig) {
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
		Member memberInfo = new Member();
		System.out.println("ID를 입력하세요");
		memberInfo.setId(sc.next());
		System.out.println("비밀번호를 입력 하세요");
		memberInfo.setPw(sc.next());
		System.out.println("이름을 입력 하세요");
		memberInfo.setName(sc.next());
		memberInfo.setAccount(1500);
		newUser(memberInfo.getId(), memberInfo);
		System.out.println(memberInfo.toString());
		run();
	}

	private void newUser(String id, Member mb) {
		newUser.addMember(id, mb);
	}

	private void login() { // 로그인
		System.out.println("ID를 입력하세요");
		String id = sc.next();
		if (newUser.member.containsKey(id)) {
			System.out.println("비밀번호를 입력 하세요");
			String pw = sc.next();
			if (newUser.member.get(id).getPw().equals(pw)) {
				if (newUser.member.get(id).getAccount() < 1200) {
					System.out.println("남은 사용시간이 부족합니다. 충전창으로 이동합니다");
					nowUserAccount = newUser.member.get(id).getAccount();
					charging(nowUserAccount);
				} else {
					System.out.println("로그인에 성공했습니다");
					nowUserId = id;
					nowUserName = newUser.member.get(id).getName();
					nowUserAccount = newUser.member.get(id).getAccount();
					printUserMenu(nowUserId,nowUserName,nowUserAccount);
				}
			}
		}

	}

	private void printUserMenu(String id, String name ,int account) {
		System.out.println("id : " + id + " 이름 : " + name);
		System.out.println("1. 잔액 충전");
		System.out.println("2. 상품 주문");
		System.out.println("3. 잔액 조회");
		System.out.println("4. 사용 종료");
		mod = sc.next();
		while(true) {
			switch (mod) {
			case "1":
				charging(account);
				break;
			case "2":
				order();
				break;
			case "3":
				checkAccount(account);
				break;
			case "4":
				logOut();
				break;
			}
		}
	}

	private void logOut() {
		System.out.println("사용을 종료 하시겠습니까?(y,n)");
		switchYN = sc.next();
		if (switchYN.equals("y") || switchYN.equals("Y")) {
			run();
		} else if (switchYN.equals("n") || switchYN.equals("N")) {
			printUserMenu(nowUserId,nowUserName,nowUserAccount);
		} else {
			System.out.println("y또는 n을 입력해 주세요");
			logOut();
		}
	}

	private void checkAccount(int account) {
		System.out.println(nowUserId + "님의 잔액은 " + account + "입니다.");
		printUserMenu(nowUserId,nowUserName,account);
	}

	private void order() {

	}

	private void charging(int account) {
		System.out.println("1. 1000원 충전");
		System.out.println("2. 5000원 충전");
		System.out.println("3. 10000원 충전");
		System.out.println("4. 50000원 충전");
		System.out.println("5. 충전 종료");
		mod = sc.next();
		switch (mod) {
		case "1":
			account += 1000;
			charging(account);
			break;
		case "2":
			account += 5000;
			charging(account);
			break;
		case "3":
			account += 10000;
			charging(account);
			break;
		case "4":
			account += 50000;
			charging(account);
			break;
		case "5":
			System.out.println("충전을 종료합니다");
			newUser.member.get(nowUserId).setAccount(account);
			nowUserAccount = newUser.member.get(nowUserId).getAccount();
			printUserMenu(nowUserId,nowUserName,nowUserAccount);
			break;
		}
	}

	private void control() { // admin 컨트롤
		System.out.println("ID를 입력하세요");
		if (sc.next().equals("admin")) {
			System.out.println("비밀번호를 입력 하세요");
			if (sc.next().equals("admin")) {
				System.out.println("관리자 콘솔 접속 성공");
				adminControl.adminMenu(newUser);
			} else {
				System.out.println("관리자 비밀번호가 아닙니다");
				control();
			}
		} else {
			System.out.println("관리자 아이디가 아닙니다");
			control();
		}
	}

}
