/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Classes;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;
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
        
        glEnable(GL_TEXTURE_2D);
        
        Texture texture = new Texture("assets\\face.png");
        
        //Game Loop
        float x = 0;
        float y = 0;
        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();            
            glfwSwapBuffers(window);
                      
            
            if(glfwGetKey(window, GLFW_KEY_A) == GL_TRUE){
                x -= 0.001f;                
            }
            if(glfwGetKey(window, GLFW_KEY_W) == GL_TRUE){
                y += 0.001f;                
            }
            if(glfwGetKey(window, GLFW_KEY_S) == GL_TRUE){
                y -= 0.001f;                
            }
            if(glfwGetKey(window, GLFW_KEY_D) == GL_TRUE){
                x += 0.001f;                
            }                        
            
            glClear(GL_COLOR_BUFFER_BIT);
            
            texture.bind();
            
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2f(-0.5f+x, 0.5f+y);
                
                glTexCoord2f(1, 0);
                glVertex2f(0.5f+x, 0.5f+y);
                
                glTexCoord2f(1, 1);
                glVertex2f(0.5f+x, -0.5f+y);
                
                glTexCoord2f(0, 1);
                glVertex2f(-0.5f+x, -0.5f+y);
            glEnd();
            
        }
        
        glfwTerminate(); //Libera los recursos de SO
    }
    
}
