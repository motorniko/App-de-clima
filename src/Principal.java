import java.util.Scanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre de la ciudad: ");
        String nombreCiudad = scanner.nextLine();
        System.out.println("Buscando ciudad");

        String apiKey = "10b2a7f84745734bbc3aab9110eb2c6f";

        // Construye la URL
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + nombreCiudad +
                "&appid=" + apiKey + "&units=metric&lang=es";

        // Crea cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Construye la petición
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Ejecuta la petición
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // Parsear el JSON con Gson
                JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

                // Extraer descripción del clima
                String clima = json.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();

                // Extraer temperatura
                double temperatura = json.getAsJsonObject("main")
                        .get("temp").getAsDouble();

                System.out.println("Clima en " + nombreCiudad + ": " + clima);
                System.out.println("Temperatura: " + temperatura + "°C");

            } else {
                System.out.println("Error en la respuesta: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error al hacer la petición: " + e.getMessage());
        }

    }
}
