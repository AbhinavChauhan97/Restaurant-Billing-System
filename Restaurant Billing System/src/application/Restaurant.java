package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;

public class Restaurant {

	private static Restaurant restaurant;
	private List<Item> ourItems;
	private List<Table> availableTables;
	private int selectedTable;
	
	private Restaurant() {
		System.out.println("restuarnt created by" + Thread.currentThread().getName());
		availableTables = new ArrayList<>(9);
		for(int i = 1 ; i <= 9 ; ++i) {
	    availableTables.add(new Table(i));
		}
		ourItems = Database.pullItems();
	}
	
	public static Restaurant getRestaurant() {
		if(restaurant == null)
			restaurant = new Restaurant();
		return restaurant;
	}
	public void setSelectedTable(int table) {
		this.selectedTable = table;
	}
	public int getSelectedTable() {
		return this.selectedTable;
	}
	public Table getTable(int tableNumber) {
		if(tableNumber > 9 || tableNumber < 1) {
			throw new IllegalArgumentException("table number is incorrect pass a number 1 - 9 to getSelectedTable(int)");
		}
		else {
		return availableTables.get(tableNumber - 1);
	  }
	}
	public ArrayList<String> getAvailableItemNames() {
		
		ArrayList<String> itemNames = new ArrayList<>(ourItems.size());
		ourItems.forEach(item -> itemNames.add(item.getName()));
		return itemNames;
	}
	public List<String> getTableNumbers(){
		if(availableTables == null) {
			throw new IllegalStateException("available table list is empty assign Restaurant.getRestaurant().getAvailableTables() to it");
		}
		List<String> tableNumbers = new LinkedList<>();
		Restaurant.getRestaurant()
		.getAvailableTables()
		.forEach(table -> tableNumbers.add(String.valueOf(table.getNumber())));
		return tableNumbers;
	}
	
	public List<Table> getAvailableTables(){
		return availableTables;
	}
	
	public void makeTableUnavailable(int tableNumber) {
		availableTables.get(tableNumber).setEmpty(false);
	}
	
	public void makeTableAvailable(int tableNumber) {
		availableTables.get(tableNumber).setEmpty(true);
	}
	
	public List<Item> getAvailableItems() {
		return ourItems;
	}
	
	public List<Order> getHistory(int month,int year){
		return Database.pullOrders(month, year);
	}
	
	public void addItem(Item item) {
		ourItems.add(item);
		Database.addItemToMenu(item);
	}
	
	public void deleteItem(Item item) {
		ourItems.remove(item);
		Database.deleteItemFromMenu(item);
	}
	public List<Order> searchOrder(int id) {
		
		List<Order> searchedOrder = new ArrayList<>(1);
		Order order = Database.searchOrder(id);
		searchedOrder.add(order);
		return searchedOrder;
	}
	
	public void finishOrder() {
		Table table = availableTables.get(selectedTable - 1);
		table.setEmpty(true);
		Database.pushOrder(table.getOrder());
		table.cancelOrder();
	}
}
