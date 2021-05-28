package com;

public class Usuario {

    private String cedula;
    private String nombre;
    public int prioridad;

    public Usuario(String cedula, String nombre, int prioridad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.prioridad = prioridad % 3;
        System.out.println("El usuario: "+this.getNombre()+ " esta iniciando el proceso. En el tiempo " + Reloj.getTiempo());
    }

    public String getNombre(){
        return this.nombre;
    }
}