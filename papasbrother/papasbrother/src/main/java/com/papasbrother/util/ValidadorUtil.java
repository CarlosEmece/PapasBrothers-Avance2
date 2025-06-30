package com.papasbrother.util;

public class ValidadorUtil {
    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^\\d{9,15}$");
    }

    public static boolean esTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
