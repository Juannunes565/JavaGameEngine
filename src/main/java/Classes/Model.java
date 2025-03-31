package Classes;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Model {
    private int drawCount;
    private int vaoId;
    private int vertexId;
    private int textureId;

    public Model(float[] vertices, float[] tex_coord) {
        drawCount = vertices.length / 3;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vertexId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexId);
        glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); // Atributo de posición (location = 0)
        glEnableVertexAttribArray(0);

        textureId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureId);
        glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(tex_coord), GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0); // Atributo de coordenadas de textura (location = 1)
        glEnableVertexAttribArray(1);

        glBindVertexArray(0); // Desvincular VAO
        glBindBuffer(GL_ARRAY_BUFFER, 0); // Desvincular VBO
    }

    public void render() {
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, drawCount);
        glBindVertexArray(0);
    }

    private FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}