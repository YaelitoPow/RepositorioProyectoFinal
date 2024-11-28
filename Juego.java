import java.util.List;

public abstract class Juego {
    protected String palabraCorrecta;
    protected List<String> palabrasPosibles;
    protected int intentos;

    public Juego(List<String> palabrasPosibles) {
        this.palabrasPosibles = palabrasPosibles;
        this.palabraCorrecta = palabrasPosibles.get((int)(Math.random() * palabrasPosibles.size()));
        this.intentos = 0;
    }

    public abstract String comprobarPalabra(String intento);
    public abstract void reiniciarJuego();

    public String getPalabraCorrecta() {
        return palabraCorrecta;
    }

    public boolean isJuegoTerminado() {
        return intentos >= 6 || palabraCorrecta.equals(palabraCorrecta);
    }

}
