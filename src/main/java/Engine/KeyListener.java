/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Engine;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 *
 * @author Juan
 */
public class KeyListener {
    private static KeyListener instance;
    private boolean KeyPressed[] = new boolean[350];
    
    private KeyListener(){
        
    }
    
    
    public static KeyListener get(){
        if(instance == null){
            KeyListener.instance = new KeyListener();
        }
        
        return KeyListener.instance;
    }
    
    
    public static void KeyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS){
            get().KeyPressed[key] = true;
        }
        else if(action == GLFW_RELEASE){
            get().KeyPressed[key] = false;
        }
    }
    
    
    public static boolean isKeyPressed(int keyCode){
        return get().KeyPressed[keyCode];            
    }
    
}
