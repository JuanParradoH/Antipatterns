package consulado.rescatado.evaluacion;

public class EvaluadorVisa {

    public EstadoEvaluacion evaluar(TipoVisa tipoVisa, int edad) {
        if (tipoVisa == null) {
            throw new IllegalArgumentException("El tipo de visa no puede ser nulo.");
        }
        return tipoVisa.cumpleRangoEdad(edad)
                ? EstadoEvaluacion.APROBADA
                : EstadoEvaluacion.RECHAZADA;
    }

    public String evaluarPorCodigoSeguro(int codigoTipo, int edad) {
        try {
            TipoVisa tipo = TipoVisa.buscarPorCodigo(codigoTipo);
            return evaluar(tipo, edad).name();
        } catch (IllegalArgumentException ex) {
            return "ERROR DE VALIDACIÓN: " + ex.getMessage();
        }
    }
}