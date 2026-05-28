package frontend;

import backend.SistemaRutinas;
import javax.swing.*;
import java.awt.*;

public class VentanaGeneracion extends JFrame {
    private SistemaRutinas backend;
    private JSpinner spinnerCardio;
    private JSpinner spinnerFuerza;
    private JComboBox<String> comboIntensidad;

    public VentanaGeneracion(SistemaRutinas backend) {
        this.backend = backend;

        setTitle("Parametros de Rutina");
        setSize(320, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 8, 8));

        spinnerCardio = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));
        spinnerFuerza = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));
        
        String[] niveles = {"Básico", "Intermedio", "Avanzado", "Alto rendimiento"};
        comboIntensidad = new JComboBox<>(niveles);
        JButton btnGenerar = new JButton("Calcular");

        add(new JLabel("  Cant. Cardio:"));
        add(spinnerCardio);
        add(new JLabel("  Cant. Fuerza:"));
        add(spinnerFuerza);
        add(new JLabel("  Intensidad:"));
        add(comboIntensidad);
        add(new JLabel(""));
        add(btnGenerar);

        comboIntensidad.addActionListener(e -> actualizarLimitesMaximos());

        actualizarLimitesMaximos();

        btnGenerar.addActionListener(e -> {
            int cardio = (int) spinnerCardio.getValue();
            int fuerza = (int) spinnerFuerza.getValue();
            String intensidad = (String) comboIntensidad.getSelectedItem();
            
            backend.generarRutina(cardio, fuerza, intensidad);
            dispose();
        });
    }

    private void actualizarLimitesMaximos() {
        String intensidadSeleccionada = (String) comboIntensidad.getSelectedItem();
        
        int maxCardio = backend.obtenerStockDisponible("Cardiovascular", intensidadSeleccionada);
        int maxFuerza = backend.obtenerStockDisponible("Fuerza", intensidadSeleccionada);

        int valorActualCardio = (int) spinnerCardio.getValue();
        int valorActualFuerza = (int) spinnerFuerza.getValue();

        if (valorActualCardio > maxCardio) valorActualCardio = maxCardio;
        if (valorActualFuerza > maxFuerza) valorActualFuerza = maxFuerza;

        spinnerCardio.setModel(new SpinnerNumberModel(valorActualCardio, 0, maxCardio, 1));
        spinnerFuerza.setModel(new SpinnerNumberModel(valorActualFuerza, 0, maxFuerza, 1));
    }
}