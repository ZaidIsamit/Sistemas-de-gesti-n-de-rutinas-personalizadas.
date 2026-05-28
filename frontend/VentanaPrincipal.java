package frontend;

import backend.SistemaRutinas;
import backend.Ejercicio;
import backend.SubscriptorRutina;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class VentanaPrincipal extends JFrame implements SubscriptorRutina {
    private SistemaRutinas backend;
    private Vector<Ejercicio> rutinaEnMemoria;

    private JLabel lblTotal, lblTiempo, lblCardio, lblFuerza, lblBasico, lblAvanzado;
    private JButton btnIniciarGeneracion;

    public VentanaPrincipal(SistemaRutinas backend) {
        this.backend = backend;
        this.rutinaEnMemoria = new Vector<>();
        this.backend.suscribir(this);

        setTitle("Sistema de Gestion de Rutinas");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Panel de Control", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelMetricas = new JPanel(new GridLayout(3, 2, 10, 10));
        panelMetricas.setBorder(BorderFactory.createTitledBorder("Metricas Base de Datos"));

        lblTotal = new JLabel("Total Ejercicios: 0");
        lblTiempo = new JLabel("Tiempo Disponible: 0 min");
        lblCardio = new JLabel("Cardiovascular: 0");
        lblFuerza = new JLabel("Fuerza: 0");
        lblBasico = new JLabel("Basico / Intermedio: 0");
        lblAvanzado = new JLabel("Avanzado / Alto Rend.: 0");

        panelMetricas.add(lblTotal);
        panelMetricas.add(lblTiempo);
        panelMetricas.add(lblCardio);
        panelMetricas.add(lblFuerza);
        panelMetricas.add(lblBasico);
        panelMetricas.add(lblAvanzado);

        add(panelMetricas, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnIniciarGeneracion = new JButton("Configurar Rutina");

        panelBotones.add(btnIniciarGeneracion);
        add(panelBotones, BorderLayout.SOUTH);

        btnIniciarGeneracion.addActionListener(e -> new VentanaGeneracion(backend).setVisible(true));
    }

    @Override
    public void ordenarActualizarDatosCarga(Vector<Ejercicio> ejerciciosCargados) {
        int total = ejerciciosCargados.size();
        int tiempo = 0, cardio = 0, fuerza = 0;
        int grupoFácil = 0, grupoAvanzado = 0;

        for (Ejercicio ej : ejerciciosCargados) {
            tiempo += ej.getTiempoEstimado();
            if (ej.getTipo().equalsIgnoreCase("Cardiovascular")) cardio++;
            else fuerza++;

            if (ej.getIntensidad().equalsIgnoreCase("Básico") || ej.getIntensidad().equalsIgnoreCase("Intermedio")) {
                grupoFácil++;
            } else {
                grupoAvanzado++;
            }
        }

        lblTotal.setText("Total Ejercicios: " + total);
        lblTiempo.setText("Tiempo Disponible: " + tiempo + " min");
        lblCardio.setText("Cardiovascular: " + cardio);
        lblFuerza.setText("Fuerza: " + fuerza);
        lblBasico.setText("Basico / Intermedio: " + grupoFácil);
        lblAvanzado.setText("Avanzado / Alto Rend.: " + grupoAvanzado);
    }

    @Override
    public void ordenarDesplegarRutinaGenerada(Vector<Ejercicio> rutina) {
        this.rutinaEnMemoria = rutina;
        if (rutina.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existen ejercicios disponibles que cumplan las restricciones.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            new VentanaRevision(rutinaEnMemoria).setVisible(true);
        }
    }
}