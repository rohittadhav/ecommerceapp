package com.jspiders.ecommerceapp.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.jspiders.ecommerceapp.entity.Product;
import com.mysql.cj.jdbc.Driver;

public class ProductJDBC {

	private Driver driver;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String query;

	private void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			driver = new com.mysql.cj.jdbc.Driver();
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_app", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (driver != null) {
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addProduct(Product product) {
		openConnection();
		query = "INSERT INTO product VALUES(?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, product.getTitle());
			preparedStatement.setString(3, product.getDescription());
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setBoolean(5, product.isSold());
			int res = preparedStatement.executeUpdate();
			System.out.println(res + " row(s) affected");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public Product findProductById(int id) {
		openConnection();
		Product product = null;
		query = "SELECT * FROM product WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				product = new Product();
				System.out.println("Product found");
				int pId = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String description = resultSet.getString("description");
				double price = resultSet.getDouble("price");
				boolean sold = resultSet.getBoolean("sold");
				System.out.println("Product Id: " + pId);
				System.out.println("Title: " + title);
				System.out.println("Description: " + description);
				System.out.println("Price: " + price);
				System.out.println("Sold status: " + sold);

				product = new Product(pId, title, description, price, sold);
			} else
				System.out.println("Product not found with ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return product;
	}

	public void displayAllProducts() {
		openConnection();
		query = "SELECT * FROM product";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			int count = 0;
			System.out.println("All the products details are as follows: ");
			while (resultSet.next()) {
				count++;
				System.out.println("Id: " + resultSet.getInt("id"));
				System.out.println("Title: " + resultSet.getString("title"));
				System.out.println("Description: " + resultSet.getString("description"));
				System.out.println("Price: " + resultSet.getDouble("price"));
				System.out.println("Sold status: " + resultSet.getBoolean("sold"));
				System.out.println("-------------------------------------------------------");
			}
			if (count == 0) {
				System.out.println("No products found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void completePurchase(boolean status, int id) {
		openConnection();
		try {
			query = "UPDATE product SET sold = ? WHERE id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, status);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void removeProduct(int id) {
		openConnection();
		query = "DELETE FROM product WHERE ID = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int res = preparedStatement.executeUpdate();
			if (res > 0) {
				System.out.println(res + "row(s) affected");
				System.out.println("Product removed with ID: " + id);
			} else {
				System.out.println("Product not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void updateProduct(int id, String column, String newValue) {
		openConnection();
		query = "UPDATE product SET " + column + " = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newValue);
			preparedStatement.setInt(2, id);
			int res = preparedStatement.executeUpdate();
			System.out.println(res + " row(s) affected");
			if (res != 0) {
				System.out.println(column + " updated successfully");
			} else {
				System.out.println("Invalid product id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void updateProduct(int id, String column, double newValue) {
		openConnection();
		query = "UPDATE product SET " + column + " = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, newValue);
			preparedStatement.setInt(2, id);
			int res = preparedStatement.executeUpdate();
			System.out.println(res + " row(s) affected");
			if (res != 0) {
				System.out.println(column + " updated successfully");
			} else {
				System.out.println("Invalid product id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void updateProduct(int id, String column, boolean newValue) {
		openConnection();
		query = "UPDATE product SET " + column + " = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, newValue);
			preparedStatement.setInt(2, id);
			int res = preparedStatement.executeUpdate();
			System.out.println(res + " row(s) affected");
			if (res != 0) {
				System.out.println(column + " updated successfully");
			} else {
				System.out.println("Invalid product id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void orderDetails(int pId, int quantity, double totalPrice, String address, long mobileNo,
			String paymentMethod, LocalDate orderDate, LocalDate deliveryDate) {
		openConnection();
		try {
			query = "INSERT INTO orders (product_id, quantity, total_price, address, mobile_no, payment_method, order_date, delivery_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, pId);
			preparedStatement.setInt(2, quantity);
			preparedStatement.setDouble(3, totalPrice);
			preparedStatement.setString(4, address);
			preparedStatement.setLong(5, mobileNo);
			preparedStatement.setString(6, paymentMethod);
			preparedStatement.setDate(7, Date.valueOf(orderDate));
	        preparedStatement.setDate(8, Date.valueOf(deliveryDate));
			int res = preparedStatement.executeUpdate();
			System.out.println(res + " row(s) affected");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		
	}
}
