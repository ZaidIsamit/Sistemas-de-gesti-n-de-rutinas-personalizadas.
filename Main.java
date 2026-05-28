import backend.SistemaRutinas;
import frontend.VentanaPrincipal;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaRutinas sistema = new SistemaRutinas();
            VentanaPrincipal gui = new VentanaPrincipal(sistema);
            
            try {
                sistema.cargarEjerciciosDesdeCSV("data/ejercicios.csv");
                gui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error crítico al iniciar el sistema: " + e.getMessage(), 
                    "Error de Inicialización", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}