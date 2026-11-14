public class Lista {
    private Nodo primero;
    private Nodo ultimo;
    private int tamanio;

    public Lista() {
        limpiar();
    }

    public void agregar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setDerecha(nuevo);
            nuevo.setIzquierda(ultimo);
            ultimo = nuevo;
        }
        tamanio++;
    }

    public void agregarAlInicio(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setDerecha(primero);
            primero.setIzquierda(nuevo);
            primero = nuevo;
        }
        tamanio++;
    }

    public int cuentaElementos() {
        return tamanio;
    }

    public void limpiar() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public void mostrarAdelante() {
        if (primero == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        Nodo actual = primero;
        int i = 0;
        while (actual != null) {
            System.out.println("[" + i + "] " + actual.getDato());
            actual = actual.getDerecha();
            i++;
        }
    }

    public void mostrarAtras() {
        if (ultimo == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        Nodo actual = ultimo;
        int i = tamanio - 1;
        while (actual != null) {
            System.out.println("[" + i + "] " + actual.getDato());
            actual = actual.getIzquierda();
            i--;
        }
    }
}
