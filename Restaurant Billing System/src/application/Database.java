package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Database {

	private static Statement statement;

	/*
	 * static { establishConnection(); }
	 */
	public static void establishConnection() {
		Connection connection;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			DatabaseMetaData metaData = connection.getMetaData();
			if (!metaData.getTables(null, null, "ITEMS", null).next()) {
				statement.executeUpdate("create table items (ITEM_NAME VARCHAR(20) , price INTEGER)");
			}
			if (!metaData.getTables(null, null, "HISTORY", null).next()) {
				statement.executeUpdate("create table history (id INTEGER,name VARCHAR(30),price INTEGER,tableno INTEGER,servings INTEGER,day INTEGER,month INTEGER,year INTEGER)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("connection stablished by " + Thread.currentThread().getName());
	}

	public static LinkedList<Item> pullItems() {
		if (statement == null) {
			throw new IllegalStateException(
					"Database connection is not established call Database.establishConection() before calling any method of Database class");
		}
		LinkedList<Item> allItems = new LinkedList<Item>();
		try {
			ResultSet items = statement.executeQuery(" SELECT * FROM items ");
			while (items.next()) {
				allItems.add(new Item(items.getString(1), items.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Items pulled by" + Thread.currentThread().getName());
		return allItems;
	}

	public static void addItemToMenu(Item item) {
		if (statement == null) {
			throw new IllegalStateException(
					"Database connection is not established call Database.establishConection() before calling any method of Database class");
		}
		try {
			statement.executeUpdate("INSERT INTO items values('" + item.getName() + "','" + item.getPrice() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteItemFromMenu(Item item) {
          
		try {
			statement.executeUpdate("DELETE FROM items WHERE ITEM_NAME = '" + item.getName() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void pushOrder(Order order) {
		if (statement == null) {
			throw new IllegalStateException(
					"Database connection is not established call Database.establishConection() before calling any method of Database class");
		}
		for (int i = 0, size = order.getItemsList().size(); i < size; ++i) {
			Item item = order.getItemsList().get(i);
			try {
				statement.executeUpdate("INSERT INTO HISTORY values('" + order.getId() + "','" + item.getName() 
				+ "','" + item.getPrice() + "','" + order.getTableNumber() 
				+ "','" + item.getServings()
				+ "','" + order.getDay() + "','" + order.getMonth() + "','"
						+ order.getYear() + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static List<Order> pullOrders(int month , int year){
		if (statement == null) {
			throw new IllegalStateException(
					"Database connection is not established call Database.establishConection() before calling any method of Database class");
		}
		List<Order> orders = new LinkedList<>();
		try {
			 ResultSet ordersSet = statement.executeQuery("SELECT * FROM history WHERE month =" + month + "and year =" + year );
			 int id = 0;
			 Order order = null;
			 while(ordersSet.next()) {
				 
				 String itemName = ordersSet.getString(2);
				 int orderId = ordersSet.getInt(1);
				 int itemPrice = ordersSet.getInt(3);
				 int tableNo = ordersSet.getInt(4);
				 int servings = ordersSet.getInt(5);
				 int orderDay = ordersSet.getInt(6);
				 int orderMonth = ordersSet.getInt(7);
				 int orderYear  = ordersSet.getInt(8);
				 boolean isLastRow = ordersSet.isLast();
				 boolean isFirstRow = ordersSet.isFirst();
				 Item item = new Item(itemName,itemPrice);
				 item.setServings(servings);
				 if(isLastRow && isFirstRow ) {
					 order = new Order(tableNo);
					 order.setId(orderId);
					 order.setDay(orderDay);
					 order.setMonth(orderMonth);
					 order.setYear(orderYear);
					 order.setTotal(itemPrice);
					 order.addItem(item);
					 orders.add(order);
				 }
				 else if(isLastRow) {
					 order.addItem(item);
				 }

				 if(id != orderId || isLastRow) {
				 id = orderId;
				 
				 if(id != 0) {
					 orders.add(order);
				 }
				 order = new Order(tableNo);
				 order.setId(orderId);
				 order.setDay(orderDay);
				 order.setMonth(orderMonth);
				 order.setYear(orderYear);
				 
				 }
				// System.out.println("id = " + orderId + "name =" + itemName + "price =" + itemPrice + "table =" + tableNo ); 
				 order.setTotal(order.getTotal() + itemPrice);
				 order.addItem(item); 
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;	
	}
	
	public static Order searchOrder(int id) {
		
		Order order = new Order(1);
		boolean notFound = true;
		try {
		 ResultSet searchedOrder = statement.executeQuery("SELECT * FROM HISTORY WHERE id = " + id);
         
		 while(searchedOrder.next()) {
		 notFound = false;
		 String itemName = searchedOrder.getString(2);
		 int orderId = searchedOrder.getInt(1);
		 int itemPrice = searchedOrder.getInt(3);
		 int tableNo = searchedOrder.getInt(4);
		 int servings = searchedOrder.getInt(5);
		 int orderDay = searchedOrder.getInt(6);
		 int orderMonth = searchedOrder.getInt(7);
		 int orderYear  = searchedOrder.getInt(8);
		 Item item = new Item(itemName,itemPrice);
		 item.setServings(servings);
		 order.setId(orderId);
		 order.setTableNo(tableNo);
		 order.setDay(orderDay);
		 order.setMonth(orderMonth);
		 order.setYear(orderYear);
		 order.addItem(item);
		 order.setTotal(itemPrice);
		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(notFound)
			return null;
		else {
		return order;
		}
	}
	
	
}
