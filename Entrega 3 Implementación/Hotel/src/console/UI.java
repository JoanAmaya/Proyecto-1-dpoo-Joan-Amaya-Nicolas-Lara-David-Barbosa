package console;

import Modulo.Factura;
import Modulo.Grupo;
import Modulo.Habitacion;
import Modulo.ReservaHabitacion;

import java.io.*;
import java.time.*;
import java.util.*;

public class UI {

    private static String[] args;
    private static String login;
    private final static User USER = new User("src/Users.txt");
    private static Admin admin;
    private static Receptionist receptionist;
    private static Employee employee;


    public static void main(String[] args)
    {
        UI.args = args;
        boolean in_login = true;
        while (in_login)
        {
            login = input("usuario: ");
            String passwd = input("contraseña: ");
            if (USER.isAdministrator(login, passwd)) {
                print("=== administrator login success ===");
                admin = new Admin(
                        LocalTime.parse(input("Hora inicio restaurante: ")),
                        LocalTime.parse(input("Hora final restaurante: ")),
                        LocalTime.parse(input("Hora inicio spa: ")),
                        LocalTime.parse(input("Hora final spa: ")),
                        Integer.parseInt(input("Precio spa: ")),
                        LocalTime.parse(input("Hora inicio guia: ")),
                        LocalTime.parse(input("Hora final guia: ")),
                        Integer.parseInt(input("Precio guia: "))
                );
                crearHabitaciones();
                adminOptions();
                in_login = false;
            }
            else if (USER.isReceptionist(login, passwd)) {
                print("=== receptionist login success ===");
                if (admin == null) print("Habitaciones no cargadas");
                else {
                    ArrayList<Habitacion> desocupadas = new ArrayList<>();
                    for (Habitacion habitacion : admin.getHabitaciones()){
                        if (!habitacion.isOcupada()) desocupadas.add(habitacion);
                    }
                    receptionist = new Receptionist(desocupadas);
                    receptionistOptions();
                    in_login = false;
                }
            }
            else if (USER.isEmployee(login, passwd)) {
                print("=== employee login success ===");
                if (receptionist == null || admin == null) print("Grupos o servicios no creados.");
                else if (receptionist.getGroups().isEmpty()) print("Grupos no creados.");
                else {
                    employee = new Employee(receptionist.getGroups(), admin.getRestaurante(), admin.getSpa(), admin.getGuia());
                    employeeOptions();
                    in_login = false;
                }
            }
            else {
                print("usuario o contraseña son incorrectos");
                if (input("¿desea reintentar(Y/N)? : ").equals("N")) return;
            }
        }
    }

