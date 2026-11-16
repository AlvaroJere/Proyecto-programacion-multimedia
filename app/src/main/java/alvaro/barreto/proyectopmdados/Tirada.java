package alvaro.barreto.proyectopmdados;

import java.io.Serializable;

public class Tirada implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jugador;
    private int caras;
    private int resultado;

    public Tirada(String jugador, int caras, int resultado) {
        this.jugador = jugador;
        this.caras = caras;
        this.resultado = resultado;
    }

    public String getJugador() { return jugador; }
    public void setJugador(String jugador) { this.jugador = jugador; }

    public int getCaras() { return caras; }
    public void setCaras(int caras) { this.caras = caras; }

    public int getResultado() { return resultado; }
    public void setResultado(int resultado) { this.resultado = resultado; }
}