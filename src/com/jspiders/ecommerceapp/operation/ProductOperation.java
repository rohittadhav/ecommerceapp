package com.jspiders.ecommerceapp.operation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jspiders.ecommerceapp.collection.ProductCollection;
import com.jspiders.ecommerceapp.entity.Product;
import com.jspiders.ecommerceapp.entity.User;
import com.jspiders.ecommerceapp.jdbc.ProductJDBC;

public class ProductOperation {

	private static ProductCollection productCollection = new ProductCollection();
	private static ProductJDBC productJDBC = new ProductJDBC();
	private List<Product> cart = new ArrayList<>();
	private List<Integer> quantities = new ArrayList<>();
	private String address;
	private long mobileNo;
	private User user = new User();

	public void addProduct(Scanner scanner) {
		try {
			System.out.println("Enter product id");
			int id = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter product title");
			String title = scanner.nextLine();
			System.out.println("Enter product description");
			String description = scanner.nextLine();
			System.out.println("Enter product price");
			double price = scanner.nextDouble();
			scanner.nextLine();
			System.out.println("Enter product sale status");
			String status = scanner.next();
			boolean sold = false;
			if (status.equals("true")) {
				sold = true;
			}
			Product product = new Product(id, title, description, price, sold);
			List<Product> products = productCollection.getProducts();
			products.add(product);
			// Using JDBC
			productJDBC.addProduct(product);
			System.out.println("Product added");
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine();
		}
	}

	public void findProductById(Scanner scanner) {
		System.out.println("Enter product id:");
		int id = scanner.nextInt();
		productJDBC.findProductById(id);
	}

	public void displayAllProducts() {
		productJDBC.displayAllProducts();
	}

