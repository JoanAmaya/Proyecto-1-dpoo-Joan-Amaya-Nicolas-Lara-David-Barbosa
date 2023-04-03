package console;

import Modulo.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receptionist {
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Grupo> grupos;
    private ArrayList<ReservaHabitacion> reservasH;
    private ArrayList<ReservaCompleta> reservasC;
    private HashMap<Grupo, ReservaCompleta> reservaCompleta;

    public Receptionist(ArrayList<Habitacion> habitaciones){
        this.habitaciones = habitaciones;
        grupos = new ArrayList<>();
        reservasH = new ArrayList<>();
        reservasC = new ArrayList<>();
        reservaCompleta = new HashMap<>();
    }
    public Grupo huespedes(){
        for (int index = 0; index < grupos.size(); index ++)
        {
            print(index, ". líder:", grupos.get(index).getHuespedPrincipal().getNombre());
        }
        return grupos.remove(Integer.parseInt(input("Seleccione el grupo")));

    }
    public Grupo createGroup () {
        String[] infoHuesped = input("Información huesped (nombre;edad;documento;correo): ").split(";");
        ArrayList<Huesped> huespedes = new ArrayList<>();
        huespedes.add(new Huesped(infoHuesped[0], Integer.parseInt(infoHuesped[1]), Long.parseLong(infoHuesped[2]), infoHuesped[3]));
        boolean is_adding = true;
        while (is_adding) {
            print("1. Añadir huésped");
            print("2. Cerrar");
            if (Integer.parseInt(input("Opción: ")) == 1) {
                infoHuesped = input("Información huesped (nombre;edad;documento;correo): ").split(";");
                huespedes.add(new Huesped(infoHuesped[0], Integer.parseInt(infoHuesped[1]), Integer.parseInt(infoHuesped[2]), infoHuesped[3]));
            } else is_adding = false;
        }
        Grupo grupo = new Grupo(huespedes, huespedes.get(0));
        grupos.add(grupo);
        return grupo;
    }

    ArrayList<Grupo> getGroups(){
        return grupos;
    }
    public static void print(Object ... values)
    {
        String message = "";
        for (Object value: values)
        {
            message = message.concat(value + " ");
        }
        System.out.println(message);
    }

    public static String input(String prompt)
    {
        System.out.print(prompt);
        try
        {
            return (new BufferedReader(new InputStreamReader(System.in))).readLine();
        }
        catch (IOException ignored)
        {
            return "";
        }
    }
    public static String input()
    {
        System.out.print("");
        try
        {
            return (new BufferedReader(new InputStreamReader(System.in))).readLine();
        }
        catch (IOException ignored)
        {
            return "";
        }
    }
    //Crear Reserva
    public void crearReserva(LocalDate fechaInicio, LocalDate fechaFinal,Grupo grupo)
    {

        System.out.println("Las habitaciones tienen el número de personas, recuerde que 2 niños equivalen a un adulto");
        int TotalRemanente= grupo.totaladultosgrupo()+(int)grupo.totalninosgrupo()/2;
        System.out.println("El total de personas en su grupo es: "+TotalRemanente);
        System.out.println("Las habitaciones son las siguientes: ");
        int j=0;
        boolean sentinel = true;
        ArrayList<Habitacion> habitacionesseleccionadas=new ArrayList<>();
        while(sentinel)
        {
            for (Habitacion i: habitaciones)
            {
                if (!i.isOcupada())
                {
                    ArrayList<Cama> obtenercamas= i.getCamas();
                    HashMap<String, Integer> personas= i.cantidadPersonas(obtenercamas);
                    int totalpersonas=personas.get("adultos");
                    HashMap<String, Integer> tipo = i.getTipo();
                    String tipoH= "";
                    int precio=0;
                    for (Map.Entry<String, Integer> entry: tipo.entrySet())
                    {
                        String temp = entry.getKey();
                        int tempp= entry.getValue();
                        temp.concat(tipoH);
                        precio+=tempp;
                        System.out.println(j+".La habitacion numero "+i.getNumeroCuarto()+" con capacidad de " +totalpersonas+" y de tipo " +temp+ " y con precio de "+precio);
                    }

                    

                }
                j+=1;


            }
            String valorinput=input("Digite el número del inicio de la habitación que quiere seleccionar");
            int valorhabitacion= Integer.parseInt(valorinput);
            Habitacion habitacionsel= habitaciones.get(valorhabitacion);
            habitacionsel.setOcupada(true);
            habitacionesseleccionadas.add(habitacionsel);
            ArrayList<Cama> obtenercamas1= habitacionsel.getCamas();
            HashMap<String, Integer> personas1= habitacionsel.cantidadPersonas(obtenercamas1);
            int personasacaparar=personas1.get("adultos");
            TotalRemanente-=personasacaparar;
            if (TotalRemanente>0)
            {
                sentinel=true;
                System.out.println("Por favor seleccione mas habitaciones porque su grupo no cabe solamente en la seleccionada");

            }
            else {
                sentinel=false;
            }

        }
        ReservaHabitacion nuevareserva= new ReservaHabitacion(fechaInicio, fechaFinal, habitacionesseleccionadas, grupo );
        reservasH.add(nuevareserva);
        ReservaCompleta reservanueva= new ReservaCompleta(nuevareserva);
        reservasC.add(reservanueva);
        reservaCompleta.put(grupo, reservanueva);



    }

    public ArrayList<Habitacion> getHabitaciones(){
        return habitaciones;
    }
    public ReservaCompleta getReservasC(Grupo grupo) {
        return reservaCompleta.get(grupo);
    }
    public ArrayList<ReservaHabitacion> getReservasH(){
        return reservasH;
    }
    

    
}