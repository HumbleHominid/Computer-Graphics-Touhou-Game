#version 430

in vec2 tc;
in vec3 eye;
out vec4 color;

uniform mat4 mv_matrix;
uniform mat4 p_matrix;
layout (binding=0) uniform sampler2D s;

void main(void) {
    // vertically `foggy`. It gets hazy at the top of the screen
    float fogStart = 0.0;
    float fogEnd = 3.0;
    float fogFactor = clamp((fogEnd - eye.y) / (fogEnd - fogStart), 0.0, 1.0);

    color = mix(vec4(0.7, 0.8, 0.9, 0.0), texture(s, tc), fogFactor);
}
