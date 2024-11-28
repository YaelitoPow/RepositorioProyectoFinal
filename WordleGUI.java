import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class WordleGUI {
    private JFrame frame;
    private JButton comprobarButton;
    private JPanel gridPanel;
    private JuegoWordle juego;
    private final List<String> palabrasPosibles = Arrays.asList("perro", "gato", "panda", "nieve", "flama"); // Lista de palabras en español.

    private int intentoActual = 0;

    // Cuadrícula de letras
    private JLabel[][] gridLabels;

    public WordleGUI() {
        juego = new JuegoWordle(palabrasPosibles);
        frame = new JFrame("Wordle en Español");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        // Cambiar el fondo del JFrame a oscuro
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        // Panel para la cuadrícula de 6x5
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(6, 5)); // 6 intentos, 5 letras
        gridPanel.setBackground(Color.DARK_GRAY); // Fondo oscuro para la cuadrícula

        // Crear la cuadrícula de 6x5 para mostrar los intentos
        gridLabels = new JLabel[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                gridLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Líneas gris oscuro
                gridLabels[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                gridLabels[i][j].setOpaque(true);
                gridLabels[i][j].setBackground(Color.BLACK); // Fondo oscuro para las celdas
                gridLabels[i][j].setForeground(Color.WHITE); // Texto blanco para las letras
                gridPanel.add(gridLabels[i][j]);
            }
        }

        // Panel de teclado virtual
        JPanel tecladoPanel = new JPanel();
        tecladoPanel.setLayout(new GridLayout(5, 10)); // 5 filas de botones (alfabeto)
        tecladoPanel.setBackground(Color.DARK_GRAY); // Fondo oscuro para el teclado

        // Crear botones para el teclado virtual (letras A-Z)
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alfabeto.length(); i++) {
            char letra = alfabeto.charAt(i);
            JButton boton = new JButton(String.valueOf(letra));
            boton.setFont(new Font("Arial", Font.PLAIN, 18));
            boton.setBackground(Color.GRAY); // Fondo oscuro para los botones
            boton.setForeground(Color.WHITE); // Texto blanco para los botones
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Añadir la letra presionada a la palabra en curso
                    if (intentoActual < 6 && getLetrasIngresadas() < 5) {
                        agregarLetra(String.valueOf(letra));
                    }
                }
            });
            tecladoPanel.add(boton);
        }

        // Botón para borrar la última letra
        JButton borrarButton = new JButton("Borrar");
        borrarButton.setFont(new Font("Arial", Font.PLAIN, 18));
        borrarButton.setBackground(Color.GRAY); // Fondo oscuro para el botón
        borrarButton.setForeground(Color.WHITE); // Texto blanco para el botón
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarUltimaLetra();
            }
        });
        tecladoPanel.add(borrarButton);

        // Botón de Enviar
        comprobarButton = new JButton("Enviar");
        comprobarButton.setFont(new Font("Arial", Font.PLAIN, 18));
        comprobarButton.setBackground(Color.GRAY); // Fondo oscuro para el botón
        comprobarButton.setForeground(Color.WHITE); // Texto blanco para el botón
        comprobarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobarPalabra();
            }
        });
        tecladoPanel.add(comprobarButton);

        // Agregar paneles
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(tecladoPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Método para obtener cuántas letras han sido ingresadas hasta el momento
    private int getLetrasIngresadas() {
        int letrasIngresadas = 0;
        for (int i = 0; i < 5; i++) {
            if (gridLabels[intentoActual][i].getText().isEmpty()) {
                break;
            }
            letrasIngresadas++;
        }
        return letrasIngresadas;
    }

    // Método para agregar una letra a la cuadrícula
    private void agregarLetra(String letra) {
        for (int i = 0; i < 5; i++) {
            JLabel label = gridLabels[intentoActual][i];
            if (label.getText().isEmpty()) {
                label.setText(letra);
                break;
            }
        }
    }

    // Método para borrar la última letra ingresada
    private void borrarUltimaLetra() {
        for (int i = 4; i >= 0; i--) {
            JLabel label = gridLabels[intentoActual][i];
            if (!label.getText().isEmpty()) {
                label.setText(""); // Borra la letra
                break;
            }
        }
    }

    // Método para comprobar la palabra ingresada
    private void comprobarPalabra() {
        StringBuilder palabraIngresada = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            palabraIngresada.append(gridLabels[intentoActual][i].getText().toLowerCase());
        }

        String intento = palabraIngresada.toString();

        // Asegurarse de que la palabra tenga 5 letras
        if (intento.length() != 5) {
            JOptionPane.showMessageDialog(frame, "La palabra debe tener 5 letras.");
            return;
        }

        // Verificar si aún hay intentos disponibles
        if (intentoActual < 6) {
            // Mostrar la palabra ingresada en la cuadrícula
            for (int i = 0; i < 5; i++) {
                char letra = intento.charAt(i);
                JLabel label = gridLabels[intentoActual][i]; // Obtener el JLabel para esta celda

                // Colorear según la lógica de Wordle
                if (letra == juego.getPalabraCorrecta().charAt(i)) {
                    label.setBackground(Color.GREEN); // Letra correcta y posición correcta
                } else if (juego.getPalabraCorrecta().contains(String.valueOf(letra))) {
                    label.setBackground(Color.ORANGE); // Letra correcta pero posición incorrecta
                } else {
                    label.setBackground(Color.RED); // Letra incorrecta
                }
            }

            intentoActual++; // Incrementar el intento actual

            // Verificar si el jugador ha ganado
            if (intento.equals(juego.getPalabraCorrecta())) {
                mostrarOpcionesFinJuego("¡Felicidades! Has adivinado la palabra.");
                return; // Terminar el juego si la palabra es correcta
            }

            // Verificar si se han agotado los intentos
            if (intentoActual == 6) {
                mostrarOpcionesFinJuego("¡Has perdido! La palabra correcta era: " + juego.getPalabraCorrecta());
            }

            // Limpiar la cuadrícula para el siguiente intento
            for (int i = 0; i < 5; i++) {
                gridLabels[intentoActual][i].setText("");
                gridLabels[intentoActual][i].setBackground(Color.BLACK);
            }
        }
    }

    private void mostrarOpcionesFinJuego(String mensaje) {
        // Crear un panel con los botones de salir y jugar otra vez
        Object[] opciones = {"Jugar otra vez", "Salir"};
        int seleccion = JOptionPane.showOptionDialog(frame, mensaje, "Fin del juego", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) {
            reiniciarJuego();
        } else {
            System.exit(0); // Salir del juego
        }
    }

    // Método para reiniciar el juego con una nueva palabra
    private void reiniciarJuego() {
        juego.reiniciarJuego();
        intentoActual = 0;
        // Limpiar la cuadrícula
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                gridLabels[i][j].setText("");
                gridLabels[i][j].setBackground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        new WordleGUI();
    }
}
