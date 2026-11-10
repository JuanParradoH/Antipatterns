package consulado.rescatado.tarifas;

public class EstrategiaVisaTrabajo implements EstrategiaTarifa {
    private static final double FACTOR_RECARGO_TRABAJO = 1.5;
    private static final String MODALIDAD_TRABAJO = "TRABAJO";

    @Override
    public String getNombreModalidad() {
        return MODALIDAD_TRABAJO;
    }

    @Override
    public double obtenerFactorMultiplicador() {
        return FACTOR_RECARGO_TRABAJO;
    }
}