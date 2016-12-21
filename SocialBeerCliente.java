import localhost.WS.SocialBeer.*;
import davidr.axis.services.SocialBeer.*;

import java.util.Scanner;

import javax.swing.*;
import java.awt.event.*;


public class SocialBeerCliente {

    public static void main(String [] args) throws Exception {

        if (args.length != 2)
            System.out.println("Uso: java SocialBeerCliente username telefono");
        else{
            final String ANSI_CLS = "\u001b[2J";
            final String ANSI_HOME = "\u001b[H";
            SocialBeerService service = new SocialBeerServiceLocator();

            SocialBeer grupos = service.getSocialBeer();
            // Variable que guarda la opcion elegida en el menu principal
            String opcionPrincipal=null;
            // Variable que guarda el grupo que se ha seleccionado en el menú
            String grupoSeleccionado=null;
            // Variable que almacena el nombre de un grupo
            String grupoMensaje = null;
            // Variable que guarda la opcion del foro elegida
            String foroOpcion=null;
            // Usuario que se registra en la red social
            Usuario usuarioSocial=null;
            // Variable que recoge los datos introducidos por terminal
            Scanner in = new Scanner(System.in);
            // Grupos a los que pertenece el usuario
            Grupo gruposUsuario [] = null;
            // Total de grupos creados en la red social
            Grupo gruposTotales [] = null;
            // Variable que comprueba si el usuario quiere seguir viendo opciones del foro
            boolean contForo = true;
            // Variable que controla cuando tiene que finalizar el programa
            boolean salirPrograma = false;



            // Se crea al nuevo usuario
            usuarioSocial = new Usuario();
            usuarioSocial.setNombreUsuario(args[0]);
            usuarioSocial.setTelefono(args[1]);

            final MapaCliente mapaCliente = new MapaCliente(usuarioSocial);

            // Gestión del mapa
            JFrame frame = new JFrame();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mapaCliente);
            frame.setResizable(false);
            frame.pack();

            frame.setLocationByPlatform(true);
            frame.setVisible(true);

            Timer timer;
 
            class CambiosActionListener implements ActionListener {
                SocialBeer sb;
                String nombreUsuario;

                public CambiosActionListener(SocialBeer sb, String nombreUsuario) {
                    this.sb = sb;
                    this.nombreUsuario = nombreUsuario;

                }

                public void actionPerformed(ActionEvent event) {
                    try{
                        mapaCliente.cambiaMapa(sb.gruposDeUsuario(nombreUsuario));
                    }catch(Exception e){
                        System.out.println("Excepcion en CambiosActionListener: " +e);
                    }
                }
            }
               
            try{

                // Acción temporizada para aplicar cambios en el mapa gráfico
                timer = new Timer(500, new CambiosActionListener(grupos,usuarioSocial.getNombreUsuario()));
                timer.start();

                do{

                    // Las siguientes dos lineas limpian la pantalla
                    System.out.print(ANSI_CLS + ANSI_HOME);
                    System.out.flush();
                    System.out.println ("#####################################################");
                    System.out.println ("###                                               ###");
                    System.out.println ("###            BIENVENIDO A SOCIALBEER            ###");
                    System.out.println ("###                                               ###");
                    System.out.println ("#####################################################");
                    System.out.println ("> ¿Qué desea hacer? ");
                    System.out.println ("1. Inscripcion en grupos");
                    System.out.println ("2. Salir de un grupo");
                    System.out.println ("3. Ver mis grupos");
                    System.out.println ("4. Foro");
                    System.out.println ("5. Crear grupos");
                    System.out.println ("6. Eliminar grupos");
                    System.out.println ("q. Salir");
                    System.out.print("Seleccione una opción: ");
                    // Obtiene la opcion que hemos elegido
                    opcionPrincipal=in.nextLine(); 

                    switch (opcionPrincipal){
                        // Insercion de un usuario en un grupo
                        case "1": 
                            // Limpia la pantalla
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();
                            // Obtiene los grupos existentes
                            gruposTotales = grupos.gruposCreados();
                            // Comprueba que hay algún grupo creado
                            if(gruposTotales.length != 0){
                                // Lista las opciones que hay
                                System.out.println("Grupos existentes: ");
                                for(int listaGrupo=0; listaGrupo<gruposTotales.length; listaGrupo++){
                                    System.out.println(listaGrupo+". "+gruposTotales[listaGrupo].getNombreGrupo());
                                }
                                System.out.println ("r. Atrás");
                                System.out.print("Seleccione una opción: ");
                                // Recoge la opción elegida
                                grupoSeleccionado=in.nextLine();
                                // Comprueba que no queremos volver
                                if (!grupoSeleccionado.equals("r") && !grupoSeleccionado.equals("R")){ 
                                        try{
                                            int indiceInscribe= Integer.parseInt(String.valueOf(grupoSeleccionado));
                                            Grupo grupoAInscribir = new Grupo();
                                            grupoAInscribir=gruposTotales[indiceInscribe];
                                            System.out.println("Se va a añadir el usuario "+usuarioSocial.getNombreUsuario()+" al grupo " +grupoAInscribir.getNombreGrupo());
                                            // Se inscribe el usuario y se comprueba si se ha realizado con exito
                                            boolean usuarioInscrito = grupos.addUsuario(grupoAInscribir.getNombreGrupo(), usuarioSocial);
                                            if(usuarioInscrito == true){
                                                System.out.println("Se ha añadido correctamente al grupo");
                                            }
                                            else{
                                                System.err.println("ERROR: El usuario ya pertenece al grupo");  
                                            }
                                        }
                                        // En caso de que la opcion no sea correcta
                                        catch(Exception e){
                                            System.err.println("Opción incorrecta");
                                            System.err.println("Volviendo al menú principal...");
                                        }
                                    }
                                // En caso de que pulsemos r o R volvemos al menú
                                else{
                                    System.out.println("Volviendo al menú principal...");
                                }
                            }
                            // En caso de que no haya ningún grupo creado aún
                            else{
                                System.out.println("Actualmente no hay nigún grupo creado");
                                System.out.println("Volviendo al menú principal...");
                            }
                            Thread.sleep(2000);
                            break;
                        
                        // Caso que un usuario quiera salir de un grupo
                        case "2":
                            // Las siguientes dos lineas limpian la pantalla
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();
                            try{
                                gruposUsuario = grupos.gruposDeUsuario(usuarioSocial.getNombreUsuario());
                                // Comprueba si el usuario pertenece a algun grupo
                                if (gruposUsuario != null){
                                    // Muestra los grupos a los que pertenece el usuario
                                    System.out.println("Seleccione el grupo del que quiere salir");
                                    for(int indiceGrupo=0; indiceGrupo<gruposUsuario.length; indiceGrupo++){
                                        System.out.println(indiceGrupo+". "+gruposUsuario[indiceGrupo].getNombreGrupo());
                                    }

                                    System.out.println ("r. Atrás");
                                    grupoSeleccionado=in.nextLine();
                                    // Comprueba que no se selecciona la opción de volver
                                    if (!grupoSeleccionado.equals("r") && !grupoSeleccionado.equals("R")){
                                        // Obtiene la posicion del grupo del que quiere salir
                                        int grupoASalir= Integer.parseInt(String.valueOf(grupoSeleccionado));
                                        // Elimina al usuario del grupo y comprueba el resultado
                                        boolean usuarioBorrado = grupos.borraUsuario(gruposUsuario[grupoASalir].getNombreGrupo(), usuarioSocial);
                                        if (usuarioBorrado == true){
                                            System.out.println("El usuario "+usuarioSocial.getNombreUsuario() 
                                                +" ha salido del grupo " +gruposUsuario[grupoASalir].getNombreGrupo());
                                        }
                                        else{
                                            System.out.println("El usuario no ha podido salir del grupo " +gruposUsuario[grupoASalir].getNombreGrupo());
                                        }
                                    }
                                    System.out.println("Volviendo al menú principal...");
                                }
                                // El usuario no pertenece a ningun grupo
                                else{
                                    System.out.println("En estos momentos no pertenece a ningún grupo");
                                    System.out.println("Volviendo al menú principal...");
                                }
                                Thread.sleep(2000);
                            }
                            // En caso de cadena vacía, letra o numero fuera de indice
                            catch(Exception e){
                                System.err.println("Opción incorrecta");
                                System.err.println("Volviendo al menú principal...");
                                Thread.sleep(2000);
                            }
                            break;

                        // En caso de querer ver los grupos a los que pertenece un usuario  
                        case "3":
                            // Las siguientes dos lineas limpian la pantalla
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();
                            // En caso que el usuario no se haya creado
                            if(usuarioSocial == null){
                                System.out.println("Usuario inexistente");
                            }
                            else{
                                // Obtiene la lista de grupos de ese usuario
                                gruposUsuario = grupos.gruposDeUsuario(usuarioSocial.getNombreUsuario());
                                // Si la lista de grupos es vacía
                                if (gruposUsuario == null){
                                    System.err.println("El usuario "+usuarioSocial.getNombreUsuario()+" no pertenece a ningún grupo actualmente");
                                }
                                else{
                                    
                                    try{// Muestra las diferentes opciones al usuario
                                        System.out.println("Los grupos a los que pertenece el usuario "+usuarioSocial.getNombreUsuario());
                                        for(int indexGrupo=0; indexGrupo<gruposUsuario.length; indexGrupo++){
                                            System.out.println(indexGrupo+". "+gruposUsuario[indexGrupo].getNombreGrupo());
                                        }
                                        System.out.println("r. Atrás");
                                        System.out.print("Elije el grupo: ");
                                        // Lee por teclado las opciones 
                                        String gruposOpciones=in.nextLine();
                                        // Las dos siguientes lineas limpian la pantalla
                                        System.out.print(ANSI_CLS + ANSI_HOME);
                                        System.out.flush();
                                        if (!gruposOpciones.equals("r") && !gruposOpciones.equals("R") && !gruposOpciones.equals("")){
                                            int grupoElegido = Integer.parseInt(gruposOpciones);
                                            // Muestra los detalles del grupo
                                            System.out.println("Nombre de grupo: \n" +gruposUsuario[grupoElegido].getNombreGrupo());
                                            System.out.println("Descripción: \n" +gruposUsuario[grupoElegido].getDescripcion());
                                            System.out.println("Creador del grupo: \n" +gruposUsuario[grupoElegido].getAdminGrupo().getNombreUsuario());
                                            System.out.print("\n\nPulse una tecla para volver al menú principal ");
                                            // Espera a que el usuario pulse una tecla
                                            in.nextLine(); 
                                        }
                                        // Se obtiene una cadena vacía
                                        else if(gruposOpciones.equals("")){
                                            System.out.println("Cadena vacía");
                                            System.out.println("Volviendo al menú principal...");
                                        }
                                        // Si se pulsa r o R se vuelve atrás
                                        else{
                                            System.out.println("Volviendo al menú principal...");
                                        }
                                    }
                                    catch(Exception e){
                                        System.err.println("Opción incorrecta");
                                        System.err.println("Volviendo al menú principal...");
                                    }
                                }
                                // Se esperan 2 segundos para visualizar el mensaje
                                Thread.sleep(2000); 
                            }   
                            break;
                        // En caso de ver el foro
                        case "4":
                            do{
                                // Las siguientes dos lineas limpian la pantalla
                                System.out.print(ANSI_CLS + ANSI_HOME);
                                System.out.flush();
                                System.out.println("> Foro Social Beer ");
                                System.out.println("1. Ver mensajes del foro ");
                                System.out.println("2. Escribir un mensaje ");
                                System.out.println("r. Volver al menú principal ");                         
                                System.out.print("Elija una opción: ");
                                foroOpcion=in.nextLine();
                                    switch (foroOpcion){
                                        // Caso de querer ver los mensajes de un grupo
                                        case "1":
                                            // Las siguientes dos lineas limpian la pantalla
                                            System.out.print(ANSI_CLS + ANSI_HOME);
                                            System.out.flush();
                                            // Se obtiene los grupos del usuario
                                            gruposUsuario = grupos.gruposDeUsuario(usuarioSocial.getNombreUsuario());
                                            // Si no pertenece a ningun grupo
                                            if (gruposUsuario == null){
                                                System.err.println("El usuario "+usuarioSocial.getNombreUsuario()+" no pertenece a ningún grupo actualmente");
                                                System.out.println("Volviendo...");
                                                Thread.sleep(2000);
                                            }
                                            // Muestra la lista de grupos
                                            else{
                                                try{
                                                    System.out.println("Seleccione el grupo del que quiere ver los mensajes");
                                                    for(int indiceMensaje=0; indiceMensaje<gruposUsuario.length; indiceMensaje++){
                                                        System.out.println(indiceMensaje+". "+gruposUsuario[indiceMensaje].getNombreGrupo());
                                                    }
                                                    // Obtiene el grupo seleccionado
                                                    grupoSeleccionado=in.nextLine();
                                                    int muestraMensaje= Integer.parseInt(String.valueOf(grupoSeleccionado));
                                                    grupoMensaje=gruposUsuario[muestraMensaje].getNombreGrupo();
                                                    // Obtiene la lista de mensajes del grupo
                                                    Mensaje mensajesGrupo [] = grupos.mensajesDeGrupo (grupoMensaje);
                                                    // Comprueba si hay mensajes en el grupo
                                                    if(mensajesGrupo.length != 0){
                                                        // Las siguientes dos lineas limpian la pantalla
                                                        System.out.print(ANSI_CLS + ANSI_HOME);
                                                        System.out.flush();
                                                        // Muestra los mensajes del grupo
                                                        System.out.println("Mensajes grupo " + grupoMensaje + " : ");
                                                        for(int indexMensaje=0; indexMensaje<mensajesGrupo.length; indexMensaje++){
                                                            System.out.println(indexMensaje+1 + " " + mensajesGrupo[indexMensaje].getCadena() + " | publicado por: "
                                                                + mensajesGrupo[indexMensaje].getAutor().getNombreUsuario() + " ("
                                                                + mensajesGrupo[indexMensaje].getFechaPubli().getTime() + ")");
                                                        }
                                                    }
                                                    // En caso de que no haya mensajes publicados
                                                    else{
                                                        System.out.println("Aún no hay ningún mensaje en el grupo " +grupoMensaje);
                                                    }
                                                    // Vuelve al menú anterior al pulsar una tecla
                                                    System.out.println("Pulse una tecla para volver atrás");
                                                    in.nextLine();
                                                }
                                                catch(Exception e){
                                                    System.out.println("Error en la selección del grupo");
                                                    System.out.println("Volviendo...");
                                                    Thread.sleep(2000);
                                                }
                                            } 
                                        break;
                                        
                                        // Caso de querer escribir un nuevo mensaje en un grupo
                                        case "2":
                                            // Las siguientes dos lineas limpian la pantalla
                                            System.out.print(ANSI_CLS + ANSI_HOME);
                                            System.out.flush();
                                            // Obtiene los grupos del usuario
                                            gruposUsuario = grupos.gruposDeUsuario(usuarioSocial.getNombreUsuario());
                                            // Comprueba si pertenece a algun grupo el usuario
                                            if (gruposUsuario == null){
                                                System.err.println("El usuario "+usuarioSocial.getNombreUsuario()+" no pertenece a ningún grupo actualmente");
                                                System.out.println("Volviendo...");
                                                Thread.sleep(2000);
                                            }
                                            else{
                                                try{
                                                    // Muestra la lista de los grupos del usuario
                                                    System.out.println("Seleccione el grupo en el que quiere publicar el mensajes");
                                                    for(int indiceEscribe=0; indiceEscribe<gruposUsuario.length; indiceEscribe++){
                                                        System.out.println(indiceEscribe+". "+gruposUsuario[indiceEscribe].getNombreGrupo());
                                                    }
                                                    // Obtiene el grupo seleccionado
                                                    grupoSeleccionado=in.nextLine();
                                                    int escribeGrupo= Integer.parseInt(String.valueOf(grupoSeleccionado));
                                                    grupoMensaje=gruposUsuario[escribeGrupo].getNombreGrupo();
                                                    // Se crea el mensaje a incluir en el grupo
                                                    Mensaje mensajeEscribir = new Mensaje ();
                                                    String cadenaMensaje="";
                                                    // Mientras no se escriba un mensaje se pide la cadena
                                                    do{
                                                        System.out.println("Escriba un mensaje para el foro: (Escribe q para salir) " );
                                                        cadenaMensaje = in.nextLine();
                                                    } while (cadenaMensaje.equals(""));
                                                    // Escribe un nuevo mensaje
                                                    if (!cadenaMensaje.equals("q")){
                                                        mensajeEscribir.setCadena(cadenaMensaje);
                                                        grupos.addMensaje(grupoMensaje, usuarioSocial, mensajeEscribir);
                                                        System.out.println("Mensaje publicado");
                                                        Thread.sleep(2000);
                                                    }

                                                    // En caso de escribir una "q" se anula la escritura del mensaje
                                                    else{
                                                        System.out.println("Saliendo...");
                                                        Thread.sleep(2000);
                                                    }
                                                }
                                                catch(Exception e){
                                                    System.out.println("Error en la selección del grupo");
                                                    System.out.println("Volviendo...");
                                                    Thread.sleep(2000);
                                                }
                                            }
                                        break;
                                        
                                        // En caso de querer salir del foro
                                        case "r":
                                        case "R":
                                            System.out.println("Volviendo al menú principal...");
                                            contForo = false;
                                            break;

                                        // En caso de elegir algo que no exista
                                        default:
                                            System.out.println("Opción incorrecta");
                                            Thread.sleep(2000); 
                                            break;
                                    }
                
                            } while (contForo);
                            // Volvemos a poner la variable para poder entrar de nuevo en el bucle
                            contForo = true;
                            Thread.sleep(2000); 
                            break;
                        // En caso de querer crear un nuevo grupo
                        case "5":
                            // Las siguientes dos lineas limpian la pantalla
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();
                            String nomGrupoCrear = "";
                            String descGrupo = "";
                            boolean creaNuevoGrupo = true;
                            System.out.println("Creando un nuevo grupo...");
                            // Obtiene un nombre para el grupo
                            while( nomGrupoCrear.equals("") && creaNuevoGrupo == true){
                                System.out.println("Por favor introduce un titulo para el grupo: (Pulsa q para salir)");
                                nomGrupoCrear = in.nextLine();
                                // Si se quiere salir
                                if (nomGrupoCrear.equals("q")){
                                    creaNuevoGrupo=false;
                                }
                            }
                            // Obtiene una descripcion para el grupo
                            while( descGrupo.equals("") && creaNuevoGrupo == true) {
                                System.out.println("Por favor introduce una descripción para el grupo: (Pulsa q para salir)");
                                descGrupo = in.nextLine();
                                // Si se quiere salir
                                if (descGrupo.equals("q")){
                                    creaNuevoGrupo=false;
                                }
                            }
                            // En caso de que se quiera crear el grupo
                            if(creaNuevoGrupo){
                                // Se crea el nuevo grupo y se obtiene el resultado
                                boolean grupoCreado = grupos.crearGrupo(nomGrupoCrear, usuarioSocial, descGrupo);
                                if (grupoCreado == true){
                                    System.out.println("Grupo creado");
                                }
                                else {
                                    System.err.println("Error al crear el grupo. Compruebe que el grupo no exista");
                                }
                            }
                            else{
                                System.out.println("Saliendo...");
                            }

                            Thread.sleep(2000); 
                            break;
                        // En caso de querer eliminar un grupo
                        case "6":
                            // Las siguientes dos lineas limpian la pantalla
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();

                            // Obtiene los grupos del usuario
                            gruposUsuario = grupos.gruposDeUsuario(usuarioSocial.getNombreUsuario());
                            // Comprueba que pertenece a algún grupo
                            if(gruposUsuario != null){
                                try{
                                    // Muestra los grupos a los que pertenece
                                    System.out.println("Selecciona el grupo a borrar: ");
                                    for(int indiceBorra=0; indiceBorra<gruposUsuario.length; indiceBorra++){
                                        System.out.println(indiceBorra+". "+gruposUsuario[indiceBorra].getNombreGrupo());
                                    }

                                    System.out.println("r. Volver al menú principal ");
                                    // Se obtiene el grupo seleccionado
                                    grupoSeleccionado=in.nextLine();
                                    if (!grupoSeleccionado.equals("r") && !grupoSeleccionado.equals("R")){
                                        int borraGrupo= Integer.parseInt(String.valueOf(grupoSeleccionado));
                                        String grupoABorrar = gruposUsuario[borraGrupo].getNombreGrupo();
                                        // Se elimina el grupo y se comprueba si se ha hecho bien
                                        boolean grupoEliminado = grupos.eliminarGrupo(grupoABorrar, usuarioSocial);
                                        if (grupoEliminado == true){
                                            System.out.println("Se ha eliminado el grupo " +grupoABorrar);
                                        }
                                        else {
                                            System.err.println("No se ha podido eliminar el grupo " +grupoABorrar +
                                                ". Por favor verifica que existe y eres el administrador");
                                        }
                                    }
                                }
                                catch(Exception e){
                                    System.out.println("Error en la selección del grupo");
                                    System.out.println("Volviendo...");
                                    Thread.sleep(2000);
                                }
                                    
                            }
                            // En caso de que no pertenezca a ningún grupo
                            else{
                                System.err.println("Actualmente no existe ningún grupo creado por usted");
                            }
                            System.err.println("Volviendo al menú principal...");
                            Thread.sleep(2000); 
                            break;
                        // En caso de querer salir del programa
                        case "q":
                            salirPrograma = true;
                            System.out.println("Saliendo de la red Social...");
                            Thread.sleep(2000);
                            System.out.print(ANSI_CLS + ANSI_HOME);
                            System.out.flush();
                            frame.dispose();
                            break;
                        // En caso de opcion incorrecta
                        default:
                            System.err.println("Opción incorrecta");
                            System.err.println("Volviendo al menú principal...");
                            Thread.sleep(2000); 
                            break;              
            
                    }
        
                }while (!salirPrograma);

            }
            catch(Exception e){
                System.out.println("Excepcion en SocialBeerCliente: " +e);
            }
        }
        System.exit(0);
    }
}
