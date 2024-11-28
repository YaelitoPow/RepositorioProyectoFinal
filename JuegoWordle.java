import java.util.List;

public class JuegoWordle extends Juego implements InterfazJuego{
    private String palabraIngresada;

    public JuegoWordle(List<String> palabrasPosibles) {
        super(palabrasPosibles);
        this.palabraIngresada = "";
    }

    @Override
    public void agregarLetra(String letra) {
        if (palabraIngresada.length() < 5) {
            palabraIngresada += letra;
        }
    }

    @Override
    public void borrarUltimaLetra() {
        if (palabraIngresada.length() > 0) {
            palabraIngresada = palabraIngresada.substring(0, palabraIngresada.length() - 1);
        }
    }

    @Override
    public void comprobarPalabra() {
        if (palabraIngresada.length() != 5) {
            System.out.println("La palabra debe tener 5 letras.");
            return;
        }

        if (palabraIngresada.equals(getPalabraCorrecta())) {
            System.out.println("¡Felicidades! Has adivinado la palabra.");
        } else {
            System.out.println("La palabra ingresada es incorrecta. Intenta de nuevo.");
        }
    }

    @Override
    public void mostrarOpcionesFinJuego(String mensaje) {
        System.out.println(mensaje);
        // Opciones como "Jugar otra vez" o "Salir" pueden implementarse aquí si se requiere
    }

    @Override
    public void reiniciarJuego() {
        // Reiniciar la palabra ingresada y seleccionar una nueva palabra
        this.palabraIngresada = "";
        super.palabraCorrecta = palabrasPosibles.get((int)(Math.random() * palabrasPosibles.size()));
    }

    // Implementación del método abstracto comprobarPalabra(String intento) de la clase base Juego
    @Override
    public String comprobarPalabra(String intento) {
        if (intento.length() != 5) {
            return "La palabra debe tener 5 letras.";
        }

        if (intento.equals(getPalabraCorrecta())) {
            return "¡Felicidades! Has adivinado la palabra.";
        } else {
            return "La palabra ingresada es incorrecta. Intenta de nuevo.";
        }
    }
}
