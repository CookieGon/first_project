package menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import account.Management;
import account.Member;
import product.Drink;
import product.Meal;
import product.Products;
import product.Snack;

public class Menu {
	Management newUser = new Management();
	AdminMenu adminControl = new AdminMenu();
	private String mod;
	private String switchYN;

	// 상품 리스트 생성
	Products[] Drink = new Products[3];
	Products[] Meal = new Products[3];
	Products[] Snack = new Products[3];

	// 장바구니 ArrayList 제네릭타입 Products 으로 생성
	ArrayList<Products> aList = new ArrayList<>();

	// 로그인중인 사용자 id, name ,account
	private String nowUserId;
	private String nowUserName;
	private int nowUserAccount;

	private boolean runnig = true; // 메인메뉴 동작 상태
	private boolean userRunnig = true; // 메인메뉴 동작 상태

	Scanner sc = new Scanner(System.in);

	Timer timer = new Timer();

	public void topPc() {
		while (runnig) {
			System.out.println();
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

	/*
	 * 최초 상품 목록 생성
	 */
	public void setProduct() {
		Drink[0] = new Drink("음료", "콜라", 1200, 20);
		Drink[1] = new Drink("음료", "사이다", 1100, 20);
		Drink[2] = new Drink("음료", "에너지드링크", 1500, 20);

		Meal[0] = new Meal("식사", "라면", 3000, 30);
		Meal[1] = new Meal("식사", "김치볶음밥", 4000, 30);
		Meal[2] = new Meal("식사", "짜장라면", 3100, 30);

		Snack[0] = new Snack("간식", "감자칩", 2000, 10);
		Snack[1] = new Snack("간식", "쿠키", 1100, 10);
		Snack[2] = new Snack("간식", "오다리", 1500, 10);
	}

	/*
	 * 파일 종료
	 */
	private void exit() {
		System.out.println("시스템을 종료합니다.");
		runnig = false;
	}

	/*
	 * 회원가입 메소드
	 */
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
	}

	private void newUser(String id, Member mb) {
		newUser.addMember(id, mb);
	}

	// 로그인 메소드 hashMap에서 아이디 비밀번호 조회 / 비교
	// 최소금액 1200원 미만일 경우 충전창으로 자동 이동
	private void login() {
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
					timer.schedule(task, 3000, 3000);
					printUserMenu();
				}
			}
		}

	}

	// 현재 로그인 된 사용자 메뉴 출력
	private void printUserMenu() {
		if (nowUserAccount > 1200) {
			while (userRunnig) {
				System.out.println();
				System.out.println("id : " + nowUserId + " 이름 : " + nowUserName);
				System.out.println("1. 잔액 충전");
				System.out.println("2. 상품 주문");
				System.out.println("3. 잔액 조회");
				System.out.println("4. 사용 종료");
				mod = sc.next();
				while (true) {
					switch (mod) {
					case "1":
						charging(nowUserAccount);
						break;
					case "2":
						order();
						break;
					case "3":
						checkAccount(nowUserAccount);
						break;
					case "4":
						logOut();
						break;
					default:
						System.out.println();
						System.out.println("메뉴 번호를 입력해 주세요!");
						System.out.println();
					}
				}

			}
		} else {
			System.out.println();
			System.out.println("사용시간 부족으로 사용을 종료합니다");
			System.out.println();
			topPc();
			return;
		}
	}

	// 로그아웃 메소드
	private void logOut() {
		System.out.println("사용을 종료 하시겠습니까?(y,n)");
		switchYN = sc.next();
		if (switchYN.equals("y") || switchYN.equals("Y")) {
			topPc();
		} else if (switchYN.equals("n") || switchYN.equals("N")) {
			printUserMenu();
		} else {
			System.out.println("y또는 n을 입력해 주세요");
			logOut();
		}
	}

	// 현재 로그인 된 사용자 잔액 조회 메소드
	private void checkAccount(int account) {
		System.out.println(nowUserId + "님의 잔액은 " + account + "입니다.");
		printUserMenu();
	}

	// 상품 주문 메소드
	private void order() {
//		boolean ot = true;
		System.out.println("상품 카테고리 선택");
		System.out.println("1. 음료");
		System.out.println("2. 식사");
		System.out.println("3. 간식");
		System.out.println("4. 결제/종료");
		int cno = sc.nextInt();
		printProducts(cno);
	}

	// 상품주문 카테고리 호출 메소드
	public void printProducts(int categoryNo) {
		switch (categoryNo) {
		case 1:
			getCategory(Drink);
			break;
		case 2:
			getCategory(Meal);
			break;
		case 3:
			getCategory(Snack);
			break;
		case 4:
			checkOut();
			break;
		default:
			System.out.println("메뉴 번호를 입력해주세요");
			order();
			break;
		}

	}

	// 카테고리 호출 후 상품 선택 메소드(재고수량 비교)
	private void getCategory(Products[] category) {
		int i = 1;
		int pno;
		for (Products c : category) {
			System.out.println(i + " : " + c.toString());
			i++;
		}
		try {
			System.out.print("상품 번호를 입력해주세요 -> ");
			pno = sc.nextInt() - 1;
			if (category[pno].getStock() > 0) {
				aList.add(category[pno]);
				category[pno].setStock(category[pno].getStock() - 1);
			} else {
				System.out.println();
				System.out.println("해당 상품의 재고가 부족합니다");
				System.out.println();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println();
			System.out.println("해당 번호의 상품이 없습니다!");
			System.out.println();
		}
	}

	// 장바구니에 추가된 상품 결제(비어있을경우 유저 메뉴로 돌아감)
	// 현재 로그인된 사용자의 잔액으로 비교/결제
	private void checkOut() {
		int totalP = 0;
		String checkYN;
		if (aList.isEmpty()) {
			printUserMenu();
		} else {
			for (Products a : aList) {
				totalP += a.getPrice();
			}
			System.out.println("총 금액 : " + totalP + " 원");
			System.out.print("결제 하시겠습니까 ? (y/n) : ");
			checkYN = sc.next();
			switch (checkYN) {
			case "y":
			case "Y":
				if (nowUserAccount - 1200 < totalP) {
					System.out.println("잔액이 부족합니다");
					aList.removeAll(aList);
					printUserMenu();
				} else {
					System.out.println("상품 주문 완료");
					nowUserAccount -= totalP;
					System.out.println("잔액 : " + nowUserAccount + " 원");
					printUserMenu();
				}
				break;
			case "n":
			case "N":
				aList.removeAll(aList);
				printUserMenu();
				break;
			default:
				System.out.println();
				System.out.println("(y/n)을 입력해주세요!");
				System.out.println();
				break;
			}

		}
	}

	// 잔액 충전 메소드
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
			printUserMenu();
			break;
		default:
			System.out.println();
			System.out.println("메뉴 번호를 입력해 주세요!");
			System.out.println();
			break;
		}
	}

	// admin계정 접속 / 관리자 콘솔 호출
	private void control() { // admin 컨트롤
		for (int i = 0; i <= 3; i++) {
			System.out.println("ID를 입력하세요");
			if (sc.next().equals("admin")) {
				System.out.println("비밀번호를 입력 하세요");
				if (sc.next().equals("admin")) {
					System.out.println("관리자 콘솔 접속 성공");
					adminControl.adminMenu(newUser);
				} else {
					System.out.println("관리자 비밀번호가 아닙니다");
					i++;
				}
			} else {
				System.out.println("관리자 아이디가 아닙니다");
				i++;
			}
		}
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			if (nowUserAccount > 1200) {
				nowUserAccount -= 1200;
			} else {
				timer.cancel();
				printUserMenu();
			}
		}

	};

}
