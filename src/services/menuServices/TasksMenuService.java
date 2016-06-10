package services.menuServices;

import enums.TaskStatusEnum;
import models.Task;
import repositories.TasksRepository;
import services.AuthenticationService;

import java.util.ArrayList;
import java.util.Scanner;

public class TasksMenuService {
    private static Scanner sc;

    public static void renderTaskMenu() {
        System.out.println();
        System.out.println("Choose option: ");
        System.out.println("1. View your tasks");
        System.out.println("2. View task details");
        System.out.println("3. Add new task");
        System.out.println("4. Update task");
        System.out.println("5. Delete task");
        System.out.println();
        System.out.println("6. Update your personal data");
        System.out.println("7. Logout");
        System.out.println("0. Exit");

        sc = new Scanner(System.in);
        int taskOption = Integer.parseInt(sc.nextLine());

        switch (taskOption) {
            case 1:
                renderTaskListing();
                renderTaskMenu();
                break;

            case 2:
                renderTaskListing();
                renderTaskDetails();
                renderTaskMenu();
                break;

            case 3:
                renderTaskInserting();
                renderTaskMenu();
                break;

            case 4:
                renderTaskListing();
                renderTaskUpdating();
                renderTaskMenu();
                break;

            case 5:
                renderTaskListing();
                renderTaskDeleting();
                renderTaskMenu();
                break;

            case 6:
                UsersMenuService.renderUserUpdating();
                renderTaskMenu();
                break;

            case 7:
                UsersMenuService.renderUserLogout();
                break;

            case 0:
                System.exit(0);
                break;

            default:
                renderTaskMenu();
                break;
        }
    }

    private static void renderTaskListing() {
        TasksRepository tasksRepo = new TasksRepository();
        ArrayList<Task> tasks = tasksRepo.getByUserId(AuthenticationService.getLoggedUser().getId());

        if (tasks.size() == 0) {
            System.out.println("No tasks to show.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task.getId());
            System.out.println(task.getTitle());
            System.out.println(task.getContent());
            System.out.println(task.getStatus());
        }

        System.out.println("------------------------------");
    }

    private static void renderTaskDetails() {
        System.out.print("Enter task number which details you want to see: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository();
        Task task = tasksRepo.getById(taskId);

        if (task != null) {
            if (task.getUser().getId() == AuthenticationService.getLoggedUser().getId()) {
                System.out.println("Number in task list: " + task.getId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Content: " + task.getContent());
                System.out.println("Status: " + task.getStatus());
            } else {
                System.out.println("Task does not exists.");
            }
        } else {
            System.out.println("Task does not exists.");
        }

        System.out.println("------------------------------");
    }

    private static void renderTaskInserting() {
        TasksRepository tasksRepo = new TasksRepository();
        Task task = new Task();

        sc = new Scanner(System.in);
        System.out.print("Enter title: ");
        task.setTitle(sc.nextLine());

        System.out.print("Enter content: ");
        task.setContent(sc.nextLine());

        task.setUser(AuthenticationService.getLoggedUser());
        task.setStatus(TaskStatusEnum.UnDone);

        tasksRepo.save(task);

        System.out.println("Successfully added.");
        System.out.println("------------------------------");
    }

    private static void renderTaskUpdating() {
        System.out.println("Set task number you want to edit: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository();
        Task updatedTask = tasksRepo.getById(taskId);

        if (updatedTask != null) {
            if (updatedTask.getUser().getId() == AuthenticationService.getLoggedUser().getId()) {
                System.out.println("Title: " + updatedTask.getTitle());
                System.out.println("Content: " + updatedTask.getContent());
                System.out.println("Status: " + updatedTask.getStatus());
                System.out.println("-------------------------");
                System.out.println();

                System.out.println("Choose what you want to update: ");
                System.out.println("1. Title");
                System.out.println("2. Content");
                System.out.println("3. Status");

                sc = new Scanner(System.in);
                int updatingOption = Integer.parseInt(sc.nextLine());

                switch (updatingOption) {
                    case 1:
                        System.out.print("Enter title: ");
                        updatedTask.setTitle(sc.nextLine());
                        break;

                    case 2:
                        System.out.print("Enter content: ");
                        updatedTask.setContent(sc.nextLine());
                        break;

                    case 3:
                        System.out.println("Choose status: ");
                        System.out.println("1. UnDone");
                        System.out.println("2. InProgress");
                        System.out.println("3. Done");

                        int status = Integer.parseInt(sc.nextLine());
                        switch (status) {
                            case 1: updatedTask.setStatus(TaskStatusEnum.UnDone); break;
                            case 2: updatedTask.setStatus(TaskStatusEnum.InProgress); break;
                            case 3: updatedTask.setStatus(TaskStatusEnum.Done); break;
                        }
                        break;
                }

                tasksRepo.save(updatedTask);
                System.out.println("Successfully updated");
            } else {
                System.out.println("Task does not exists.");
            }
        } else {
            System.out.println("Task does not exists.");
        }

        System.out.println("------------------------------");
    }

    private static void renderTaskDeleting() {
        System.out.println("Set task number you want to delete: ");
        int taskId = Integer.parseInt(sc.nextLine());

        TasksRepository tasksRepo = new TasksRepository();
        Task deletedTask = tasksRepo.getById(taskId);

        if (deletedTask != null) {
            if (deletedTask.getUser().getId() == AuthenticationService.getLoggedUser().getId()) {
                tasksRepo.delete(taskId);
                System.out.println("Successfully deleted");
            } else {
                System.out.println("Task does not exists.");
            }
        } else {
            System.out.println("Task does not exists.");
        }

        System.out.println("------------------------------");
    }
}