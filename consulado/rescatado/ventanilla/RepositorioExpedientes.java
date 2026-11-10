package consulado.rescatado.ventanilla;

public class RepositorioExpedientes {
    public void guardarExpediente(String nombreViajero, String pasaporte) {
        System.out.println("[RepositorioExpedientes] Guardando expediente de " + nombreViajero + " (Pasaporte: " + pasaporte + ")");
    }
}