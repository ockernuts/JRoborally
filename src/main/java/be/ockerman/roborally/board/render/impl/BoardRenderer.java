package be.ockerman.roborally.board.render.impl;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import be.ockerman.roborally.board.domain.Board;
import be.ockerman.roborally.board.domain.BoardPoint;
import be.ockerman.roborally.board.domain.Direction;
import be.ockerman.roborally.board.domain.Tile;
import be.ockerman.roborally.board.domain.TileType;
import be.ockerman.roborally.render.Renderer;

public class BoardRenderer implements Renderer {

    private Texture plainTileTexture;
    private Board board;
    float fAnim = 0.0f;
    float fRotX = 0.0f;
    float fRotY = 0.0f;

    @Override
    public void init() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(
                    "/boardtextures/square.png");
            plainTileTexture = TextureLoader.getTexture("PNG", inputStream);
        } catch (IOException ex) {
            // Logger.getLogger(EngineTest.class.getName()).log(Level.SEVERE,
            // null, ex);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render() {
        if (board == null) return;
        

        GL11.glRotatef(fRotX, 1.0f, 0.0f, 0.0f); // Rotate further by user keys
        GL11.glRotatef(fRotY, 0.0f, 1.0f, 0.0f); // Rotate further by user keys
        
        GL11.glColor4f(1.0f,1.0f,1.0f,1.0f);
        
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping

        for (int x = 0; x < board.getSizeX(); x++) {
            for (int y = 0; y < board.getSizeY(); y++) {
                BoardPoint bp = new BoardPoint(x, y);
                Tile tile = board.getTile(bp);
                renderTile(tile);

            }
        }
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);

    }

    private void renderTile(Tile tile) {
        if (tile == null) return;

        if (tile.getTileType() == TileType.HOLE) return;

        GL11.glPushMatrix();
        GL11.glTranslatef( getPos3DFromTilePosX(tile.getX()), 0.f, getPos3DFromTilePosY(tile.getY()));
        RotateToTileDirection( tile );
        plainTileTexture.bind();
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        {
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glTexCoord2f(0.0f + fAnim, 0.0f);
            GL11.glVertex3f(-0.5f, 0.0f, 0.5f);
            GL11.glTexCoord2f(1.0f + fAnim, 0.0f);
            GL11.glVertex3f(0.5f, 0.0f, 0.5f);
            GL11.glTexCoord2f(0.0f + fAnim, 1.0f);
            GL11.glVertex3f(-0.5f, 0.0f, -0.5f);
            GL11.glTexCoord2f(1.0f + fAnim, 1.0f);
            GL11.glVertex3f(0.5f, 0.0f, -0.5f);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        

    }

    private void RotateToTileDirection(Tile tile) {
        Direction tileDirection = getDirectionForTile(tile);
        // Orient the tile according to the given direction
        float fRotateAngle = 0.f;

        switch (tileDirection) 
        {
          case LEFT: fRotateAngle = 180.f; break;
          case RIGHT: fRotateAngle = 0.f; break;
          case UP: fRotateAngle = 90.f; break;
          case DOWN: fRotateAngle = 270.f; break;
        }

        GL11.glRotatef(fRotateAngle, 0.f, 1.f, 0.f);
        
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    float getPos3DFromTilePosX(int x) {
        return ((float) x - (board.getSizeX() / 2.f) + 0.5f);
    }

    float getPos3DFromTilePosY(int y) {
        return ((board.getSizeY() / 2.f) - 0.5f - (float) y);
    }

    Direction getDirectionForTile(Tile tile) {
        // TODO for some tiles get the Direction from the Tile.
        Direction tileDirection = null;
        switch ((tile.getX() & 1) + ((tile.getY() & 1) << 1)) {
        case 0:
            tileDirection = Direction.LEFT;
            break;
        case 1:
            tileDirection = Direction.UP;
            break;
        case 2:
            tileDirection = Direction.DOWN;
            break;
        case 3:
            tileDirection = Direction.RIGHT;
            break;
        }
        return tileDirection;
    }
    


}
