package graingrowth;


import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.w3c.dom.css.RGBColor;
import graingrowth.Board1;
import graingrowth.DistributionEnergy;
import graingrowth.Grain;
import graingrowth.MonteCarlo;

public class Gui extends javax.swing.JFrame {

    static final int size_x = 300;              
    static final int size_y = 300;              // size of array
    double dT = 0;
    HandlerClass handler = new HandlerClass();
    Thread t;
    Grain boardGrain[][];
    boolean simLoop;
    private Board1 board;
    private boolean MC;
    private MonteCarlo monteCarlo;
    private int time;
    private boolean selectGrains = false;
    ArrayList<Integer> selectedGrainList = new ArrayList();

    public Gui() {
        time = 0;
        
        monteCarlo = new MonteCarlo();
        MC = false;
        board = new Board1(size_x, size_y);                  // creating array
        boardGrain = new Grain[size_x][size_y];
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                boardGrain[i][j] = new Grain();             // filling the array
            }
        }
        initComponents();
       
 
        this.jLabel14.setVisible(false);
        this.jPanel5.setVisible(false);
        this.probabilityTextField.setVisible(false);
        jLabel9.setText("" + board.getCountGrainsCristal());// amount of grain
        canvas1.addMouseListener(handler);                  // using mouse
        canvas1.addMouseMotionListener(handler);
        
        this.selectionTypeBox.setVisible(false);
        this.procedGrainSelection.setVisible(false);
        
        this.MCiterationLabel.setVisible(true);
        this.MCiterationText.setVisible(true);
        this.McJgbLabel.setVisible(MC);
        this.McJgbText.setVisible(MC);
    }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        MonteCarloToogleButton = new javax.swing.JToggleButton();
        StartButton = new javax.swing.JButton();
        StopButton = new javax.swing.JButton();
        NeighborhoodComboBox = new javax.swing.JComboBox();
        GenerateButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        NeighborhoodLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        randomSeedsCountText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        inclusionsAmount = new javax.swing.JTextField();
        inclusionsSize = new javax.swing.JTextField();
        inclusionShapeComboBox = new javax.swing.JComboBox<>();
        addInclusionsButton = new javax.swing.JButton();
        McJgbLabel = new javax.swing.JLabel();
        McJgbText = new javax.swing.JTextField();
        EnergyDistributionButton = new javax.swing.JButton();
        selectGrainsButton = new javax.swing.JButton();
        selectionTypeBox = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        ImportMenu = new javax.swing.JMenu();
        FromBitmapImport = new javax.swing.JMenuItem();
        FromTextFileImport = new javax.swing.JMenuItem();
        ExportMenu = new javax.swing.JMenu();
        ToBitmapExport = new javax.swing.JMenuItem();
        ToTextFileExport = new javax.swing.JMenuItem();
        showGrainsBorders = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(1150, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel5.setLayout(new java.awt.GridBagLayout());
        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 400, -1, -1));

        MonteCarloToogleButton.setText("Monte Carlo");
        MonteCarloToogleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonteCarloToogleButtonActionPerformed(evt);
            }
        });
        jPanel1.add(MonteCarloToogleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 140, 50));

        StartButton.setText("Grain Growth");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });
        jPanel1.add(StartButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 130, 40));

        StopButton.setText("Stop");
        StopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopButtonActionPerformed(evt);
            }
        });
        jPanel1.add(StopButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 130, 40));

        NeighborhoodComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Moore", "von Neumann'a", "Hexagonal (left)", "Hexagonal (right)", "Hexagonal (random)", "Pentagonal", "Random", "Radius", "Extended Moore" }));
        NeighborhoodComboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                NeighborhoodComboBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        NeighborhoodComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeighborhoodComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(NeighborhoodComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 130, 40));

        GenerateButton.setText("Nucleation");
        GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateButtonActionPerformed(evt);
            }
        });
        jPanel1.add(GenerateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 140, 40));

        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 130, 40));

        NeighborhoodLabel.setText("Neighborhood");
        jPanel1.add(NeighborhoodLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 80, -1));
        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 530, 660, 40));

        jLabel3.setText("Grains");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, 20));

        randomSeedsCountText.setText("10");
        randomSeedsCountText.setMinimumSize(new java.awt.Dimension(40, 22));
        randomSeedsCountText.setPreferredSize(new java.awt.Dimension(40, 22));
        jPanel1.add(randomSeedsCountText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        jLabel1.setText("Amount of inclusions ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, -1, -1));

        jLabel2.setText("Size of inclusions");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, -1, -1));

        jLabel6.setText("Shape of inclusions");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 450, -1, -1));

        inclusionsAmount.setText("10");
        inclusionsAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inclusionsAmountActionPerformed(evt);
            }
        });
        jPanel1.add(inclusionsAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 120, -1));

        inclusionsSize.setText("5");
        inclusionsSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inclusionsSizeActionPerformed(evt);
            }
        });
        jPanel1.add(inclusionsSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 470, 100, -1));

        inclusionShapeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "square", "circular" }));
        inclusionShapeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inclusionShapeComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(inclusionShapeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 470, 130, -1));

        addInclusionsButton.setText("Add Inclusions");
        addInclusionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addInclusionsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addInclusionsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 460, 130, 40));

        McJgbLabel.setText("MonteCarlo Jgb");
        jPanel1.add(McJgbLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, -1, -1));

        McJgbText.setText("1");
        jPanel1.add(McJgbText, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 410, 70, -1));

        EnergyDistributionButton.setText("Energy Distribution");
        EnergyDistributionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnergyDistributionButtonActionPerformed(evt);
            }
        });
        jPanel1.add(EnergyDistributionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 460, -1, 40));

        selectGrainsButton.setText("Select Grains");
        selectGrainsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectGrainsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(selectGrainsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 130, 40));

        selectionTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DualPhase", "Subphase", " Boundaries" }));
        selectionTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionTypeBoxActionPerformed(evt);
            }
        });
        jPanel1.add(selectionTypeBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, 130, 40));

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        ImportMenu.setText("Import");
        ImportMenu.setToolTipText("");

        FromBitmapImport.setText("From Bitmap");
        FromBitmapImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromBitmapImportActionPerformed(evt);
            }
        });
        ImportMenu.add(FromBitmapImport);

        FromTextFileImport.setText("From TextFile");
        FromTextFileImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromTextFileImportActionPerformed(evt);
            }
        });
        ImportMenu.add(FromTextFileImport);

        jMenuBar1.add(ImportMenu);

        ExportMenu.setText("Export");

        ToBitmapExport.setText("To bitmap");
        ToBitmapExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToBitmapExportActionPerformed(evt);
            }
        });
        ExportMenu.add(ToBitmapExport);

        ToTextFileExport.setText("To text file");
        ToTextFileExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToTextFileExportActionPerformed(evt);
            }
        });
        ExportMenu.add(ToTextFileExport);

        showGrainsBorders.setSelected(true);
        showGrainsBorders.setText("Show grains boarders");
        ExportMenu.add(showGrainsBorders);

        jMenuBar1.add(ExportMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }

    private BufferedImage map( int sizeX, int sizeY, boolean showBoundary){
    final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
            for(int j=0;j<size_y;j++){
            for(int i=0;i<size_x;i++){
                if(boardGrain[i][j].getId() == 0 || (boardGrain[i][j].isbi()) && showBoundary ){
                    res.setRGB(i,j, Color.BLACK.getRGB() );
                }else{
                    int R = boardGrain[i][j].getR();
                    int G = boardGrain[i][j].getG();
                    int B = boardGrain[i][j].getB();
                    int grain = boardGrain[i][j].getId();
              
                    res.setRGB(i,j, new Color(R,G,B).getRGB() );
                }
            } 
        }
    return res;
    }
    
    private static void saveBMP( final BufferedImage bi, final String path ){
        try {
            RenderedImage rendImage = bi;
            ImageIO.write(rendImage, "bmp", new File(path));
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    private void ToBitmapExportActionPerformed(java.awt.event.ActionEvent evt) {
        BufferedImage img = map( size_x, size_y, showGrainsBorders.isSelected());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            saveBMP( img, fileToSave.getAbsolutePath() + ".bmp" );
        }
    }

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        time = 0;                                                      
                           
      
        boardGrain = board.clear();                               
        canvas1.setGrains(boardGrain);                                
        canvas1.repaint();
        jLabel9.setText("" + board.getCountGrainsCristal());
    }

    private void GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateButtonActionPerformed
        if (MC) {
            monteCarlo.setSeedsCount(Integer.parseInt(randomSeedsCountText.getText()));
            boardGrain = monteCarlo.randomBoard();
            canvas1.setGrains(boardGrain);
            canvas1.repaint();
        } else {
            //boardGrain = board.clear();           
            boardGrain = board.randomBoard(ConditionsComboBox.getSelectedIndex(),  
                Integer.parseInt(countXText.getText()),                 
                Integer.parseInt(countYText.getText()),                
                Integer.parseInt(randomSeedsCountText.getText()),
                Integer.parseInt(ringSizeField.getText()),              
                Integer.parseInt(amountRingsField.getText()));          
            canvas1.setGrains(boardGrain);                                  
            canvas1.repaint();                                              
            jLabel9.setText("" + board.getCountGrainsCristal());
        }
    }

    private void NeighborhoodComboBoxActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void NeighborhoodComboBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
        switch (NeighborhoodComboBox.getSelectedIndex()) {        // MENU 
            case 7:
            this.jPanel8.setVisible(true);
            break;
            case 8:
                this.probabilityOfGrowth.setVisible(true);
                this.probabilityTextField.setVisible(true);
            break;
            default:
            this.jPanel8.setVisible(false);
            break;
        }
    }

    private void StopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (t != null) {                          // stop simulation
            t.stop();
        }
    }

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {
        time = 0;
        jLabel12.setText("Czas: " + time);      
        if (t != null) {                        
            t.stop();
        }
        t = new Thread(new Runnable() {         
            @Override
            public void run() {
                sim();
            }
        });
        t.start();                              
    }

    private void MonteCarloToogleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MC = !MC;
        this.jPanel5.setVisible(MC);
        this.MCiterationLabel.setVisible(MC);
        this.MCiterationText.setVisible(MC);
        this.McJgbLabel.setVisible(MC);
        this.McJgbText.setVisible(MC);
        if(MC)
            monteCarlo.setBoard(board.getBoard());
        else
            board.setBoard(monteCarlo.getBoard());
        
    }
    
    private void FromBitmapImportActionPerformed(java.awt.event.ActionEvent evt) {
        try { 
            HashSet<Color> treeSet = new HashSet<>();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");   
            int userSelection = fileChooser.showSaveDialog(this);
           
            File bmpFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            BufferedImage image = ImageIO.read(bmpFile);
            if(image.getHeight()== size_y && image.getWidth() == size_y);
            {
                for(int y=0;y<size_y;y++){
                    for(int x=0;x<size_x;x++){
                        int clr=  image.getRGB(x,y); 
                        int  red   = (clr & 0x00ff0000) >> 16;
                        int  green = (clr & 0x0000ff00) >> 8;
                        int  blue  =  clr & 0x000000ff;
                        boardGrain[x][y].setRGB(red, green, blue);
                        if( red == 255 && blue == 255 && green == 255) {
                            boardGrain[x][y].setId(-1);
                            continue;
                        }
                        treeSet.add(new Color(red,green,blue));
                    }
                }
            }
            
            HashMap<Color, Integer> colorMapping = new HashMap<Color, Integer>();
            int uniq_id = 1;
            Color white = new Color(255,255,255);
            for(Color cl : treeSet)
            {
                if(cl == white)
                {
                    colorMapping.put(cl, -1);
                }
                else
                {
                    colorMapping.put(cl, uniq_id);
                    uniq_id++;
                }
            }
            for(int y=0;y<size_y;y++){
                for(int x=0;x<size_x;x++){
                    if (boardGrain[x][y].getId() != -1)
                        boardGrain[x][y].setId(colorMapping.get(new Color(boardGrain[x][y].getR(),boardGrain[x][y].getG(),boardGrain[x][y].getB()))); 
                    }
            }
            boardGrain = board.edge();
            canvas1.setGrains(boardGrain);
            canvas1.repaint();
        } catch (IOException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    private void ToTextFileExportActionPerformed(java.awt.event.ActionEvent evt) {
        PrintWriter writer = null;
        try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");   
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            writer = new PrintWriter(fileToOpen, "UTF-8");
            
            for(int y=0;y<size_y;y++){    
                for(int x=0;x<size_x;x++){
                    writer.println(boardGrain[x][y].getId() + "," 
                            + x + "," + y+ ","
                            + boardGrain[x][y].getR() + "," 
                            + boardGrain[x][y].getG() + "," 
                            + boardGrain[x][y].getB());
                }
            }
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    private void FromTextFileImportActionPerformed(java.awt.event.ActionEvent evt) {

            try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");            
            int userSelection = fileChooser.showSaveDialog(this);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                BufferedReader in = new BufferedReader(new FileReader(fileToOpen));
                while(true){
                    String line = in.readLine();
                    if(line == null ) break;
                    String[] propperties = line.split(",");
                    int id = Integer.parseInt(propperties[0]);
                    int x = Integer.parseInt(propperties[1]);		
                    int y = Integer.parseInt(propperties[2]);
                    int R = Integer.parseInt(propperties[3]);
                    int G = Integer.parseInt(propperties[4]);
                    int B = Integer.parseInt(propperties[5]);
                    boardGrain[x][y].setId(id);
                    boardGrain[x][y].setRGB(R,G,B); 
                }          
            }
        } catch (HeadlessException headlessException) {
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (IOException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            boardGrain = board.edge();
            canvas1.setGrains(boardGrain);
            canvas1.repaint();
    }

    private void inclusionsAmountActionPerformed(java.awt.event.ActionEvent evt) {
       
    }

    private void inclusionsSizeActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void inclusionShapeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
     
    }

    private void addInclusionsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int size = Integer.parseInt(inclusionsSize.getText());
        int amount = Integer.parseInt(inclusionsAmount.getText());
        int shape = inclusionShapeComboBox.getSelectedIndex();
        int x0 = 0;
        int y0 = 0;
        Random rand = new Random();
       
        ArrayList<Grain> grainList = board.getGrainsOnBorder();

        for(int attempt = 0; attempt< amount; attempt++)
        {
            if(board.isEdgeGenerated()){
                int randomGrainIndex = rand.nextInt(grainList.size());
                Grain randomGrain = grainList.get(randomGrainIndex);  
                x0 = randomGrain.getX();
                y0 = randomGrain.getY();
            }
            else{
                x0 = rand.nextInt(size_x);
                y0 = rand.nextInt(size_y);
            }
            
            if (shape == 0)
            {
                int a = (int)((size/1.44)/2);    
                for(int stepX = -a; stepX <a; stepX++)
                {
                    for(int stepY = -a; stepY <a; stepY++)
                    {
                        if(x0+stepX < size_x && x0+stepX > 0 && y0+stepY < size_y && y0+stepY >0 )
                        {
                            boardGrain[x0+stepX][y0+stepY].setId(-1);
                            boardGrain[x0+stepX][y0+stepY].setRGB(255,255,255);
                        }
                    }
                }
            }
            else {
                for(int y=-size; y<=size; y++)
                    for(int x=-size; x<=size; x++)
                        if(x*x+y*y <= size*size && x0+x < size_x && x0+x>0 && y0+y < size_y && y0+y>0)
                            boardGrain[x0+x][y0+y].setId(-1);
            }       
        }
        boardGrain = board.edge();
        canvas1.setGrains(boardGrain);
        canvas1.repaint();
        
    }

    private void selectGrainsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        selectedGrainList.clear();
        this.selectGrains = !this.selectGrains;
        this.selectionTypeBox.setVisible(this.selectGrains);
        this.procedGrainSelection.setVisible(this.selectGrains);
        
    }

    private void selectionTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void EnergyDistributionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int lowEnergyValue = Integer.parseInt(this.lowLevelEnergyField.getText());
        int highEnergyValue = Integer.parseInt(this.highLevelEnergyField.getText());
        
        EnergyDistributionFrame eDistribution = new EnergyDistributionFrame();
        eDistribution.getCanvas().setGrains(boardGrain);
        eDistribution.getCanvas().repaint();
        eDistribution.setVisible(true);
    }

    private void sim() {

        if (MC) {
            simLoop = true;
            int currentIteration = 0;
            int iterationNumber = Integer.parseInt(this.MCiterationText.getText());
            float grainBoundaryEnergy = Float.parseFloat(this.McJgbText.getText());
            while (currentIteration < iterationNumber && simLoop) {
                time++;
                jLabel12.setText("Czas: " + time);
                monteCarlo.setGrainsToSkip(selectedGrainList);
                boardGrain = monteCarlo.calculate(grainBoundaryEnergy);
                canvas1.setGrains(boardGrain);
                canvas1.repaint();
                if (monteCarlo.getChanged() == 0) {
                    simLoop = false;
                }
                currentIteration++;
                if(currentIteration >= iterationNumber) simLoop = false;
            }
        } else {
            simLoop = true;
            while (simLoop) {
                time++;
                jLabel12.setText("Czas: " + time);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                board.setGrainsToSkip(selectedGrainList);
                boardGrain = board.calculate(NeighborhoodComboBox.getSelectedIndex(),
                        Integer.parseInt(radiusText.getText()), 
                        Integer.parseInt(probabilityTextField.getText()));
                
                simLoop = board.ammountOfNotEmptyCells() != (size_x * size_y);
                canvas1.setGrains(boardGrain);
                canvas1.repaint();

                if (!simLoop) {
                    boardGrain = board.edge();
                    jLabel9.setText("" + board.getCountGrainsCristal());
                    t.stop();
                }
            }
        }

    }

    private void reSim() {

        if (MC) {
            monteCarlo = new MonteCarlo(size_x, size_y, boardGrain);
            simLoop = true;
            while (simLoop) {
                time++;
                jLabel12.setText("Czas: " + time);
                boardGrain = monteCarlo.calculate(1);
                canvas1.setGrains(boardGrain);
                canvas1.repaint();
                if (monteCarlo.getChanged() == 0) {
                    simLoop = false;
                }
            }
        } else {
            simLoop = true;
            int lowEnergyValue = Integer.parseInt(this.smallLevelEnergyField.getText());
            int highEnergyValue = Integer.parseInt(this.highLevelEnergyField.getText());
            boardGrain = board.setH(lowEnergyValue, highEnergyValue);
            canvas1.setGrains(boardGrain);
            canvas1.repaint();
            int maxIteration = Integer.parseInt(MCiterationText.getText());
            int it = 0;
            boardGrain = board.addNewRecrystalizedGrains(onlyBoundaries.isSelected(),
                    Integer.parseInt(recrystalizedOnBegining.getText()));
            while (simLoop) {
                time++;
                jLabel12.setText("Czas: " + time);
                boardGrain = board.addNewRecrystalizedGrains(onlyBoundaries.isSelected(), Integer.parseInt(grainInEveryStep.getText()));
                boardGrain = board.reCalculate(NeighborhoodComboBox.getSelectedIndex(), Float.parseFloat(grainBoundariesEnergy.getText()));
                it++;
                simLoop = board.recrystal() != (size_x * size_y);
                if (it > maxIteration)
                    simLoop = false;
                canvas1.setGrains(boardGrain);
                canvas1.repaint();
                jLabel9.setText("" + board.getCountGrainsRecristal());
                if (!simLoop) {
                    boardGrain = board.edge();
                    t.stop();
                }
            }
        }

    }

    public class HandlerClass implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(selectGrains)
                addGrainToListOnMouseClick(e.getX(), e.getY());
            else
                mouseC(e.getX(), e.getY());
            
        }

        int xPres, yPres;

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mauseM(e.getX(), e.getY());
        }

    }

    private void mauseM(int i, int j) {

        int R = boardGrain[i][j].getR();
        int G = boardGrain[i][j].getG();
        int B = boardGrain[i][j].getB();
        int x = boardGrain[i][j].getX();
        int y = boardGrain[i][j].getY();
        int grain = boardGrain[i][j].getId();  
        idLabel.setText("" + boardGrain[i][j].getId());
        colorValueLabel.setText("(" + x + "," + y + ")" + "     " + boardGrain[i][j].isR());
        colorPanel.setBackground(new Color(R, G, B));
    }

    private void mouseC(int x, int y) {                 
        int xp = (int) Math.floor(x / (300 / size_x));
        int yp = (int) Math.floor(y / (300 / size_y));
        boardGrain = board.addGrain(xp, yp);
        canvas1.setGrains(boardGrain);
        canvas1.repaint();
        jLabel9.setText("" + board.getCountGrainsCristal());
    }
    
    private void addGrainToListOnMouseClick(int x, int y)
    {
        selectedGrainList.add(boardGrain[x][y].getId());
        System.err.println(selectedGrainList);
    }

    public static void main(String args[]) {
        
       
    
            }
        });
    }

    
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton EnergyDistributionButton;
    private javax.swing.JMenu ExportMenu;
    private javax.swing.JMenuItem FromBitmapImport;
    private javax.swing.JMenuItem FromTextFileImport;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JMenu ImportMenu;
    private javax.swing.JLabel McJgbLabel;
    private javax.swing.JTextField McJgbText;
    private javax.swing.JToggleButton MonteCarloToogleButton;
    private javax.swing.JComboBox NeighborhoodComboBox;
    private javax.swing.JLabel NeighborhoodLabel;
    private javax.swing.JButton StartButton;
    private javax.swing.JButton StopButton;
    private javax.swing.JMenuItem ToBitmapExport;
    private javax.swing.JMenuItem ToTextFileExport;
    private javax.swing.JButton addInclusionsButton;
    private javax.swing.JComboBox<String> inclusionShapeComboBox;
    private javax.swing.JTextField inclusionsAmount;
    private javax.swing.JTextField inclusionsSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField randomSeedsCountText;
    private javax.swing.JButton selectGrainsButton;
    private javax.swing.JComboBox<String> selectionTypeBox;
    private javax.swing.JRadioButtonMenuItem showGrainsBorders;
   
}
