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

	// 메인메뉴 동작 상태
	boolean runnig = true;
	// 가입한 사용자 정보 저장 해쉬맵
	Management newUser = new Management();

	// 관리자 메뉴 호출을 위한 adminControl 생성
	AdminMenu adminControl = new AdminMenu();

	// 입력을 위한 스캐너 생성
	Scanner sc = new Scanner(System.in);
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

	// 사용시간 타이머를 위해 Timer와 TimerTask 선언
	Timer timer;
	TimerTask task;

	// timer 중복실행 방지용
	boolean timerRunnig = false;

	// 기존 사용자 등록 (회원가입시 id 중복 체크)
	public void setDefaultUser() {
		Member m1 = new Member("abc", "abc", "홍길동", 20000);
		Member m2 = new Member("abcd", "abcd", "둘리", 13000);
		Member m3 = new Member("123", "123", "라이언", 16000);
		newUser(m1.getId(), m1);
		newUser(m2.getId(), m2);
		newUser(m3.getId(), m3);
	}

	// 매점 상품 등록
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

	public void printLogo() {
		System.out.println(":::::::::::  ::::::::  :::::::::   :::::::::   ::::::::  \n"
				+ "    :+:     :+:    :+: :+:    :+:  :+:    :+: :+:    :+: \n"
				+ "    +:+     +:+    +:+ +:+    +:+  +:+    +:+ +:+        \n"
				+ "    +#+     +#+    +:+ +#++:++#+   +#++:++#+  +#+        \n"
				+ "    +#+     +#+    +#+ +#+         +#+        +#+        \n"
				+ "    #+#     #+#    #+# #+#         #+#        #+#    #+# \n"
				+ "    ###      ########  ###         ###         ########  ");
	}

	// 메인 메뉴
	public void topPc() {
		while (runnig) {
			System.out.println();
			System.out.println("\t 1. login");
			System.out.println("\t 2. join");
			System.out.println("\t 4. exit");
			System.out.println("\t 메뉴의 번호를 입력해 주세요");
			System.out.print("\t >>> ");
			mod = sc.next();
			switch (mod) {
			case "1":
				logIn(); // 로그인
				break;
			case "2":
				join(); // 회원가입
				break;
			case "3":
				control(); // 관리자 로그인
				break;
			case "4":
				exit(); // 시스템 종료
				break;
			default:
				System.out.println();
				System.out.println("메뉴의 번호를 입력해주세요");
				System.out.println();
				break;
			}
		}
	}

	// 회원가입 메소드
	private void join() {
		String joinId;
		String joinPw;
		String joinName;
		System.out.print("ID를 입력하세요 : ");
		joinId = sc.next();
		if (newUser.member.containsKey(joinId)) { // 중복 아이디 체크
			System.out.println("중복된 아이디가 존재 합니다");
			join();
		} else {
			System.out.print("비밀번호를 입력 하세요 : ");
			joinPw = sc.next();
			System.out.print("이름을 입력 하세요 : ");
			joinName = sc.next();
			System.out.println("\t-회원가입 완료-");
			Member memberInfo = new Member(joinId, joinPw, joinName, 0); // 신규 회원 생성
			newUser(memberInfo.getId(), memberInfo); // key : id, value : memberInfo로 newUser()메소드 호출
			topPc();
		}
	}

	// 신규 가입 회원을 member 해쉬맵에 (key id, value Member로 저장)
	private void newUser(String id, Member mb) {
		newUser.addMember(id, mb);
	}

	// 파일종료
	private void exit() {
		System.out.println("시스템을 종료합니다.");
		System.exit(0);
	}

	// 로그인 메소드 hashMap에서 아이디 비밀번호 조회 / 비교
	// 최소금액 1200원 미만일 경우 충전창으로 자동 이동
	private void logIn() {
		System.out.print("ID를 입력하세요 : ");
		String id = sc.next();
		if (newUser.member.containsKey(id)) {
			System.out.print("비밀번호를 입력 하세요 : ");
			String pw = sc.next();
			if (newUser.member.get(id).getPw().equals(pw)) {
				System.out.println("로그인에 성공했습니다");
				System.out.println();
				runnig = false;
				nowUserId = id;
				nowUserName = newUser.member.get(id).getName();
				nowUserAccount = newUser.member.get(id).getAccount();
				if (nowUserAccount < 1200) {
					System.out.println("남은 사용시간이 부족합니다. 충전창으로 이동합니다");
					charging(nowUserAccount);
				} else {
					printUserMenu();
				}
			} else {
				System.out.println();
				System.out.println("비밀번호가 틀렸습니다.");
			}
		} else {
			System.out.println();
			System.out.println("아이디가 존재하지 않습니다!");
			System.out.println();

		}

	}

	// 현재 로그인 된 사용자 메뉴 출력
	private void printUserMenu() {
		if (!timerRunnig) { // 타이머 중복 실행 방지 체크
			startTimer();
		}
		System.out.println();
		System.out.println("id : " + nowUserId + " 이름 : " + nowUserName);
		System.out.println("------------");
		System.out.println("|1. 잔액 충전|");
		System.out.println("|2. 상품 주문|");
		System.out.println("|3. 잔액 조회|");
		System.out.println("|4. 사용 종료|");
		System.out.println("------------");
		System.out.print(">>>");
		mod = sc.next();
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
			printUserMenu();
			break;

		}
	}

	// 피시방 사용시간 1시간(5초)당 1200원 차감 > 잔여시간 부족시 종료
	private void startTimer() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				try {
					if (nowUserAccount > 1200) {
						newUser.member.get(nowUserId).setAccount(nowUserAccount -= 1200);
						System.out.println("잔액 : " + nowUserAccount);
					} else {
						System.out.println("잔액이 부족해 사용을 종료 합니다.");
						exit();
						timer.cancel();
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 5000, 5000);
		// 타이머 중복실행 방지
		timerRunnig = true;
	}

	// 현재 로그인 된 사용자 잔액 조회 메소드
	private void checkAccount(int account) {
		System.out.println();
		System.out.println(nowUserId + "님의 잔액은 " + account + "입니다.");
		System.out.println();
		printUserMenu();
	}

	// 상품 주문 메소드
	private void order() {
		System.out.println();
		System.out.println("상품 카테고리 선택");
		System.out.println("1. 음료");
		System.out.println("2. 식사");
		System.out.println("3. 간식");
		System.out.println("4. 결제/종료");
		System.out.print(">>>");
		printProducts();
	}

	// 상품주문 카테고리 호출 메소드
	public void printProducts() {
		while (true) {
			String mod = sc.next();
			switch (mod) {
			case "1":
				getCategory(Drink);
				break;
			case "2":
				getCategory(Meal);
				break;
			case "3":
				getCategory(Snack);
				break;
			case "4":
				checkOut();
				break;
			default:
				System.out.println();
				System.out.println("메뉴 번호를 선택해주세요");
				System.out.println();
				order();
				break;
			}
		}

	}

	// 카테고리 호출 후 상품 선택 메소드(재고수량 비교)
	private void getCategory(Products[] category) {
		int i = 1;
		int pno;
		System.out.println("-----------------------------------------------------");
		for (Products c : category) {
			System.out.println(i + " : " + c.toString());
			i++;
		}
		System.out.println("-----------------------------------------------------");
		try {
			System.out.print("상품 번호를 입력해주세요 -> ");
			pno = sc.nextInt() - 1;
			if (category[pno].getStock() > 0) {
				aList.add(category[pno]); // 선택한 상품을 장바구니에 추가
				category[pno].setStock(category[pno].getStock() - 1); // 재고 현황에 반영
			} else {
				System.out.println();
				System.out.println("해당 상품의 재고가 부족합니다");
				System.out.println();
				printProducts();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println();
			System.out.println("해당 번호의 상품이 없습니다!");
			System.out.println();
		} catch (InputMismatchException e) {
			System.out.println();
			System.out.println("해당 번호의 상품이 없습니다!");
			System.out.println();
		} finally {
			order();
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

			for (Products a : aList) {
				System.out.println("[ 상품명 : " + a.getPname() + " 가격 : " + a.getPrice() + " ] ");
			}
			System.out.println("총 금액 : " + totalP + " 원");
			System.out.print("결제 하시겠습니까 ? (y/n) : ");
			checkYN = sc.next();
			switch (checkYN) {
			case "y":
			case "Y":
				if (nowUserAccount - 1200 < totalP) {
					System.out.println("잔액이 부족합니다");
					aList.removeAll(aList); // 장바구니 비우기
					printUserMenu();
				} else {
					System.out.println();
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
	// case 5 입력시 현재사용자 해쉬맵에 잔액 충전 정보 저장
	private void charging(int account) {
		System.out.println("1. 1000원 충전");
		System.out.println("2. 5000원 충전");
		System.out.println("3. 10000원 충전");
		System.out.println("4. 50000원 충전");
		System.out.println("5. 충전 종료");
		System.out.print(">>>");
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
			newUser.member.get(nowUserId).setAccount(account);
			nowUserAccount = newUser.member.get(nowUserId).getAccount();
			if (nowUserAccount < 1200) {
				System.out.println("사용 가능 잔액이 부족합니다.");
				System.out.println("1. 충전");
				System.out.println("2. 사용 종료");
				System.out.print(">>>");
				String str = sc.next();
				if (str.equals("1")) {
					charging(nowUserAccount);
				} else if (str.equals("2")) {
					logOut();
				} else {
					System.out.println("메뉴의 번호를 입력해주세요!");
				}
			} else {
				System.out.println("충전을 종료합니다");
				printUserMenu();
				break;
			}
		default:
			System.out.println();
			System.out.println("메뉴의 번호를 입력해 주세요!");
			System.out.println();
			break;
		}
	}

	// 로그아웃 메소드
	// 'y' or 'n'을 입력받아 로그아웃 실행
	private void logOut() {
		System.out.println("사용을 종료 하시겠습니까?(y,n)");
		System.out.print(">>>");
		switchYN = sc.next();
		if (switchYN.equals("y") || switchYN.equals("Y")) {
			timerRunnig = false; // 사용자 로그아웃시 timerRunnig = false, timer.cancle() >> 타이머 종료
			timer.cancel();
			runnig = true;
			topPc();
		} else if (switchYN.equals("n") || switchYN.equals("N")) {
			printUserMenu();
		} else {
			System.out.println("y또는 n을 입력해 주세요");
			logOut();
		}
	}

	// admin계정 접속 / 관리자 콘솔 호출
	private void control() { // admin 컨트롤
		String admod;
		System.out.println();
		System.out.println("1. 관리자 로그인");
		System.out.println("2. 종료");
		System.out.print(">>>");
		admod = sc.next();
		if (admod.equals("1")) {
			for (int i = 1; i <= 4; i++) { // 관리자 로그인 시도 3회까지 허용
				if (i == 4) {
					System.out.println("\t-접속 실패-");
					System.out.println("메인 메뉴로 돌아갑니다");
					topPc();
				} else {
					System.out.print("관리자 ID를 입력하세요 : ");
					if (sc.next().equals("admin")) {
						System.out.print("관리자 비밀번호를 입력 하세요 : ");
						if (sc.next().equals("admin")) {
							System.out.println();
							System.out.println("관리자 콘솔 접속 성공");
							System.out.println();
							adminControl.setProducts(Drink, Meal, Snack); // 관리자 콘솔에 현재 상품 현황을 전달
							adminControl.adminMenu(newUser); // 관리자 콘솔에 회원목록을 전달
							control();
						} else {
							System.out.println("관리자 비밀번호가 아닙니다. " + i + "회 오류");
						}
					} else {
						System.out.println("관리자 아이디가 아닙니다. " + i + "회 오류");
					}
				}
			}
		} else if (admod.equals("2")) {
			topPc();
		} else {
			System.out.println();
			System.out.println("메뉴 번호를 입력해 주세요");
			System.out.println();
			control();
		}
	}

}
