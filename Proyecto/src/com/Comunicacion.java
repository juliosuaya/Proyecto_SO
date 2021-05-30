package com;

import java.util.concurrent.Semaphore;

public class Comunicacion extends Thread {

    public Usuario usuario;
    private Semaphore sem = new Semaphore(1);
    public Comunicacion(Usuario usuario){
        this.usuario=usuario;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            sem.acquire(); // Espero a que hayan recursos
           // this.usuario.mensaje="Vaya a vacunarse a ";
            System.out.println("Se comunica al usuario que puede ir a vacunarse");
            sem.release(); //

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //}
    }
}