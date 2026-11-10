package consulado.rescatado.cancelacion;

public class ObservadorCorreo implements ObservadorCancelacion {
    @Override
    public void notificarCancelacion(String nombreViajero, double montoReembolso) {
        System.out.println("[ObservadorCorreo] Enviando correo a " + nombreViajero + ": su solicitud fue cancelada.");
    }
}