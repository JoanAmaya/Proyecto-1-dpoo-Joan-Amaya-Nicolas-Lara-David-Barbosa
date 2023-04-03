package Modulo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;

public class ReservaHabitacion
{
	private LocalDate fechaInicio;
	private LocalDate fechaFinal;
	private int idReserva;
	private ArrayList<Habitacion> habitaciones;
	private Grupo grupo;
	HashMap<LocalDate, Integer> precio_intervalo = new HashMap<LocalDate, Integer>();
	
	public ReservaHabitacion(LocalDate fechaInicio, LocalDate fechaFinal, ArrayList<Habitacion> habitaciones, Grupo grupo) {
		Random random = new Random();
		idReserva = random.nextInt(100000000);
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.habitaciones = habitaciones;
		this.grupo = grupo;
	}
	
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	public int getNumeroHabitaciones(ArrayList<Habitacion> habitaciones) {
		return habitaciones.size();
	}
	
	public void agregarHabitacion(Habitacion habitacion) {
		habitaciones.add(habitacion);
	}

		
	public void setPrecioIntervalo(int precio){  /* HashMap en el que las llaves son las fechas del intervalo asignado por don cangrejo y el valor es su respectivo precio. Ya está incluido el quedarse con el precio menor */
        long numDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinal);
        for (int i = 0; i <= numDias; i++) {
            LocalDate fechaActual = fechaInicio.plusDays(i);
            boolean contiene = precio_intervalo.containsKey(fechaActual);
            if (contiene == true){
            	int precio_0 = precio_intervalo.get(fechaActual);
            			if (precio < precio_0)
            				precio_intervalo.put(fechaActual, precio);}
            else{
            	precio_intervalo.put(fechaActual, precio);}  
        }
	}
	
	public int getMenorPrecioIntervalo() {
		
		int menor = 0;
		for (int valor : precio_intervalo.values()) {
			menor = valor;
			break;}
				
		for (Map.Entry<LocalDate, Integer> entry: precio_intervalo.entrySet()) 
		{
			int precio = entry.getValue();		
			if (precio < menor) 
				menor = precio;
		}
		return menor;
	}
	
	public int getPrecioMapa(HashMap<String, Integer> mapa) {
		int retorno = 0;
		for (int valor : mapa.values()) {
			retorno = valor;
			break;}	
		return retorno;
	}
	public ArrayList<Habitacion> getHabitaciones(){return habitaciones;}
	
	public ArrayList<LocalDate> getIntervaloFechas() {
		ArrayList<LocalDate> listaFechas = new ArrayList<>();
        long numDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinal);
        for (int i = 0; i <= numDias; i++) {
            LocalDate fechaActual = fechaInicio.plusDays(i);
            listaFechas.add(fechaActual);
        }
        return listaFechas;
	}
	
	
	public ArrayList<DayOfWeek> getDiasSemanaIntervalo() {  
		ArrayList<DayOfWeek> retorno = new ArrayList<DayOfWeek>();
		ArrayList<LocalDate> listafechas = getIntervaloFechas();
		for (LocalDate fecha:listafechas) {
			retorno.add(fecha.getDayOfWeek());
		}
		return retorno;
	}
	
	public int getPrecioDiasEspecialesIntervalo(Habitacion pieza) {     /* Calcula la suma de las tarifas de los días especiales */
		ArrayList<DayOfWeek> dias = getDiasSemanaIntervalo();
		HashMap<DayOfWeek, Integer> dia_semana = pieza.dia_semana;	
		int precio = 0;
		
		for (DayOfWeek day:dias) 
		{
			if (dia_semana.containsKey(day)) {
				precio += dia_semana.get(day);}
		}
		return precio;		
	}
	

	public int calcularPrecioHabitacion() {
		int precio_base = getMenorPrecioIntervalo();
		
		for (Habitacion i: habitaciones) {
			
			int precioDiasSemana = getPrecioDiasEspecialesIntervalo(i);
			precio_base += precioDiasSemana;
			
			int precio_tipo = getPrecioMapa(i.tipo);
			precio_base += precio_tipo;
			
			int precio_valor = getPrecioMapa(i.valor_agregado);
			precio_base += precio_valor;
			
		}
		return precio_base;
	}
	
	
}
