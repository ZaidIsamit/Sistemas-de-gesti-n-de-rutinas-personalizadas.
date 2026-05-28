package backend;

public class EjercicioCardio extends Ejercicio {
    public EjercicioCardio(String codigo, String nombre, String intensidad, int tiempoEstimado, String descripcion, int ultimaSemanaUsado) {
        super(codigo, nombre, "Cardiovascular", intensidad, tiempoEstimado, descripcion, ultimaSemanaUsado);
    }
}