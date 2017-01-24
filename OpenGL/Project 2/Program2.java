/* Edgar Ruiz
 * CS 445
 * Program 2
 * October 25, 2016
 */

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.Collections;

public class Program2 {
    private float colorRed;
    private float colorGreen;
    private float colorBlue;
    
    /* start() includes the necessary methods to draw with OpenGl.
     */
    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* createWindow() creates a window the size of (width) 640 x 480 (height),
     * with the title "Program 1 - Draw Lines, Circles, and Ellipses".
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 2 - Fill in Polygons");
        Display.create();
    }

    /* initGL() specifies background color (glClearColor), loads camera
     * using projection to view scene (glMatrixMode), loads identity matrix
     * (glLoadIdentity), sets up orthographic matrix with the size 640 x 480
     * where the origin is the center of the screen (glOrtho), sets up scene to
     * Model view (glMatrixMode), and provides rendeing hints (glHint).
     */
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-320, 320, -240, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    /* render() is called every frame by containing a loop. Window will 
     * close when closing the window is requested or when the ESC key is pressed 
     * on the keyboard. Each line from "coordinates.txt", located in the 
     * coordinates folder under src, will be read. It will be stored line per line
     * on an ArrayList to be easier to parse through. When a "P is encountered as the
     * first character, or token, the rest of the numbers are parsed as floats and 
     * assigned as colors for the polygon. Any lines in between that line and a line
     * containing "T" will be the vertices of the polygon. Anything after the "T" will
     * be the transformations.
     */
    private void render() {
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                //add lines in coordinates.txt to ArrayList to facilitate parse
                ArrayList<String> fileLines = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader("src/coordinates/coordinates.txt"))) {
                    String line;
                    while((line = br.readLine()) != null) {
                        fileLines.add(line);
                    }
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }                
                int fileRun = 0;                
                while(fileRun < fileLines.size()){
                    ArrayList<String> transformations = new ArrayList<>();
                    StringTokenizer tokenizer = new StringTokenizer(fileLines.get(fileRun++));
                    String[] tokens = new String[tokenizer.countTokens()];
                    tokenize(tokenizer, tokens);
                    float[][] vertices = new float[50][2];
                    //Start reading file
                    if(tokens[0].equals("P")){
                        setColors(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
                        int numVertices = 0;
                        while(!fileLines.get(fileRun).equals("T") && fileRun < fileLines.size()){
                            tokenizer = new StringTokenizer(fileLines.get(fileRun));
                            fileRun++;
                            tokens = new String[tokenizer.countTokens()];
                            tokenize(tokenizer, tokens);
                            vertices[numVertices][0] = Float.parseFloat(tokens[0]);
                            vertices[numVertices][1] = Float.parseFloat(tokens[1]);
                            numVertices++;
                        }
                        fileRun++;
                        float xmin = 0;
                        float xmax = 0;
                        float ymin = 0;
                        float ymax = 0;
                        findTranslation(vertices, xmin, xmax, ymin, ymax);
                        float midY = (ymax - ymin)/2;
                        float midX = (xmax- xmin)/2;
                        while(!fileLines.get(fileRun).substring(0, 1).equals("P")){
                            transformations.add(fileLines.get(fileRun));
                            fileRun++;
                            if(fileRun == fileLines.size()){
                                break;
                            }
                        }
                        glLoadIdentity();
                        for(int i = transformations.size() - 1; i >= 0; i--){
                            tokenizer = new StringTokenizer(transformations.get(i));
                            tokens = new String[tokenizer.countTokens()];
                            tokenize(tokenizer, tokens);
                            parseTrans(tokens);                           
                        }
                        edges(vertices, numVertices);
                    }
                }
                Display.update();            
                Display.sync(60);
            } 
            catch(Exception e){
            }
        }
        Display.destroy();
    }
    
    /* tokenize() fills in an array of tokens. 
     */
    public void tokenize(StringTokenizer tokenizer, String[] tokens){
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokenizer.nextToken();
        }
    }
    
    /* Finds ymin, ymax, xmin, xmax to be used to find center of polygon to move to origin and back
     * when scaling or rotating.
     */
    public void findTranslation(float[][] vertices, float xmin, float xmax, float ymin, float ymax){
        xmin = vertices[0][0];
        ymin = vertices[0][1];
        xmax = vertices[vertices.length-1][0];
        ymax = vertices[vertices.length-1][1];
        for(int i = 1; i < vertices.length; i++){
            if(vertices[i][0] < xmin){
                xmin = vertices[i][0];
            }
            else if(vertices[i][0] > xmax){
                xmax = vertices[i][0];
            }
        }
        
        for(int j = 0; j < vertices.length; j++){
            if(vertices[j][1] < ymin){
                ymin = vertices[j][1];
            }
            else if(vertices[j][1] > ymax){
                ymax = vertices[j][1];
            }
        }
    }
    /* parseTrans() parses through transformations of the polygons.
     */
    public void parseTrans(String[] tokens){
        if(tokens[0].equals("t")) {
            glTranslatef(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), 0f);
        }
        else if(tokens[0].equals("r")) {
            glRotatef(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), 1f);
        } 
        else if(tokens[0].equals("s")) {
            glScalef(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), 1f);
        }
    }
       
    /* edges() takes in the vertices and number of vertices to produce an allEdges table.
     */
    public void edges(float[][] vertices, int numVertices){
        float x0;
        float x1;
        float y0;
        float y1;
        float[][] edges = new float[numVertices][4];
        float[][] allEdges = new float[numVertices][2];
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < allEdges[0].length; j++){
                allEdges[i][j] = vertices[i][j];
            }
        }
        
        for(int k = 0; k < numVertices - 1; k++){
            x0 = allEdges[k][0];
            x1 = allEdges[k+1][0];
            y0 = allEdges[k][1];
            y1 = allEdges[k+1][1];
            slope(edges, k, x0, y0, x1, y1);            
            fillIn(edges, k, x0, y0, x1, y1);            
        }
        x0 = allEdges[numVertices-1][0];
        x1 = allEdges[0][0];
        y0 = allEdges[numVertices-1][1];
        y1 = allEdges[0][1];
        fillIn(edges, numVertices-1, x0, y0, x1, y1);
        if((y1 - y0) == 0){
            edges[numVertices-1][3] = Float.POSITIVE_INFINITY;
        }
        else{
            edges[numVertices-1][3] = (x1-x0) / (y1-y0);
        }
        globalEdges(edges);
    }
    
    /* slope() calculates 1/m to be placed in the allEdges table.  
     */
    public void slope(float[][] allEdges, int k, float x0, float y0, float x1, float y1){
        if((y1 - y0) == 0){
            allEdges[k][3] = Float.POSITIVE_INFINITY;
        }
        else if((x1 - x0) == 0){
            allEdges[k][3] = 0;            
        }
        else{
            allEdges[k][3] = (x1 - x0) / (y1 - y0);
        }
    }
    
    /* fillIn() fills in allEdges table with proper y0, x1, and x0 for yMin, yMax, 
     * and Xmin values. 
     */
    public void fillIn(float[][] allEdges, int i, float x0, float y0, float x1, float y1){
        if(y0 < y1){
            allEdges[i][0] = y0;
            allEdges[i][1] = y1;
            allEdges[i][2] = x0;
        }
        else if(y0 > y1){
            allEdges[i][0] = y1;
            allEdges[i][1] = y0;
            allEdges[i][2] = x1;
        }
        else if(x0 < x1){
            allEdges[i][0] = y0;
            allEdges[i][1] = y1;
            allEdges[i][2] = x0;              
        }
        else{
            allEdges[i][0] = y1;
            allEdges[i][1] = y0;
            allEdges[i][2] = x1;
        }
    }
    
    /* globalEdges() calculates the globalEdges table that contains the yMinimum, yMaximum,
     * xMinimum, and 1/m (slope).
     */
    public void globalEdges(float[][] allEdges){
        float ymin;
        float ymax;
        float xmin;
        float slope;
        float global[][] = new float[allEdges.length][4];
        float globalEdges[][];
        int count = 0;
        for(int i = 0; i < allEdges.length; i++){
            Boolean add = false;
            ymin = allEdges[i][0];
            ymax = allEdges[i][1];
            xmin = allEdges[i][2];
            slope = allEdges[i][3];
            if(allEdges[i][3] != Float.POSITIVE_INFINITY){
                if(count == 0){
                    global[0][0] = ymin;
                    global[0][1] = ymax;
                    global[0][2] = xmin;
                    global[0][3] = slope;
                    count++;
                }
                else{
                    for(int j = 0; j < count; j++){
                        if(ymin < global[j][0]){
                            addToGlobal(global, count, j, ymin, ymax, xmin, slope);
                            count++;
                            add = true;
                            break;
                        }
                        else if(ymin == global[j][0]){
                            int counter = j;
                            for(int k = j; k < count; k++){
                                if(ymin == global[k][0]){
                                    counter++;
                                }
                            }
                            for(int l = j; l < counter; l++){
                                if(xmin < global[l][2]){
                                    addToGlobal(global, count, l, ymin, ymax, xmin, slope);
                                    count++;
                                    add = true;
                                    break;
                                }
                                else if(xmin == global[l][2]){
                                    counter = l;
                                    for(int m = l; m < count; m++){
                                        if(ymin == global[m][0] && xmin == global[m][2]){
                                            counter++;
                                        }
                                    }
                                    for(int n = l; n < counter; n++){
                                        if(ymax < global[n][1]){
                                            addToGlobal(global, count, n, ymin, ymax, xmin, slope);
                                            count++;
                                            add = true;
                                            break;
                                        }
                                        else if(ymax != global[n][1] && n + 1 == count){
                                            addToGlobal(global, count, n+1, ymin, ymax, xmin, slope);
                                            count++;
                                            add = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(add == false){
                                addToGlobal(global, count, counter, ymin, ymax, xmin, slope);
                                count++;
                                add = true;
                                break;
                            }
                            break;
                        }
                    }
                    if(add == false){
                        global[count][0] = ymin;
                        global[count][1] = ymax;
                        global[count][2] = xmin;
                        global[count][3] = slope;
                        count++;
                    }
                }
            }
        }
        globalEdges = new float[count][4];
        for(int t = 0; t < count; t++){
            globalEdges[t][0] = global[t][0];
            globalEdges[t][1] = global[t][1];
            globalEdges[t][2] = global[t][2];
            globalEdges[t][3] = global[t][3];
        }
        fillPolygon(globalEdges);
    }
    
    /* addToGlobal() method created to reuse operation and assign ymin, ymax, xmin, and
     * and slope values to proper location in globalEdges table.
     */
    public void addToGlobal(float[][] globalEdges, int count, int j, float ymin, float ymax, float xmin, float slope){
        for(int i = count; i >= j; i--){
            globalEdges[i+1][0] = globalEdges[i][0];
            globalEdges[i+1][1] = globalEdges[i][1];
            globalEdges[i+1][2] = globalEdges[i][2];
            globalEdges[i+1][3] = globalEdges[i][3];
        }
        globalEdges[j][0] = ymin;
        globalEdges[j][1] = ymax;
        globalEdges[j][2] = xmin;
        globalEdges[j][3] = slope;                         
    }
    
    /* fillPolygon() takes in the globalEdges table and draws the polygon on the display.
     */
    public void fillPolygon(float[][] globalEdges){
        glColor3f(getColor("red"), getColor("green"), getColor("blue"));
        int row = 0;
        float scanLine = globalEdges[0][0];
        int counter = 0;
        for(int i = 0; i < globalEdges.length; i++){
            if(globalEdges[i][0] == scanLine){
                counter++;
            }
        }
        int pending = globalEdges.length - counter;       
        float[][] currentGlobal = new float[pending][4];
        float[][] activeEdges = new float[counter][3];
        for(int j = 0; j < globalEdges.length; j++){
            if(globalEdges[j][0] == scanLine){
                activeEdges[row][0] = globalEdges[j][1];
                activeEdges[row][1] = globalEdges[j][2];
                activeEdges[row][2] = globalEdges[j][3];
                row++;
            }
        }
        row = 0;
        for(int k = 0; k < globalEdges.length; k++){
            if(globalEdges[k][0] > scanLine){
                currentGlobal[row][0] = globalEdges[k][0];
                currentGlobal[row][1] = globalEdges[k][1];
                currentGlobal[row][2] = globalEdges[k][2];
                currentGlobal[row][3] = globalEdges[k][3];
                row++;
            }
        }       
        float yMinimum = currentGlobal[0][0];
        while(true){
            while(scanLine < yMinimum){
                drawPixels(scanLine, activeEdges);
                for(int m = 0; m < activeEdges.length; m++){
                    activeEdges[m][1] = activeEdges[m][1] + activeEdges[m][2];
                }
                swap(activeEdges);
                scanLine++;
            }
            int x = 0;
            row = 0;
            float[][] active = new float[globalEdges.length][3];
            for(int n = 0; n < activeEdges.length; n++){
                if(activeEdges[n][0] > scanLine){
                    active[x][0] = activeEdges[n][0];
                    active[x][1] = activeEdges[n][1];
                    active[x][2] = activeEdges[n][2];
                    x++;
                }
            }
            for(int o = 0; o < activeEdges.length; o++){
                active[o][1] = active[o][1] + active[o][2];
            }
            for(int p = 0; p < currentGlobal.length; p++){
                if(currentGlobal[p][0] == scanLine){
                    active[x][0] = currentGlobal[p][1];
                    active[x][1] = currentGlobal[p][2];
                    active[x][2] = currentGlobal[p][3];
                    x++;
                    row++;
                }
            }
            activeEdges = new float[x][3];
            pending = currentGlobal.length - row;
            float[][] move = new float[pending][4];
            for(int q = 0; q < activeEdges.length; q++){
                for(int r = 0; r < activeEdges[0].length; r++){
                    activeEdges[q][r] = active[q][r];
                }
            }
            swap(activeEdges);
            row = 0;
            for(int s = 0; s < currentGlobal.length; s++){
                if(currentGlobal[s][0] > scanLine){
                    move[row][0] = currentGlobal[s][0];
                    move[row][1] = currentGlobal[s][1];
                    move[row][2] = currentGlobal[s][2];
                    move[row][3] = currentGlobal[s][3];
                    row++;
                }
            }
            currentGlobal = new float[pending][4];
            for(int t = 0; t < currentGlobal.length; t++){
                for(int u = 0; u < currentGlobal[0].length; u++){
                    currentGlobal[t][u] = move[t][u];
                }
            }
            if(x > 0){
                yMinimum = activeEdges[0][0];
            }
            else{
                break;
            }
        }
    }
    
    /* drawPixels() draws pixels to form polygon by using scanLine and activeEdges table.
     *
     */
    public void drawPixels(float scanLine, float[][] activeEdges){
        int parity = 0;
        for(int l = 0; l < activeEdges.length - 1; l++){
            float currentX = activeEdges[l][1];
            float nextX = activeEdges[l + 1][1];
            if(parity % 2 == 0){
                if(currentX == nextX){
                    glBegin(GL_POINTS);
                    currentX -= 1;
                    glVertex2f(currentX, scanLine);
                    glEnd();
                    parity = 1;                           
                }
                else{
                    nextX -= 1;
                    while(currentX < nextX){
                        glBegin(GL_POINTS);
                        glVertex2f(currentX, scanLine);
                        glEnd();
                        currentX += 0.1;
                    }
                    parity = 1;
                }
            }
            else{
                parity = 0;
            }
        }
    }
    /* swap() takes in table with activeEdges and does a swap operation. Reused in fillPolygon() method.
     */
    public void swap(float[][] activeEdges){
        for(int i = 0; i < activeEdges.length - 1; i++){
            for(int j = 1; j < activeEdges.length - i; j++){
                if(activeEdges[j-1][1] > activeEdges[j][1]){
                    float x = activeEdges[j-1][1];
                    float y = activeEdges[j-1][0];
                    float s = activeEdges[j-1][2];
                    activeEdges[j-1][0] = activeEdges[j][0];
                    activeEdges[j-1][1] = activeEdges[j][1];
                    activeEdges[j-1][2] = activeEdges[j][2];
                    activeEdges[j][1] = x;
                    activeEdges[j][0] = y;
                    activeEdges[j][2] = s;
                }
            }
        }
    }
    
    /* setColors() sets the colors for the polygon.
     */
    public void setColors(float red, float green, float blue){
        colorRed = red;
        colorGreen = green;
        colorBlue = blue;
    }
    
    /* getColor() returns color based on String value.
     */
    public float getColor(String color){
        if(color.equals("red")){
            return colorRed;
        }
        else if(color.equals("green")){
            return colorGreen;
        }
        else{
            return colorBlue;
        }
    }
   
    /* main() method that will run the program.
     */
    public static void main(String[] args) {
        Program2 polygonDraw = new Program2();
        polygonDraw.start();
    }
}