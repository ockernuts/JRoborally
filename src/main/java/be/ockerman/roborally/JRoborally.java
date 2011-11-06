package be.ockerman.roborally;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import be.ockerman.roborally.uiframework.AppManager;
import be.ockerman.roborally.uiframework.AppStage;


public class JRoborally implements AppManager {
    
    private boolean done = false;
    private boolean fullscreen = false;
    private final String windowTitle = "JRoborally";
    private DisplayMode displayMode;
    
    private AppStage appStage = null; 

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JRoborally roborally = new JRoborally();
        roborally.run();
    }
    
    public void run() {
        try {
            init();
            while (!done) {
                if (appStage != null) {
                    appStage.mainloop(this);
                    appStage.render(this);
                }
                
                Display.update();
            }
            cleanup();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    

    
    private void createWindow() throws Exception {
        Display.setFullscreen(fullscreen);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        if (d.length == 0) {
            throw new Exception("No DisplayModes could be retrieved");
        }
        displayMode = d[0]; // fall back display mode if 640X480X32 is not listed...
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640
                && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
    }
    private void init() throws Exception {
        createWindow();
        initGL();
        appStage = new BoardRenderTestAppStage();
        appStage.init(); 
    }

    private void initGL() {
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do

        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          10.f,
          10240.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix

        // Really Nice Perspective Calculations
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }
    private static void cleanup() {
        Display.destroy();
    }

    public AppStage getAppStage() {
        return appStage;
    }

    public void makeFullScreen() {
        fullscreen = true; 
        try {
            Display.setFullscreen(fullscreen);
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void makeWindowed() {
        fullscreen = false; 
        try {
            Display.setFullscreen(fullscreen);
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

    public void quit() {
        done = true;
        
    }

    public void setAppStage(AppStage appStage) {
       appStage = appStage; 
        
    }
    
    

}
