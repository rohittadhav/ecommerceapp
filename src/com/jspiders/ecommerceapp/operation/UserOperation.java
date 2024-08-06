package com.jspiders.ecommerceapp.operation;

import java.util.List;
import java.util.Scanner;

import com.jspiders.ecommerceapp.collection.UserCollection;
import com.jspiders.ecommerceapp.entity.User;
import com.jspiders.ecommerceapp.jdbc.UserJDBC;
import com.jspiders.ecommerceapp.main.ProductMain;

public class UserOperation {

	private static UserCollection userCollection = new UserCollection();
	private static UserJDBC userJDBC = new UserJDBC();

	public void signUp(Scanner scanner) {

		System.out.println("Enter user id");
		int id = 0;
		try {
			id = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid type of id‍");
		}
		scanner.nextLine();
		System.out.println("Enter user name");
		String name = scanner.nextLine();
		System.out.println("Enter user email");
		String email = scanner.nextLine();
		System.out.println("Enter user mobile number");
		long mobile = scanner.nextLong();
		scanner.nextLine();
		System.out.println("Enter user password");
		String password = scanner.nextLine();
		System.out.println("Enter user role");
		System.out.println("Enter 1 for SELLER \nEnter 2 for BUYER");
		int choice = scanner.nextInt();
		String role = null;
		switch (choice) {
		case 1:
			role = "SELLER";
			break;
		case 2:
			role = "BUYER";
			break;
		default:
			System.out.println("Invalid choice");
		}
		User user = new User(id, name, email, mobile, password, role);
		List<User> users = userCollection.getUsers();
		users.add(user);
		// Using JDBC
		userJDBC.addUser(user);
		System.out.println("Signed up successfully!");

	}

	public void findUserById(Scanner scanner) {
		System.out.println("Enter user id");
		int id = scanner.nextInt();
		userJDBC.findUserById(id);
	}

	public void displayAllUsers() {
		userJDBC.displayAllUsers();
	}

	public void logIn(Scanner scanner) {
		scanner.nextLine();
		System.out.println("Enter user email");
		String email = scanner.nextLine();
		System.out.println("Enter user password");
		String password = scanner.nextLine();
		User user = userJDBC.logIn(email, password);
		if (user != null) {
			ProductMain.main(user, scanner);
		} else {
			System.out.println("Login failed. Please check your credentials and try again.");
		}

	}

	public void deleteUser(Scanner scanner) {
		System.out.println("Enter the user id");
		int id = scanner.nextInt();
		userJDBC.deleteUser(id);
	}

	public void updateUser(Scanner scanner) {
		System.out.println("Enter the user id");
		int id = scanner.nextInt();
		User user = userJDBC.findUserById(id);
		if (user == null) {
			System.out.println("User is not found");
			return;
		}
		System.out.println("User is found. Choose the field you want to update:");
		boolean update = true;
		while (update) {
			System.out.println(
					"************************************************************************************************************************************************************");
			System.out.println("Enter 1 for Name");
			System.out.println("Enter 2 for Email");
			System.out.println("Enter 3 for Mobile");
			System.out.println("Enter 4 for Password");
			System.out.println("Enter 5 for Exit from the update");
			System.out.println(
					"************************************************************************************************************************************************************");
			int choice = scanner.nextInt();
			scanner.nextLine();

			String newValue = "";
			long newMobile = 0;
			String column = "";
			switch (choice) {
			case 1:
				System.out.println("Enter new name");
				newValue = scanner.nextLine();
				user.setName(newValue);
				column = "name";
				break;
			case 2:
				System.out.println("Enter new email");
				newValue = scanner.nextLine();
				user.setEmail(newValue);
				column = "email";
				break;
			case 3:
				System.out.println("Enter new mobile");
				newMobile = scanner.nextLong();
				user.setMobile(newMobile);
				column = "mobile";
				scanner.nextLine();
				break;
			case 4:
				System.out.println("Enter new password");
				newValue = scanner.nextLine();
				user.setPassword(newValue);
				column = "password";
				break;
			case 5:
				update = false;
				System.err.println("User details updated successfully👌");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
			if (column.equals("mobile")) {
				userJDBC.updateUser(id, column, newMobile);
			} else {
				userJDBC.updateUser(id, column, newValue);
			}
		}
	}
}