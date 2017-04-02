name = "icicle"
filename_in = "source data and dev/spells/teleport_rune.png"
directory = "tiles/map_symbols/cast/"
##filename_out = [
##    directory+"l_*.png",
##    directory+"lu*.png",
##    directory+"_u*.png",
##    directory+"ru*.png",
##    directory+"r_*.png",
##    directory+"rd*.png",
##    directory+"_d*.png",
##    directory+"ld*.png"]
filename_out = [directory+"move*.png"]
number_of_sprites_x = 4
number_of_sprites_y = 1
sprite_start_x = 0
sprite_start_y = [0]#,1,2,3,4,5,6,7]
sprite_size_x = 256/4
sprite_size_y = 64/1
number = 1 #probably do not change this!  Ever!

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











