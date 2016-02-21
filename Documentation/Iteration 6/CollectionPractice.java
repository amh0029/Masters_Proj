import java.util.Collections;
import java.util.ArrayList;

public class CollectionPractice
{
   ArrayList<Integer> colorOrder = new ArrayList<Integer>();
   int red = 11111111;
   int green = 11111111;
   int blue = 11111111;
   int cover = 01010101;
   
   public void shuffleCheck()
   {
      Collections.addAll(colorOrder, red, green, blue, cover);
      for(int i = 0; i < 5; i++)
      {
         System.out.println(colorOrder);
         //Of course the integers do not update
         red += 100; 
         Collections.shuffle(colorOrder);
      }
   }
}