package backend;

public class EjercicioFactory {
    public static Ejercicio crearEjercicio(String codigo, String nombre, String tipo, String intensidad, int tiempo, String descripcion, int ultimaSemana) throws FormatoInvalidoException {
        if (tipo.equalsIgnoreCase("Cardiovascular")) {
            return new EjercicioCardio(codigo, nombre, intensidad, tiempo, descripcion, ultimaSemana);
        } else if (tipo.equalsIgnoreCase("Fuerza")) {
            return new EjercicioFuerza(codigo, nombre, intensidad, tiempo, descripcion, ultimaSemana);
        } else {
            throw new FormatoInvalidoException("Tipo de ejercicio invalido: " + tipo);
        }
    }
}