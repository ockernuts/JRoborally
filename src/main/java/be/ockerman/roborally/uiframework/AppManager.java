package be.ockerman.roborally.uiframework;

public interface AppManager {
    void quit();
    void makeFullScreen(); 
    void makeWindowed();
    void setAppStage(AppStage appStage);
    AppStage getAppStage();
}
