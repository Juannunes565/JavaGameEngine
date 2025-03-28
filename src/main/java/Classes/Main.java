/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Classes;
import static org.lwjgl.glfw.GLFW.*;
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
        
        //Game Loop
        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();
        }
        
        glfwTerminate(); //Libera los recursos de SO
    }
    
}
