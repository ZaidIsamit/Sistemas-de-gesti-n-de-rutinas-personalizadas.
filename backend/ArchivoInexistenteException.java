package backend;

public class ArchivoInexistenteException extends Exception {
    public ArchivoInexistenteException(String mensaje) {
        super(mensaje);
    }
}