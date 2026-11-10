package consulado.rescatado.ventanilla;

public class ValidadorPasaporte {
    private static final int LONGITUD_MINIMA_PASAPORTE = 6;

    public boolean esValido(String pasaporte) {
        boolean valido = pasaporte != null && pasaporte.trim().length() >= LONGITUD_MINIMA_PASAPORTE;
        if (!valido) {
            System.out.println("[ValidadorPasaporte] Error: Pasaporte inválido (mínimo " + LONGITUD_MINIMA_PASAPORTE + " caracteres).");
        } else {
            System.out.println("[ValidadorPasaporte] Pasaporte '" + pasaporte + "' verificado correctamente.");
        }
        return valido;
    }
}