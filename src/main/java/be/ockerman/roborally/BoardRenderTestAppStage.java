package be.ockerman.roborally;

import java.io.IOException;
import java.io.InputStream;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import be.ockerman.roborally.uiframework.AppManager;
import be.ockerman.roborally.uiframework.AppStage;

import be.ockerman.roborally.board.domain.Board;
import be.ockerman.roborally.board.domain.BoardPoint;
import be.ockerman.roborally.board.domain.HoleTile;
import be.ockerman.roborally.board.render.impl.BoardRenderer;

public class BoardRenderTestAppStage implements AppStage {
    private float rtri = 0.0f; 
    
    private Board board = null; 
    private BoardRenderer boardRenderer = new BoardRenderer(); 
   
    public void init() {
        
         // TODO Abstract board parsing in different entity. 
         InputStream inputStream = getClass().getResourceAsStream("/boards/island.xml");

         // Bind the instance to the generated XMLBeans types.
         try {
            XmlBoardDocument boardDocument =  XmlBoardDocument.Factory.parse(inputStream);
            XmlBoard xmlBoard = boardDocument.getBoard();
            Board parsedBoard = new Board(); 
            parsedBoard.initSize(xmlBoard.getSizex(), xmlBoard.getSizey());
            for ( XmlPositionTilePhrase entry : xmlBoard.getTiles().getAtArray() ) {
                if (entry.getHole() != null) {
                    BoardPoint bp = new BoardPoint(entry.getX()-1, entry.getY()-1);
                    parsedBoard.setTile( new HoleTile(bp));
                }
                
                
            }
            
            
            
            board = parsedBoard; 
            
        } catch (XmlException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        boardRenderer.init();
    
        
        
    }

    public void mainloop(AppManager appManager) {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
            appManager.quit();
        }
        if(Display.isCloseRequested()) {                     // Exit if window is closed
            appManager.quit();
        }
    }

    public void render(AppManager appManager) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
       
        /*
     // glColorMask( GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE );
        glColor3ub(255,255,255);
        //glColor3ub(128,128,128);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, m_uiControlBack);
        glBegin (GL_QUADS);
          glTexCoord2f( 0.0f, 0.0f);
          glVertex3f (-1.0f, -1.0f, 1.0f);
          glTexCoord2f( 2.5f, 0.0f);
          glVertex3f ( 1.0f, -1.0f, 1.0f);
          glTexCoord2f( 2.5f, 2.0f);
          glVertex3f ( 1.0f,  1.0f, 1.0f);
          glTexCoord2f( 0.0f, 2.0f);
          glVertex3f (-1.0f,  1.0f, 1.0f);
        glEnd ();
        glDisable(GL_TEXTURE_2D);
        */
        

        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        
        GL11.glPushMatrix(); 
        {
            GL11.glTranslatef(0.0f,0.0f,-20.0f);
            GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f); // Rotate further by user keys


            boardRenderer.setBoard(board);
            boardRenderer.render();

        }
        GL11.glPopMatrix();
        
        GL11.glTranslatef(-1.5f,0.0f,-6.0f);                // Move Left 1.5 Units And Into The Screen 6.0
        GL11.glRotatef(rtri,0.0f,1.0f,0.0f);                // Rotate The Triangle On The Y axis ( NEW )
        GL11.glBegin(GL11.GL_TRIANGLES);                    // Drawing Using Triangles
            GL11.glColor3f(1.0f,0.0f,0.0f);             // Red
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);         // Top Of Triangle (Front)
            GL11.glColor3f(0.0f,1.0f,0.0f);             // Green
            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);         // Left Of Triangle (Front)
            GL11.glColor3f(0.0f,0.0f,1.0f);             // Blue
            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);         // Right Of Triangle (Front)
            GL11.glColor3f(1.0f,0.0f,0.0f);             // Red
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);         // Top Of Triangle (Right)
            GL11.glColor3f(0.0f,0.0f,1.0f);             // Blue
            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);         // Left Of Triangle (Right)
            GL11.glColor3f(0.0f,1.0f,0.0f);             // Green
            GL11.glVertex3f( 1.0f,-1.0f, -1.0f);            // Right Of Triangle (Right)
            GL11.glColor3f(1.0f,0.0f,0.0f);             // Red
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);         // Top Of Triangle (Back)
            GL11.glColor3f(0.0f,1.0f,0.0f);             // Green
            GL11.glVertex3f( 1.0f,-1.0f, -1.0f);            // Left Of Triangle (Back)
            GL11.glColor3f(0.0f,0.0f,1.0f);             // Blue
            GL11.glVertex3f(-1.0f,-1.0f, -1.0f);            // Right Of Triangle (Back)
            GL11.glColor3f(1.0f,0.0f,0.0f);             // Red
            GL11.glVertex3f( 0.0f, 1.0f, 0.0f);         // Top Of Triangle (Left)
            GL11.glColor3f(0.0f,0.0f,1.0f);             // Blue
            GL11.glVertex3f(-1.0f,-1.0f,-1.0f);         // Left Of Triangle (Left)
            GL11.glColor3f(0.0f,1.0f,0.0f);             // Green
            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);         // Right Of Triangle (Left)
        GL11.glEnd();
        
        rtri+=0.2f;
        

        
    }

    public void destroy() {
        
        
        
    }

    

}
