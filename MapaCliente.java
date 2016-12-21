import localhost.WS.SocialBeer.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


import java.awt.Font;
import java.awt.FontMetrics;


import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;

import java.util.Vector;

public class MapaCliente extends JPanel {

    // Posición del usuario
    private int x;
    private int y;

    private Usuario usuario;


    // Array de colores
    private static final Color[] COLORES = {Color.RED, Color.CYAN, Color.ORANGE, Color.GREEN};


    // Vector de Círculos
    Vector<Circulo> vectorCirculos;

    // Map de nombres de cada grupo y color
    HashMap<String,Color> leyenda;

    public static Image buffer;
    public static Graphics bg;
    private int opacidad = 25;

    // Tamaño de la ventana
    public static final int screenw = 600;
    public static final int screenh = 400;

    Timer timer; // Timer para la actualización de los cambios del gráfico

    public MapaCliente(Usuario usuario){
        /* Constructor */
        this.usuario = usuario;

        // Establecer el tamaño de la ventana gráfica
        setPreferredSize(new Dimension(screenw, screenh));


        //Posicionar el cliente por defecto.
        x = screenw/2;
        y = screenh/2;
        usuario.setXUbicacion(x);
        usuario.setYUbicacion(y);


        vectorCirculos = new Vector<Circulo>();
        leyenda = new HashMap<String,Color>();


        // La animación simplemente consiste redibujar todos los gráficos con los datos actualizados
        ActionListener animation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        };
        timer = new Timer(50, animation); 
        timer.start();


        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
                cambiaPosicion(e.getX(), e.getY());
                //System.out.println("Cambio de la posición del usuario: " + e.getX() + "," + e.getY());
                removeMouseListener(this);
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });


    }


    @Override
    public void paintComponent(Graphics g) {
        buffer = createImage(screenw, screenh);
        bg = buffer.getGraphics();

        int w = getWidth();
        int h = getHeight();

        // Imagen de fondo
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("background.jpg"));
		} catch (IOException e) {}
		bg.drawImage(img, 0, 0, null);

		// Características de las fuentes de texto
        bg.setFont(new Font("SanSerif", Font.PLAIN, 12));


        // Pintar el cliente
        bg.setColor(new Color(0,0,128,opacidad));
        bg.fillOval(x-10, y-10, 20, 20);


        for(Circulo c : vectorCirculos) {
            bg.setColor(c.getColor());
            bg.fillOval(c.getX(), c.getY(), c.getR(), c.getR());
            bg.drawString(c.getLabel(), c.getX()+2, c.getY()-2);
        }


        // Leyenda
        bg.setColor(Color.WHITE);
        bg.fillRect(10, 10, 150, 10+20*leyenda.size()); //fondo blanco
        int i = 0;
        for(Entry<String, Color> grupo : leyenda.entrySet()) {
            String texto = grupo.getKey();
            Color color = grupo.getValue();
            bg.setColor(color);
            bg.drawString(texto, 20, 30+20*i);
            i++;
        }
        bg.setColor(Color.BLACK);
        bg.drawRect (10, 10, 150, 10+20*i);




        g.drawImage(buffer, 0, 0, null);
    }

    public void cambiaMapa(Grupo arrayGrupos[]) {
        //System.out.println("Entro");
        vectorCirculos.removeAllElements();
        leyenda.clear();
        if(arrayGrupos != null) {
            //System.out.println("No es null");
            int i = 0;
            for (Grupo g : arrayGrupos) {
                Vector<Usuario> vectorUsuarios = g.getUsuarios();
                Color color = COLORES[(i)%COLORES.length];;
                for (Usuario u: vectorUsuarios) {
                    if(! u.getNombreUsuario().equals(usuario.getNombreUsuario())){
                        Circulo c = new Circulo();
                        c.setColor(color);
                        c.setX(u.getXUbicacion());
                        c.setY(u.getYUbicacion());
                        c.setLabel(u.getNombreUsuario());
                        vectorCirculos.add(c);
                    }
                }
                i++;
                leyenda.put(g.getNombreGrupo(),color);
            }
        }
    }


    private void cambiaPosicion(int x, int y){
        opacidad = 255;
        this.x = x;
        this.y = y;
        usuario.setXUbicacion(x);
        usuario.setYUbicacion(y);
    }

    public static double dist(double x1, double y1, double x2, double y2){
        double x = x2-x1;
        double y = y2-y1;
        return Math.sqrt((x*x)+(y*y));
    }

    public void drawCenteredCircle(Graphics g, int x, int y, int r) {

      x = x-(r/2);
      y = y-(r/2);
      g.fillOval(x,y,r,r);
    }

}