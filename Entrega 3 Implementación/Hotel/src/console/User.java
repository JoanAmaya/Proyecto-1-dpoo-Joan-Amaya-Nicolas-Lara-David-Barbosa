package console;

import java.io.*;
import java.util.*;

public class User {
    private final HashMap<String, String> administrators;
    private final HashMap<String, String> receptionists;
    private final HashMap<String, String> employees;

    /**
     * load info with File object
     * @param file File object
     */
    public User(File file)
    {
        administrators = new HashMap<>();
        receptionists = new HashMap<>();
        employees = new HashMap<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                String[] line = it.next().split(";");
                if (line[2].equals("admin")) administrators.put(line[0], line[1]);
                if (line[2].equals("receptionist")) receptionists.put(line[0], line[1]);
                if (line[2].equals("employee")) employees.put(line[0], line[1]);
            }
        }
        catch (IOException warn)
        { print("WARNING: ", warn.getCause()); }
    }

    /**
     * load info with String path
     * @param path route of file
     */
    public User(String path)
    {
        administrators = new HashMap<>();
        receptionists = new HashMap<>();
        employees = new HashMap<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                String[] line = it.next().split(";");
                switch (line[2]) {
                    case "admin" -> administrators.put(line[0], line[1]);
                    case "receptionist" -> receptionists.put(line[0], line[1]);
                    case "employee" -> employees.put(line[0], line[1]);
                }
            }
            reader.close();
        }
        catch (IOException warn)
        { print("WARNING: ", warn.getCause()); }
    }

    /**
     * no params create testing accounts
     * @administrator: admin - root
     * @receptionist: receptionist - root
     * @employees: employee - root
     */
    public User()
    {
        administrators = new HashMap<>();
        receptionists = new HashMap<>();
        employees = new HashMap<>();
        administrators.put("admin", "root");
        receptionists.put("receptionist", "root");
        employees.put("employee", "root");
    }
    public boolean isAdministrator(String login, String passwd)
    {
        if (administrators.containsKey(login)) return administrators.get(login).equals(passwd);
        return false;
    }
    public boolean isReceptionist(String login, String passwd)
    {
        if (receptionists.containsKey(login)) return receptionists.get(login).equals(passwd);
        return false;
    }
    public boolean isEmployee(String login, String passwd)
    {
        if (employees.containsKey(login)) return employees.get(login).equals(passwd);
        return false;
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
}
