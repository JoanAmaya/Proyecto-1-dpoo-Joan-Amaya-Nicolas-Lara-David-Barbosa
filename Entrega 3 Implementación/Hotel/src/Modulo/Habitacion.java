package Modulo;
import java.util.*;
import java.time.DayOfWeek;


public class Habitacion
{
	private int numeroCuarto;
	private ArrayList<Cama> camas;
	private boolean ocupada;
	HashMap<String, Integer> tipo = new HashMap<String, Integer>(); /* El tipo puede ser estandar, suite o suite doble */
	HashMap<DayOfWeek, Integer> dia_semana = new HashMap<DayOfWeek, Integer>(); /* Viernes, sabado, domingo y su tarifa*/
	HashMap<String, Integer> valor_agregado = new HashMap<String, Integer>(); /* Balcón, vista y/o cocina integrada con su respectivo precio */

	public Habitacion(
			boolean ocupada,
			String tipoH,
			int Precio,
			String Complemento,
			int PrecioCom,
			ArrayList<String> DiasSemana,
			ArrayList<Integer> PrecioDias
	) {
		Random random= new Random();
		tipo.put(tipoH, Precio);
		valor_agregado.put(tipoH, PrecioCom);
		numeroCuarto = random.nextInt(100000000);
		this.camas = new ArrayList<>();
		this.ocupada = ocupada;

		for (int i=0; i<DiasSemana.size(); i++) {
			String Diatemp= DiasSemana.get(i);
			int preciotemp = PrecioDias.get(i);
			DayOfWeek dayOfWeekObject = DayOfWeek.valueOf(Diatemp.toUpperCase());
			dia_semana.put(dayOfWeekObject, preciotemp);

		}
	}


	public int getNumeroCuarto() {
		return numeroCuarto;
	}

	public void setNumeroCuarto(int numeroCuarto) {
		this.numeroCuarto = numeroCuarto;
	}


	public ArrayList<Cama> getCamas() {
		return camas;
	}

	public void agregarCamas(Cama cama) {
		camas.add(cama);
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}


	public HashMap<String, Integer> getTipo() {
		return tipo;
	}

	public void setTipo(String pTipo, int precio) {
		boolean vacio = tipo.isEmpty();
		if (vacio == true)
			tipo.put(pTipo, precio);
		else {
			int valor = tipo.get(pTipo);
			if (valor < precio)
				tipo.put(pTipo, valor);
		}
	}

	public HashMap<DayOfWeek, Integer> getDiaSemana() {
		return dia_semana;
	}

	public void setDiaSemana(DayOfWeek dia, int precio) {
		boolean contiene = dia_semana.containsKey(dia);
		if (contiene == true) {
			int precio_0 = dia_semana.get(dia);
			if (precio < precio_0)
				dia_semana.put(dia, precio);
		} else {
			dia_semana.put(dia, precio);
		}
	}

	public HashMap<String, Integer> getValor_agregado() {
		return valor_agregado;
	}

	public void setValor_agregado(String valor, int precio) {
		boolean contiene = valor_agregado.containsKey(valor);
		if (contiene == true) {
			int precio_0 = valor_agregado.get(valor);
			if (precio < precio_0)
				valor_agregado.put(valor, precio);
		} else {
			valor_agregado.put(valor, precio);
		}
	}


	public int cantidadCamas(ArrayList<Cama> camas) {
		return camas.size();
	}

	public HashMap<String, Integer> cantidadPersonas(ArrayList<Cama> camas){  /* Cantidad de personas que caben en la habitación */

		HashMap<String, Integer> retorno = new HashMap<String, Integer>();

		int chamaco = 0;
		int adulto = 0;

		for (Cama i: camas) {
			int alto = i.getAlto();
			int ancho = i.getAncho();
			HashMap<String, Integer> mapa = i.totalPersonas(alto, ancho);
			chamaco += mapa.get("infantes");
			adulto += mapa.get("adultos");
		}

		retorno.put("infantes", chamaco);
		retorno.put("adultos", adulto);

		return retorno;
	}

}