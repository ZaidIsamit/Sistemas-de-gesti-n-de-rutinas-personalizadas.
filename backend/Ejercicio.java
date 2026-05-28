package backend;

public abstract class Ejercicio {
    private String codigo;
    private String nombre;
    private String tipo;
    private String intensidad;
    private int tiempoEstimado;
    private String descripcion;
    private int ultimaSemanaUsado;

    public Ejercicio(String codigo, String nombre, String tipo, String intensidad, int tiempoEstimado, String descripcion, int ultimaSemanaUsado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.intensidad = intensidad;
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
        this.ultimaSemanaUsado = ultimaSemanaUsado;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getIntensidad() { return intensidad; }
    public int getTiempoEstimado() { return tiempoEstimado; }
    public String getDescripcion() { return descripcion; }
    public int getUltimaSemanaUsado() { return ultimaSemanaUsado; }
    public void setUltimaSemanaUsado(int ultimaSemanaUsado) { this.ultimaSemanaUsado = ultimaSemanaUsado; }
}