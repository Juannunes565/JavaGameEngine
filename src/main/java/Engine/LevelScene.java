/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Engine;

/**
 *
 * @author Juan
 */
public class LevelScene extends Scene {
    
    public LevelScene(){
        System.out.println("Adentro del nivel");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
        Window.get().a = 1;
    }
    
    @Override
    public void update(float dt){
        System.out.println((1.0f / dt) + " FPS");
    }
    
    
}
