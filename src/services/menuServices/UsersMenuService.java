package services.menuServices;

import models.User;
import repositories.UsersRepository;
import services.AuthenticationService;

import java.util.Scanner;

public class UsersMenuService {
    private static Scanner sc;

    public static void renderUserRegister() {
        System.out.println("Register user");

        sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        UsersRepository usersRepo = new UsersRepository();

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        if (!usersRepo.checkIfUserExists(u)) {
            usersRepo.save(u);
            System.out.println("Successfully registered.");
            System.out.println();

            renderUserLogin();
        } else {
            System.out.println("Username is already taken.");
            System.out.println();

            MenuService menuService = new MenuService();
            menuService.renderMainMenu();
        }
    }

    public static void renderUserLogin() {
        System.out.println("Login user");

        sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        AuthenticationService.authenticate(username, password);

        if (AuthenticationService.getLoggedUser() == null) {
            System.out.println("Invalid username or password.");

            MenuService menuService = new MenuService();
            menuService.renderMainMenu();
        } else {
            System.out.println("Login successful.");
            TasksMenuService.renderTaskMenu();
        }
    }

    public static void renderUserLogout() {
        AuthenticationService.logout();
        System.out.println("Logout successful.");

        MenuService menuService = new MenuService();
        menuService.renderMainMenu();
    }

    public static void renderUserUpdating() {
        UsersRepository usersRepo = new UsersRepository();
        User user = usersRepo.getById(AuthenticationService.getLoggedUser().getId());

        System.out.println("Update your personal data");

        sc = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String password = sc.nextLine();

        user.setPassword(password);
        usersRepo.save(user);

        System.out.println("Personal data was successfully updated.");
    }
}
