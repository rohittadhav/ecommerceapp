package com.jspiders.ecommerceapp.main;

import java.util.Scanner;

import com.jspiders.ecommerceapp.entity.User;
import com.jspiders.ecommerceapp.operation.ProductOperation;

public class ProductMain {
	private static ProductOperation productOperation = new ProductOperation();

	public static void main(User user, Scanner scanner) {
		// Scanner scanner = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println(
					"<==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==>");
			System.out.println(
					"Enter 1 to Add product \nEnter 2 to Find Product By Id \nEnter 3 Display all products \nEnter 4 for Update product details \nEnter 5 for Remove product \nEnter 6 to Exit");
			System.out.println(
					"<==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==>");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				if (user.getRole().equalsIgnoreCase("Seller")) {
					productOperation.addProduct(scanner);
				} else {
					System.out.println("Welcome to Shopping😀");
					productOperation.buyProduct(scanner);
				}
				break;
			case 2:
				productOperation.findProductById(scanner);
				break;
			case 3:
				productOperation.displayAllProducts();
				break;
			case 4:
				if (user.getRole().equalsIgnoreCase("SELLER")) {
					productOperation.updateProduct(scanner);
				} else {
					System.err.println("Unauthorized access");
				}
				break;
			case 5:
				if (user.getRole().equalsIgnoreCase("SELLER")) {
					productOperation.removeProduct(scanner);
				} else {
					System.err.println("Unauthorized access");
				}
				break;
			case 6:
				flag = false;
				System.err.println("Thank you🤝");
				break;
			default:
				System.out.println("Invalid choice🤦‍♂️");
			}
		}
	}

}
