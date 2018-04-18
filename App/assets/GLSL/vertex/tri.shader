#version 430

uniform float x;
uniform float y;

void main(void) {
    if (gl_VertexID == 0)
        gl_Position = vec4(x + 0.25, y + -0.25, 0.0, 1.0);
    else if (gl_VertexID == 1)
        gl_Position = vec4(x + -0.25, y + -0.25, 0.0, 1.0);
    else
        gl_Position = vec4(x + 0.25, y + 0.25, 0.0, 1.0);
}
