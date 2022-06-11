package serpiente;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Toolkit;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Snake extends JFrame /*La clase principal es una ventana (JFrame)*/
{
	/** Ancho de la ventana*/
	private int widht = 640;
	/** Alto de la ventana*/
	private int height = 480;
	
	/** Coordenadas de la serpiente*/
	private Point snake;
	/** Coordenadas de la comida*/
	private Point comida;
	
	/** Panel en el que se generan todos los gráficos*/
	private ImagenSnake imagenSnake;
	
	/** Ancho de la serpiente*/
	private int widthPoint = 10;
	/** Alto de la serpiente*/
	private int heightPoint= 10;
	
	/** Lista de cuadrados azules que no son la cabeza de la serpiente*/
	private ArrayList<Point> puntos = new ArrayList<Point>();
	
	/** Direccion a la que se va a mover la cabeza de la serpiente*/
	private int direccion = KeyEvent.VK_UP;
	/** Frecuencia a la que se procesa los cambios de imagen de los cubos*/
	private long frecuencia = 100;
	/** Binario que dice si la serpiente se debe de mover o no*/
	private boolean movimiento = true;
	
	/** Binario que dice si se termina el juego*/
	private boolean gameOver = false;
	/** Binario que dice si está en uso el modo desarrollador*/
	private boolean desarrollador = false;
	/** Binario que dice si la serpiente es inmortal*/
	private boolean inmortal = false;
	
	public Snake()
	{
		setTitle("Serpiente");       /*Nombre de la ventana*/
		this.setSize(widht, height); /*Tamaño de la ventana*/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 -widht/2, dim.height/2 -height/2);  /*Para que aparezca centrado*/
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);    /* Para apagar el programa cuando cierre la ventana*/
		
		ImageIcon icon =new ImageIcon(getClass().getResource("/imgs/snake.png"));       /*Para cargar la imagen del logo*/
		Image image = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);  /*Para transformar de "ImageIcon" a "Image"*/
		setIconImage(image);
		
		Teclas teclas = new Teclas();
		this.addKeyListener(teclas);   /*Añade la clase Teclas a la ventana*/
		
		iniciaCoordenadas();
		
		imagenSnake = new ImagenSnake();
		this.getContentPane().add(imagenSnake); /*Añadir el JPanel (imagenSnake) a la ventana JFarme (snake)*/
		
		Momento momento = new Momento();
		Thread trid = new Thread(momento);
		trid.start();
	}
	
	/** Método que inicializa las posiciones de la serpiente y la comida*/
	private void iniciaCoordenadas()
	{
		snake = new Point((widht/2), (height/2)+5);   /*Posición de la serpiente dentro de la ventana*/
		comida = new Point(0,0);
		
		crearComida();
	}
	
	/** Método que cambia la posición de la comida a una aleatoria*/
	private void crearComida()
	{
		Random rnd = new Random();
		comida.x = rnd.nextInt(widht);
		if(comida.x < 0)
			comida.x=comida.x -20;
		if(comida.x > widht-20)
			comida.x = comida.x -50;
		
		comida.y = rnd.nextInt(height);
		
		if(comida.y > height-40)
			comida.y = comida.y -50;
		if(comida.y < 0)
			comida.y = comida.y - 40;
		
		
		if((comida.x % 10)>0)
			comida.x = comida.x-(comida.x % 10);
		if((comida.y % 10)>0)
			comida.y = comida.y-(comida.y % 10);
		
		comida.y = comida.y + 5;
	}
	
	/** Método que hace crecer la serpiente*/
	private void crece()
	{
		puntos.add(0,new Point(snake.x,snake.y)); // Añade un nuevo punto
		if(puntos.size() == 1)
		{
			puntos.add(0,new Point(snake.x,snake.y));
		}
	}
	
	public static void main(String[] args)
	{
		Snake D = new Snake(); /*Crea la ventana*/
		D.setVisible(true);    /*Hacer visible la ventana*/   /* Para modo pantalla completa ->  D.setExtendedState(Frame.MAXIMIZED_BOTH)*/
	}
	/** Clase que tiene información de lo que hay que hacer cuando se pulsa una tecla y ve si pulsamos alguna de estas*/
	public class Teclas extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				System.exit(0);
			}
			
			if(e.getKeyChar() == "n".charAt(0)) // Para cambiar la posición de la comida
			{
				if(desarrollador)
				{
					Snake.this.crearComida();
				}
			}
			
			if(e.getKeyChar() == "i".charAt(0)) // Para ser inmortal
			{
				if(desarrollador)
				{
					inmortal =! inmortal;
				}
			}
			
			if(e.getKeyChar() == "c".charAt(0)) // Para hacer crecer la serpiente
			{
				if(desarrollador)
				{
					Snake.this.crece();
				}
			}
			
			if(e.getKeyChar() == "d".charAt(0))  // Para activar o desactivar el modo de desarrollador
				{
					desarrollador = !desarrollador;
				}
			
			if(e.getKeyChar() == "k".charAt(0))  // Para aumentar velocidad
			{
				if(desarrollador)
				{
					if(frecuencia > 25)
						frecuencia = frecuencia - 25;
				}
			}
			
			if(e.getKeyChar() == "j".charAt(0))  // Para reducir velocidad
			{
				if(desarrollador)
					frecuencia = frecuencia + 25;
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(direccion != KeyEvent.VK_DOWN)
					direccion = KeyEvent.VK_UP;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if(direccion != KeyEvent.VK_UP)
					direccion = KeyEvent.VK_DOWN;
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(direccion != KeyEvent.VK_RIGHT)
					direccion = KeyEvent.VK_LEFT;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(direccion != KeyEvent.VK_LEFT)
					direccion = KeyEvent.VK_RIGHT;
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				movimiento= !(movimiento);
//				snake.y = 0;
//				snake.x = 100;
			}
		}
	}
	
	
	/**Clase que tiene los gráficos de la serpiente, la comida, el borde y si está activada la variable desarrollador, también los de la información adicional*/
	public class ImagenSnake extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			g.setColor(new Color(0,0,255));
			g.fillRect(snake.x, snake.y, widthPoint, heightPoint);
			
			for(int i=0;i<puntos.size();i++)
			{
				Point p = puntos.get(i);
				g.fillRect(p.x, p.y, widthPoint, heightPoint);
			}
			
			g.setColor(new Color(255,0,0));
			g.fillRect(comida.x, comida.y, widthPoint, heightPoint);
			
			if(gameOver && !inmortal)
			{
				g.setFont(new Font("TimesRoman", Font.PLAIN, 75)); 
				g.drawString("Game Over", ((widht/2)-170), (height/2));
				movimiento=false;
			}
			
			g.setColor(new Color(87,166,57));
			Graphics2D g2 = (Graphics2D) g;
			float thickness = 4;
			g2.setStroke(new BasicStroke(thickness));
			g2.drawRect(1, 1, widht-18, height-41);
			g2.setStroke(new BasicStroke());
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
			if(desarrollador)
			{
				g.setColor(new Color(255,0,0));
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
				g.drawString("coordenada x: "+String.valueOf(comida.x), (10), (25));
				g.drawString("coordenada y: "+String.valueOf(comida.y), (10), (45));
				
				g.setColor(new Color(0,0,255));
				g.drawString("coordenada x: "+String.valueOf(snake.x), (10), (125));
				g.drawString("coordenada y: "+String.valueOf(snake.y), (10), (145));

				g.drawString("Velocidad de la serpiente: "+String.valueOf(((1/Long.valueOf(frecuencia).doubleValue())*100)), (10), (175));
				if(frecuencia == 25)
					g.drawString(" (Máximo)", (325), (170));
				
				if(inmortal == true)
					g.drawString("Inmortal", (10), (205));
				else
					g.drawString("Mortal", (10), (205));
			}
			
			g.setColor(new Color(130,130,130));
			if(puntos.size() == 0)
			{
				g.drawString(" Puntuación: 0", (450), (25));
			}
			else
			{
				g.drawString(" Puntuación: "+((puntos.size())-1), (450), (25));
			}
			
		}
	}
	
	public class Momento extends Thread
	{
		private long last = 0;
		public void run()
		{
			while(movimiento)
			{
				if(java.lang.System.currentTimeMillis()-last > frecuencia)
				{
					if(direccion == KeyEvent.VK_UP)
					{
						snake.y = snake.y - heightPoint;
						if(snake.y < -5)                       /*Cero es la altura del techo*/
							snake.y = height - 45;
					}
					else if(direccion == KeyEvent.VK_DOWN)
					{
						snake.y = snake.y + heightPoint;
						if(snake.y > height-40)
							snake.y = -5;
					}
					else if(direccion == KeyEvent.VK_LEFT)
					{
						snake.x = snake.x - widthPoint;
						if(snake.x < 0)
							snake.x = widht-20;
					}
					else if(direccion == KeyEvent.VK_RIGHT)
					{
						snake.x = snake.x + widthPoint;
						if(snake.x > widht-20)
							snake.x = 0;
					}
					
					actualizar();
					last = java.lang.System.currentTimeMillis();
					espera((Long.valueOf(frecuencia).intValue())/2);
				}
			}
			espera((Long.valueOf(frecuencia).intValue())/2);
			if(!gameOver)
				run();
		}
		
		/** Primero pinta la serpiente entera, después actualiza (para la siguiente vez) el resto de cubos azules y comprueba si choca con un cubo azul o rojo */
		private void actualizar()
		{
			if(!gameOver)
			{
				imagenSnake.repaint();
				
				puntos.add(0,new Point(snake.x,snake.y));  // Añade al principio un nuevo punto
				puntos.remove(puntos.size()-1);            // Y elimina el más antiguo
				
				// Si entra en contacto con un cubo azul (que no sea el primero / la propia cabeza)
				for(int i=1;i<puntos.size();i++)
				{
					Point punto = puntos.get(i);
					if(snake.x==punto.x && snake.y==punto.y)
					{
						if(!inmortal)
							gameOver=true;
					}
				}
				
				// Si entra en contacto con la comida
				if(snake.x > (comida.x -10) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10)) && (snake.y < (comida.y + 10)))
				{
					crece();
					crearComida();
				}
			}
		}
		
		/** Hace que el hilo en ejecución se pare durante un tiempo 'n' medido en milisegundos (Para que no esté todo el tiempo funcionando, que tenga pausas)*/
		private void espera(int n)
		{
			try
			{
				Thread.sleep(n);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}
}