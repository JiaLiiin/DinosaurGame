import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.Image;
import javax.swing.ImageIcon;



public class Game extends JPanel implements ActionListener, KeyListener{
  Label l;
  Random r=new Random();
  Timer t=new Timer(5,this);
  static boolean jump=false; static boolean OnScreen=false; static boolean enemy=false; static boolean down=false;
  double PlayerY=320; double MovementY=4.5;
  double EnemyX=680; double MovementX=-1;
  double GroundStart=0;
  double secondspassed;
  int Input;
  double timepassed;
  int check=1;
  int LengthX=0;
  int highest=0;
  int spot=60;
  boolean done=false;
  double gravity=.1;
  
  ImageIcon Dinosaurleft = new ImageIcon("left.png");
  ImageIcon Dinosaurright = new ImageIcon("right.png");
  ImageIcon Dinosaurjump = new ImageIcon("jump.png");
  ImageIcon Cactus = new ImageIcon("cac.png");
  ImageIcon EasyCactus = new ImageIcon("easycac.png");
  ImageIcon ground = new ImageIcon("background.png");
  
  Image Player, Block, Background;
  long starttime=System.currentTimeMillis();
  
  BlockControl b=new BlockControl();
  
  int[] scoreboard= {0,0,0};
  
  
  enum page{
    LOSE,
    PLAY
  }
  page p=page.PLAY;


  public Game() { //activates listeners
    t.start();    
    addKeyListener(this); 
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);  

  }

  public void paintComponent(Graphics g) {

    
    if (p==page.PLAY)
    {
      
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, 1000, 1000);
      Background=ground.getImage();
      g.drawImage(Background, (int)GroundStart, 0,5000,725,l);
      
      timepassed=System.currentTimeMillis()-starttime;
      secondspassed=timepassed/1000;
      g.setColor(Color.BLACK);
      g.drawString("score: "+(int)secondspassed,10,10);
 
      
      if (enemy==false&&secondspassed>0)
      {
        Input=b.GetBlock();
        enemy=true;
      }

    //cactus
    if (enemy=true&&Input==1)
    {
      LengthX=50;

      Block=Cactus.getImage();
      g.drawImage(Block, (int)EnemyX, 330,LengthX,30,l);
    }
    else if (enemy=true&&Input==2)
    {
      LengthX=25;

      Block=EasyCactus.getImage();
      g.drawImage(Block, (int)EnemyX, 330,LengthX,30,l);

    }
    else if (enemy=true&&Input==0)
    {
      LengthX=25;
      Block=EasyCactus.getImage();
      g.drawImage(Block, (int)EnemyX, 330,LengthX,30,l);

    }
    //motion
    if (PlayerY<320)
    {
      Player=Dinosaurjump.getImage();
      g.drawImage(Player, 80,(int)PlayerY,60,40,l);
    }
    else if (timepassed%2==0)
    {
      Player=Dinosaurleft.getImage();
      g.drawImage(Player, 80,(int)PlayerY,60,40,l);
    }
    else
    {
      Player=Dinosaurright.getImage();
      g.drawImage(Player, 80,(int)PlayerY,60,40,l);
    }


  }
    

    else if (p==page.LOSE)
    {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 1000, 1000);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
      g.setColor(Color.WHITE);
          
        g.drawString("Press \"R\" to return", 220, 300);
        g.drawString("You just earned "+(int)secondspassed, 220, 20);
        g.drawString("first place: "+ scoreboard[0], 230, 50);
        g.drawString("second place: "+ scoreboard[1], 230, 80);
        g.drawString("third place: "+ scoreboard[2], 230, 110);

      }
        
    
    }
  

  public void actionPerformed(ActionEvent e)
  { 
   
    MovementX= (-(Math.round(timepassed /10000.0 * 1000.0) / 1000.0) -1);
  
    if(p==page.PLAY)
    {
      EnemyX+=MovementX;
      GroundStart+=MovementX;

    }
    if (p==page.LOSE&&done==false&&(int) secondspassed>scoreboard[0])
    {
      scoreboard[2]=scoreboard[1];
      scoreboard[1]=scoreboard[0];
      scoreboard[0]=(int) secondspassed;   
      done=true;
    }
    else if(p==page.LOSE&&done==false&&(int) secondspassed>scoreboard[1])
    {
      scoreboard[2]=scoreboard[1];
      scoreboard[1]=(int) secondspassed;
      done=true;
    }
    else if(p==page.LOSE&&done==false&&(int) secondspassed>scoreboard[2])
    {
      scoreboard[2]=(int) secondspassed;
      done=true;
    }
   

    if (GroundStart<=-1345)
      {
      GroundStart=0;
      }

    if (EnemyX+60<0)
    {
      enemy=false;
      EnemyX=680;
    }


    
    
    if (jump==true)
    {
      PlayerY-=MovementY;
      MovementY-=gravity;
    }
    
    if (PlayerY>319)
    {
      jump=false;
    }

 
    
    if (PlayerY<=319 && down==true)
    {
      MovementY=2;
    }

    if(PlayerY>=320) PlayerY=320;
    
    
    
    if((80>=EnemyX-40&&120<=EnemyX+LengthX+30)&&(PlayerY>=290&&PlayerY<=330))
    {
      p=page.LOSE;
    }

    repaint();

  }

  public void keyTyped(KeyEvent e)
  {
    
  }


  public void keyPressed(KeyEvent e)
  {
    int c=e.getKeyCode();
    if (c==KeyEvent.VK_SPACE && PlayerY>=319)
    {
      MovementY=4.5;
      jump=true;
    }
    if (c==KeyEvent.VK_R)
    {
      starttime=System.currentTimeMillis();
      timepassed=0;
      EnemyX=680;
      MovementX=-1;
      GroundStart=0;
      p=page.PLAY;
      done=false;
      spot=220;
    }
    //if (c==KeyEvent.VK_S)
   // {
    //  down=true;
    //  jump=false;
    //}
    
    
  }


  public void keyReleased(KeyEvent e)
  {
    int c=e.getKeyCode();
    if (c==KeyEvent.VK_S)
    {
      down=false;
    }
  }


  public static void main(String[] args)
  {
    JFrame application = new JFrame();
    

    application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    application.setLocation(588,280);

    Game myPanel = new Game();
    
    application.add(myPanel);

    application.setSize(700, 420);

    application.setVisible(true);


  }
}

