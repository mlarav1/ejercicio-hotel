import java.util.Scanner;

public class SistemaHotel {
    private Lista listaGeneral;
    private Cola colaPendientes;
    private Pila historialProcesados;
    private Scanner scanner;

    public SistemaHotel() {
        listaGeneral = new Lista();
        colaPendientes = new Cola();
        historialProcesados = new Pila();
        scanner = new Scanner(System.in);
    }

    public boolean registrarReserva(String codigo, String huesped, int habitacion, int noches) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo de reserva no puede estar vacio.");
        }
        if (huesped == null || huesped.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del huesped no puede estar vacio.");
        }
        if (habitacion <= 0) {
            throw new IllegalArgumentException("El numero de habitacion debe ser mayor a cero.");
        }
        if (noches <= 0) {
            throw new IllegalArgumentException("El numero de noches debe ser mayor a cero.");
        }
        ReservaHabitacion reserva = new ReservaHabitacion(codigo.trim(), huesped.trim(), habitacion, noches);
        if (listaGeneral.contiene(reserva)) {
            return false;
        }
        listaGeneral.agregar(reserva);
        colaPendientes.encolar(reserva);
        return true;
    }

    public ReservaHabitacion procesarSiguiente() {
        if (colaPendientes.esVacia()) {
            throw new IllegalStateException("No hay reservas pendientes por procesar.");
        }
        ReservaHabitacion procesada = (ReservaHabitacion) colaPendientes.desencolar();
        procesada.setEstado("CONFIRMADA");
        historialProcesados.apilar(procesada);
        return procesada;
    }

    public ReservaHabitacion buscarReserva(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo de busqueda no puede estar vacio.");
        }
        ReservaHabitacion molde = new ReservaHabitacion(codigo.trim(), "-", 1, 1);
        return (ReservaHabitacion) listaGeneral.buscarDato(molde);
    }

    public boolean cancelarPendiente(String codigo) {
        if (colaPendientes.esVacia()) {
            throw new IllegalStateException("No hay reservas pendientes para cancelar.");
        }
        Cola auxiliar = new Cola();
        boolean cancelada = false;
        while (!colaPendientes.esVacia()) {
            ReservaHabitacion actual = (ReservaHabitacion) colaPendientes.desencolar();
            if (!cancelada && actual.getCodigoReserva().equalsIgnoreCase(codigo.trim())) {
                actual.setEstado("CANCELADA");
                cancelada = true;
            } else {
                auxiliar.encolar(actual);
            }
        }
        while (!auxiliar.esVacia()) {
            colaPendientes.encolar(auxiliar.desencolar());
        }
        return cancelada;
    }

    public ReservaHabitacion deshacerUltimoProcesamiento() {
        if (historialProcesados.esVacia()) {
            throw new IllegalStateException("No hay procesamientos para deshacer.");
        }
        ReservaHabitacion ultimo = (ReservaHabitacion) historialProcesados.desapilar();
        ultimo.setEstado("PENDIENTE");
        colaPendientes.encolar(ultimo);
        return ultimo;
    }

    public void mostrarTodas() {
        listaGeneral.mostrarAdelante();
    }

    public void mostrarPendientes() {
        colaPendientes.mostrar();
    }

    public void mostrarHistorial() {
        historialProcesados.mostrar();
    }

    public void mostrarCantidades() {
        System.out.println("Reservas registradas: " + listaGeneral.cuentaElementos());
        System.out.println("Reservas pendientes: " + colaPendientes.tamanio());
        System.out.println("Reservas procesadas: " + historialProcesados.tamanio());
    }
}
