package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

/**
 * Sección principal de las reservas del Hotel
 */
public class Hotel {

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    public Hotel(String nombre, String direccion, String telefono) {

    }

    /**Agregar una nueva habitación al hotel
     * @param tipo
     * @param precioBase
     */

    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }

    /** Registrar habitaciones
     * @param tipos
     * @param preciosBase
     */
    public void registrarHabitaciones(List<String> tipos, List<Double> preciosBase) {
        for(int i = 0; i < tipos.size(); i++) {
            Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipos.get(i), preciosBase.get(i));
            habitaciones.add(habitacion);
            reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
        }
    }

    /**
     * Mostrar un listado de las habitaciones disponibles
     */
    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                System.out.println("Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo() + " - Precio base: " + habitacion.getPrecioBase());
            }
        }
    }

    /**
     * @param numero
     * @return el número de habitación
     */
    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }



    /**
     * Gestiona la reserva de habitación
     * @param clienteId
     * @param tipo
     * @param fechaEntrada
     * @param fechaSalida
     * @return disponibilidad
     */
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Comprobamos si hay habitaciones en el hotel
        return disponibilidad(clienteId, tipo, fechaEntrada, fechaSalida);


    }

    /**
     * Comprueba si hay habitaciones disponibles en el hotel
     * @param clienteId
     * @param tipo
     * @param fechaEntrada
     * @param fechaSalida
     * @return si existe el cliente continua el proceso de reserva si no  EL PROGRAMA NO FUNCIONA
     */
    private int disponibilidad(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if(!habitaciones.isEmpty()) {
            //comprobamos si existe el cliente
            return existeCliente(clienteId, tipo, fechaEntrada, fechaSalida);
        } else {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }
    }

    /**
     * Comprobar si existe el cliente
     * @param clienteId
     * @param tipo
     * @param fechaEntrada
     * @param fechaSalida
     * @return si existe cliente valida las fechas
     */
    private int existeCliente(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if(this.clientes.get(clienteId) != null) {
            Cliente cliente = this.clientes.get(clienteId);
            // comprobamos si las fechas son coherentes
            return validezFechas(tipo, fechaEntrada, fechaSalida, cliente);
        } else {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }
    }

    /**
     * Comprobar la validez de fechas
     * @param tipo
     * @param fechaEntrada
     * @param fechaSalida
     * @param cliente
     * @return si las fechas es válida comprobar la disponiblidad
     */
    private int validezFechas(String tipo, LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente) {
        if(fechaEntrada.isBefore(fechaSalida)) {
            //buscamos una habitación disponible
            return comprobarDisponibilidad(tipo, fechaEntrada, fechaSalida, cliente);
        } else {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }
    }

    /**
     * Comprobar la disponibilidad de la habitación en las fechas dadas
     * @param tipo
     * @param fechaEntrada
     * @param fechaSalida
     * @param cliente
     * @return crear la reserva y añadir al cliente Vip si cumple requisitos
     */
    private int comprobarDisponibilidad(String tipo, LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getTipo().equals(tipo.toUpperCase()) && habitacion.isDisponible()) {
                // Comprobamos si el cliente pasa a ser vip tras la nueva reserva
                isNewClienteVip(cliente);
                // Creamos la reserva
                return crearReserva(fechaEntrada, fechaSalida, cliente, habitacion);
            }
        }
        // si no hay habitaciones disponibles del tipo solicitado, mostramos un mensaje
        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return -1;
    }

    /**
     * Metodo que crea la reserva de habitación y marca la habitación como no disponible.
     * @param fechaEntrada
     * @param fechaSalida
     * @param cliente
     * @param habitacion
     * @return El número de la habitación que ha sido resevada
     */
    private int crearReserva(LocalDate fechaEntrada, LocalDate fechaSalida, Cliente cliente, Habitacion habitacion) {
        Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
        reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
        // Marcamos la habitación como no disponible
        habitacion.reservar();

        System.out.println("Reserva realizada con éxito");
        return habitacion.getNumero();
    }

    /**
     * Verifica si un el cliente cumple los requisitos para convertirse en Vip, actualizando su estado.
     * @param cliente
     */
    private void isNewClienteVip(Cliente cliente) {
        int numReservas = 0;
        for (List<Reserva> reservasHabitacion : reservasPorHabitacion.values()) {
            for(Reserva reservaCliente : reservasHabitacion) {
                if(reservaCliente.getCliente().equals(cliente)) {
                    if(reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))) {
                        numReservas++;
                    }
                }
            }
        }
        if(numReservas > 3 && !cliente.esVip) {
            cliente.esVip = true;
            System.out.println("El cliente " + cliente.nombre + " ha pasado a ser VIP");
        }
    }

    /**
     * Mostrar un listado de las reservas de habitaciones
     */
    public void listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                "Reserva #" + reserva.getId() + " - Cliente: " + reserva.getCliente().nombre
                    + " - Fecha de entrada: " + reserva.getFechaInicio()
                    + " - Fecha de salida: " + reserva.getFechaFin()));
        });
    }

    /**
     * Mostar un listado de clientes con los datos
     */
    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            System.out.println("Cliente #" + cliente.id + " - Nombre: " + cliente.nombre + " - DNI: " + cliente.dni + " - VIP: " + cliente.esVip);
        }
    }

    /**
     * Registra un nuevo cliente y lo añade a la lista de clientes
     * @param nombre
     * @param email
     * @param dni
     * @param esVip
     */
    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.id, cliente);
    }
}
