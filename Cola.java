public class Cola {
    private Nodo frente;
    private Nodo fin;
    private int tamanio;

    public Cola() {
        limpiar();
    }

    public void encolar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (fin == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setDerecha(nuevo);
            nuevo.setIzquierda(fin);
            fin = nuevo;
        }
        tamanio++;
    }

    public Object desencolar() {
        if (frente == null) {
            throw new IllegalStateException("La cola esta vacia.");
        }
        Object dato = frente.getDato();
        frente = frente.getDerecha();
        if (frente == null) {
            fin = null;
        } else {
            frente.setIzquierda(null);
        }
        tamanio--;
        return dato;
    }

    public Object peek() {
        if (frente == null) {
            throw new IllegalStateException("La cola esta vacia.");
        }
        return frente.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean esVacia() {
        return tamanio == 0;
    }

    public boolean contiene(Object dato) {
        Nodo actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getDerecha();
        }
        return false;
    }

    public void limpiar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public void mostrar() {
        if (frente == null) {
            System.out.println("La cola esta vacia.");
            return;
        }
        Nodo actual = frente;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". " + actual.getDato());
            actual = actual.getDerecha();
            posicion++;
        }
    }
}
