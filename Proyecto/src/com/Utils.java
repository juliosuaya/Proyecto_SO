package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Utils {
    public static ArrayList<ArrayList<Usuario>> cargarUsuarios() {
        ArrayList<ArrayList<Usuario>> arr = new  ArrayList<ArrayList<Usuario>>();
        File f = new File("Usuarios.txt");
        FileReader fr = null;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int i = 0;
            while(line != null) {
                String[] content = line.split(",");
                arr.add(new ArrayList<Usuario>());
                for(int x = 0; x < content.length/5; x++) {
                    arr.get(i).add(new Usuario(content[5*x].strip(), content[1+(5*x)].strip(), Boolean.valueOf(content[2+(5*x)].strip()), Integer.valueOf(content[3+(5*x)].strip()),Boolean.valueOf(content[4+(5*x)].strip())));
                }
                i++;
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;

    }
}
