
package graingrowth;



import java.util.Random;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.ArrayList;
import java.awt.Color;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

public class Board1 {

    private final int size_x;
    private final int size_y;
    private Grain boardGrain[][];
    private Grain boardGrain_stm[][];
    private int board_stm[][];

    private int countGrainsCristal = 0;
    private int countGrainsRecristal = 0;

    private int n;
    private Random rand = new Random();
    private boolean endSimulation;

    private final double reA = 86710969050178.5;
    private final double reB = 9.41268203527779;
    private final double roMax = 28105600.95;
    private boolean contentGrains;
    private boolean edgeGenerated;
    private ArrayList<Integer> grainsToSkip;

    public void changeContentGrains() {
        contentGrains = !contentGrains;
    }
   
    public boolean isEndSimulation() {
        return endSimulation;
    }

    public Board1(int size_x, int size_y) {
        contentGrains = false;
        edgeGenerated = false;
        endSimulation = false;
        this.size_x = size_x;
        this.size_y = size_y;
        n = 0;
        boardGrain = new Grain[size_x][size_y];
        boardGrain_stm = new Grain[size_x][size_y];
        board_stm = new int[size_x][size_y];
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain[i][j] = new Grain();
                boardGrain_stm[i][j] = new Grain();
            }
        }
    }

    public Board1() {
        contentGrains = false;
        endSimulation = false;
        this.size_x = 300;
        this.size_y = 300;
        n = 0;
        boardGrain = new Grain[size_x][size_y];
        boardGrain_stm = new Grain[size_x][size_y];
        board_stm = new int[size_x][size_y];
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain[i][j] = new Grain();
                boardGrain_stm[i][j] = new Grain();
            }
        }
    }


    public Grain[][] randomBoard(int randomSetup, int countX, int countY, int randomSize, int ringSize, int countRing) {
        switch (randomSetup) {
            case 0:

                for (int i = 0; i < size_x; i++) {
                    for (int j = 0; j < size_y; j++) {
                        if (rand.nextInt(1000) > 998) {
                            n++;
                            boardGrain[i][j].setId(n);
                        }
                    }
                }

                break;
            case 1:

                for (int i = countX; i < size_x; i += countX) {
                    for (int j = countY; j < size_y; j += countY) {
                        n++;
                        boardGrain[i][j].setId(n);
                    }
                }

                break;
            case 2:

                for (int i = 0; i < randomSize; i++) {
                    n++;
                    boardGrain[rand.nextInt(size_x)][rand.nextInt(size_y)].setId(n);
                }

                break;
            case 3:

                ArrayList<Array> points = new ArrayList<>();

                for (int i = 0; i < countRing; i++) {
                    int spr = 0;
                    int randX = rand.nextInt(size_x);
                    int randY = rand.nextInt(size_y);
                    boolean findOk = false;
                    while (spr < 100) {
                        spr++;
                        findOk = true;

                        for (Array p : points) {
                            findOk = true;

                            if (!p.point2point(randX, randY, ringSize)) {
                                findOk = false;
                                randX = rand.nextInt(size_x);
                                randY = rand.nextInt(size_y);
                                break;
                            }
                        }
                        if (findOk) {
                            n++;
                            points.add(new Array(randX, randY, 0, n));
                            break;
                        }
                    }
                }
                for (Array p : points) {
                    boardGrain[p.getX()][p.getY()].setId(p.getId());
                }

                break;
            default:
                break;
        }
        countGrainsCristal = n;
        return boardGrain;
    }

    public Grain[][] clear() {
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain[i][j].setId(0);
            }
        }
        for (int i = 0; i < size_x; i++) {
            for (int j = 1; j < size_y; j++) {
                boardGrain[i][j].setB(false);
            }
        }
        n = 0;
        edgeGenerated = false;
        return boardGrain;
    }

    public Grain[][] edge() {
        for (int i = 1; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (boardGrain[i][j].getId() != boardGrain[i - 1][j].getId() && !boardGrain[i][j].isbi()) {
                    boardGrain[i][j].setB(true);
                }
            }
        }
        for (int i = 0; i < size_x; i++) {
            for (int j = 1; j < size_y; j++) {
                if (boardGrain[i][j].getId() != boardGrain[i][j - 1].getId() && !boardGrain[i][j].isbi()) {
                    boardGrain[i][j].setB(true);
                }
            }
        }
        edgeGenerated = true;
        return boardGrain;
    }

    public Grain[][] addGrain(int i, int j) {
        n++;
        boardGrain[i][j].setId(n);
        countGrainsCristal = n;
        return boardGrain;
    }

    public int recrystal() {
        int sum = 0;
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (boardGrain[i][j].isR()) {
                    sum++;
                }
            }
        }
        return sum;
    }
    
    public ArrayList<Grain> getGrainsOnBorder(){
        ArrayList<Grain> grainList = new ArrayList();
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if(boardGrain[i][j].isbi())
                    grainList.add(boardGrain[i][j]);
            }
        }
        return grainList;
    }

    public int ammountOfNotEmptyCells() {
        int sum = 0;
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (boardGrain[i][j].getId() != 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

 
    public Grain[][] counting(int areaSetup, int r, int probability) {
        endSimulation= true;
        int tmp[][] = new int[3][3];

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                board_stm[i][j] = boardGrain[i][j].getId();
            }
        }

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (boardGrain[i][j].getId() == 0) {
                    endSimulation = false;
                    if (areaSetup == 7) {
                        board_stm[i][j] = randomArea(i, j, r);
                    } 
                    else if (areaSetup == 8)
                    {
                        board_stm[i][j] = extendedMoorArea(i,j,probability);
                    }
                    else {
                        tmp = createArea(i, j, areaSetup, false);
                        board_stm[i][j] = area(tmp);
                    }
                }
            }
        }

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain[i][j].setId(board_stm[i][j]);
            }
        }

        return boardGrain;
    }

    public Grain[][] reCalculate(int areaSetup, double dT) {
        endSimulation = true;
        double suma = 0;
     
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                
                if ( (boardGrain[i][j].isbi() || contentGrains ) && !boardGrain[i][j].isR()) {
                   
          
                        endSimulation = false;
                    }
                        n++;
                        boardGrain[i][j].setId(n);
                        boardGrain[i][j].setB(false);
                        boardGrain[i][j].setR(true);
                        countGrainsRecristal++;
                        endSimulation = false;
            
            }
        }
        

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain_stm[i][j].setId(boardGrain[i][j].getId());
            }
        }

        int tmp[][] = new int[3][3];

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (!boardGrain[i][j].isR()){
                    if (areaSetup == 7) {
                        tmp = createArea(i, j, 6, true);
                        board_stm[i][j] = area(tmp);
                    } else {
                        tmp = createArea(i, j, areaSetup, true);
                        board_stm[i][j] = area(tmp);
                    }
                    boardGrain_stm[i][j].setId(board_stm[i][j]);
                }
            }
        }

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if (boardGrain_stm[i][j].getId() > 0) {

                    endSimulation = false;
                    boardGrain[i][j].setId(boardGrain_stm[i][j].getId());
                    boardGrain[i][j].setB(false);
                    boardGrain[i][j].setR(true);
                }
            }
        }
        
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
            }
        }
        System.out.println(String.format("%.12f",suma));
        
        return boardGrain;

    }
    
    private int randomArea(int x, int y, int r) {
        int tmp[][];
        tmp = new int[2 * r + 1][2 * r + 1];

        for (int i = 0; i < 2 * r + 1; i++) {
            for (int j = 0; j < 2 * r + 1; j++) {
                float r_tmp = (float) (Math.sqrt(Math.pow(r - i, 2) + Math.pow(r - j, 2)));
                if (r < r_tmp) {
                    tmp[i][j] = 0;
                } else {
                    int xl = (x - r + i) % size_x;
                    xl = xl < 0 ? size_x - 1 : xl;

                    int yl = (y - r + j) % size_y;
                    yl = yl < 0 ? size_y - 1 : yl;

                    }

                }
            }
        

        ArrayList<Array> areas = new ArrayList<Array>();
        ArrayList<Array> maxAreas = new ArrayList<Array>();
        int max = 0;
        boolean find = false;

        for (int i = 0; i < 2 * r + 1; i++) {
            for (int j = 0; j < 2 * r + 1; j++) {
                if (tmp[i][j] != 0) {

                    find = false;
                    for (int l = 0; l < areas.size(); l++) {
                        if (tmp[i][j] == areas.get(l).getId()) {
                            areas.get(l).getNumber();
                            find = true;
                            max = max < areas.get(l).getNumber() ? areas.get(l).getNumber() : max;
                            break;
                        }
                    }

                    if (!find) {
                        areas.add(new Array(i, j, 1, tmp[i][j]));
                        max = max < 1 ? 1 : max;
                    }
                }
            }
        }

        if (max == 0) {
            return 0;
        } else {
            for (int l = 0; l < areas.size(); l++) {
                if (max == areas.get(l).getNumber()) {
                    maxAreas.add(areas.get(l));
                }
            }
            return maxAreas.get(new Random().nextInt(maxAreas.size())).getId();
        }

    }
    
    private int[][] createArea(int i, int j, int areaSetup, boolean recrystal) {
        int tmp[][] = new int[3][3];

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int l_x = (size_x + (i - 1 + k)) % size_x;
                int l_y = (size_y + (j - 1 + l)) % size_y;
                if (recrystal) {
                    if (boardGrain[l_x][l_y].isR()) {
                        tmp[k][l] = boardGrain[l_x][l_y].getId();
                    } else {
                        tmp[k][l] = 0;
                    }
                } else {
                    tmp[k][l] = boardGrain[l_x][l_y].getId();
                }
            }
        }

       
        int tmp_area = areaSetup;
        boolean _rand = true;
        while (_rand) {
            switch (tmp_area) {
                case 0: //moore
                {
                    _rand = false;
                    break;
                }
                case 1: //neumann
                {
                    tmp[0][0] = 0;
                    tmp[2][0] = 0;
                    tmp[0][2] = 0;
                    tmp[2][2] = 0;
                    _rand = false;
                    break;
                }
               
                case 7: //for moor extended
                {
                    tmp[0][1] = 0;
                    tmp[1][0] = 0;
                    tmp[1][2] = 0;
                    tmp[2][1] = 0;
                    _rand = false;
                    break;
                }
                default:
                    _rand = false;
                    break;
            }
        }

        return tmp;
    }
    
    private int area(int[][] tab) {                                // Check neiberhoood and return id of seed that dominate in this area 
        int areas[][] = new int[3][3];
        ArrayList<Array> max_l = new ArrayList<Array>();
        int max_x = 0;
        int max_y = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (tab[k][l] != 0 && tab[i][j] > 0 && !grainsToSkip.contains(tab[i][j])) {
                            if (tab[i][j] == tab[k][l]) {
                                areas[i][j]++;
                            }
                        }
                    }
                }

                if (areas[i][j] > areas[max_x][max_y] && tab[i][j] != -2 && !grainsToSkip.contains(tab[i][j])) {
                    max_x = i;
                    max_y = j;
                }

            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (areas[i][j] == areas[max_x][max_y]) {
                    if (tab[i][j]!= -1 && tab[i][j]!= -2 && !grainsToSkip.contains(tab[i][j]))
                        max_l.add(new Array(i, j, areas[i][j]));
                }
            }
        }
        int max = rand.nextInt(max_l.size());

        return tab[max_l.get(max).getX()][max_l.get(max).getY()];
    }

    boolean isEdgeGenerated() {
        return edgeGenerated;
    }
    
    private int extendedMoorArea(int x, int y, int probability)
    {
        int tmp[][] = new int[3][3];
        HashSet<Integer> uniqueIds = new HashSet<>();
        LinkedHashMap<Integer, Integer> configurations = 
                new LinkedHashMap<Integer, Integer>(){{put(0,5); put(1,3); put(7,3);}};
        Set<Map.Entry<Integer,Integer>> configurationsSet = configurations.entrySet(); 

        for (Map.Entry<Integer, Integer> it : configurationsSet){
            tmp = createArea(x ,y ,it.getKey(), false);
            uniqueIds = getUniqueIdsFromNeighborhood(tmp);
            for(Integer id : uniqueIds)
                 if (countOccurrence(id, tmp) > it.getValue()) return id;
        }
        
        tmp = createArea(x ,y ,0, false);
        if(rand.nextInt(100)> (100 - probability))
            return area(tmp);
        else return 0;
    }
    
    private HashSet<Integer> getUniqueIdsFromNeighborhood(int tmp[][])
    {
        HashSet<Integer> uniqueIds = new HashSet<>();
        for(int i =0;i<3;i++)
            for(int j =0;j<3;j++)
                if(tmp[i][j] != 0) uniqueIds.add(tmp[i][j]);
        return uniqueIds;      
    }
    
    private int countOccurrence(int id, int tmp[][])
    {
        int count = 0;
        for(int i =0;i<3;i++)
            for(int j =0;j<3;j++)
                if (id == tmp[i][j]) count++;
        
        return count;
    }
    
     public Grain[][] removeAllGrainsExceptSeleected(ArrayList<Integer> selectedGrains)
    {
        for(int i =0; i<selectedGrains.size(); i++)
            System.err.println(selectedGrains.get(i));
        
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if(!selectedGrains.contains(boardGrain[i][j].getId()))
                {
                    boardGrain[i][j].setId(0);
            }}
        }
        return boardGrain;
    }
    
    void setGrainsToSkip(ArrayList<Integer> selectedGrainList) {
        grainsToSkip = selectedGrainList;
        return;
    }

    Grain[][] dualPhaseIdChange() {
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                 if (boardGrain[i][j].getId() != 0)
                     boardGrain[i][j].setId(-2);
            }
        }
        return boardGrain;
    }
    
    void drawBoundaries()
    {
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if(boardGrain[i][j].isbi()) boardGrain[i][j].setId(0);
            }
        }
    }
    
    boolean isHaveBoundariesInNaiberhood(int x, int y)
    {
        for (int i = x-1; i <= x+1 && i>0 && i< size_x; i++) {
            for (int j = y-1; j <= y+1 && j>0 && j< size_y; j++) {
                if(boardGrain[i][j].isbi()) return true;
            }
        }
        return false;
    }
    
    Grain[][] growBoundaries( int size , ArrayList<Integer> selectedGrainList)
    {
        boardGrain = edge();
        ArrayList<Grain> grainToSet = new ArrayList<Grain>();
        
        if(selectedGrainList.isEmpty())
        {
            for (int k = size -1; k > 0; k--)
            {
            for (int i = 0; i < size_x; i++) {
                for (int j = 0; j < size_y; j++) {
                    if (isHaveBoundariesInNaiberhood(i,j))
                    {
                        grainToSet.add(boardGrain[i][j]);
                    }
                }
            }
            for(Grain grain : grainToSet)
            {
                grain.setB(true);
                grain.setId(0);
            }
            
        }
        drawBoundaries();
        }
        else
        {
            for (int k = size -1; k > 0; k--)
            {
            for (int i = 0; i < size_x; i++) {
                for (int j = 0; j < size_y; j++) {
                    if (isHaveBoundariesInNaiberhood(i,j) && selectedGrainList.contains(boardGrain[i][j].getId()))
                    {
                        grainToSet.add(boardGrain[i][j]);
                    }
                }
            }
            clearEdgedifferentThan(selectedGrainList);
            for(Grain grain : grainToSet)
            {
                grain.setB(true);
            }
        }
        }
        drawBoundaries();
        return boardGrain;
    }
    
    void clearEdgedifferentThan(ArrayList<Integer> selectedGrainList)
    {
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                if(boardGrain[i][j].isbi() && !selectedGrainList.contains(boardGrain[i][j].getId()))
                    boardGrain[i][j].setB(false);
            }
        }
    }
    
    void setBoard(Grain boardGrain[][])
    {
        this.boardGrain = boardGrain;
    }
    
    Grain[][] getBoard()
    {
        return this.boardGrain;
    }
    
    ArrayList<Integer> getGrainsToSkip()
    {
        return this.grainsToSkip;
    }
}


