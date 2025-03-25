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
public class MouseListener { 
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;        
    }
    
    public static MouseListener get(){
        if(MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }
        
        return MouseListener.instance;
    }
    
    
    public static void mousePosCallback(long window, double xpos, double ypos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        
        boolean dragging = false;
        for(boolean button: get().mouseButtonPressed){
            if(button){
                dragging = true;
            }
        }
        get().isDragging = dragging;
        
    }
    
    
    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(button < 0 || button > get().mouseButtonPressed.length){
            return;
        }
        
        if(action == GLFW_PRESS){
            get().mouseButtonPressed[button] = true;            
        }
        else if(action == GLFW_RELEASE){
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;   
        }
    }
    
    
    public static void mouseScrollCallBack(long window, double xOffset, double yOffset){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }
    
    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX(){
        return (float) get().xPos;
    }
    
    public static float getY(){
        return (float) get().yPos;
    }
    
    public static float getDx(){
        return (float) (get().lastX - get().xPos);
    }
    
    public static float getDy(){
        return (float) (get().lastY - get().yPos);
    }
    
    public static float getScrollX(){
        return (float) get().scrollX;
    }
    
    public static float getScrollY(){
        return (float) get().scrollY;
    }
    
    public static boolean isDragging(){
        return get().isDragging;
    }
    
    
    
    
    
    
}
