package restaurants;

public class Restaurant {
    private String nameRestaurant;
    private Menu menu;

    public Restaurant(String nameRestaurant, Menu menu) {
        this.nameRestaurant = nameRestaurant;
        this.menu = menu;
    }

    public double costMenu(int numDishes) {
        return menu.costMenu(numDishes);
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Restaurant [nameRestaurant=" + nameRestaurant + ", menu=" + menu + "]";
    }
}