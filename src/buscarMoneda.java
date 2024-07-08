import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import modelo.Moneda;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class buscarMoneda {
    public Moneda buscar(String abreviatura) {
        Moneda moneda = null;
        try {
            String urlStr = "https://v6.exchangerate-api.com/v6/e16bed036c1eed71d7d3433d/latest/USD";

            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement root = JsonParser.parseReader(new InputStreamReader(request.getInputStream()));
            JsonObject jsonObject = root.getAsJsonObject();

            JsonObject jsonInterno = jsonObject.getAsJsonObject("conversion_rates");

            for (String clave : jsonInterno.keySet()) {
                if (clave.equals(abreviatura)) {
                    JsonElement valor = jsonInterno.get(clave);
                    double doubleValor = valor.getAsDouble();
                    moneda = new Moneda(clave, doubleValor);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Ha surgido un error: " + e.getMessage());
        }
        return moneda;
    }
}