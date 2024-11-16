package aed;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int posicionTimestamp;
    private int posicionGanancia;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public int getGananciaNeta() {
        return this.gananciaNeta;
    }

    public int getPosicionGanancia() {
        return this.posicionGanancia;
    }

    public int getPosicionTimestamp() {
        return this.posicionTimestamp;
    }
    
    public int getId(){
        return this.id;
    }

    public int getOrigen() {
        return this.origen;
    }
    
    public int getDestino() {
        return this.destino;
    }

    public void setPosicionGanancia(int i) {
        this.posicionGanancia = i;
    }

    public void setPosicionTimestamp(int i) {
        this.posicionTimestamp = i;
    }
}

