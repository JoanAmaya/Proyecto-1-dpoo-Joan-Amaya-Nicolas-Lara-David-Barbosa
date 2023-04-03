package console;

import Modulo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.*;

public class Employee {
    private ArrayList<Grupo> grupos;
    private Restaurante restaurante;
    private Spa spa;
    private GuiaTuristico guia;



    public Employee(ArrayList<Grupo> grupos, Restaurante restaurante, Spa spa, GuiaTuristico guia) {
        this.grupos = grupos;
        this.restaurante = restaurante;
        this.spa = spa;
        this.guia = guia;
    }

    private Huesped getHuesped(){
        for (int index = 0; index < grupos.size(); index ++)
        {
            print(index + ": " +  grupos.get(index).getHuespedPrincipal().getNombre());
        }
        ArrayList<Huesped> grupos = this.grupos.get(Integer.parseInt(input("selecciona el grupo: "))).getHuespedes();
        for (int index = 0; index < grupos.size(); index ++)
        {
            print(index + ": " +  grupos.get(index));
        }
        return grupos.get(Integer.parseInt(input("selecciona el huesped: ")));
    }
    public void addSpaUsage()
    {
        Huesped huesped = getHuesped();
        if (input("desea cancelar el servicio ya? (Y/N): ").equals("Y")) print(spa.getSpend());
        else huesped.getConsumoHuesped().anadirservicios(spa);
    }

    public void addRestaurantUsage()
    {
        Huesped huesped = getHuesped();
        boolean sentinel= true;
        while (sentinel)
        {
            String PoB = input("Escriba si desea pedir 'platos' o 'bebidas'");
            if (PoB.equals("platos"))
            {
                print("¿Que platos desea ordenar?");
                ArrayList<Plato> platos = restaurante.getPlatos();
                for (int index = 0; index < platos.size(); index ++)
                {
                    print(index + ". " + platos.get(index).getNombreItem() + ": $" + platos.get(index).getPrecio(),
                            "con rango de horas",
                            "<" + platos.get(index).getHoraInicio() + "-" + platos.get(index).getHoraFinal() + ">");
                }
                String nombrePlato = input("Digite el nombre del plato que va a ordenar");
                LocalTime Hora = LocalTime.parse(input("Digite la hora a la que el huesped ordena el plato"));
                String Lugar = input("Digite el lugar en el que quiere ordenar");
                restaurante.anadirpedidosplatos(nombrePlato, Hora, Lugar);
            }
            else if (PoB.equals("bebidas"))
            {
                print("¿Que bebidas desea ordenar?");
                ArrayList<Bebida> bebidas = restaurante.getBebidas();
                int j1= 0;
                for (int index = 0; index < bebidas.size(); index ++)
                {
                    print(index + ". " + bebidas.get(index).getNombreItem() + ": $" + bebidas.get(index).getPrecio(),
                            "con rango de horas",
                            "<" + bebidas.get(index).getHoraInicio() + "-" + bebidas.get(index).getHoraFinal() + ">");
                }
                String nombreBebida = input("Digite el nombre de la bebida que va a ordenar: ");
                LocalTime Hora = LocalTime.parse(input("Digite la hora (in-range) a la que el huesped ordena el plato: "));
                String LugarB = input("Digite el lugar en el que quiere ordenar");
                restaurante.anadirpedidosbebida(nombreBebida, Hora, LugarB);
            }

            print("¿Va a ordenar mas platos o bebidas?");
            String opcion = input("digite 'si' si quiere seguir ordenando de lo contrario digite 'no'");
            if (opcion.equals("no"))  sentinel=false;
        }
        String payNow = input("¿Desea pagar ya responda con Y afirmativamente o N si desea pagar en el checkout?");
        if (payNow.equals("Y")) print(restaurante.getSpend());
        else if (payNow.equals("N")) huesped.getConsumoHuesped().anadirservicios(restaurante);
    }

    public void addGuideUsage()
    {
        //Obtiene la información del grupo
        print("¿De que grupo es el usuario que va a ordenar?");
        for (int index = 0; index < grupos.size(); index ++)
        {
            print(index + ". " + grupos.get(index).getId_grupo() + " con Huesped principal de " + grupos.get(index).getHuespedPrincipal().getNombre());
        }
        Grupo grupo = grupos.get(Integer.parseInt(input("Digite el número del grupo ")));
        String payNow = input("¿Desea pagar ya responda con Y afirmativamente o N si desea pagar en el checkout?");
        if (payNow.equals("Y")) print(guia.getSpend());
        else if (payNow.equals("N")) grupo.anadirguia(guia);
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
}