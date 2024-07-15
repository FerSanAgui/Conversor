package principal;

import service.ConsumoApi;
import service.ConvierteDatos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        ConsumoApi consumoApi = new ConsumoApi();
        ConvierteDatos conversor = new ConvierteDatos();
        Scanner teclado = new Scanner(System.in);

        String menu = "*********************************" +
                "Bienvendio al Conversor de Monedas\n" +
                "\n" +
                "Listado de Monedas disponibles\n"+
                "[USD] - Dolar Estadunidense\n"+
                "[ARS] - Peso Argentino\n"+
                "[COP] - Peso Colombiano\n"+
                "[BRL] - Real Brasileno\n"+
                "1 - > [USD] -> [ARS]\n"+
                "2 - > [ARS] -> [USD]\n"+
                "3 - > [USD] -> [COP]\n"+
                "4 - > [COP] -> [USD]\n"+
                "5 - > [USD] -> [BRL]\n"+
                "6 - > [BRL] -> [USD]\n"+
                "7 - > Salir\n"+
                "\n"+
                "Elija una opcion \n" +
                "*********************************";
        String solicitudAConvertir = "Ingresa el valor que deseas convertir";

        while (true) {
            try {
                System.out.println(menu);
                var solicitudUsuario = teclado.nextInt();
                if (solicitudUsuario == 7) {
                    break;
                }
                if (solicitudUsuario <= 0) {
                    System.out.println("Opcion invalida, ejecute nuevamente");
                    break;
                }
                if (solicitudUsuario >= 8) {
                    System.out.println("Opcion invalidad, ejecutar nuevamente");
                    break;
                }

                System.out.println(solicitudAConvertir);
                var montoUsuario = teclado.nextDouble();

                String urlBase = "https://v6.exchangerate-api.com/v6/";
                String apiKey = "cd33ec3066526641478fd2c2";
                String urlRespuesta = "/pair/";
                String monedaBase = "";
                String monedaDestino = "";

                switch (solicitudUsuario) {
                    case 1:
                        monedaBase = "USD";
                        monedaDestino = "ARS";
                        break;
                    case 2:
                        monedaBase = "ARS";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        monedaBase = "USD";
                        monedaDestino = "COP";
                        break;
                    case 4:
                        monedaBase = "COP";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        monedaBase = "USD";
                        monedaDestino = "BRL";
                        break;
                    case 6:
                        monedaBase = "BRL";
                        monedaDestino = "USD";
                        break;

                }

                URI direccion = URI.create(urlBase + apiKey + urlRespuesta + monedaBase + "/" + monedaDestino + "/" + montoUsuario);

                String json = consumoApi.obtenerDatosApi(direccion);
                var conversion = conversor.convierteDatos(json);
                BigDecimal resultado = BigDecimal.valueOf(montoUsuario * conversion.conversion_rate());
                System.out.println("El valor de: $" + montoUsuario + "["
                        + monedaBase + "] corresponde al valor final de =>>> $"
                        + resultado.setScale(2, RoundingMode.HALF_UP) + "[" + monedaDestino + "].");
                var comprobacionResultado = conversion.conversion_result();
            } catch (InputMismatchException e){
                System.out.println("Acepta solo valores numericos, ejecuta de nuevo porfavor");
                break;
            }
        }
    }
}