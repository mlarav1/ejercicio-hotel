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

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje).trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero valido.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== HOTEL - GESTION DE RESERVAS =====");
        System.out.println("1. Registrar reserva");
        System.out.println("2. Ver todas las reservas registradas");
        System.out.println("3. Ver reservas pendientes");
        System.out.println("4. Procesar siguiente reserva");
        System.out.println("5. Ver historial de reservas procesadas");
        System.out.println("6. Buscar reserva por codigo");
        System.out.println("7. Cancelar reserva pendiente");
        System.out.println("8. Deshacer ultimo procesamiento");
        System.out.println("9. Ver cantidad de reservas");
        System.out.println("10. Salir");
    }

    private void opcionRegistrar() {
        String codigo = leerTexto("Codigo de reserva: ");
        String huesped = leerTexto("Nombre del huesped: ");
        int habitacion = leerEntero("Numero de habitacion: ");
        int noches = leerEntero("Numero de noches: ");
        if (registrarReserva(codigo, huesped, habitacion, noches)) {
            System.out.println("Reserva registrada y enviada a pendientes.");
        } else {
            System.out.println("Error: ya existe una reserva con ese codigo.");
        }
    }

    private void opcionBuscar() {
        ReservaHabitacion reserva = buscarReserva(leerTexto("Codigo a buscar: "));
        if (reserva == null) {
            System.out.println("No se encontro ninguna reserva con ese codigo.");
        } else {
            System.out.println("Encontrada: " + reserva);
        }
    }

    private void opcionCancelar() {
        if (cancelarPendiente(leerTexto("Codigo de la reserva pendiente a cancelar: "))) {
            System.out.println("Reserva cancelada correctamente.");
        } else {
            System.out.println("No existe una reserva pendiente con ese codigo.");
        }
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        opcionRegistrar();
                        break;
                    case 2:
                        mostrarTodas();
                        break;
                    case 3:
                        mostrarPendientes();
                        break;
                    case 4:
                        System.out.println("Procesada: " + procesarSiguiente());
                        break;
                    case 5:
                        mostrarHistorial();
                        break;
                    case 6:
                        opcionBuscar();
                        break;
                    case 7:
                        opcionCancelar();
                        break;
                    case 8:
                        System.out.println("Procesamiento deshecho: " + deshacerUltimoProcesamiento());
                        break;
                    case 9:
                        mostrarCantidades();
                        break;
                    case 10:
                        salir = true;
                        System.out.println("Hasta pronto.");
                        break;
                    default:
                        System.out.println("Opcion invalida. Elija entre 1 y 10.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new SistemaHotel().ejecutar();
    }
}
