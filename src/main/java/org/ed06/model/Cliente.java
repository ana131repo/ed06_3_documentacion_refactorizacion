package org.ed06.model;

/**
 * Sección principal del cliente
 */
public class Cliente {
    public final int id;
    public String nombre;
    public String dni;
    public String email;
    public boolean esVip;

    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        validarNombre(nombre);
        this.nombre = nombre;
        validarDni(dni);
        this.dni = dni;
        validarEmail(email);
        this.email = email;
        this.esVip = esVip;
    }

    /**Comprobación que el nombre sea válido
     * @param nombre
     * @throws IllegalArgumentException Si el nombre es null o es inferior a 3 caracteres
     */
    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
    }

    /** Comprobación que el email tenga el formato correcto
     * @param email
     * @throws IllegalArgumentException si el email no cumple con un patron correcto
     */
    public static void validarEmail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("El email no es válido");
        }
    }

    /** Comprobación que el DNI es correcto
     * @param dni
     * @throws IllegalArgumentException si el DNI no es válido en cantidad de números y letra correspondiente.
     */
    public static void validarDni(String dni) {
        if (!dni.matches("[0-9]{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
    }

}