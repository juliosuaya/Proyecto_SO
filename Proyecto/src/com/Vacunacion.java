package com;

import java.util.concurrent.Semaphore;

public class Vacunacion extends Thread {
    public Usuario usuario;
    public ListaEspera listaDeEspera;
    private Semaphore sem = new Semaphore(1);
    public Vacunacion (Usuario usuario, ListaEspera listaDeEspera){
        this.usuario=usuario;
        this.listaDeEspera=listaDeEspera;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            sem.acquire(); //Espero a que se le asigne fecha y lugar a un usuario para vacunarse
           // this.usuario.vacunado=true;
            System.out.println("El usuario: "+this.usuario.getNombre()+ " fue vacunado");
        //    this.listaDeEspera.listaEspera.remove(this.usuario);
            sem.release(); // Una vez vacunado se libera
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //}
    }

}