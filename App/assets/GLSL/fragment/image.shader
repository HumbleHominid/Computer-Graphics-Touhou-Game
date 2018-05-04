#version 430

in vec2 tc;
in vec3 eye;
out vec4 color;

uniform mat4 mv_matrix;
uniform mat4 p_matrix;
layout (binding=0) uniform sampler2D s;

void main(void) {
    // This is `fog` but since it is 2D and everything is on same z it just
    //  looks like faded out instead of foggy
    float dist = length(eye.z);
    float fogFactor = clamp(1.0 - dist, 0.0, 1.0);

    color = mix(vec4(0.7, 0.8, 0.9, 0.0), texture(s, tc), fogFactor);
}
