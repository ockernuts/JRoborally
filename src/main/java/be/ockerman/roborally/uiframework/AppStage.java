package be.ockerman.roborally.uiframework;

public interface AppStage {
    void mainloop(AppManager appManager); 
    void render(AppManager appManager);
    void init(); 
    void destroy(); 
}
