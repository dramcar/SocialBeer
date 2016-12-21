package socialwebservice;
import java.util.*;
import java.util.Vector;
import java.io.*;

public class Grupo implements java.io.Serializable {

    private String nombreGrupo;			// Nombre del grupo
    private Usuario adminGrupo;			// Administrador del grupo
    private String descripcion;         // Descripcion del grupo
    private Vector vUsuarios = null;	// Vector de usuarios
    private Vector vMensajes = null;	// Vector de mensajes

    public Grupo () throws Exception{
    	vUsuarios = new Vector();
    	vMensajes = new Vector();
    }

    // Metodos setter
    public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
    }
    public void setAdminGrupo(Usuario usuario) {
		this.adminGrupo = usuario;
    }
    public void setDescripcion(String descripcion) {
	   this.descripcion = descripcion;
    }

    // Metodos getter
    public String getNombreGrupo() {
		return this.nombreGrupo;
    }
    public Usuario getAdminGrupo() {
		return this.adminGrupo;
    }
    public String getDescripcion() {
		return this.descripcion;
    }
    public Vector getUsuarios(){
        return vUsuarios;
    }
    public Vector getMensajes(){
    	return vMensajes;
    }

    // Añade un nuevo usuario al grupo
    public boolean addUsuario (Usuario usuario){
        boolean usuarioExistente = false;
        
        // Se comprueba si ya hay algún usuario con ese nombre
        for (int buscaUsuarios = 0; buscaUsuarios < vUsuarios.size() && usuarioExistente == false; buscaUsuarios++){
            Usuario usuarioObtenido = (Usuario)vUsuarios.get(buscaUsuarios);
            // En caso de que exista el usuario
            if (usuarioObtenido.getNombreUsuario().equals(usuario.getNombreUsuario())){
                usuarioExistente = true;
            }
        }

        // Si el usuario no existe, lo añadimos
        if (usuarioExistente == false){
            vUsuarios.add(usuario);
        }
        return usuarioExistente;
    }

    // Añade un nuevo mensaje al grupo
    public boolean addMensaje (Mensaje mensaje){
        boolean mensajePublicado = false;
        
        // Se comprueba si ya hay algún usuario con ese nombre
        for (int buscaUsuarios = 0; buscaUsuarios < vUsuarios.size() && mensajePublicado == false; buscaUsuarios++){
            Usuario usuarioObtenido = (Usuario)vUsuarios.get(buscaUsuarios);
            // En caso de que exista el usuario
            if (usuarioObtenido.getNombreUsuario().equals(mensaje.getAutor().getNombreUsuario())){
                mensajePublicado = true;
                // Añadimos el mensaje al vector correspondiente
                mensaje.setFechaPubli();
                vMensajes.add(mensaje);
            }
        }
        return mensajePublicado;
    }

    // Borra un usuario del grupo
    public boolean borraUsuario (Usuario usuario){
        boolean usuarioBorrado = false;
        
        // Se comprueba si ya hay algún usuario con ese nombre
        for (int buscaUsuarios = 0; buscaUsuarios < vUsuarios.size() && usuarioBorrado == false; buscaUsuarios++){
            Usuario usuarioObtenido = (Usuario)vUsuarios.get(buscaUsuarios);
            // En caso de que exista el usuario se borra
            if (usuarioObtenido.getNombreUsuario().equals(usuario.getNombreUsuario())){
                vUsuarios.remove(usuarioObtenido);
                usuarioBorrado = true;
            }
        }

 
        return usuarioBorrado;
    }
}