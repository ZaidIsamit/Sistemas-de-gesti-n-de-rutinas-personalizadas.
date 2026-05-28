package frontend;

import backend.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VentanaRevision extends JFrame {
    private Vector<Ejercicio> rutina;
    private int index = 0;

    private JLabel lblNombre, lblTipo, lblIntensidad, lblTiempo;
    private JTextArea txtDescripcion;
    private JButton btnAnterior, btnSiguiente;

    public VentanaRevision(Vector<Ejercicio> rutina) {
        this.rutina = rutina;

        setTitle("Rutina Generada");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        panelInfo.add(new JLabel("Nombre:")); lblNombre = new JLabel(); panelInfo.add(lblNombre);
        panelInfo.add(new JLabel("Tipo:")); lblTipo = new JLabel(); panelInfo.add(lblTipo);
        panelInfo.add(new JLabel("Intensidad:")); lblIntensidad = new JLabel(); panelInfo.add(lblIntensidad);
        panelInfo.add(new JLabel("Tiempo:")); lblTiempo = new JLabel(); panelInfo.add(lblTiempo);
        add(panelInfo, BorderLayout.NORTH);

        txtDescripcion = new JTextArea();
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBorder(BorderFactory.createTitledBorder("Guia de Ejecucion"));
        add(scroll, BorderLayout.CENTER);

        JPanel panelControl = new JPanel(new FlowLayout());
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        panelControl.add(btnAnterior);
        panelControl.add(btnSiguiente);
        add(panelControl, BorderLayout.SOUTH);

        btnAnterior.addActionListener(e -> {
            if (index > 0) {
                index--;
                mostrarEjercicio();
            }
        });

        btnSiguiente.addActionListener(e -> {
            if (btnSiguiente.getText().equals("Ver Resumen")) {
                new VentanaResumen(rutina).setVisible(true);
                dispose();
            } else {
                if (index < rutina.size() - 1) {
                    index++;
                    mostrarEjercicio();
                }
            }
        });

        mostrarEjercicio();
    }

    private void mostrarEjercicio() {
        Ejercicio ej = rutina.get(index);
        lblNombre.setText(ej.getNombre());
        lblTipo.setText(ej.getTipo());
        lblIntensidad.setText(ej.getIntensidad());
        lblTiempo.setText(ej.getTiempoEstimado() + " min");
        txtDescripcion.setText(ej.getDescripcion());

        btnAnterior.setEnabled(index != 0);

        if (index == rutina.size() - 1) {
            btnSiguiente.setText("Ver Resumen");
        } else {
            btnSiguiente.setText("Siguiente");
        }
    }
}