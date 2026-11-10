package consulado.heredado;

public class OficinaDeTodo {
    public void procesar(String nombre, String pasaporte, double precio) {
        if (pasaporte == null || pasaporte.length() < 6) {
            System.out.println("[Heredado - Ramiro/OficinaDeTodo] Pasaporte invalido");
            return;
        }
        System.out.println("[Heredado - Ramiro/OficinaDeTodo] Cobrando $" + precio + " a " + nombre);
        System.out.println("[Heredado - Ramiro/OficinaDeTodo] Guardando expediente de " + nombre);
        System.out.println("[Heredado - Ramiro/OficinaDeTodo] Correo para " + nombre + ": su visa va en camino");
    }
}