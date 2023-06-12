package restaurants;

import java.util.Arrays;

public class Menu {
	//Imagino que tot ha de ser en anglès com a l'anunciat
	//Update: Sí. 
	//La bd està en anglès 
    private String dishes[];
    private double unitPrize;

    public Menu(String[] dishes, double unitPrize) {
        this.dishes = dishes;
        this.unitPrize = unitPrize;
    }

    public double costMenu(int numDishes) {
        return numDishes * unitPrize;
    }

    //hqmel
    @Override
    public String toString() {
        return "Menu [dishes=" + Arrays.toString(dishes) + ", unitPrize=" + unitPrize + "]";
    }
}
