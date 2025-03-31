package Classes;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL;

public class Main {
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
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
             0.0f,  0.5f, 0.0f
        };

        int VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        String vertexShaderSource = "#version 330 core\n"
                + "layout (location = 0) in vec3 aPos;\n"
                + "void main()\n"
                + "{\n"
                + "    gl_Position = vec4(aPos, 1.0);\n"
                + "}";

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error en Vertex Shader: " + glGetShaderInfoLog(vertexShader));
            return;
        }

        String fragmentShaderSource = "#version 330 core\n"
                + "out vec4 FragColor;\n"
                + "void main()\n"
                + "{\n"
                + "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
                + "}";

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error en Fragment Shader: " + glGetShaderInfoLog(fragmentShader));
            return;
        }

        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Error al enlazar Shader Program: " + glGetProgramInfoLog(shaderProgram));
            return;
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        // Game Loop
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT); // Limpia la pantalla en cada iteración
            
            glUseProgram(shaderProgram);
            glBindVertexArray(VAO);
            glDrawArrays(GL_TRIANGLES, 0, 3);

            glfwSwapBuffers(window);
        }

        // Cleanup
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glDeleteProgram(shaderProgram);

        glfwTerminate();
    }
}
