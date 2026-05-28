package backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Stream;

public class SistemaRutinas {
    private Vector<Ejercicio> bancoEjercicios;
    private Vector<Ejercicio> rutinaActual;
    private Vector<SubscriptorRutina> subscriptores;
    private final int SEMANA_ACTUAL = 3;

    public SistemaRutinas() {
        this.bancoEjercicios = new Vector<>();
        this.rutinaActual = new Vector<>();
        this.subscriptores = new Vector<>();
    }

    public void suscribir(SubscriptorRutina sub) {
        this.subscriptores.add(sub);
    }

    public void cargarEjerciciosDesdeCSV(String rutaArchivo) throws ArchivoInexistenteException, FormatoInvalidoException {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            throw new ArchivoInexistenteException("El archivo especificado no existe en la ruta.");
        }

        bancoEjercicios.clear();

        try (Stream<String> lineas = Files.lines(Paths.get(rutaArchivo))) {
            final StringBuilder errores = new StringBuilder();

            lineas.forEach(linea -> {
                if (linea.trim().isEmpty()) return;
                
                String[] columnas = linea.split(";");
                if (columnas.length < 7) {
                    errores.append("Estructura de columnas incompleta. ");
                    return;
                }

                try {
                    String codigo = columnas[0].trim();
                    String nombre = columnas[1].trim();
                    String tipo = columnas[2].trim();
                    String intensidad = columnas[3].trim();
                    int tiempo = Integer.parseInt(columnas[4].trim());
                    String descripcion = columnas[5].trim();
                    int ultimaSemana = Integer.parseInt(columnas[6].trim());

                    Ejercicio ej = EjercicioFactory.crearEjercicio(codigo, nombre, tipo, intensidad, tiempo, descripcion, ultimaSemana);
                    bancoEjercicios.add(ej);

                } catch (NumberFormatException e) {
                    errores.append("Error en formato de datos numericos. ");
                } catch (FormatoInvalidoException e) {
                    errores.append(e.getMessage()).append(" ");
                }
            });

            if (errores.length() > 0) {
                throw new FormatoInvalidoException(errores.toString());
            }

        } catch (IOException e) {
            throw new FormatoInvalidoException("Falla en la lectura del archivo.");
        }

        notificarCambioCarga();
    }

    public void generarRutina(int cantCardio, int cantFuerza, String intensidadRequerida) {
        rutinaActual.clear();
        int cardioCont = 0;
        int fuerzaCont = 0;

        for (Ejercicio ej : bancoEjercicios) {
            if (ej.getIntensidad().equalsIgnoreCase(intensidadRequerida)) {
                if (SEMANA_ACTUAL - ej.getUltimaSemanaUsado() <= 1) {
                    continue; 
                }

                if (ej.getTipo().equalsIgnoreCase("Cardiovascular") && cardioCont < cantCardio) {
                    rutinaActual.add(ej);
                    cardioCont++;
                } else if (ej.getTipo().equalsIgnoreCase("Fuerza") && fuerzaCont < cantFuerza) {
                    rutinaActual.add(ej);
                    fuerzaCont++;
                }
            }
        }

        notificarRutinaGenerada();
    }

    public int obtenerStockDisponible(String tipo, String intensidad) {
        int cont = 0;
        for (Ejercicio ej : bancoEjercicios) {
            if (ej.getTipo().equalsIgnoreCase(tipo) && ej.getIntensidad().equalsIgnoreCase(intensidad)) {
                if (SEMANA_ACTUAL - ej.getUltimaSemanaUsado() > 1) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private void notificarCambioCarga() {
        for (SubscriptorRutina sub : subscriptores) {
            sub.ordenarActualizarDatosCarga(bancoEjercicios);
        }
    }

    private void notificarRutinaGenerada() {
        for (SubscriptorRutina sub : subscriptores) {
            sub.ordenarDesplegarRutinaGenerada(rutinaActual);
        }
    }
}