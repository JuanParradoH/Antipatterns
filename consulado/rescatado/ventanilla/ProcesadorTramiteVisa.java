package consulado.rescatado.ventanilla;

public class ProcesadorTramiteVisa {
    private final ValidadorPasaporte validadorPasaporte;
    private final ServicioCobro servicioCobro;
    private final RepositorioExpedientes repositorioExpedientes;
    private final ServicioCorreo servicioCorreo;

    public ProcesadorTramiteVisa(
            ValidadorPasaporte validadorPasaporte,
            ServicioCobro servicioCobro,
            RepositorioExpedientes repositorioExpedientes,
            ServicioCorreo servicioCorreo) {
        this.validadorPasaporte = validadorPasaporte;
        this.servicioCobro = servicioCobro;
        this.repositorioExpedientes = repositorioExpedientes;
        this.servicioCorreo = servicioCorreo;
    }

    public boolean procesarTramite(String nombreViajero, String pasaporte, double precio) {
        if (!validadorPasaporte.esValido(pasaporte)) {
            return false;
        }
        servicioCobro.cobrar(nombreViajero, precio);
        repositorioExpedientes.guardarExpediente(nombreViajero, pasaporte);
        servicioCorreo.enviarConfirmacionTramite(nombreViajero);
        return true;
    }
}