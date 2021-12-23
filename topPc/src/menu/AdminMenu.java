package menu;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import account.Management;
import account.Member;
import product.Products;

public class AdminMenu {
	Scanner sc = new Scanner(System.in);
	Management newUser = new Management();
	private String mod;
	private int mod2;
	private String delete;
	private boolean adMenu;
	private String inputId;

	ArrayList<Products> pList = new ArrayList<>();

	public void adminMenu(Management inputUser) {
		adMenu = true;
		this.newUser = inputUser;
		while (adMenu) {
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
			default:
				System.out.println();
				System.out.println("메뉴의 번호를 입력해주세요");
				System.out.println();

			}

		}
	}

	private void manageProduct() {
		System.out.println();
		int count = 1;
		System.out.println("-----------------------------------------------------");
		for (Products a : pList) {
			System.out.println(count + " : " + a);
			count++;
		}
		System.out.println("-----------------------------------------------------");
	}

	public void manageMember() {
		for (Entry<String, Member> e : newUser.member.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		System.out.println("1. 회원탈퇴");
		System.out.println("2. 종료");
		mod2 = sc.nextInt();
		if (mod2 == 1) {
			System.out.print("삭제할아이디 입력 : ");
			inputId = sc.next();
			if (newUser.member.containsKey(inputId)) {
				System.out.println("삭제하실 아이디는 : " + inputId + " 입니다. 삭제하시겠습니까?(y/n)");
				delete = sc.next();
				switch (delete) {
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
			} else {
				System.out.println("해당 아이디의 사용자가 없습니다");

			}
		} else if (mod2 == 2) {
			System.out.println("회원관리종료");
		} else {
			System.out.println("메뉴의 번호를 선택해주세요");
			manageMember();
		}
	}

	public void setProducts(Products[] drink, Products[] meal, Products[] snack) {
		for (int i = 0; i < drink.length; i++) {
			pList.add(drink[i]);
		}
		for (int i = 0; i < meal.length; i++) {
			pList.add(meal[i]);
		}
		for (int i = 0; i < snack.length; i++) {
			pList.add(snack[i]);
		}
	}
}
