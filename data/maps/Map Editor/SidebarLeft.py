import pygame
from pygame.locals import *

import random

from config import *
from Map import *
from Sidebar import *

class TileType:
    font = pygame.font.SysFont("Times New Roman",12)
    def __init__(self,name,length=1):
        self.name = name
        self.rendered_name = TileType.font.render(self.name,True,(255,255,255))
        self.length = length
        
        self.images = []
        self.names = []

        print "Loading",self.name
        i = 0
        while True:
            str_num = str(i)
            while len(str_num)<self.length: str_num = "0"+str_num
            try:
                name,image = map_get_tile_image(self.name+"/tile"+str_num)
                self.names.append(name)
                self.images.append(image)
            except:
                if i==0: raise NameError("Could not find any matching images for \""+self.name+"\"!");
                break
            i += 1

        self.number = len(self.images)

class SidebarLeft(Sidebar):
    def __init__(self):
        self.mode = 1
        self.adding_type_int = 0
        self.adding_tile_int = 0

        self.types = [
            TileType("desert"),
            TileType("flagstones"),
            TileType("forest"),
            TileType("ice"),
            TileType("plain"),
            TileType("portal",2),
            TileType("swamp"),
            TileType("wasteland"),
            TileType("water")
        ]

    def get_tile_name(self):
        return self.types[self.adding_type_int].names[self.adding_tile_int]
    def get_tile_name_random(self):
        return random.choice( self.types[self.adding_type_int].names )

    def process(self,mpos,key,event):
        def toggle_mode():
            if self.mode == 1:
                self.mode = 2
                self.adding_tile_int = 0
            else:
                self.mode = 1
            
        if event.type == KEYDOWN:
            delta = 1
            if key[K_LSHIFT] or key[K_RSHIFT]: delta = 5
            if   event.key ==    K_UP:
                if self.mode == 1:
                    self.adding_type_int = (self.adding_type_int-delta) % len(self.types)
                else:
                    self.adding_tile_int = (self.adding_tile_int-delta) % self.types[self.adding_type_int].number
            elif event.key ==  K_DOWN:
                if self.mode == 1:
                    self.adding_type_int = (self.adding_type_int+delta) % len(self.types)
                else:
                    self.adding_tile_int = (self.adding_tile_int+delta) % self.types[self.adding_type_int].number
            elif event.key ==  K_LEFT: toggle_mode()
            elif event.key == K_RIGHT: toggle_mode()
        return False
        
    def draw(self,surface):
        surface.blit(Sidebar.side_bar_l,(0,0))
        y = 5; i = 0
        for tile_type in self.types:
            surface.blit(tile_type.rendered_name,(5,y))
            if i == self.adding_type_int and self.mode == 1:
                pygame.draw.rect(surface,(255,255,0),(2,y-1,60,18),1)
            y += 15
            i += 1
            
        x = 80; y = 5; i = 0
        for image in self.types[self.adding_type_int].images:
            surface.blit(image,(x,y))
            if i==self.adding_tile_int and self.mode == 2:
                pygame.draw.rect(surface,(255,255,0),(x-2,y-2,68,35),1)
            y += 35
            if y > SCREENSIZE[1]-32:
                x += 69
                y = 5
            i += 1
