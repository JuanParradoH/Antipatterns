package consulado.rescatado.cancelacion;

public interface ObservadorCancelacion {
    void notificarCancelacion(String nombreViajero, double montoReembolso);
}