package application;

public class Item {

	private String name ;
	private int price;
	private int servings;
	public Item(String name , int price) {
		this.name = name;
		this.price = price;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	public int getServings() {
		
		return this.servings;
	}
	public String getName() {
		return this.name;
	}
	public Integer getPrice() {
		return this.price;
	}
}
