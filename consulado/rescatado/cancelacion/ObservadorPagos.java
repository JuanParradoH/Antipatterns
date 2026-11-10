package consulado.rescatado.cancelacion;

public class ObservadorPagos implements ObservadorCancelacion {
    @Override
    public void notificarCancelacion(String nombreViajero, double montoReembolso) {
        System.out.println("[ObservadorPagos] Reembolsando automáticamente $" + montoReembolso + " a " + nombreViajero + ".");
    }
}