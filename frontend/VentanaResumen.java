package frontend;

import backend.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class VentanaResumen extends JFrame {
    public VentanaResumen(Vector<Ejercicio> rutina) {
        setTitle("Resumen Operacional");
        setSize(360, 240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        int cardio = 0, fuerza = 0, tiempo = 0;
        int b = 0, i = 0, a = 0, ar = 0;

        for (Ejercicio ej : rutina) {
            tiempo += ej.getTiempoEstimado();
            if (ej.getTipo().equalsIgnoreCase("Cardiovascular")) cardio++;
            else fuerza++;

            switch (ej.getIntensidad().toLowerCase()) {
                case "básico": b++; break;
                case "intermedio": i++; break;
                case "avanzado": a++; break;
                case "alto rendimiento": ar++; break;
            }
        }

        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt.append("        RESUMEN DE PLANIFICACION    \n");
        txt.append("Ejercicios Cardio : " + cardio + "\n");
        txt.append("Ejercicios Fuerza : " + fuerza + "\n");
        txt.append("Tiempo Estimado   : " + tiempo + " minutos\n");
        txt.append("------------------------------------\n");
        txt.append("Distribucion por cargas:\n");
        txt.append(" Basico: " + b + " | Intermedio: " + i + "\n");
        txt.append(" Avanzado: " + a + " | Alto Rendimiento: " + ar + "\n");

        add(new JScrollPane(txt), BorderLayout.CENTER);

        JButton btnSalir = new JButton("Terminar");
        btnSalir.addActionListener(e -> dispose());
        add(btnSalir, BorderLayout.SOUTH);
    }
}