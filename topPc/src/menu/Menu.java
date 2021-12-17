package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import account.Management;
import account.Member;

public class Menu {
	Management newUser = new Management();
	private String mod;
	Scanner sc = new Scanner(System.in);

	public void run() {
		System.out.println("메뉴의 번호를 입력해 주세요");
		while (true) {
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
				case "4:":
					System.out.println("종료되었습니다.");
					sc.close();
					break;
				default: // 지정된 숫자 외 입력시 재입력 요구
					System.out.println();
					System.out.println("메뉴의 번호를 입력해 주세요!");
					System.out.println();
					break;
				}
			} catch (InputMismatchException e) { // 숫자 외 입력 오류 방지
				System.out.println("숫자를 입력해 주세요");
			}
		}

	}

	private void join() { // 회원가입
		System.out.println("ID를 입력하세요");
		String id = sc.next();
		System.out.println("비밀번호를 입력 하세요");
		String pw = sc.next();
		System.out.println("이름을 입력 하세요");
		String name = sc.next();
		Member newMember = new Member(id, pw, name);
		newUser(id, pw);
		System.out.println(newMember.toString());
		run();
	}

	private void newUser(String id, String pw) {
		newUser.addMember(id, pw);
	}

	private void login() { // 로그인
		System.out.println("ID를 입력하세요");
		String id = sc.next();
		if(newUser.member.containsKey(id)) {
			System.out.println("비밀번호를 입력 하세요");
			String pw = sc.next();
			if(newUser.member.get(id).equals(pw)) {
				System.out.println("로그인에 성공했습니다");
			}
		}

	}

	private void control() { // admin 컨트롤
		System.out.println("ID를 입력하세요");
		System.out.println("비밀번호를 입력 하세요");
	}

}
