public class Pila {
    private Nodo cima;
    private int tamanio;

    public Pila() {
        limpiar();
    }

    public void apilar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (cima != null) {
            nuevo.setDerecha(cima);
            cima.setIzquierda(nuevo);
        }
        cima = nuevo;
        tamanio++;
    }

    public Object desapilar() {
        if (cima == null) {
            throw new IllegalStateException("La pila esta vacia.");
        }
        Object dato = cima.getDato();
        cima = cima.getDerecha();
        if (cima != null) {
            cima.setIzquierda(null);
        }
        tamanio--;
        return dato;
    }

    public Object peek() {
        if (cima == null) {
            throw new IllegalStateException("La pila esta vacia.");
        }
        return cima.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean esVacia() {
        return tamanio == 0;
    }

    public boolean contiene(Object dato) {
        return buscar(dato) != null;
    }

    public void limpiar() {
        cima = null;
        tamanio = 0;
    }

    public void mostrar() {
        if (cima == null) {
            System.out.println("La pila esta vacia.");
            return;
        }
        Nodo actual = cima;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". " + actual.getDato());
            actual = actual.getDerecha();
            posicion++;
        }
    }

    public Object buscar(Object dato) {
        Nodo actual = cima;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getDerecha();
        }
        return null;
    }
}
