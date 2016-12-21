package socialwebservice;
import java.util.*;
import java.util.Vector;
import java.io.*;

public class Mensaje implements java.io.Serializable {

    private String cadena;			// Texto a escribir
    private Usuario autorMensaje;	// Autor del mensaje
    private Date fechaPubli; 		// Fecha de publicación del mensaje

    public Mensaje () throws Exception{

    }

    // Metodos setter
    public void setCadena(String cadena) {
		this.cadena = cadena;
    }
    public void setAutor(Usuario autorMensaje) {
		this.autorMensaje = autorMensaje;
    }
    public void setFechaPubli() {
		fechaPubli = new Date();
    }

    // Metodos getter
    public String getCadena() {
		return this.cadena;
    }
    public Usuario getAutor() {
		return this.autorMensaje;
    }
    public Date getFechaPubli(){
    	return fechaPubli;
    }

    // Metodo toString que obtiene el mensaje publicado y la fecha
    public String toString(){
        return "Mensaje: " + cadena + " Fecha de publicacion: " +fechaPubli;
    }

}