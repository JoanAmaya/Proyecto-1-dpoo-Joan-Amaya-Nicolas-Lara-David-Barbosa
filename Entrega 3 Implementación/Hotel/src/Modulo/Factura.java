package Modulo;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException; 


public class Factura {
	private String archivo;
	private ReservaCompleta grupo;

	public Factura(String archivo, ReservaCompleta grupo) {
		this.archivo = archivo;
		this.grupo = grupo;
	}

	public String textofactura() {
		String texto= "La factura total es de \n";
		double total=0;
		ArrayList<Huesped> listahuesped= grupo.getrHabitacion().getGrupo().getHuespedes();
		total += grupo.calcularCostoTotalEstadia();
		for (Huesped i: listahuesped) {
			texto = texto.concat("el huesped con nombre "+i.getNombre()+" con consumo de "+i.getConsumo()+"\n" );
			total += i.getConsumo();
			for (Servicios bill : i.getConsumoHuesped().getServicio())
			{
				texto = texto.concat(bill.getSpend());
			}

		}
		texto = texto.concat("El total de la factura es de: " + total);
		return texto;
	}

	public void guardarFactura() {
		try {
			File myObj = new File(archivo+grupo.getrHabitacion().getGrupo().getId_grupo()+".txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			FileWriter factura = new FileWriter(archivo+grupo.getrHabitacion().getGrupo().getId_grupo()+".txt");
			String texto= textofactura();
			factura.write(texto);
			factura.close();
			System.out.println("Successfully wrote to the file.");

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
