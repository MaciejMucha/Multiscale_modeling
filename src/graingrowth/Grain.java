package graingrowth;

public class Grain {


    private int id = 0;
    private boolean bi = false;
    private boolean r = false;
    private int R;
    private int G;
    private int B;
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isbi() {
        return bi;
    }

    public void setB(boolean b) {
        this.bi = bi;
    }

    public boolean isR() {
        return r;
    }

    public void setR(boolean r) {
        this.r = r;
    }
    
    public void setRGB(int R, int G, int B)
    {
      this.R = R;
      this.G = G;
      this.B = B;
    }
    
    public int getR() {
        return R;
    }
    
    public int getG() {
        return G;
    }
    
    public int getB() {
        return B;
    }
    
}
  

