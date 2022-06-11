package serpiente;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




@SuppressWarnings("serial")
public class Serpiente extends JFrame
{

	private JButton botonEmpezar = new JButton("Empezar ");
	private Dimension dim;
	
	public Serpiente()
	{
		setTitle("Serpiente");
		setSize(401, 200);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 -400/2, dim.height/2 -200/2);  /*Para que aparezca centrado*/
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setContentPane(new Panel());
		this.getContentPane().setLayout(null);
		this.getContentPane().add(botonEmpezar);
		
		botonEmpezar.setBounds(100, 75, 200, 27);
		botonEmpezar.addActionListener(new ActionListener()
		{
		        public void actionPerformed(ActionEvent e)
		        {
		    		Snake D = new Snake();
		        	D.setVisible(true);
		        	
		        	Serpiente.this.dispose();
		        }
		});
		
		ImageIcon icon =new ImageIcon(getClass().getResource("/imgs/play.png"));
		ImageIcon icon2 =new ImageIcon(getClass().getResource("/imgs/snake.png"));
		Image image2 = icon2.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		
		botonEmpezar.setIcon(icon);
		setIconImage(image2);
		botonEmpezar.setFocusable(false);
	}
	
	public class Panel extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			this.setSize(400,200);
			super.paintComponent(g);
			
			g.setColor(new Color(0,100,250));
			Graphics2D g2 = (Graphics2D) g;
			float thickness = 4;
			g2.setStroke(new BasicStroke(thickness));
			g2.drawLine(1, 1, 400, 1);
			g2.setStroke(new BasicStroke());
		}
	}
	
 	public static void main(String[] args)
      {
		Serpiente t = new Serpiente();
     	t.setVisible(true);
      }
}