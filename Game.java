
/*
 * Drawings of fish (Left and Right)
 * move based on arrow key- copy from blobworld
 * Player- if off screen move to other side of screen
 * Background fish enter one side and leave the other
 * Determine when the player fish can eat another fish.

Determine when the player has been eaten by another fish.

End the game when the player is the largest fish in the pond.

Allow the player to grow based on how many fish it has eaten (and how big they are).


/*
 * WISH LIST
 * List of Fish so we can recur through updating positions on tick
 * everything stated above at top
 * 
 */

import tester.Tester;
import javalib.funworld.*;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;



// Represent the world of the fish game
class FishWorld extends World {
    int width = 500;
    int height = 500;
    Player player;

    // The constructor 
    public FishWorld(Player player) {
        super();
        this.player = player;
    }

    @Override
    public WorldScene makeScene() {
      // TODO Auto-generated method stub
      return null;
    }
}

//represents a fish and holds methods
interface IFish{
  public WorldImage drawFish();
  }



//represents the general idea of a fish and its specs 
abstract class AFish implements IFish{
  int x; 
  int y; 
  int size; 
  int speed;
  boolean direction;
  Color color;
  
  
  AFish(int x, int y, int size, int speed, boolean direction, Color color) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.speed = speed;
    this.direction = direction;
    this.color = color;
  }
  /*
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * this.speed ...int
   * this.direction ... boolean //true represents right
   * this.color... color
   * 
   * methods:
   * methods for fields:
   */
  public WorldImage drawFish() {
    if (this.direction) {
      return new BesideImage(new RotateImage(new EquilateralTriangleImage(this.size / 2,
          OutlineMode.SOLID, this.color), 90),
          new EllipseImage(this.size, this.size / 2, OutlineMode.SOLID, this.color));
      }
    else {
      return new BesideImage(new EllipseImage(this.size, this.size / 2, 
          OutlineMode.SOLID, this.color),
          new RotateImage(new EquilateralTriangleImage(this.size / 2,
          OutlineMode.SOLID, this.color), 270));
    }
  }
  
   
  
}

//represents the player's fish
class Player extends AFish{

    //Player speed is automatically set to 5
    Player(int x, int y, boolean direction, int size) {
      super(x, y, size, 5, direction, Color.ORANGE);
    }
    /*
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * this.speed ...int
   * this.direction ... boolean
   * this.color... color
   * 
   * methods:
   * methods for fields:
   * */
}


class EnemyFish extends AFish{
  
    //enemy fish needs to have random speed, can decide on random color or set color later
    EnemyFish(int x, int y, int size, int speed, boolean direction, Color color) {
      super(x, y, size, speed, direction, color);
    }
    /*
   * this.x ... int
   * this.y ... int
   * this.size ... int
   * this.speed ...int
   * this.direction ... boolean
   * this.color... color
   * 
   * methods:
   * methods for fields:
   * */
  
}

class FishExamples {
  AFish player = new Player(250, 250, true, 10);
  //need to build a method to auto spawn fish randomly on the sides
  AFish enemy1 = new EnemyFish(125, 125, 50, 10, false, Color.BLUE);
  
  
boolean testDrawMobile(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(player.drawFish(), this.player.x, this.player.y)
        .placeImageXY(this.enemy1.drawFish(), this.enemy1.x, this.enemy1.y))
        && c.show();

  }
}
