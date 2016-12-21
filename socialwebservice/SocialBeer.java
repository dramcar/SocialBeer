package socialwebservice;
import java.util.*;
import java.util.Vector;

public class SocialBeer {

    
    private Vector vGrupos = null;

    public SocialBeer() throws Exception{
		vGrupos = new Vector();
    }
    
    // Método para crear un nuevo grupo
    public boolean crearGrupo(String nombreGrupo, Usuario usuario, String descripcion) throws Exception {	
	    // No pueden existir dos grupos con el mismo nombre
		boolean grupoExistente = false;
		boolean grupoCreado = false;
		
		// Comprueba si quiere crear el grupo un usuario válido
		if(usuario.getNombreUsuario().equals("null")){
			throw new Exception("Usuario erróneo");
		}

		else{
			
			// Comprueba si el grupo ya existe
			for (int index = 0; index < vGrupos.size() && grupoExistente == false; index++){
		   		Grupo grupoObtenido = (Grupo)vGrupos.get(index);
		   		// Comprueba si el nombre ya existe en la lista
		    	if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
		    		grupoExistente = true;
		    	}
			}

			// Si no existe el grupo se crea
			if(grupoExistente == false){
			    
				// Se crea el nuevo objeto grupo y se inicializa
			    Grupo g = new Grupo ();
		 	    g.setNombreGrupo(nombreGrupo);
			    g.setAdminGrupo(usuario);
			    g.setDescripcion(descripcion);

			    // Se añade el grupo a la lista de grupos 
			    vGrupos.add(g);
			    grupoCreado = true;
			    System.out.println("El administrador del grupo "+ nombreGrupo +" es: ");
			    System.out.println("Se ha creado el grupo " + nombreGrupo +
			    	" y el administrador del grupo es: " +usuario.getNombreUsuario());
				// Se añade el usuario que crea el grupo a la lista de usuarios
			    addUsuario(g.getNombreGrupo(), usuario);
			}	

			// Si existe el grupo se lanza una excepción
			else{
		    	System.err.println("El grupo "+nombreGrupo+" ya existe");
			}
		}
		return grupoCreado;
    }

    // Método para eliminar un grupo existente
	public boolean eliminarGrupo(String nombreGrupo, Usuario usuario) throws Exception {
		boolean encontrado = false;
		boolean eliminado = false;

		// Comprueba que usuario y nombre del grupo son validos
		if (usuario != null && !(nombreGrupo.equals(""))){
			
			// Busca el grupo
			for (int index = 0; index < vGrupos.size() && encontrado == false; index++){
			    Grupo grupoObtenido = (Grupo)vGrupos.get(index);
			    
			    // Se comprueba si es el grupo que se busca
			    if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
			    	encontrado = true;		// Cuando encuentra el grupo no sigue buscando
			    	
			    	// Se comprueba si el usuario es el administrador del grupo
			    	if (grupoObtenido.getAdminGrupo().getNombreUsuario().equals(usuario.getNombreUsuario())){	
						eliminado = true;
						vGrupos.remove(grupoObtenido);	// Elimina el grupo
					   	System.out.println("Grupo " +nombreGrupo+ " eliminado");
					}
					
					// Si no es el administrador salta la excepción
					else{
						System.err.println("El usuario " + usuario.getNombreUsuario() + " no es el propietario del grupo " +nombreGrupo);
					}
			    }
			}
			if(encontrado == false){
				System.err.println("El grupo no existe");
			}

	    }
	    else{
	    	System.err.println("El usuario o grupo no tiene el formato adecuado!");
	    }
	    return eliminado;
	}

	// Metodo que lista todos los grupos existentes
    public Grupo[] gruposCreados() throws Exception {
		Vector gruposExistentes = new Vector ();

		// Busca los grupos que hay creados
		for (int indexGrupo = 0; indexGrupo < vGrupos.size(); indexGrupo++){
	   		Grupo vectorGrupo = (Grupo)vGrupos.get(indexGrupo);
			gruposExistentes.add(vectorGrupo);	
		}

		// Añado los grupos encontrados a un array de grupos
        Grupo arrayGrupos[] = new Grupo[gruposExistentes.size()];

        for (int indexAdd=0; indexAdd < gruposExistentes.size(); indexAdd++) {
            arrayGrupos[indexAdd] = (Grupo)gruposExistentes.get(indexAdd);
        }
        
        // Devuelve el array con todos los grupos existentes
        return arrayGrupos;        
    }

    // Metodo que lista los grupos a los que pertenece un usuario
    public Grupo[] gruposDeUsuario(String nombreUsuario) throws Exception {
		// Buscar las cuentas de un mismo titular en función del dni
		boolean encontrado = false;	
		Vector gruposExistentes = new Vector ();
		Grupo arrayGruposUsuarios[];
		
		// Buscar los grupos que pertenecen al usuario
		for (int buscaGrupo = 0; buscaGrupo < vGrupos.size(); buscaGrupo++){
			// Se obtiene un grupo del vector de grupos
			Grupo grupoVector = (Grupo)vGrupos.get(buscaGrupo);

			// Busca entre los usuarios de ese grupo
			for(int buscaUsuario = 0; buscaUsuario < grupoVector.getUsuarios().size(); buscaUsuario++){
				Usuario usuarioVector = (Usuario)grupoVector.getUsuarios().get(buscaUsuario);

		    // Comprueba si el usuario está en la lista de usuarios del grupo
				if (usuarioVector.getNombreUsuario().equals(nombreUsuario)){
					// Si el usuario pertenece al grupo añade el grupo al vector
					gruposExistentes.add(grupoVector);	
					encontrado = true;
				}
			}
		}
		if (encontrado == false){
		   	//System.err.println("El usuario " + nombreUsuario + " no pertenece a ningun grupo");
		   	arrayGruposUsuarios = null;		
		}

		// Si el usuario pertenece a algún grupo se prepara un array con sus grupos
		else{
            arrayGruposUsuarios = new Grupo[gruposExistentes.size()];

            for (int addGrupo=0; addGrupo < gruposExistentes.size(); addGrupo++) {
                arrayGruposUsuarios[addGrupo] = (Grupo)gruposExistentes.get(addGrupo);
            }       
		}
            
        return arrayGruposUsuarios; 
    }



    // Obtiene los usuarios que pertenecen a un grupo
    public Usuario [] miembrosDeGrupo (String nombreGrupo) throws Exception{

        boolean grupoEncontrado = false; 
        Vector usuariosGrupo = new Vector (); 
        Usuario arrayUsuarios[];   
        
        // Busca si el grupo existe
        for (int numGrupos = 0; numGrupos < vGrupos.size(); numGrupos++){
            Grupo grupoObtenido = (Grupo)vGrupos.get(numGrupos);
            
            // Cuando el grupo existe obtiene los usuarios de ese grupo
            if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
                for(int numUsuarios = 0; numUsuarios < grupoObtenido.getUsuarios().size(); numUsuarios ++){
                    Usuario usuarioVector = (Usuario)grupoObtenido.getUsuarios().get(numUsuarios);
                    usuariosGrupo.add(usuarioVector); 
                }

            	grupoEncontrado = true;
            }
        }

        if (grupoEncontrado == false){
           System.err.println("Grupo no existente");  
           arrayUsuarios = null;      
        }

        else{
            arrayUsuarios = new Usuario[usuariosGrupo.size()];

            for (int usuarioAdd=0; usuarioAdd < usuariosGrupo.size(); usuarioAdd++) {
                arrayUsuarios[usuarioAdd] = (Usuario)usuariosGrupo.get(usuarioAdd);
            }        
        }
                
        return arrayUsuarios;
    }

    // Metodo que obtiene los mensajes de un grupo
    public Mensaje [] mensajesDeGrupo (String nombreGrupo) throws Exception{

        boolean grupoEncontrado = false; 
        Vector mensajesGrupo = new Vector ();   
        Mensaje arrayMensajes[];
        
        // Busca si el grupo existe
        for (int numGrupos = 0; numGrupos < vGrupos.size(); numGrupos++){
            Grupo grupoObtenido = (Grupo)vGrupos.get(numGrupos);
            
            // Cuando el grupo existe vemos los mensajes del grupo
            if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
           	 	grupoEncontrado = true;
                for(int numMensajes = 0; numMensajes < grupoObtenido.getMensajes().size(); numMensajes ++){
                    Mensaje mensajeVector = (Mensaje)grupoObtenido.getMensajes().get(numMensajes);
                    mensajesGrupo.add(mensajeVector); 
                }
            }
        }

        if (grupoEncontrado == false){
           System.err.println("Grupo no existente");  
           arrayMensajes = null;      
        }

        // Si el grupo existe se prepara un array con los mensajes que contiene
        else{
            arrayMensajes = new Mensaje[mensajesGrupo.size()];

            // Añade los mensajes obtenidos al array de mensajes
            for (int mensajeAdd=0; mensajeAdd < mensajesGrupo.size(); mensajeAdd++) {
                arrayMensajes[mensajeAdd] = (Mensaje)mensajesGrupo.get(mensajeAdd);
            }       
        }

        return arrayMensajes; 
    }

    // Añade un nuevo usuario al grupo
    public boolean addUsuario (String nombreGrupo, Usuario usuario) throws Exception{
        boolean grupoExistente = false;
        boolean usuarioExistente = false;	// Comprueba que no haya dos usuarios con mismo nombre
        boolean usuarioAdd = false;
        
        // Comprueba si es un usuario valido
        if(usuario != null){
	        // Busca si el grupo existe
	        for (int buscaGrupo = 0; buscaGrupo < vGrupos.size() && grupoExistente == false; buscaGrupo++){
	            Grupo grupoObtenido = (Grupo)vGrupos.get(buscaGrupo);
	            // En caso de que exista el usuario
	            if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
	                grupoExistente = true;
	                usuarioExistente = grupoObtenido.addUsuario(usuario);
	            }
	        }
	        // Si se ha encontrado un grupo con ese nombre y no hay ningun usuario con ese
	        // nombre, se añade el nuevo usuario
	        if (usuarioExistente == false && grupoExistente == true){
	            System.out.println("El usuario " + usuario.getNombreUsuario() + " se ha unido al grupo " 
	            	+nombreGrupo);
	            	usuarioAdd = true;
	        }
	        // Si no se encuentra el grupo
	        else if(grupoExistente == false){
	        	System.err.println("El grupo al que quiere unirse no existe");
	        }
	        // Si se encuentra el grupo y ya existe un usuario con ese nombre
	        else{
	        	System.err.println("Usuario existente");
	        }
	    }
	    return usuarioAdd;
    }

    // Añade un nuevo usuario al grupo
    public void addMensaje (String nombreGrupo, Usuario usuario, Mensaje mensaje) throws Exception{
        boolean grupoExistente = false;
        boolean mensajePublicado = false;
        
        // Comprueba si es un usuario y mensaje valido
        if(usuario != null && mensaje != null){
	        // Busca si el grupo existe
	        for (int buscaGrupo = 0; buscaGrupo < vGrupos.size() && grupoExistente == false; buscaGrupo++){
	            Grupo grupoObtenido = (Grupo)vGrupos.get(buscaGrupo);
	            // En caso de que exista el usuario
	            if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
	                grupoExistente = true;
	                mensaje.setAutor(usuario);
	                mensajePublicado = grupoObtenido.addMensaje(mensaje);
	            }
	        }
	        // Si se ha encontrado el grupo
	        if (mensajePublicado == true ){
	            System.out.println("El mensaje " + mensaje.getCadena() + " ha sido publicado");
	        }
	        // Si no se encuentra el grupo
	        else if (grupoExistente == false){
	        	System.err.println("El grupo en el que quiere publicar el mensaje no existe");
	        }
	        // Si hay algún problema al publicar el mensaje
	        else{
	        	throw new Exception("Ocurrió un problema al publicar el mensaje");
	        }
	    }
    }

    // Elimina a un usuario del grupo
    public boolean borraUsuario (String nombreGrupo, Usuario usuario) throws Exception{
        boolean grupoExistente = false;
        boolean usuarioBorrado = false;	// Comprueba que se ha borrado el usuario
        
        // Comprueba si es un usuario valido
        if(usuario != null){
	        // Busca si el grupo existe
	        for (int buscaGrupo = 0; buscaGrupo < vGrupos.size() && grupoExistente == false; buscaGrupo++){
	            Grupo grupoObtenido = (Grupo)vGrupos.get(buscaGrupo);
	            // En caso de que exista el usuario
	            if (grupoObtenido.getNombreGrupo().equals(nombreGrupo)){
	                grupoExistente = true;
	                usuarioBorrado = grupoObtenido.borraUsuario(usuario);
	            }
	        }

	        // Si se encuentra el grupo y el usuario pertenece al mismo
	        if (usuarioBorrado == true){
	            System.out.println("El usuario " + usuario.getNombreUsuario() 
	            	+ " ha sido eliminado del grupo " +nombreGrupo);
	        }
	        // Si no se encuentra el grupo
	        else if (grupoExistente == false){
	        	System.err.println("El grupo no existe");
	        }
	        // Si se encuentra el grupo y el usuario no pertenece a el
	        else{
	        	throw new Exception("Usuario inválido o existente");
	        }
	    }
   		return usuarioBorrado;
    }
}
