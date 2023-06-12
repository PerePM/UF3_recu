package restaurants;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    /*public static void main(String[] args) {
        String[] dishes = {"Spaguetti", "Llom", "Verdura", "Rap"};
        double unitPrice = 3.5;

        Menu menu = new Menu(dishes, unitPrice);
        Restaurant restOne = new Restaurant("El raco de les brases", menu);

        System.out.println("Preus del: " + restOne.getNameRestaurant());
        System.out.println("El menu del dia es: " + restOne.getMenu());
        System.out.println("Si agafem un plat, ens costa: " + restOne.costMenu(1));
        System.out.println("Si agafem dos plats, ens costa: " + restOne.costMenu(2));
        System.out.println("Si agafem tres plats, ens costa: " + restOne.costMenu(3));
    }*/
    public static void main(String[] args) {
        // Cadenes de connexió a la base de dades MongoDB
        String connectionString = "mongodb://localhost:27017";
        String databaseName = "restaurantdb";
        String collectionName = "restaurants";

        // Instanciar el MongoClient i la MongoDatabase
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Obtenir la col·lecció de restaurants
            MongoCollection<Document> collection = database.getCollection(collectionName);
            
            if(queFer().equalsIgnoreCase("v")) {
            	// Demanar el nom del barri a l'usuari
                String barri = getBarriUsuari();
                // Cridar al mètode llistarRestaurants amb el barri proporcionat
                listarRestaurants(collection, barri);
            }else {

            	System.out.println("Per inserir el restaurant cal saber...");
            	System.out.println("-Nom:");
            	String nomResIns=reader.readLine();
            	System.out.println("-Tipus de cuina:");
            	String tipusCuinaIns=reader.readLine();
            	System.out.println("-Barri:");
            	String barriIns=reader.readLine();
            	inserirRestaurant(collection, nomResIns, tipusCuinaIns, barriIns);
            }
            
        } catch (IOException e) {
            System.out.println("Error en llegir l'entrada de l'usuari: " + e.getMessage());
        }
    }
    
    public static void listarRestaurants(MongoCollection<Document> collection, String barriOrigen) {

        // Realitzar una consulta amb el filtre
        FindIterable<Document> iterable;
        
        if(barriOrigen.equalsIgnoreCase("tot")) {
        	iterable = collection.find();
        }else {
        	// Crear un filtre per buscar restaurants amb el barri especificat
            Document filter = new Document("borough", barriOrigen);
        	iterable = collection.find(filter);
        }

        // Recórrer els documents i mostrar les dades, llistant nom, tipus de cuina i barri
        for (Document document : iterable) {
            String nomRestaurant = document.getString("name");
            if (nomRestaurant != null && !nomRestaurant.isEmpty()) {
            	String tipusCuina = document.getString("cuisine");
            	String barri = document.getString("borough");

            	System.out.println("Nom del restaurant: " + nomRestaurant);
            	System.out.println("Tipus de cuina: " + tipusCuina);
            	System.out.println("Barri: " + barri);
            	System.out.println();
            }
        }
    }
    public static String getBarriUsuari() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Introdueix el nom del barri (Escriu 'tot' si els vols veure tots): ");
        return reader.readLine();
    }
    public static String queFer() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true) {
        	System.out.println("Vols veure restaurants o inserir-ne'n? (v per veure, i per inserir)");
        	String resposta=reader.readLine();
        	if(resposta.equalsIgnoreCase("v")||resposta.equalsIgnoreCase("i"))
        		return resposta;
        }
    }
    public static void inserirRestaurant(MongoCollection<Document> collection, String nomRestaurant, String tipusCuina, String barri) {
    	Document document = new Document("name", nomRestaurant)
            	.append("cuisine", tipusCuina)
            	.append("borough", barri);

    	collection.insertOne(document);
    	System.out.println("Restaurant inserit amb èxit!");
}


}