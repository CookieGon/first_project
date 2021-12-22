package product;

public class Products {
	private String category;
	private String pname;
	private int price;
	private int stock;
	
	
	@Override
	public String toString() {
		return "상품 [카테고리=" + category + ", 상품=" + pname + ", 가격=" + price + ", 재고=" + stock + "]";
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Products(String category, String pname, int price, int stock) {
		this.category = category;
		this.pname = pname;
		this.price = price;
		this.stock = stock;
	}
	
}
