
package graingrowth;


public class Array{
    private int x;
    private int y;
    private int id;
    private int number;

    public Array(){
        this.x = 0;
        this.y = 0;
        this.number = 0;
    }
    
    public Array(int y, int number, int x, int id){
        this.x = x;
        this.y = y;
        this.number = number;
        this.id = id;
    }
    
    public Array(int x, int y, int number){
        this.x = x;
        this.y = y;
        this.number = number;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getId(){
        return id;
    }
    
    public void number(){
        number++;
    }
    
    public boolean point2point(int x, int y,int r){
        return Math.sqrt(Math.pow(this.x - x,2)+Math.pow(this.x - x,2))>r ;
    }
    
    
    }
