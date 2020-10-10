import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;


// Represent the world of a Blob 
class BlobWorldFun extends World {
    int width = 200;
    int height = 300;
    Blob blob;

    // The constructor 
    public BlobWorldFun(Blob blob) {
        super();
        this.blob = blob;
    }

    // Move the Blob when the player presses a key 
    public World onKeyEvent(String ke) {
        if (ke.equals("x"))
            return this.endOfWorld("Goodbye");
        else
            return new BlobWorldFun(this.blob.moveBlob(ke));
    }

    // On tick move the Blob in a random direction.
    public World onTick() {
        return new BlobWorldFun(this.blob.randomMove(5));
    }

     // On mouse click move the blob to the mouse location, make the color red.
    public World onMouseClicked(Posn loc) {
        return new BlobWorldFun(new Blob(loc.x, loc.y, 20, Color.RED));
    }

    // The entire background image for this world 
    public WorldImage blackHole = new OverlayImage(new CircleImage(10,
            OutlineMode.SOLID, Color.BLACK), new RectangleImage(this.width,
            this.height, OutlineMode.SOLID, Color.BLUE));

     // produce the image of this world by adding the moving blob to the
     // background image
    public WorldScene makeScene() {
        return this
                .getEmptyScene()
                .placeImageXY(this.blackHole, this.width / 2, this.height / 2)
                .placeImageXY(this.blob.blobImage(), this.blob.x,
                        this.blob.y);
    }

    // produce the last image of this world by adding text to the image 
    public WorldScene lastScene(String s) {
        return this.makeScene().placeImageXY(new TextImage(s, Color.red), 100,
                40);
    }

    
     // Check whether the Blob is out of bounds, or fell into the black hole in
     // the middle.
    public WorldEnd worldEnds() {
        // if the blob is outside the canvas, stop
        if (this.blob.outsideBounds(this.width, this.height)) {
            return new WorldEnd(true,
                    this.lastScene("Blob is outside the bounds"));
        }
        // time ends is the blob falls into the black hole in the middle
        if (this.blob.nearCenter(this.width, this.height)) {
            return new WorldEnd(true, this.makeScene().placeImageXY(
                    new TextImage("Black hole ate the blob", 13, FontStyle.BOLD_ITALIC, Color.red),
                    100, 40));
        } else {
            return new WorldEnd(false, this.makeScene());
        }
    }

}

// Class that represents a colored circle that moves around the Canvas 
class Blob {
	int x;
	int y;
    int radius;
    Color col;

    // The constructor 
    Blob(int x, int y, int radius, Color col) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.col = col;
    }

    // produce the image of this blob 
    WorldImage blobImage() {
        return new CircleImage(this.radius, "outline", this.col);
    }

     // move this blob 5 pixels in the direction given by the ke or change its
     // color to Green, Red or Yellow
    public Blob moveBlob(String ke) {
        if (ke.equals("right")) {
            return new Blob(this.x + 5, this.y, this.radius, this.col);
        } else if (ke.equals("left")) {
            return new Blob(this.x - 5, this.y, this.radius, this.col);
        } else if (ke.equals("up")) {
            return new Blob(this.x, this.y - 5, this.radius, this.col);
        } else if (ke.equals("down")) {
            return new Blob(this.x, this.y + 5, this.radius, this.col);
        }
        // change the color to Y, G, R
        else if (ke.equals("Y")) {
            return new Blob(this.x, this.y, this.radius, Color.YELLOW);
        } else if (ke.equals("G")) {
            return new Blob(this.x, this.y, this.radius, Color.GREEN);
        } else if (ke.equals("R")) {
            return new Blob(this.x, this.y, this.radius, Color.RED);

        } else
            return this;
    }

    // produce a new blob moved by a random distance < n pixels 
    Blob randomMove(int n) {
        return new Blob(this.x + this.randomInt(n),
                this.y + this.randomInt(n), this.radius, this.col);
    }

    // helper method to generate a random number in the range -n to n 
    int randomInt(int n) {
        return -n + (new Random().nextInt(2 * n + 1));
    }

    // is the blob outside the bounds given by the width and height 
    boolean outsideBounds(int width, int height) {
        return this.x < 0 || this.x > width || this.y < 0
                || this.y > height;
    }

    // is the blob near the center of area given by the width and height 
    boolean nearCenter(int width, int height) {
        return this.x > width / 2 - 10 && this.x < width / 2 + 10
                && this.y > height / 2 - 10
                && this.y < height / 2 + 10;
    }
}

