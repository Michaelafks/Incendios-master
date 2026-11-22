package incendios.utilitario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validacion {

    private static Scanner sc = new Scanner(System.in);

    public static String leerString(String mensaje) {
        String dato;
        do {
            System.out.print(mensaje + " : ");
            dato = sc.nextLine();
        } while (dato == null || dato.trim().isEmpty());
        return dato.trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje + " : ");
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero.");
            }
        }
    }

    public static int leerEnteroPositivo(String mensaje) {
        int dato;
        do {
            dato = leerEntero(mensaje);
            if (dato <= 0) {
                System.out.println("Error: el numero debe ser positivo (mayor que 0).");
            }
        } while (dato <= 0);
        return dato;
    }

    public static int leerEnteroEntre(String mensaje, int min, int max) {
        int dato;
        do {
            dato = leerEntero(mensaje);
            if (dato < min || dato > max) {
                System.out.println("Error: el numero debe estar entre " + min + " y " + max + ".");
            }
        } while (dato < min || dato > max);
        return dato;
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje + " : ");
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero valido.");
            }
        }
    }

    public static double leerDoubleEntre(String mensaje, double min, double max) {
        double dato;
        do {
            dato = leerDouble(mensaje);
            if (dato < min || dato > max) {
                System.out.println("Error: el valor debe estar entre " + min + " y " + max + ".");
            }
        } while (dato < min || dato > max);
        return dato;
    }

    public static double leerDoublePositivo(String mensaje) {
        double dato;
        do {
            dato = leerDouble(mensaje);
            if (dato <= 0) {
                System.out.println("Error: el valor debe ser mayor que 0.");
            }
        } while (dato <= 0);
        return dato;
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (aaaa-mm-dd) : ");
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Error: fecha invalida, use el formato aaaa-mm-dd.");
            }
        }
    }

    public static String leerOpcion(String mensaje, String... opciones) {
        System.out.println(mensaje);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + (i + 1) + ". " + opciones[i]);
        }
        int n = leerEnteroEntre("Elija una opcion", 1, opciones.length);
        return opciones[n - 1];
    }
}
