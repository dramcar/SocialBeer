package socialwebservice;
import java.util.*;
import java.util.Vector;
import java.io.*;

public class Usuario implements java.io.Serializable {

    private String nombre;			// Nombre del usuario
    private String telefono;			// Telefono del usuario
    private int xUbicacion;      // Ubicacion del usuario en el eje X
    private int yUbicacion;      // Ubicacion del usuario en el eje Y

    public Usuario () throws Exception{

    }

    // Métodos setter
    public void setNombreUsuario(String nombre) {
		this.nombre = nombre;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setXUbicacion(int xUbicacion) {
        this.xUbicacion = xUbicacion;
    }
    public void setYUbicacion(int yUbicacion) {
        this.yUbicacion = yUbicacion;
    }

    // Métodos getter
    public String getNombreUsuario() {
		return this.nombre;
    }
    public String getTelefono() {
		return this.telefono;
    }
    public int getXUbicacion(){
        return this.xUbicacion;
    }
    public int getYUbicacion(){
        return this.yUbicacion;
    }

    // Método toString para obtener datos de usuario
    public String toString(){
       return "Nombre: " + nombre + " Telefono: " + telefono + " || Ubicacion: ("+xUbicacion+","+yUbicacion+")";
    }
}