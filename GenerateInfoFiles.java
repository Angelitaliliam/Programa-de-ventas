package Ventas;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Clase para generar archivos de prueba para el proyecto de ventas.
 * Genera:
 *  - productos.txt
 *  - vendedores.txt
 *  - ventas_{nombreVendedor}.txt
 */
public class GenerateInfoFiles {

    private static final String PRODUCTS_FILE = "productos.txt";
    private static final String SELLER_FILE = "vendedores.txt";

    public static void main(String[] args) {

        try {
            int productsCount = 10;
            int salesmanCount = 5;
            int salesPerSalesman = 5;

            String[] salesmanNames = {"Juan", "Camila", "Daniel", "Pedro", "Angela"};
            String[] lastNames = {"Perez", "Gomez", "Rodriguez", "Lopez", "Martinez"};
            String[] documentTypes = {"CC", "CE"};

            Random random = new Random();

            // Crear productos
            createProductsFile(productsCount);

            // Crear vendedores y archivos de ventas
            for (int i = 0; i < salesmanCount; i++) {

                String name = salesmanNames[i];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String docType = documentTypes[random.nextInt(documentTypes.length)];
                long docNumber = 10000000 + random.nextInt(9000000);

                // Guardar info del vendedor en vendedores.txt
                createSalesManInfoFile(int salesmanCount, String salesmanNames, String lastNames, String documentTypes);

                // Crear archivo de ventas para este vendedor
                createSalesMenFile(salesPerSalesman, name, docType, docNumber);
            }

            System.out.println("Archivos creados correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creando archivos");
        }
    }

    /**
     * Genera archivo de productos con ID, nombre y precio.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        String[] products = {
                "Patines","Patineta","Bicicleta","Balon_Baloncesto",
                "Balon_Futbol","Balon_Voleibol","Guantes_Boxeo",
                "Raqueta_Tenis","Morral","Pelota_Yoga"
        };

        int[] price = {500000,1500000,6000000,200000,250000,230000,600000,500000,350000,100000};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (int i = 0; i < productsCount; i++) {
                writer.write((1000 + i) + ";" + products[i] + ";" + price[i]);
                writer.newLine();
            }
        }
    }

    /**
     * Guarda la información de un vendedor en vendedores.txt
     */
 // Crear todos los vendedores en un solo archivo
    public static void createSalesManInfoFile(int salesmanCount, String[] salesmanNames, String[] lastNames, String[] documentTypes) throws IOException {
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SELLER_FILE))) { // NO append
            for (int i = 0; i < salesmanCount; i++) {
                String name = salesmanNames[i];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String docType = documentTypes[random.nextInt(documentTypes.length)];
                long docNumber = 10000000 + random.nextInt(9000000);

                writer.write(docType + ";" + docNumber + ";" + name + ";" + lastName);
                writer.newLine();
        }
    }

    /**
     * Crea archivo de ventas para un vendedor
     */
    public static void createSalesMenFile(int salesPerSalesman, String salesmanName, String docType, long docNumber) throws IOException {
        Random random = new Random();
        String fileName = "ventas_" + salesmanName + ".txt";

        // IDs de productos disponibles
        int[] productIds = new int[10];
        for (int i = 0; i < productIds.length; i++) {
            productIds[i] = 1000 + i;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Primera línea: tipoDocumento;NúmeroDocumento
            writer.write(docType + ";" + docNumber);
            writer.newLine();

            // Generar ventas
            for (int i = 0; i < salesPerSalesman; i++) {
                int index = random.nextInt(productIds.length - i);
                int productId = productIds[index];

                // Intercambiar para evitar repetidos
                int temp = productIds[index];
                productIds[index] = productIds[productIds.length - 1 - i];
                productIds[productIds.length - 1 - i] = temp;

                int quantity = random.nextInt(5) + 1; // Cantidad 1-5

                writer.write(productId + ";" + quantity);
                writer.newLine();
            }
        }
    }
}