	public void buyProduct(Scanner scanner) {
		boolean shopping = true;
		while (shopping) {
			try {
				System.out.println(
						"************************************************************************************************************************************************************");
				System.out.println("Enter 1 for See all products");
				System.out.println("Enter 2 for Add product to cart");
				System.out.println("Enter 3 for Buy the product");
				System.out.println("Enter 4 for Exit from the shopping");
				System.out.println(
						"************************************************************************************************************************************************************");

				int choice = scanner.nextInt();
				scanner.nextLine();
				switch (choice) {
				case 1:
					displayAllProducts();
					break;
				case 2:
					addProductToCart(scanner);
					break;
				case 3:
					completePurchase(scanner);
					break;
				case 4:
					shopping = false;
					System.err.println("Exiting from purchase..🤝");
					break;
				default:
					System.out.println("Invalid choice! Enter a vaild choice🤦‍♂️");
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again.🤦‍♂️");
				scanner.nextLine();
			}
		}
	}

	public void addProductToCart(Scanner scanner) {
		try {
			System.out.println("Enter the product ID to add to cart:");
			int productId = scanner.nextInt();
			scanner.nextLine();
			Product product = findProductById(productId);
			if (product != null) {
				if (!product.isSold()) {
					System.out.println("Enter the quantity:");
					int quantity = scanner.nextInt();
					cart.add(product);
					quantities.add(quantity);
					System.out.println("Product added to cart👌");
				} else {
					System.out.println("Product is currently not available🥲");
				}
			} else {
				System.out.println("Product not found");
			}
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine();
		}
	}

	public Product findProductById(int productId) {
		return productJDBC.findProductById(productId);
	}

	public void completePurchase(Scanner scanner) {
		if (cart.isEmpty()) {
			System.out.println("Your cart is empty");
		} else {
			try {
				if (user == null) {
					System.out.println("No user is currently logged in. Please log in before making a purchase.");
					return;
				}

				System.out.println("Enter your address:");
				address = scanner.nextLine();

				System.out.println("Enter and Confirm your mobile number:");
				while (!scanner.hasNextLong()) {
					System.out.println("Invalid input. Please enter a valid mobile number:");
					scanner.next();
				}
				mobileNo = scanner.nextLong();
				scanner.nextLine();

				boolean paymentOption = false;
				String paymentMethod = "";
				while (!paymentOption) {
					try {
						System.out.println("Select payment method by entering the number:");
						System.out.println("1. Net Banking");
						System.out.println("2. UPI");
						System.out.println("3. Wallet");
						System.out.println("4. Credit Card");
						System.out.println("5. COD (Cash on Delivery)");

						int paymentChoice = scanner.nextInt();
						scanner.nextLine();

						switch (paymentChoice) {
						case 1:
							paymentMethod = "Net Banking";
							paymentOption = true;
							break;
						case 2:
							paymentMethod = "UPI";
							paymentOption = true;
							break;
						case 3:
							paymentMethod = "Wallet";
							paymentOption = true;
							break;
						case 4:
							paymentMethod = "Credit Card";
							paymentOption = true;
							break;
						case 5:
							paymentMethod = "COD (Cash on Delivery)";
							paymentOption = true;
							break;
						default:
							System.out.println("Invalid Choice! Please select a valid payment method.");
//					return;
						}
					} catch (Exception e) {
						System.out.println("Invalid input. Please try again.");
						scanner.nextLine();
					}
				}

				System.out.println("See your product details in your cart:=>");
				for (int i = 0; i < cart.size(); i++) {
					Product product = cart.get(i);
					int quantity = quantities.get(i);
					double totalPrice = product.getPrice() * quantity;
					LocalDate orderDate = LocalDate.now();
					LocalDate deliveryDate = orderDate.plusDays(5);
					int pId = product.getId();
					boolean status = product.isSold();
//					int userId = user.getId();

					System.out.println(
							"---------------------------------------------------------------------------------------------------------------------------------");
					System.out.println("Product ID: " + pId);
					System.out.println("Product Title: " + product.getTitle());
					System.out.println("Product Description: " + product.getDescription());
					System.out.println("Quantity: " + quantity);
					System.out.println("Total Price: " + totalPrice);
					System.out.println("Address: " + address);
					System.out.println("Mobile No: " + mobileNo);
					System.out.println("Expected Delivery Date: " + deliveryDate);
					System.out.println("Payment Method: " + paymentMethod);
					System.out.println(
							"---------------------------------------------------------------------------------------------------------------------------------");

					productJDBC.completePurchase(status, pId);
					productJDBC.orderDetails(pId, quantity, totalPrice, address, mobileNo, paymentMethod,
							orderDate, deliveryDate);
					System.err.println("Order Confirmed. Thank You!");
				}
				cart.clear();
				quantities.clear();
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again.🤦‍♂️");
				e.printStackTrace();
				scanner.nextLine();
			}
		}
	}

	public void removeProduct(Scanner scanner) {
		try {
			System.out.println("Enter the product ID to remove:");
			int productId = scanner.nextInt();
			scanner.nextLine();
			Product product = findProductById(productId);
			if (product != null) {
				productJDBC.removeProduct(productId);
			} else {
				System.out.println("Product is not found");
			}
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine();
		}
	}

	public void updateProduct(Scanner scanner) {
		try {
			System.out.println("Enter the product ID to update:");
			int productId = scanner.nextInt();
			Product product = findProductById(productId);
			if (product != null) {
				boolean update = true;

				while (update) {
					System.out.println("Enter the choice of the field you want to update:");
					System.out.println("Enter 1 for Title");
					System.out.println("Enter 2 for Description");
					System.out.println("Enter 3 for Price");
					System.out.println("Enter 4 for Sale Status");
					System.out.println("Enter 5 for Exit");

					int choice = scanner.nextInt();
					scanner.nextLine();

					String newValue = "";
					double newPrice = 0;
					boolean newSaleStatus = false;
					String column = "";

					switch (choice) {
					case 1:
						System.out.println("Enter new product title:");
						newValue = scanner.nextLine();
						product.setTitle(newValue);
						column = "title";
						break;
					case 2:
						System.out.println("Enter new product description:");
						newValue = scanner.nextLine();
						product.setDescription(newValue);
						column = "description";
						break;
					case 3:
						System.out.println("Enter new product price:");
						newPrice = scanner.nextDouble();
						scanner.nextLine();
						product.setPrice(newPrice);
						column = "price";
						break;
					case 4:
						System.out.println("Enter new product sale status (true/false):");
						newSaleStatus = scanner.nextBoolean();
						scanner.nextLine();
						product.setSold(newSaleStatus);
						break;
					case 5:
						update = false;
						System.err.println("Exiting update process.");
						break;
					default:
						System.out.println("Invalid choice! Please enter a valid option.");
					}

					if (column.equals("price")) {
						productJDBC.updateProduct(productId, column, newPrice);
					} else if (column.equals("sold")) {
						productJDBC.updateProduct(productId, column, newSaleStatus);
					} else {
						productJDBC.updateProduct(productId, column, newValue);
					}
				}
			} else {
				System.out.println("Product not found");
			}
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine();
		}

	}

}
