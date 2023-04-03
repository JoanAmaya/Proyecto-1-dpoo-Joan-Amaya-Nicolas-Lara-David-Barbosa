package Modulo;

import java.time.LocalTime;

public class GuiaTuristico {
    private int Tarifa;
    private LocalTime HoraInicio;
    private LocalTime HoraFinal;
    public GuiaTuristico(int tarifa, LocalTime horaInicio, LocalTime horaFinal) {
        Tarifa = tarifa;
        HoraInicio = horaInicio;
        HoraFinal = horaFinal;
    }
    public int getTarifa() {
        return Tarifa;
    }
    public void setTarifa(int tarifa) {
        Tarifa = tarifa;
    }
    public LocalTime getHoraInicio() {
        return HoraInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        HoraInicio = horaInicio;
    }
    public LocalTime getHoraFinal() {
        return HoraFinal;
    }
    public void setHoraFinal(LocalTime horaFinal) {
        HoraFinal = horaFinal;
    }

    public String getSpend(){
        return "El valor del guia es: " + Tarifa;
    }
    
}