    private static void crearHabitaciones() {
        boolean inOptions = true, notCreated = true;
        while (inOptions){
            print("===========================================================");
            print("1. cargar habitaciones por archivo");
            print("2. crear habitación");
            print("3. continue (debe al menos haberse creado una habitación).");
            switch (Integer.parseInt(input("Opción: ")))
            {
                case 1 -> {
                    BufferedReader reader = open(input("file name: "));
                    assert reader != null;
                    for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                        String[] line = it.next().split(",");
                        // ocupada,tipoH,precio,complemento,precioComp,dia1:dia2:dia3,precio1;precio2;precio3,123.213;123.12
                        boolean ocupada = Boolean.parseBoolean(line[0]);
                        String tipoH = line[1];
                        int precio = Integer.parseInt(line[2]);
                        String complemento = line[3];
                        int precioComp = Integer.parseInt(line[4]);
                        ArrayList<String> diasSemana = new ArrayList<>(Arrays.stream(line[5].split(":")).toList());
                        ArrayList<Integer> precios = new ArrayList<>();
                        for (String price : line[6].split(";")){
                            precios.add(Integer.parseInt(price));
                        }
                        admin.CrearHabitacionarchivo(
                                ocupada,
                                tipoH,
                                precio,
                                complemento,
                                precioComp,
                                diasSemana,
                                precios,
                                new ArrayList<>(Arrays.stream(line[7].split(";")).toList())
                        );
                    }

                    try { reader.close(); }
                    catch (IOException ignored) {}
                    notCreated = false;

                }
                case 2 -> {
                    String[] line = input("habitacion en fmt (tipoH,precio,complemento,precioComp,dia1:dia2:dia3,precio1;precio2;precio3,123.213;123.12): ").split(",");
                    boolean ocupada = false;
                    String tipoH = line[0];
                    int precio = Integer.parseInt(line[1]);
                    String complemento = line[2];
                    int precioComp = Integer.parseInt(line[3]);
                    ArrayList<String> diasSemana = new ArrayList<>(Arrays.stream(line[4].split(":")).toList());
                    ArrayList<Integer> precios = new ArrayList<>();
                    for (String price : line[5].split(";")){
                        precios.add(Integer.parseInt(price));
                    }
                    admin.CrearHabitacion(
                            ocupada,
                            tipoH,
                            precio,
                            complemento,
                            precioComp,
                            diasSemana,
                            precios
                    );
                    notCreated = false;
                }
                case 3 -> {
                    if (notCreated) print("debe por lo menos haber una habitación creada");
                    else {
                        String startDate = input("fecha de inicio en formato YYYY-MM-DD: ");
                        String finalDate = input("fecha de fin en formato YYYY-MM-DD: ");
                        String precioExtra = input("ingrese el precio extra en formato 0.XX: ");
                        admin.rangoAumentoPrecio(startDate, finalDate, precioExtra);
                        inOptions = false;
                    }
                }
                default -> print("no valid option");
            }
        }

    }

    private static void adminOptions()
    {
        boolean inOptions = true;
        while (inOptions)
        {
            print("=================================");
            print("1. Crear plato.");
            print("2. Crear bebida.");
            print("3. Cambiar tarifas.");
            print("4. Cargar platos por archivo");
            print("5. Cargar bebidas por archivo");
            print("6. Añadir camas");
            print("7. Logout.");
            print("8. Exit.");
            switch (Integer.parseInt(input("Opción: "))) {
                case 1 -> {
                    String nombre = input("Nombre del plato: ");
                    int precio = Integer.parseInt(input("Precio del plato: "));
                    LocalTime horaInicio = LocalTime.parse(input("Hora inicio del plato: "));
                    LocalTime horaFinal = LocalTime.parse(input("Hora final del plato: "));
                    ArrayList<String> lugares = new ArrayList<>(Arrays.asList(input("Lugares: ").split(";")));
                    admin.crearPlatos(nombre, precio, horaInicio, horaFinal, lugares);
                }
                case 2 -> {
                    String nombre = input("Nombre de la bebida: ");
                    int precio = Integer.parseInt(input("Precio de la bebida: "));
                    LocalTime horaInicio = LocalTime.parse(input("Hora inicio de la bebida: "));
                    LocalTime horaFinal = LocalTime.parse(input("Hora final de la bebida: "));
                    ArrayList<String> lugares = new ArrayList<>(Arrays.asList(input("Lugares: ").split(";")));
                    admin.crearbebidas(nombre, precio, horaInicio, horaFinal, lugares);
                }
                case 3 -> {
                    print("=================================");
                    print("1. Cambiar tarifa platos.");
                    print("2. Cambiar tarifa bebidas.");
                    print("3. Cambiar tarifa spa.");
                    print("4. Cambiar horarios spa.");
                    print("5. Cambiar horarios restaurante.");
                    print("6. Cambiar horarios guia");
                    print("7. Cambiar tarifa guia.");
                    changeRates(admin, Integer.parseInt(input("Option: ")));
                }
                case 4 -> loadPlatosByFile(admin, input("path: "));
                case 5 -> loadBebidasByFile(input("path: "));
                case 6 -> { print("Successful logout of user: " + login); inOptions = false; main(args); }
                case 8 -> System.exit(0);
                default -> print("la opción no es permitida");
            }
        }
    }

    private static void loadBebidasByFile(String path) {
        try
        {
            BufferedReader reader = open(path);
            assert reader != null;
            for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                String line = it.next();
                String[] wrapLines = line.split(", ");
                admin.crearbebidas(
                        wrapLines[0],
                        Integer.parseInt(wrapLines[3]),
                        LocalTime.parse(wrapLines[1]),
                        LocalTime.parse(wrapLines[2]),
                        new ArrayList<>(Arrays.asList(wrapLines[4].split("; ")))
                );
                print(Arrays.toString(wrapLines));
            }
            reader.close();
        }
        catch (IOException error) {
            print("WARNING: an error occurred in reading");
            error.printStackTrace();
        }
    }

    private static void loadPlatosByFile(Admin admin, String path) {
        try
        {
            BufferedReader reader = open(path);
            assert reader != null;
            for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                String line = it.next();
                String[] wrapLines = line.split(", ");
                admin.crearPlatos(
                        wrapLines[0],
                        Integer.parseInt(wrapLines[3]),
                        LocalTime.parse(wrapLines[1]),
                        LocalTime.parse(wrapLines[2]),
                        new ArrayList<>(Arrays.asList(wrapLines[4].split("; ")))
                );
            }
            reader.close();
        }
        catch (IOException error) {
            print("WARNING: an error occurred in reading");
            error.printStackTrace();
        }
    }

    public static void changeRates(Admin admin,int option)
    {
        switch (option)
        {
            case 1 -> admin.cambiartarifasplato(Integer.parseInt(input("nuevo precio plato: ")));
            case 2 -> admin.cambiartarifasbebida(Integer.parseInt(input("nuevo precio bebidas: ")));
            case 3 -> admin.CambiarTarifaSpa(Integer.parseInt(input("nuevo precio spa: ")));
            case 4 -> admin.CambiarHorariosSpa(
                    LocalTime.parse(input("nuevo horario inicio: ")),
                    LocalTime.parse(input("nuevo horario final: "))
            );
            case 5 -> admin.CambiarHorariosRestaurante(
                    LocalTime.parse(input("nuevo horario inicio: ")),
                    LocalTime.parse(input("nuevo horario final: "))
            );
            case 6 -> admin.CambiarHorariosGuia(
                    LocalTime.parse(input("nuevo horario inicio: ")),
                    LocalTime.parse(input("nuevo horario final: "))
            );
            case 7 -> admin.CambiartarifaGuia(Integer.parseInt(input("nuevo precio guia: ")));

        }
    }
    private static void receptionistOptions() {
        boolean inOptions = true;
        while (inOptions)
        {
            print("====================");
            print("1. Crear grupo huéspedes");
            print("2. Mostrar habitaciones desocupadas");
            print("3. Mostrar habitaciones ocupadas");
            print("4. Checkout.");
            print("5. Logout.");
            switch (Integer.parseInt(input("Opción: ")))
            {
                case 1 -> {

                    Grupo grupo = receptionist.createGroup();
                    receptionist.crearReserva(
                            LocalDate.parse(input("fecha inicio")),
                            LocalDate.parse(input("fecha final")),
                            grupo
                    );

                }
                case 2 -> {
                    for (int index = 0; index < receptionist.getHabitaciones().size(); index ++){
                        Habitacion habitacion = receptionist.getHabitaciones().get(index);
                        print(
                                index,
                                ".",
                                "tipo:",
                                habitacion.getTipo(),
                                "dia semana:",
                                habitacion.getDiaSemana(),
                                "camas:",
                                habitacion.getCamas(),
                                "No.",
                                habitacion.getNumeroCuarto()
                        );
                    }
                }
                case 3 -> {
                    ArrayList<ReservaHabitacion> reservaHabitacion = receptionist.getReservasH();
                    for (int index_1 = 0; index_1 < reservaHabitacion.size(); index_1++){
                        ArrayList<Habitacion> habitaciones = reservaHabitacion.get(index_1).getHabitaciones();
                        for (int index_2 = 0; index_2 < habitaciones.size(); index_2++){
                            Habitacion habitacion = habitaciones.get(index_2);
                            print(
                                    index_2,
                                    ".",
                                    "tipo:",
                                    habitacion.getTipo(),
                                    "dia semana:",
                                    habitacion.getDiaSemana(),
                                    "camas:",
                                    habitacion.getCamas(),
                                    "No.",
                                    habitacion.getNumeroCuarto(),
                                    "Grupo ocupante:",
                                    reservaHabitacion.get(index_1).getGrupo().getHuespedPrincipal().getNombre(),
                                    "Rango de fecha:",
                                    reservaHabitacion.get(index_1).getFechaInicio(),
                                    "-",
                                    reservaHabitacion.get(index_1).getFechaFinal()
                            );
                        }
                    }
                }
                case 4 -> {
                    Factura factura = new Factura("src/log.txt", receptionist.huespedes());
                    print(factura.textofactura());
                    factura.guardarFactura();
                }
                case 5 -> { print("Successful logout of user: " + login); inOptions = false; main(args); }
                case 6 -> System.exit(0);
                default -> print("la opción no es permitida");
            }
        }
    }
    private static void employeeOptions() {
        boolean inOptions = true;
        while (inOptions)
        {
            print("=================");
            print("1. Añadir uso spa");
            print("2. Añadir uso restaurante");
            print("3. Añadir uso guia");
            print("4. Logout");
            print("5. Exit");
            switch (Integer.parseInt(input("Opción: ")))
            {
                case 1 -> employee.addSpaUsage();
                case 2 -> employee.addRestaurantUsage();
                case 3 -> employee.addGuideUsage();
                case 4 -> { print("Successful logout of user: " + login); inOptions = false; main(args); }
                case 5 -> System.exit(0);
                default -> print("la opción no es permitida");
            }
        }
    }

    public static BufferedReader open(String path) {
        try {
            return new BufferedReader(new FileReader(path));
        } catch (IOException ignored) {
        }
        return null;
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

}
