import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class game extends JFrame implements Runnable
{
    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;

    //Map layout, where 0 = empty space and other number are textures
    public static int[][] map =
            {
                    {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
                    {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                    {1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
                    {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                    {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
                    {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                    {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                    {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                    {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
            };

    //Runs the game and everything
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    //Initializes the variable
    public game()
    {
        thread = new Thread(this);
        image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setSize(640,480);
        setResizable(false);
        setTitale("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }

    //Start the program
    private synchronized void start()
    {
        running = true;
        thread.start();
    }

    //Ends the program
    public synchronized void stop()
    {
        running =false;
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //Buffer the graphics
    public void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image,0,0,image.getWidth(),image.getHeight(),null);
        bs.show();
    }

    //Updates the game (hopefully we hit 60 fps)
    public void run()
    {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running)
        {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime)/ns);
            LastTime = now;
            while(delta >= 1)
            {
                delta--;
            }
            render();
        }
    }

}
