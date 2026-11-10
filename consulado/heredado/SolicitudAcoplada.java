package consulado.heredado;

public class SolicitudAcoplada {
    private String estado = "ACTIVA";
    private CorreoServicio correo = new CorreoServicio();
    private CitasServicio citas = new CitasServicio();
    private PagosServicio pagos = new PagosServicio();

    public void cancelar() {
        estado = "CANCELADA";
        correo.avisarCancelacion();
        citas.liberarCupo();
        // Síntoma del antipatrón: se olvidaron de llamar a pagos.reembolsar()
    }

    public String getEstado() {
        return estado;
    }
}