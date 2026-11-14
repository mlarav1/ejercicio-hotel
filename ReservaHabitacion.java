public class ReservaHabitacion {
    private String codigoReserva;
    private String nombreHuesped;
    private int numeroHabitacion;
    private int noches;
    private String estado;

    public ReservaHabitacion(String codigoReserva, String nombreHuesped, int numeroHabitacion, int noches) {
        this.codigoReserva = codigoReserva;
        this.nombreHuesped = nombreHuesped;
        this.numeroHabitacion = numeroHabitacion;
        this.noches = noches;
        this.estado = "PENDIENTE";
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getNombreHuesped() {
        return nombreHuesped;
    }

    public void setNombreHuesped(String nombreHuesped) {
        this.nombreHuesped = nombreHuesped;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public int getNoches() {
        return noches;
    }

    public void setNoches(int noches) {
        this.noches = noches;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{codigo='" + codigoReserva + "', huesped='" + nombreHuesped
                + "', habitacion=" + numeroHabitacion + ", noches=" + noches
                + ", estado=" + estado + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReservaHabitacion otra = (ReservaHabitacion) obj;
        return codigoReserva.equalsIgnoreCase(otra.codigoReserva);
    }

    @Override
    public int hashCode() {
        return codigoReserva.toLowerCase().hashCode();
    }
}
