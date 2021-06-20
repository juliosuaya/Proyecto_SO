package com;

public class Usuario {

    private String nombre;
    private String cedula;
    private boolean esMedico;
    private int edad;
    private boolean tienePatologias;

    public Usuario(String nombre,String cedula,boolean esMedico,int edad, boolean tienePatologias) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.esMedico = esMedico;
        this.edad = edad;
        this.tienePatologias = tienePatologias;

    }

    public String getNombre(){
        return this.nombre;
    }

    public void iniciarVacunacion() {
            Main.imprimir("El usuario: " + this.getNombre() + " esta iniciando el proceso. En el tiempo " + Reloj.getTiempo());
    }

    public int obtenerPriorodad(){
        if(esMedico){
            return 0;
        }
        if(tienePatologias){
            return 1;
        }
        if(edad >= 65 ){
            return 2;
        }
        return 3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", esMedico=" + esMedico +
                ", edad=" + edad +
                ", tienePatologias=" + tienePatologias +
                '}';
    }
}