/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Classes;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
/**
 *
 * @author Juan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(!glfwInit()){
            throw new IllegalStateException("Error al iniciar GLFW");
        }
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(640, 480, "Game", 0, 0);
        if(window == 0){
            throw new IllegalStateException("Error al crear la ventana");
        }
        
        glfwShowWindow(window);    
        
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        //Game Loop
        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();            
            glfwSwapBuffers(window);
            
            glClear(GL_COLOR_BUFFER_BIT);
            
            glBegin(GL_QUADS);
                glColor4f(1, 0, 0, 0);
                glVertex2f(-0.5f, 0.5f);
                
                glColor4f(0, 1, 0, 0);
                glVertex2f(0.5f, 0.5f);
                
                glColor4f(1, 0, 1, 0);
                glVertex2f(0.5f, -0.5f);
                
                glColor4f(1, 1, 1, 0);
                glVertex2f(-0.5f, -0.5f);
            glEnd();
            
        }
        
        glfwTerminate(); //Libera los recursos de SO
    }
    
}
