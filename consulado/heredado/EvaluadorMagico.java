package consulado.heredado;

public class EvaluadorMagico {
    public String evaluar(int tipo, int edad) {
        if (tipo == 1 && edad > 17) return "APROBADA";
        if (tipo == 2 && edad > 15 && edad < 31) return "APROBADA";
        if (tipo == 3 && edad > 17 && edad < 66) return "APROBADA";
        return "RECHAZADA";
    }
}