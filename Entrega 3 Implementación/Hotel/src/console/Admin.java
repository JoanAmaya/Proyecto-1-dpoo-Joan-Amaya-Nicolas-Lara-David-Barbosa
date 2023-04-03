package console;
import Modulo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Admin {
    private Restaurante restaurante;
    private LocalTime HoraInicio;
    private LocalTime HoraFinal;
    private LocalTime HoraInicioSpa;
    private LocalTime HoraFinalSpa;
    private int PrecioSpa;
    private LocalTime HoraInicioGuia;
    private LocalTime HoraFinalGuia;
    private int PrecioGuia;
    private Spa spa;
    private GuiaTuristico guia;
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<String> valorextra;

    public Admin(
            LocalTime horaInicio,
            LocalTime horaFinal,
            LocalTime HoraInicioSpa,
            LocalTime HoraFinalSpa,
            int PrecioSpa,
            LocalTime HoraInicioGuia,
            LocalTime HoraFinalGuia,
            int PrecioGuia
    ) {
        HoraInicio = horaInicio;
        HoraFinal = horaFinal;
        this.HoraInicioSpa= HoraInicioSpa;
        this.HoraFinalSpa= HoraFinalSpa;
        this.PrecioSpa= PrecioSpa;
        this.HoraInicioGuia= HoraInicioGuia;
        this.HoraFinalGuia= HoraFinalGuia;
        this.PrecioGuia= PrecioGuia;
        restaurante= new Modulo.Restaurante(HoraInicio, HoraFinal);
        this.spa= new Modulo.Spa(PrecioSpa, HoraInicioSpa, HoraFinalSpa);
        this.guia= new Modulo.GuiaTuristico(PrecioGuia, HoraInicioGuia, HoraFinalGuia);
        habitaciones = new ArrayList<>();
        valorextra = new ArrayList<>();

    }

    public void CrearHabitacion(boolean ocupada, String TipoH, int Precio, String Complemento,int PrecioCom, ArrayList<String> DiasSemana, ArrayList<Integer> PrecioDias )
    {
        Habitacion nueva_habitacion= new Habitacion(ocupada,TipoH,Precio,Complemento,PrecioCom,DiasSemana,PrecioDias);
        boolean sentinel= true;
        System.out.println("La habitación necesita al menos una cama digite el ancho y alto a continuación");
        while (sentinel)
        {

            //Crear camas
            String ancho=input("Digite el ancho de la cama");
            String alto=input("Digite el alto de la cama");
            int valor_ancho= Integer.parseInt(ancho);
            int valor_alto= Integer.parseInt(alto);
            Cama nueva_cama= new Cama(valor_alto,valor_ancho);
            nueva_habitacion.agregarCamas(nueva_cama);
            String opcion=input("¿Desea agregar mas camas? (Y/N)");
            if(opcion.equals("N"))
            {
                sentinel=false;
            }


        }
        habitaciones.add(nueva_habitacion);

    }
    public void rangoAumentoPrecio(String FechaInicial, String FechaFinal, String precio)
    {
        valorextra.add(FechaInicial);
        valorextra.add(FechaFinal);
        valorextra.add(precio);

    }
    public void CrearHabitacionarchivo(boolean ocupada, String TipoH, int Precio, String Complemento,int PrecioCom, ArrayList<String> DiasSemana, ArrayList<Integer> PrecioDias, ArrayList<String> camas )
    {
        Habitacion nueva_habitacion= new Habitacion(ocupada,TipoH,Precio,Complemento,PrecioCom,DiasSemana,PrecioDias);
        for (String i: camas)
        {
            String valor1= i.split("\\.")[0];
            String valor2= i.split("\\.")[1];
            int valor_alto= Integer.parseInt(valor1);
            int valor_ancho= Integer.parseInt(valor2);
            Cama camaagregar= new Cama(valor_alto, valor_ancho);
            nueva_habitacion.agregarCamas(camaagregar);

        }
        habitaciones.add(nueva_habitacion);

    }

    //Crea un plato para anadirlo al restaurante se le tiene que solicitar al usuario una lista de lugares
    public void crearPlatos(String nombre, int Precio,  LocalTime HoraInicio, LocalTime HoraFinal, ArrayList<String> Lugares) {
        Plato platocrear= new Modulo.Plato(nombre, Precio, HoraInicio, HoraFinal);
        for (String i: Lugares) {
            platocrear.anadirlugares(i);
        }
        restaurante.anadirplatos(platocrear);
    }

    //Crea un plato para anadirlo al restaurante se le tiene que solicitar al usuario una lista de lugares

    public void crearbebidas(String nombre, int Precio,  LocalTime HoraInicio, LocalTime HoraFinal, ArrayList<String> Lugares) {
        Bebida bebidacrear= new Modulo.Bebida(nombre, Precio, HoraInicio, HoraFinal);
        for (String i: Lugares) {
            bebidacrear.anadirlugares(i);
        }

        restaurante.anadirbebias(bebidacrear);
    }
    //Cambiar las tarifas de los platos
    public void cambiartarifasplato (int Precio) {
        ArrayList<Plato> Menuplatos = restaurante.getPlatos();
        int j= 1;
        for (Plato i : Menuplatos)
        {
            System.out.println(j+"."+i.getNombreItem());
            j++;
        }
        String valor = input("Dígite el número dek plato al que va a cambiar la tarifa");
        int valor1 =Integer.parseInt(valor);
        Plato platoacambiar= Menuplatos.get(valor1);
        platoacambiar.setPrecio(Precio);


    }
    public void cambiartarifasbebida (int Precio) {
        ArrayList<Bebida> Menubebidas = restaurante.getBebidas();
        int j= 1;
        for (Bebida i : Menubebidas)
        {
            System.out.println(j+"."+i.getNombreItem());
            j++;
        }
        String valor = input("Dígite el número dek plato al que va a cambiar la tarifa");
        int valor1 =Integer.parseInt(valor);
        Bebida bebidaacambiar= Menubebidas.get(valor1);
        bebidaacambiar.setPrecio(Precio);


    }

    public void CambiarTarifaSpa (int Precio)
    {
        spa.setTarifa(Precio);
    }

    public void CambiarHorariosSpa (LocalTime HoraInicio, LocalTime HoraFinal)
    {
        spa.setHoraInicio(HoraInicio);
        spa.setHoraFinal(HoraFinal);
    }

    public void CambiarHorariosRestaurante(LocalTime HoraInicio, LocalTime HoraFinal)
    {
        restaurante.setHoraFinal(HoraFinal);
        restaurante.setHoraInicio(HoraInicio);
    }

    public void CambiarHorariosGuia(LocalTime HoraInicio, LocalTime HoraFinal)
    {
        guia.setHoraFinal(HoraFinal);
        guia.setHoraInicio(HoraInicio);
    }
    public void CambiartarifaGuia(int Precio)
    {
        guia.setTarifa(Precio);
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

    public Restaurante getRestaurante() {
        return restaurante;
    }
    public Spa getSpa(){
        return spa;
    }
    public GuiaTuristico getGuia(){
        return guia;
    }

    public ArrayList<Habitacion> getHabitaciones(){return habitaciones;}
}