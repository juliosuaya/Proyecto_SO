package com;

public class Usuario {

    private String cedula;
    private String nombre;
    public int prioridad;

    public Usuario(String cedula, String nombre, int prioridad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.prioridad = prioridad % 4;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void iniciarVacunacion() {
            Main.imprimir("El usuario: " + this.getNombre() + " esta iniciando el proceso. En el tiempo " + Reloj.getTiempo());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", prioridad=" + prioridad +
                '}';
    }
}