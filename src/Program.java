import services.menuServices.MenuService;

public class Program {
    public static void main(String[] args) {
        MenuService menu = new MenuService();
        menu.renderMainMenu();
    }
}
