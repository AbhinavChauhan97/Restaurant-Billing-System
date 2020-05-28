package application;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.util.Pair;



public class Order {

	private List<Item> itemsList;
	private int id;
	private int day;
	private int month;
	private int year;
	private int tableNumber;
	private int total;
	
	public Order(int tableNumber) {
		itemsList = new LinkedList<>();
		this.tableNumber = tableNumber;
		this.id = 100000000 + new Random().nextInt(899999998);
		this.day = LocalDate.now().getDayOfMonth();
		this.month = LocalDate.now().getMonthValue();
		this.year =  LocalDate.now().getYear();
	}
	public void setTableNo(int tableNo) {
		this.tableNumber = tableNo;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return this.total;
	}
 
    public void addItem(Item item) {
    	this.itemsList.add(item);
    }
    /**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	public String getDate() {
		return this.day + "/" + this.month + "/" + this.year;
	}
	public String getItems() {
		
		StringBuilder builder = new StringBuilder();
		for(Item item : itemsList) {
			builder.append(item.getName());
			builder.append(" - ");
			builder.append(item.getServings());
			builder.append(" , ");
		}
		return  builder.toString();
		
	}
	public void removeItem(Item item) {
    	
    	for(Item i : itemsList) {
    		if(i.getName().equals(item.getName())) {
    			itemsList.remove(item);
    			break;
    		}
    	}
    }
    public void addItems(List<Item> itemsWithPrice) {
    	itemsWithPrice.forEach(item -> itemsList.add(item));
    }
    public List<Item> getItemsList(){
  
    	return this.itemsList;
    }
    public int getTableNumber() {
    	return tableNumber;
    }
}
