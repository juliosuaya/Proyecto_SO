public class main {

    public static void main(String[] args) {



    }


    public static class Usuario implements Runnable {

        private String cedula;
        private String nombre;
        private String apellido;
        private String barrio;
        private int edad;
        private boolean patologias;

        public Usuario(String cedula, String nombre, String apellido, String barrio, int edad, boolean patologias) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.barrio = barrio;
            this.edad = edad;
            this.patologias = patologias;
        }

        @Override
        public void run() {
            while(true) {

            }
        }

    }


    public static class Agenda implements Runnable{
//te pide los datos y te mete en la lista de espera
        private String nombre;
        private String apellido;
        private int edad;
        private int barrio;
        private String cedula;

        @Override
        public void run() {
            while(true) {

            }
        }
    }




    public static class ListaDeEspera implements Runnable {
//Gestiona la lista (es unica)

        @Override
        public void run() {
            while(true) {

            }
        }

    }

    public static class Recursos implements Runnable {


        @Override
        public void run() {
            while(true) {

            }
        }

    }


    public static class Comunicacion implements Runnable {


        @Override
        public void run() {
            while(true) {

            }
        }
    }


    public static class Vacunacion implements Runnable {


        @Override
        public void run() {
            while(true) {

            }
        }

    }

}
