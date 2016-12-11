#version 330

in vec2 pass_textureCoords;
in vec4 pass_color;

out vec4 out_color;

// Added to color passed from vertex shader
uniform vec3 u_color;
uniform sampler2D fontAtlas;

void main(void){
    out_color = vec4(pass_color.xyz + u_color, texture(fontAtlas, pass_textureCoords).a);
}