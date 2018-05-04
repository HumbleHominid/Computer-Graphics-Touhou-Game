#version 430

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
out vec2 tc;
out vec3 eye;

uniform mat4 mv_matrix;
uniform mat4 p_matrix;
layout (binding=0) uniform sampler2D samp;

void main(void) {
    gl_Position = p_matrix * mv_matrix * vec4(position, 1.0);
    tc = texCoord;
    // multiplying by the p_matrix is very important when using orthogonal projection
    eye = (p_matrix * mv_matrix * vec4(1.0)).xyz;
}
