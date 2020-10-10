import tester.Tester;
import javalib.funworld.*;
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
}

class Player {
  int x; 
  int y; 
  int size; 
  Color col;
 
    // The constructor 
    Player(int x, int y, int radius, Color col) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.col = col;
    }
}


class EnemyFish {
  Location loc;
  int x; 
  int y; 
  int size; 
  Color col;
  
  // The constructor 
    EnemyFish(int x, int y, int radius, Color col) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.col = col;
    }
  
}


