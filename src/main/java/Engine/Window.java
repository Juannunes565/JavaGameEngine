/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.system.MemoryUtil.NULL;
import util.Time;

/**
 *
 * @author Juan
 */
public class Window {
    
    private int width, height;  
    private String title;
    private long glfwWindow;
    
    private static Window window;
    
    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }
    
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        
        return Window.window;
    }
    
    
    
    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        
        init();
        loop();
        
        //Liberar memoria
        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);
        
        //Terminar GLFW y liberar el error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
    
    
    public void init(){
        
        //Iniciar el error callback
        GLFWErrorCallback.createPrint(System.err).set();
                
        if(!GLFW.glfwInit()){
            throw new IllegalStateException("No se puede inicializar GLWF");
        }
        
        //Configurar glfw
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        
        //Crear la ventana
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Error al crear la ventana de GLFW");
        }
        
        //Setear los callbacks
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallBack);
        GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::KeyCallback);
        
        //OpenGL
        GLFW.glfwMakeContextCurrent(glfwWindow);
        GLFW.glfwSwapInterval(1); //Habilitar v-sync
        GLFW.glfwShowWindow(glfwWindow); //Hacer la ventana visible
        
        //Esta linea es importante (Todavia no se porque)
        GL.createCapabilities();
        
    }
    
    
    public void loop(){
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        
        
        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            //Poll events
            
            GLFW.glfwPollEvents();
            GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL_COLOR_BUFFER_BIT);
           
            GLFW.glfwSwapBuffers(glfwWindow);
        }
    }
}
