package org.ed06.model;

import java.time.LocalDate;

/**
 * Sección específica de la reservas
 */
public class Reserva {
    private final int id;
    private final Habitacion habitacion;
    private final Cliente cliente;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final double precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    /**
     * @return id del clientes
     */
    public int getId() {
        return id;
    }

    /**
     * @return datos de las habitación
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * @return datos del clientes
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return datos de la fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return datos de la fecha fin
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * @return el precio total
     */
    public double getPrecioTotal() {
        return precioTotal;
    }

    /** Calcula el precio total de la reserva.
     *  En el caso de que el cliente sea vip, se aplicará un descuento del 10%. Además, si el intervalo de fechas es mayor a 7 días, se aplicará un descuento adicional del 5%.
     *
     * @return el precio total de la reserva
     */

    public double calcularPrecioFinal() {
        //calculamos los días de la reserva
        int n = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();
        // Calculamos el precio base de la habitación por el número de noches de la reserva
        // Declaramos la variable para almacenar el precio final
        double pf = habitacion.getPrecioBase() * n;

        // Si el cliente es VIP, aplicamos un descuento del 10%
        if (cliente.esVip) {
            pf *= 0.9;
        }

        // Si el intervalo de fechas es mayor a 7 días, aplicamos un descuento adicional del 5%
        if (n > 7) {
            pf *= 0.95;
        }

        // Devolvemos el precio final
        return pf;
    }

    /**
     * Mostrar la información de la reserva
     */
    public void mostrarReserva() {
        System.out.println("Reserva #" + id);
        System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Fecha de inicio: " + fechaInicio.toString());
        System.out.println("Fecha de fin: " + fechaFin.toString());
        System.out.printf("Precio total: %.2f €\n", precioTotal);
    }
}
