package graingrowth;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
public class Energy {
    
    
public class CanvasEnergy extends JComponent{
    private int tab[][];
    private Grain grains[][];
    private boolean b = false; 
    private final int size_x = 300;
    private final int size_y = 300;
    private final int max_x = 300;
    private final int max_y = 300;
    private int smallEnergy;
    private int bigEnergy;
    private boolean showEdge;

    public void changeShowEdge() {
        showEdge = !showEdge;
    }
       
    public CanvasEnergy(){
        grains = new Grain[size_x][size_y];
        for(int i=0;i<size_x;i++){
            for(int j=0;j<size_y;j++){
                grains[i][j] = new Grain();
            } 
        }  
        showEdge = false;
    }
    
    public CanvasEnergy(int low, int high){
        this();
        smallEnergy = low;
        bigEnergy = high;
    }
    
    public void setRGB(Grain[][] grains) {
        for(int j=0;j<size_y;j++){
            for(int i=0;i<size_x;i++){
                if (grains[i][j].isbi() || grains[i][j].isR())
                    grains[i][j].setRGB(255, 0, 0);
                else
                    grains[i][j].setRGB(0, 0, 255);
            }
        }
    }
    
    public void setGrains(Grain[][] grains){
        setRGB(grains);
        this.grains = grains;
    }
        
    public void paint(Graphics g){
        for(int j=0;j<size_y;j++){
            for(int i=0;i<size_x;i++){
                if(grains[i][j].getId() == 0 || (grains[i][j].isbi() && showEdge) ){
                    g.setColor(Color.BLACK);
                }else{
                    g.setColor(new Color(grains[i][j].getR(),grains[i][j].getG(),grains[i][j].getB()));
                }
                g.fillRect(Math.round((max_x/size_x)*i),Math.round((max_y/size_y)*j),Math.round(max_x/size_x),Math.round(max_y/size_y));
            } 
        }
    }
}
}


