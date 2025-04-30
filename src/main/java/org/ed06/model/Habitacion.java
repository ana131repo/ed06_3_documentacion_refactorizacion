package org.ed06.model;

/**
 * Sección específica de la habitación
 */
public class Habitacion {
    private final int numero;
    private final String tipo; // "SIMPLE", "DOBLE", "SUITE"
    private final double precioBase;

    //Todo pendiente cambiar la forma de gestionar la disponibilidad en base a las fechas de las reservas
    private boolean disponible;

    public Habitacion(int numero, String tipo, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean isDisponible() {
        return disponible;
    }

    /** Metodo que usa un switch para determinar el número máximo de huéspedes
     * @return número máximo de huespedes - int
     */

    public double obtenerNumMaxHuespedes() {
        return switch (tipo) {
            case "SIMPLE" -> 1;
            case "DOBLE" -> 3;
            case "SUITE" -> 4;
            case "LITERAS" -> 8;
            default -> 0;
        };
    }

    /**
     * Cambiar el estado de la habitación
     * Reservada / Disponible
     */
    public void reservar() {
        if (disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
        }
        disponible = true;
    }
}
