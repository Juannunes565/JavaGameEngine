package Classes;

import static org.lwjgl.opengl.GL20.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Shader {
    
    private int ID;

    // Constructor: carga los shaders desde archivos y los compila
    public Shader(String vertexPath, String fragmentPath) {
    
        String vertexCode = readFile(vertexPath);
        String fragmentCode = readFile(fragmentPath);
        
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexCode);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentCode);
        
        ID = glCreateProgram();
        glAttachShader(ID, vertexShader);
        glAttachShader(ID, fragmentShader);
        glLinkProgram(ID);
        
        if (glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Error al enlazar el shader program: " + glGetProgramInfoLog(ID));
        }
        
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }
    
    public void use() {
        glUseProgram(ID);
    }
    
    public void setBool(String name, boolean value) {
        glUniform1i(glGetUniformLocation(ID, name), value ? 1 : 0);
    }

    public void setInt(String name, int value) {
        glUniform1i(glGetUniformLocation(ID, name), value);
    }

    public void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(ID, name), value);
    }
    
    private String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo: " + filePath, e);
        }
    }
    
    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        // Verificar errores de compilación
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error al compilar shader: " + glGetShaderInfoLog(shader));
        }

        return shader;
    }
    
    
    public void deleteShader(){
        glDeleteProgram(ID);
    }
    
    public int getID() {
        return ID;
    }
}
