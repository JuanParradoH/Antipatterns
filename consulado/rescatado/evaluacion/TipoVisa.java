package consulado.rescatado.evaluacion;

public enum TipoVisa {
    TURISTA(
            ConstantesEdadVisa.CODIGO_TURISTA,
            ConstantesEdadVisa.EDAD_MINIMA_ADULTO,
            ConstantesEdadVisa.EDAD_MAXIMA_SIN_LIMITE
    ),
    ESTUDIANTE(
            ConstantesEdadVisa.CODIGO_ESTUDIANTE,
            ConstantesEdadVisa.EDAD_MINIMA_ESTUDIANTE,
            ConstantesEdadVisa.EDAD_MAXIMA_ESTUDIANTE
    ),
    TRABAJO(
            ConstantesEdadVisa.CODIGO_TRABAJO,
            ConstantesEdadVisa.EDAD_MINIMA_ADULTO,
            ConstantesEdadVisa.EDAD_MAXIMA_TRABAJO
    );

    public static final class ConstantesEdadVisa {
        public static final int CODIGO_TURISTA = 1;
        public static final int CODIGO_ESTUDIANTE = 2;
        public static final int CODIGO_TRABAJO = 3;

        public static final int EDAD_MINIMA_ADULTO = 18;      
        public static final int EDAD_MINIMA_ESTUDIANTE = 16;  
        public static final int EDAD_MAXIMA_ESTUDIANTE = 30;  
        public static final int EDAD_MAXIMA_TRABAJO = 65;     
        public static final int EDAD_MAXIMA_SIN_LIMITE = Integer.MAX_VALUE;

        private ConstantesEdadVisa() {}
    }

    private final int codigo;
    private final int edadMinima;
    private final int edadMaxima;

    TipoVisa(int codigo, int edadMinima, int edadMaxima) {
        this.codigo = codigo;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean cumpleRangoEdad(int edad) {
        return edad >= edadMinima && edad <= edadMaxima;
    }

    public static TipoVisa buscarPorCodigo(int codigo) {
        for (TipoVisa tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException(
                "Código de visa inválido (" + codigo + "). Los tipos permitidos son: "
                + "1 (TURISTA), 2 (ESTUDIANTE), 3 (TRABAJO)."
        );
    }
}