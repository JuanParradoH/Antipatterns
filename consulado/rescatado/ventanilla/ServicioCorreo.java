package consulado.rescatado.ventanilla;

public class ServicioCorreo {
    public void enviarConfirmacionTramite(String nombreViajero) {
        System.out.println("[ServicioCorreo] Correo para " + nombreViajero + ": su visa va en camino");
    }
}