class BlobExamples {

    // examples of data for the Blob class:
    Blob b1 = new Blob(100, 100, 50, Color.RED);
    Blob b1left = new Blob(95, 100, 50, Color.RED);
    Blob b1right = new Blob(105, 100, 50, Color.RED);
    Blob b1up = new Blob(100, 95, 50, Color.RED);
    Blob b1down = new Blob(100, 105, 50, Color.RED);
    Blob b1G = new Blob(100, 100, 50, Color.GREEN);
    Blob b1Y = new Blob(100, 100, 50, Color.YELLOW);

    // examples of data for the BlobWorldFun class:
    BlobWorldFun b1w = new BlobWorldFun(this.b1);
    BlobWorldFun b1leftw = new BlobWorldFun(this.b1left);
    BlobWorldFun b1rightw = new BlobWorldFun(this.b1right);
    BlobWorldFun b1upw = new BlobWorldFun(this.b1up);
    BlobWorldFun b1downw = new BlobWorldFun(this.b1down);
    BlobWorldFun b1Gw = new BlobWorldFun(this.b1G);
    BlobWorldFun b1Yw = new BlobWorldFun(this.b1Y);
    BlobWorldFun b1mouse50x50w = new BlobWorldFun(new Blob(50, 50, 20, Color.RED));

    BlobWorldFun bwOutOfBounds = new BlobWorldFun(new Blob(100, 350, 50, Color.RED));

    BlobWorldFun bwInTheCenter = new BlobWorldFun(new Blob(100, 150, 50, Color.RED));
    
    boolean testBlobWorld(Tester t) {
    	
        // run the game
        BlobWorldFun w = new BlobWorldFun(new Blob(150, 100, 20,
                Color.RED));
        return w.bigBang(200, 300, 0.3);
             
    }

    // test the method moveBlob in the Blob class 
    boolean testMoveBlob(Tester t) {
        return t.checkExpect(this.b1.moveBlob("left"), this.b1left,
                "test moveBolb - left " + "\n")
                && t.checkExpect(this.b1.moveBlob("right"), this.b1right,
                        "test movelob - right " + "\n")
                && t.checkExpect(this.b1.moveBlob("up"), this.b1up,
                        "test moveBlob - up " + "\n")
                && t.checkExpect(this.b1.moveBlob("down"), this.b1down,
                        "test moveBlob - down " + "\n")
                && t.checkExpect(this.b1.moveBlob("G"), this.b1G,
                        "test moveBlob - G " + "\n")
                && t.checkExpect(this.b1.moveBlob("Y"), this.b1Y,
                        "test moveBlob - Y " + "\n")
                && t.checkExpect(this.b1G.moveBlob("R"), this.b1,
                        "test moveBlob - R " + "\n");
    }

    // test the method onKeyEvent in the BlobWorldFun class 
    boolean testOnKeyEvent(Tester t) {
        return t.checkExpect(this.b1w.onKeyEvent("left"), this.b1leftw,
                "test moveBolb - left " + "\n")
                && t.checkExpect(this.b1w.onKeyEvent("right"), this.b1rightw,
                        "test movelob - right " + "\n")
                && t.checkExpect(this.b1w.onKeyEvent("up"), this.b1upw,
                        "test moveBlob - up " + "\n")
                && t.checkExpect(this.b1w.onKeyEvent("down"), this.b1downw,
                        "test moveBlob - down " + "\n")
                && t.checkExpect(this.b1w.onKeyEvent("G"), this.b1Gw,
                        "test moveBlob - G " + "\n")
                && t.checkExpect(this.b1w.onKeyEvent("Y"), this.b1Yw,
                        "test moveBlob - Y " + "\n")
                && t.checkExpect(this.b1Gw.onKeyEvent("R"), this.b1w,
                        "test moveBlob - R " + "\n")
                &&

                // to test the world ending, verify the value of the lastWorld
                t.checkExpect(
                        this.b1Gw.onKeyEvent("x").lastWorld,
                        new WorldEnd(true, this.b1Gw.makeScene().placeImageXY(
                                new TextImage("Goodbye", Color.red), 100, 40)));
    }

