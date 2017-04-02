name = "Goblin"
action = "jump"
filename_in = "source data and dev/characters/goblin.png"
filename_out = [
    "characters/"+name+"/"+action+"/l_*.png",
    "characters/"+name+"/"+action+"/lu*.png",
    "characters/"+name+"/"+action+"/_u*.png",
    "characters/"+name+"/"+action+"/ru*.png",
    "characters/"+name+"/"+action+"/r_*.png",
    "characters/"+name+"/"+action+"/rd*.png",
    "characters/"+name+"/"+action+"/_d*.png",
    "characters/"+name+"/"+action+"/ld*.png"]
number_of_sprites_x = 8
number_of_sprites_y = 1
sprite_start_x = 4
sprite_start_y = [0,1,2,3,4,5,6,7]
sprite_size_x = 6144/48
sprite_size_y = 1024/8
number = 8 #probably do not change this!  Ever!

import pygame

screen = pygame.display.set_mode((sprite_size_x,sprite_size_y))

image = pygame.image.load(filename_in).convert_alpha()

def vectorize(parameter):
    if type(parameter) != type([]):
        temp = []
        for x in xrange(number): temp.append(parameter)
        return temp
    return parameter
def run(index):
    n = 0
    for y in xrange(number_of_sprites_y[index]):
        for x in xrange(number_of_sprites_x[index]):
            rect = pygame.Rect(\
                (x+sprite_start_x[index])*sprite_size_x[index],\
                (y+sprite_start_y[index])*sprite_size_y[index],\
                sprite_size_x[index],\
                sprite_size_y[index])
            sub = image.subsurface(rect)

            pygame.image.save( sub, filename_out[index].replace("*",str(n)) )

            screen.fill((0,0,0))
            screen.blit(sub,(0,0))
            pygame.display.flip()

            n += 1
        
filename_out = vectorize(filename_out)
number_of_sprites_x = vectorize(number_of_sprites_x)
number_of_sprites_y = vectorize(number_of_sprites_y)
sprite_start_x = vectorize(sprite_start_x)
sprite_start_y = vectorize(sprite_start_y)
sprite_size_x = vectorize(sprite_size_x)
sprite_size_y = vectorize(sprite_size_y)
for n in xrange(number): run(n)
