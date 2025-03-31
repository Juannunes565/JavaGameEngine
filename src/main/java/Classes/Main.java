package Classes;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Main {
    
    private static float[] textCoords = {
        0.0f,  0.0f,
        1.0f,  0.0f,
        0.5f,  1.0f
    };
    
    
    public static void main(String[] args) {
        
        if (!glfwInit()) {
            throw new IllegalStateException("Error al iniciar GLFW");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(640, 480, "Game", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Error al crear la ventana");
        }

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();

        // Configurar la ventana de OpenGL
        glViewport(0, 0, 640, 480);
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f); // Establece el color de fondo

        float[] vertices = {                    
             0.0f,  0.5f, 0.0f,    1.0f, 0.0f, 0.0f,     0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,    0.0f, 1.0f, 0.0f,     0.0f, 1.0f,
             0.5f, -0.5f, 0.0f,    0.0f, 0.0f, 1.0f,     1.0f, 1.0f 
        };
        

        int VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        
        
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);                      

        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2); 
        
        
        //============Cargar textura===============
        
        // Crear un stack de memoria para manejar buffers de forma eficiente
        int texture;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            String path = "assets\\face.png";

            // Cargar la imagen con STBImage
            ByteBuffer image = STBImage.stbi_load(path, width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Error al cargar la imagen " + path + ": " + STBImage.stbi_failure_reason());
            }

            // Generar la textura en OpenGL
            texture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture);

            // Configurar los parámetros de la textura
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            // Subir los datos de la imagen a la textura de OpenGL
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            glGenerateMipmap(GL_TEXTURE_2D);

            // Liberar la imagen en STB
            STBImage.stbi_image_free(image);
        }
        
        
        
        

        
        Shader newShader = new Shader("shaders\\shaderExample.vert", "shaders\\shaderExample.frag");
        
        // Game Loop            
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT); 
                                    
            float greenValue = (float) ((Math.sin(glfwGetTime()) / 2.0f) + 0.5f);            
            int vertexColorLocation = glGetUniformLocation(newShader.getID(), "ourColor");
            glUseProgram(newShader.getID());            
            glUniform4f(vertexColorLocation, 0.0f, greenValue, 0.0f, 0.0f);
                        
            glBindVertexArray(VAO);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);

            glfwSwapBuffers(window);
        }

        // Cleanup
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        newShader.deleteShader();

        glfwTerminate();
    }
}
