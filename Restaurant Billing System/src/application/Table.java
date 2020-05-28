package application;

public class Table {
	
	private int number;
	private boolean empty;
	private Order order;
	
	public Table(int number) {
		this.number = number;
		this.empty = true;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public boolean isEmpty() {
		return this.empty;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Order getOrder() {
		if(this.order == null)
			return new Order(Restaurant.getRestaurant().getSelectedTable());
		return this.order;
	}
	public int getNumber() {
		return number;
	}
	public void cancelOrder() {
		this.order = null;
	}
}
