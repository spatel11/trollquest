name = "Axeman"
action = "attack"
directory = "tiles/flagstones/"
filename_in  = directory+"32_flagstone_tiles.png"
filename_out = directory+"tile*.png"
number_of_sprites_x = 4
number_of_sprites_y = 8

import pygame

image = pygame.image.load(filename_in)
sprite_size_x = image. get_width()/number_of_sprites_x
sprite_size_y = image.get_height()/number_of_sprites_y

screen = pygame.display.set_mode([sprite_size_x,sprite_size_y])

image = image.convert_alpha()

n = 0
for y in xrange(number_of_sprites_y):
    for x in xrange(number_of_sprites_x):
        rect = pygame.Rect(\
            x*sprite_size_x,\
            y*sprite_size_y,\
            sprite_size_x,\
            sprite_size_y)
        sub = image.subsurface(rect)

        pygame.image.save( sub, filename_out.replace("*",str(n)) )

        screen.fill((0,0,0))
        screen.blit(sub,(0,0))
        pygame.display.flip()

        n += 1
