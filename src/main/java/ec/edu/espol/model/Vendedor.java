/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.model;

import ec.edu.espol.model.Automovil;
import ec.edu.espol.model.Camioneta;
import ec.edu.espol.model.Ingreso;
import ec.edu.espol.model.Motocicleta;
import ec.edu.espol.model.Oferta;
import ec.edu.espol.model.Vehiculo;
import ec.edu.espol.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author rsgar
 */
public class Vendedor {

    int ID;
    String nombres;
    String apellidos;
    String organizacion;
    String correo;
    String clave;
    ArrayList<Vehiculo> vehiculos;

    public Vendedor() {
    }

    public Vendedor(int ID, String nombres, String apellidos, String organizacion, String correo, String clave) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.organizacion = organizacion;
        this.correo = correo;
        this.clave = clave;
        this.vehiculos = new ArrayList<>();
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getOrganizacion() {
        return this.organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public void saveFile(String nomFile) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(nomFile), true))) {
            pw.println(this.ID + "|" + this.nombres + "|" + this.apellidos + "|" + this.organizacion + "|" + this.correo + "|" + Util.toHexString(Util.getSHA(this.clave)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Vendedor> readFile(String nomFile) throws FileNotFoundException {

        ArrayList<Vendedor> vendedor = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(nomFile))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] arreglo = linea.split("\\|");
                Vendedor v = new Vendedor(Integer.parseInt(arreglo[0]), arreglo[1], arreglo[2], arreglo[3], arreglo[4], arreglo[5]);
                vendedor.add(v);
            }
        }
        return vendedor;
    }

    public static Vendedor searchByID(ArrayList<Vendedor> vendedores, int id) {
        for (Vendedor v : vendedores) {
            if (v.ID == id) {
                return v;
            }
        }
        return null;
    }
    public static boolean validarUsuario(String correo, String clave, ArrayList<Vendedor> vendedores) throws PasswordException, UserException, NoSuchAlgorithmException{
        boolean validarcorreo = false;
        boolean validarclave = false;
        for (int i = 0; i < vendedores.size(); i++) {
            String clave_i = vendedores.get(i).getClave();//clave del vendedor que estamos tomando
            String correo_i = vendedores.get(i).getCorreo();//correo del vendedor que estamos tomando            
            if (correo_i.equals(correo)) {
                validarcorreo = true;
                if (clave_i.equals(Util.toHexString(Util.getSHA(clave)))){
                    validarclave = true;
                } 
            } 
        }
        if(validarcorreo == true){
            if(validarclave == true){
                return true;
            } else {
                throw new PasswordException("PasswordException");
            }
        } else {
            throw new UserException("UserException");
        }
    }
    
    @Override
    public String toString() {
        return "Vendedor<" + this.ID + ">{Nombres=" + this.nombres + ", Apellidos=" + this.apellidos + ", Organizacion=" + this.organizacion + ", Correo=" + this.correo + ", Clave=" + this.clave + "}";
    }

    
    public static void registrarNuevoVendedor(String nombres, String apellidos, String organizacion, String correo, String clave, ArrayList<Vendedor> vendedores, String nomfile) throws VendedorException{
        int id = Util.nextID(nomfile); 
        Vendedor v = new Vendedor(id, nombres, apellidos, organizacion, correo, clave);
        if (vendedores.contains(v) == false) {
            v.saveFile(nomfile);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Usuario registrado.");
            a.show();
        } else {
            throw new VendedorException("");
        }

    }

    public static void registrarVehiculo(Scanner sc, ArrayList<Vendedor> vendedores, ArrayList<Automovil> automoviles, ArrayList<Camioneta> camionetas, ArrayList<Motocicleta> motocicletas) throws NoSuchAlgorithmException {
        System.out.println("\n=INGRESAR VEHICULO=");
        System.out.println("Ingrese correo: ");
        String correo = sc.next();
        System.out.println("Ingrese clave: ");
        String clave = sc.next();
        boolean validarCorreo = false;
        for (int i = 0; i < vendedores.size(); i++) {
            String clave_i = vendedores.get(i).getClave();//clave del vendedor que estamos tomando
            String correo_i = vendedores.get(i).getCorreo();//correo del vendedor que estamos tomando

            if (correo_i.equals(correo)) {
                validarCorreo = true;
                if (clave_i.equals(Util.toHexString(Util.getSHA(clave)))) {
                    System.out.println("\nBienvenido " + vendedores.get(i).getNombres() + " " + vendedores.get(i).getApellidos() + " de la organización " + vendedores.get(i).getOrganizacion());
                    System.out.println("Ingrese el tipo de vechiculo(auto/motocicleta/camioneta): ");
                    String tipo = sc.next();
                    int id = vendedores.get(i).getID(); //obtenemos el ID del vendedor
                    switch (tipo) {
                        case "auto":
                            Automovil.nextAutomovil(sc, id, automoviles, "automovil.txt");
                            break;
                        case "motocicleta":
                            Motocicleta.nextMotocicleta(sc, id, motocicletas, "motocicleta.txt");
                            break;
                        case "camioneta":
                            Camioneta.nextCamioneta(sc, id, camionetas, "camioneta.txt");
                            break;
                        default:
                            System.out.println("Ingrese un tipo de vehiculo correcto.");
                            break;
                    }
                } else {
                    System.out.println("Clave incorrecta");
                }
            }
        }
        if (validarCorreo == false) {
            System.out.println("Correo incorrecto");
        }
    }

    public static void aceptarOferta(Scanner sc, ArrayList<Vendedor> vendedores, ArrayList<Oferta> ofertas, ArrayList<Ingreso> ingresos, ArrayList<Vehiculo> vehiculos) throws NoSuchAlgorithmException, IOException {
        System.out.println("\n=OFERTAS=");
        System.out.println("Ingrese correo: ");
        String correo = sc.next();
        System.out.println("Ingrese clave: ");
        String clave = sc.next();
        boolean validarCorreo = false;
        for (int i = 0; i < vendedores.size(); i++) {
            String clave_i = vendedores.get(i).getClave();//clave del vendedor que estamos tomando
            String correo_i = vendedores.get(i).getCorreo();//correo del vendedor que estamos tomando

            if (correo_i.equals(correo)) {
                validarCorreo = true;
                if (clave_i.equals(Util.toHexString(Util.getSHA(clave)))) {
                    System.out.println("\nBienvenido " + vendedores.get(i).getNombres() + " " + vendedores.get(i).getApellidos() + " de la organización " + vendedores.get(i).getOrganizacion());
                    System.out.println("Ingrese una placa: ");
                    String placa = sc.next();
                    boolean validarPlaca = false;
                    for (Vehiculo v : vendedores.get(i).getVehiculos()) //recorremos los vehiculos que ha registrado ese vendedor
                    {
                        if (v.getPlaca().equals(placa)) //verificamos que la palca sea correcta
                        {
                            validarPlaca = true;
                            if (v.getOfertas().isEmpty()) //verificamos que ese vehiculo tenga ofertas
                            {
                                System.out.println("No se han realizado ofertas para este vehiculo.");
                            } else {
                                System.out.println("\nVehiculo{ Marca:" + v.getMarca() + ", Modelo:" + v.getModelo() + ", Precio: " + v.getPrecio() + "}");
                                System.out.println("Se han realizado " + v.getOfertas().size() + " ofertas");
                                ArrayList<Oferta> ofs = v.getOfertas();
                                OUTER:
                                for (int e = 0; e < ofs.size(); e++) {
                                    System.out.println("\n-Oferta <" + (e + 1) + ">-");
                                    System.out.println("Correo: " + ofs.get(e).getComprador().getCorreo());
                                    System.out.println("Precio Ofertado: $" + ofs.get(e).getPrecioOfertado() + "\n");
                                    String op = Util.aceptarOfertas(sc, e, ofs.size() - 1);
                                    switch (op) {
                                        case "anterior":
                                            e -= 2;
                                            break;
                                        case "aceptar":
                                            System.out.println("Oferta aceptada, se enviara un mensaje al correo del ofertante.");
                                            String mensaje = "<H1><b>Estimado: " + ofs.get(e).getComprador().getNombres().toUpperCase() + " " + ofs.get(e).getComprador().getApellidos().toUpperCase() + "</b></H1></br></br>"
                                                    + "<H2>Le informamos a Ud. que su oferta por el vehiculo de placas " + v.getPlaca() + " ha sido aceptada, por favor ponerse en contacto con el dueño del vehiculo antes singularizado.</H2></br></br></br>"
                                                    + "<H2><em> SYSTEM-POO-G2 </em></H2>";
                                            Util.enviarEmail(ofs.get(e).getComprador().getCorreo(), mensaje);
                                            Ingreso.eliminarIngreso(ingresos, v);
                                            Oferta.eliminarOferta(ofertas, v);
                                            Vehiculo.eliminarVehiculo(vehiculos, v);
                                            break OUTER;
                                        case "regresar":
                                            break OUTER;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    if (validarPlaca == false) {
                        System.out.println("Placa erronea.");
                    }
                } else {
                    System.out.println("Clave incorrecta");
                }
            }
        }
        if (validarCorreo == false) {
            System.out.println("Correo incorrecto");
        }

    }

}
