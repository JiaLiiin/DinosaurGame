import java.util.Random;

public class BlockControl
{
  Random r=new Random();
  
  public int GetBlock() 
  {
  int num=r.nextInt(3);
  return num;
  }
  
  
  

}
