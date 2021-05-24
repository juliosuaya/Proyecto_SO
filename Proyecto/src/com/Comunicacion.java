package com;

public class Comunicacion extends Thread {

    public Usuario usuario;

    public Comunicacion(Usuario usuario){
        this.usuario=usuario;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semComunicacion.acquire(); // Espero a que hayan recursos
            this.usuario.mensaje="Vaya a vacunarse a ";
            System.out.println("Se comunica al usuario que puede ir a vacunarse");
            Main.semVacunacion.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //}
    }
}