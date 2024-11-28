import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TecladoVirtual {
    public JPanel crearTeclado(ActionListener listener) {
        JPanel tecladoPanel = new JPanel(new GridLayout(5, 10));
        tecladoPanel.setBackground(Color.DARK_GRAY);

        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alfabeto.length(); i++) {
            char letra = alfabeto.charAt(i);
            JButton boton = new JButton(String.valueOf(letra));
            boton.setFont(new Font("Arial", Font.PLAIN, 18));
            boton.setBackground(Color.GRAY);
            boton.setForeground(Color.WHITE);
            boton.addActionListener(listener);
            tecladoPanel.add(boton);
        }

        return tecladoPanel;
    }
}
