package consulado.rescatado.tarifas;

public class EstrategiaVisaEstudiante implements EstrategiaTarifa {
    private static final double FACTOR_DESCUENTO_ESTUDIANTE = 0.5;
    private static final String MODALIDAD_ESTUDIANTE = "ESTUDIANTE";

    @Override
    public String getNombreModalidad() {
        return MODALIDAD_ESTUDIANTE;
    }

    @Override
    public double obtenerFactorMultiplicador() {
        return FACTOR_DESCUENTO_ESTUDIANTE;
    }
}