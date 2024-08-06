package com.jspiders.ecommerceapp.main;

import java.util.Scanner;

import com.jspiders.ecommerceapp.entity.User;
import com.jspiders.ecommerceapp.operation.UserOperation;

public class UserMain {

	private static UserOperation userOperation = new UserOperation();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("WELCOME TO E-COMMERCE");
			System.out.println("<==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==>");
			System.out.println("Enter 1 for Sign up \nEnter 2 for Log in \nEnter 3 for Find user by ID \nEnter 4 to Display all users \nEnter 5 for Delete user \nEnter 6 for Update user \nEnter 7 for Exit");
			System.out.println("<==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==><==>");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				userOperation.signUp(scanner);
				break;
			case 2:
				userOperation.logIn(scanner);
				break;
			case 3:
				userOperation.findUserById(scanner);
				break;
			case 4:
				userOperation.displayAllUsers();
				break;
			case 5:
				userOperation.deleteUser(scanner);
				break;
			case 6:
				userOperation.updateUser(scanner);
				break;
			case 7:
				System.out.println("Thank you🤝");
				flag = false;
				break;
			default:
				System.out.println("Invalid choice🤦‍♂️");
			}
		}
		scanner.close();

	}

}