    // test the method outsideBounds in the Blob class 
    boolean testOutsideBounds(Tester t) {
        return t.checkExpect(this.b1.outsideBounds(60, 200), true,
                "test outsideBounds on the right")
                &&

                t.checkExpect(this.b1.outsideBounds(100, 90), true,
                        "test outsideBounds below")
                &&

                t.checkExpect(new Blob(-5, 100, 50, Color.RED)
                        .outsideBounds(100, 110), true,
                        "test outsideBounds above")
                &&

                t.checkExpect(new Blob(80, -5, 50, Color.BLUE)
                        .outsideBounds(100, 90), true,
                        "test outsideBounds on the left")
                &&

                t.checkExpect(this.b1.outsideBounds(200, 400), false,
                        "test outsideBounds - within bounds");
    }

    // test the method onMOuseClicked in the BlobWorldFun class 
    boolean testOnMouseClicked(Tester t) {
        return t.checkExpect(this.b1w.onMouseClicked(new Posn(50, 50)),
                this.b1mouse50x50w);
    }

    // test the method nearCenter in the Blob class 
    boolean testNearCenter(Tester t) {
        return t.checkExpect(this.b1.nearCenter(200, 200), true,
                "test nearCenter - true")
                && t.checkExpect(this.b1.nearCenter(200, 100), false,
                        "test nearCenter - false");
    }

    // the method randomInt in the Blob class 
    boolean testRandomInt(Tester t) {
        return t.checkOneOf("test randomInt", this.b1.randomInt(3), -3, -2, -1,
                0, 1, 2, 3)
                && t.checkNoneOf("test randomInt", this.b1.randomInt(3), -5,
                        -4, 4, 5);
    }

    // test the method randomMove in the Blob class 
    boolean testRandomMove(Tester t) {
        return t.checkOneOf("test randomMove", this.b1.randomMove(1), new Blob(
                99, 99, 50, Color.RED), new Blob(99, 100,
                50, Color.RED), new Blob(99, 101, 50, Color.RED),
                new Blob(100, 99, 50, Color.RED), new Blob(
                        100, 100, 50, Color.RED), new Blob(100, 101,
                        50, Color.RED), new Blob(101, 99, 50,
                        Color.RED),
                new Blob(101, 100, 50, Color.RED), new Blob(
                        101, 101, 50, Color.RED));
    }

    /** test the method onTick in the BlobWorldFun class */
    boolean testOnTick1(Tester t) {
        boolean result = true;
        for (int i = 0; i < 20; i++) {
            BlobWorldFun bwf = (BlobWorldFun) this.b1w.onTick();
            result = result && t.checkRange(bwf.blob.x, 95, 106)
                    && t.checkRange(bwf.blob.y, 95, 106);
        }
        return result;
    }

    
     // test the method onTick when the world should end in the BlobWorldFun
     // class
    boolean testOnTick2(Tester t) {
        return
        // to test the world ending, verify the value of the lastWorld
        t.checkExpect(
                this.bwOutOfBounds.testOnTick().lastWorld,
                new WorldEnd(true, this.bwOutOfBounds
                        .lastScene("Blob is outside the bounds")))
                &&

                t.checkExpect(
                        this.bwInTheCenter.testOnTick().lastWorld,
                        new WorldEnd(true, this.bwInTheCenter.makeScene()
                                .placeImageXY(
                                        new TextImage(
                                                "Black hole ate the blob", 13,
                                                FontStyle.BOLD_ITALIC, Color.red), 100, 40)));
    }


    // test the method worldEnds for the class BlobWorld
    boolean testWorldEnds(Tester t) {
        return t.checkExpect(
                this.bwOutOfBounds.worldEnds(),
                new WorldEnd(true, this.bwOutOfBounds.makeScene().placeImageXY(
                        new TextImage("Blob is outside the bounds", Color.red),
                        100, 40)))
                &&

                t.checkExpect(
                        this.bwInTheCenter.worldEnds(),
                        new WorldEnd(true, this.bwInTheCenter.makeScene()
                                .placeImageXY(
                                        new TextImage(
                                                "Black hole ate the blob", 13,
                                                FontStyle.BOLD_ITALIC, Color.red), 100, 40)))
                &&

                t.checkExpect(this.b1w.worldEnds(), new WorldEnd(false,
                        this.b1w.makeScene()));
    }

  
