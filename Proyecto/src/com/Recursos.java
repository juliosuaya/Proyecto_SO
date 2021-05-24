package com;

public class Recursos extends Thread {

    public String centro;
    public String vacunador;
    public String vacuna;

    public Recursos(String centro, String vacunador, String vacuna){
        this.centro=centro;
        this.vacunador=vacunador;
        this.vacuna=vacuna;
    }

    @Override
    public void run() {
        //while(true) {
        try {
            Main.semRecursos.acquire();
            System.out.println("El centro: "+this.centro+" esta disponible, con el vacunador: "+this.vacunador+" y la vacuna: "+this.vacuna);
            Main.semComunicacion.release();

        } catch (Exception e) {
        }
        //Espero a que se agende a la lista
        //}
    }

}