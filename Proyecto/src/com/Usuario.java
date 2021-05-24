package com;

public class Usuario extends Thread {

    private String cedula;
    private String nombre;
    private String apellido;
    private String barrio;
    private int edad;
    public String mensaje;
    public boolean vacunado;

    public Usuario(String cedula, String nombre, String apellido, String barrio, int edad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.barrio = barrio;
        this.edad = edad;
        this.vacunado=false;
        this.mensaje="";
    }


    public String getNombre(){
        return this.nombre + " " +this.apellido;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semAgenda.release();
            System.out.println("El usuario: "+this.getNombre()+ " esta iniciando el proceso");
            Main.semVacunandose.acquire();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //}
    }

}