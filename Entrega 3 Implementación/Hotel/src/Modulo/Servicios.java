package Modulo;

import java.time.LocalTime;
import java.util.ArrayList;

public interface Servicios {
	public abstract double GetTarifa();
	public abstract ArrayList<LocalTime> GetHorarios();
	String getSpend();
	

}
