package consulado.rescatado.cancelacion;

public class ObservadorCitas implements ObservadorCancelacion {
    @Override
    public void notificarCancelacion(String nombreViajero, double montoReembolso) {
        System.out.println("[ObservadorCitas] Liberando cupo en la agenda de citas de " + nombreViajero + ".");
    }
}