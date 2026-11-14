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

    public void agregarEnPosicion(int indice, Object dato) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
        if (indice == 0) {
            agregarAlInicio(dato);
        } else if (indice == tamanio) {
            agregar(dato);
        } else {
            Nodo siguiente = obtenerNodo(indice);
            Nodo anterior = siguiente.getIzquierda();
            Nodo nuevo = new Nodo(dato);
            nuevo.setIzquierda(anterior);
            nuevo.setDerecha(siguiente);
            anterior.setDerecha(nuevo);
            siguiente.setIzquierda(nuevo);
            tamanio++;
        }
    }

    public Object eliminarPrimero() {
        if (primero == null) {
            throw new IllegalStateException("La lista esta vacia.");
        }
        Object dato = primero.getDato();
        primero = primero.getDerecha();
        if (primero == null) {
            ultimo = null;
        } else {
            primero.setIzquierda(null);
        }
        tamanio--;
        return dato;
    }

    public Object eliminarUltimo() {
        if (ultimo == null) {
            throw new IllegalStateException("La lista esta vacia.");
        }
        Object dato = ultimo.getDato();
        ultimo = ultimo.getIzquierda();
        if (ultimo == null) {
            primero = null;
        } else {
            ultimo.setDerecha(null);
        }
        tamanio--;
        return dato;
    }

    public Object eliminarEnPosicion(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
        if (indice == 0) {
            return eliminarPrimero();
        }
        if (indice == tamanio - 1) {
            return eliminarUltimo();
        }
        Nodo actual = obtenerNodo(indice);
        actual.getIzquierda().setDerecha(actual.getDerecha());
        actual.getDerecha().setIzquierda(actual.getIzquierda());
        tamanio--;
        return actual.getDato();
    }

    public Object buscarDato(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
        return obtenerNodo(indice).getDato();
    }

    public Object buscarDato(Object dato) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getDerecha();
        }
        return null;
    }

    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    private Nodo obtenerNodo(int indice) {
        Nodo actual;
        if (indice < tamanio / 2) {
            actual = primero;
            for (int i = 0; i < indice; i++) {
                actual = actual.getDerecha();
            }
        } else {
            actual = ultimo;
            for (int i = tamanio - 1; i > indice; i--) {
                actual = actual.getIzquierda();
            }
        }
        return actual;
    }
}
