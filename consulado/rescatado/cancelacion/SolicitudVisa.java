package consulado.rescatado.cancelacion;

import java.util.ArrayList;
import java.util.List;

public class SolicitudVisa {
    private final String nombreViajero;
    private final double montoPagado;
    private EstadoSolicitud estado;
    private final List<ObservadorCancelacion> observadores;

    public SolicitudVisa(String nombreViajero, double montoPagado) {
        this.nombreViajero = nombreViajero;
        this.montoPagado = montoPagado;
        this.estado = EstadoSolicitud.ACTIVA;
        this.observadores = new ArrayList<>();
    }

    public void suscribir(ObservadorCancelacion observador) {
        observadores.add(observador);
    }

    public void desuscribir(ObservadorCancelacion observador) {
        observadores.remove(observador);
    }

    public void cancelar() {
        this.estado = EstadoSolicitud.CANCELADA;
        System.out.println("[SolicitudVisa] Estado cambiado a " + estado + ". Notificando a " + observadores.size() + " observadores suscritos...");
        for (ObservadorCancelacion observador : observadores) {
            observador.notificarCancelacion(nombreViajero, montoPagado);
        }
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }
}