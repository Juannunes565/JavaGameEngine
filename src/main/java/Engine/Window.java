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
        
    private static Scene currentScene;
    
    public float r, g, b, a;
    
    
    private Window(){
        this.width = 720;
        this.height = 480;
        this.title = "GameTest"; 
        this.r = 1;
        this.b = 1;
        this.g = 1;
        this.a = 1;
    }
    
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        
        return Window.window;
    }
    
    
    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false: "Escena desconocida: " + newScene;
                break;
        }
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
        GLFW.glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        
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
        
        Window.changeScene(0);
        
    }
    
    
    public void loop(){
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;
        
        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            //Poll events
            
            GLFW.glfwPollEvents();
            GL11.glClearColor(r, g, b, a);
            GL11.glClear(GL_COLOR_BUFFER_BIT);
           
            GLFW.glfwSwapBuffers(glfwWindow);
            
            currentScene.update(dt);
            
            if(dt >= 0){
                currentScene.update(dt);
            }
            
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        
        
    }
    
    
    
    
    
    //Getters y Setters
    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }
    
    
    
}
