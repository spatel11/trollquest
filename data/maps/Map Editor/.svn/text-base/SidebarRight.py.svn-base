import pygame
from pygame.locals import *

from config import *
from Map import *
from Sidebar import *

class SidebarRight(Sidebar):
    def __init__(self):
        pass

    def draw(self,surface):
        if Map.tile_selected != None:
            surface.blit(Sidebar.side_bar_r,(SCREENSIZE[0]-80,0))
            
            y = 5
            for imagekey in Map.tile_selected.names:
                name,image = images[image_map[imagekey]]
                surface.blit(image,(SCREENSIZE[0]-80+9,y))
                y += image.get_height()

