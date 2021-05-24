package com;

public class Vacunacion extends Thread {
    public Usuario usuario;
    public ListaEspera listaDeEspera;
    public Vacunacion (Usuario usuario, ListaEspera listaDeEspera){
        this.usuario=usuario;
        this.listaDeEspera=listaDeEspera;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semVacunacion.acquire(); //Espero a que se le asigne fecha y lugar a un usuario para vacunarse
            this.usuario.vacunado=true;
            System.out.println("El usuario: "+this.usuario.getNombre()+ " fue vacunado");
            this.listaDeEspera.listaEspera.remove(this.usuario);
            Main.semVacunandose.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //}
    }

}