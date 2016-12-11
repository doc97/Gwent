#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;
layout (location = 2) in vec4 color;

out vec2 pass_textureCoords;
out vec4 pass_color;

uniform vec2 translation;

void main(void){
    gl_Position = vec4(position, 1.0);
    pass_textureCoords = textureCoords;
    pass_color = color;
}