package Modulo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.time.DayOfWeek;


public class a {

    public static void main(String[] args) {
        LocalDate fechaInicio = LocalDate.of(2022, 1, 1);
        LocalDate fechaFin = LocalDate.of(2022, 1, 31);

        ArrayList<LocalDate> listaFechas = new ArrayList<>();
        long numDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        for (int i = 0; i <= numDias; i++) {
            LocalDate fechaActual = fechaInicio.plusDays(i);
            listaFechas.add(fechaActual);
        }
        
        ArrayList<DayOfWeek> a = new ArrayList<DayOfWeek>();
        for (LocalDate fecha:listaFechas) {
        	a.add(fecha.getDayOfWeek());
        }

        System.out.println(a);
    }
}