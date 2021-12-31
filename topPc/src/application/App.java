package application;

import menu.Menu;

public class App {

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.setProduct();	// 매점 상품 세팅
		menu.setDefaultUser();	// 기존 가입된 회원 세팅
		menu.printLogo();	// TOP PC 로고 프린트
		menu.topPc();	// 프로그램 실행
	}

}


