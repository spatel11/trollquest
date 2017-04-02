import pygame

import config, util

images = []
image_map = {}
image_index = 0
def map_get_tile_image(name):
    global image_index
    if name not in image_map.keys():
        images.append( [name,pygame.image.load("../../images/tiles/"+name+".png").convert_alpha()] )
        image_map[name] = image_index
        image_index += 1
    return images[image_map[name]]

def map_invert_y(pos):
    return [pos[0],config.SCREENSIZE[1]-pos[1]]

camera_position = [0,0]
mouse_position = [0,0]
def map_set_camera_position(new_camera_position):
    global camera_position
    camera_position = list(new_camera_position)
def map_set_mouse_position(new_mouse_position):
    global mouse_position
    mouse_position = list(new_mouse_position)
def map_get_mouse_coordinate():
    return Map.convert_pixel_to_map(*map_invert_y(mouse_position))

class Tile:
    def __init__(self,position,names):
        self.set_position(position)
        self.names = names
        self.selected = False
    def set_position(self,new_position):
        self.position_map = list(new_position)
    def draw(self,surface):
        for name in self.names:
            pos = Map.convert_map_to_pixel(*self.position_map)
            name,img = map_get_tile_image(name)
            surface.blit(
                img,
                [                       pos[0]                  -img. get_width()/2,
                  config.SCREENSIZE[1]-(pos[1]+img.get_height())+img.get_height()/2 ]
            )
    @staticmethod
    def draw_selected(surface):
        color = (255,0,0)
        pygame.draw.circle(surface,(255,0,0),[config.SCREENSIZE[0]/2,config.SCREENSIZE[1]/2],2)
        
        xb = config.SCREENSIZE[0]/2-32; xm = config.SCREENSIZE[0]/2;   xt = config.SCREENSIZE[0]/2+32
        yb = config.SCREENSIZE[1]/2-17; ym = config.SCREENSIZE[1]/2-1; yt = config.SCREENSIZE[1]/2+15
        
        pygame.draw.aaline(surface,color,(xb,ym),(xb-8,ym+4))
        pygame.draw.aaline(surface,color,(xb,ym),(xb-8,ym-4))
        
        pygame.draw.aaline(surface,color,(xt,ym),(xt+8,ym+4))
        pygame.draw.aaline(surface,color,(xt,ym),(xt+8,ym-4))
        
        pygame.draw.aaline(surface,color,(xm,yb),(xm-8,yb-4))
        pygame.draw.aaline(surface,color,(xm,yb),(xm+8,yb-4))
        
        pygame.draw.aaline(surface,color,(xm,yt),(xm-8,yt+4))
        pygame.draw.aaline(surface,color,(xm,yt),(xm+8,yt+4))

class Map:
    tile_highlight = Tile([0,0],["map_symbols/tile_highlight"])
    tile_selected = None
    def __init__(self,size_x,size_y):
        self.tiles = []
        for y in xrange(size_y):
            row = []
            for x in xrange(size_x):
                row.append(Tile([x,y],["map_symbols/tile_blank"]))
            self.tiles.append(row)
        self.size_x = size_x
        self.size_y = size_y
    def draw(self,surface):
        for y in xrange(self.size_y):
            for x in xrange(self.size_x):
                self.tiles[y][x].draw(surface)
                
        Map.tile_highlight.set_position(map_get_mouse_coordinate())
        Map.tile_highlight.draw(surface)
        
        if Map.tile_selected!=None:
            Tile.draw_selected(surface)
    def clamp_to_map(tile_x,tile_y):
        tile_x = util.clamp(tile_x,0,self.size_x-1)
        tile_y = util.clamp(tile_y,0,self.size_y-1)
        return [tile_x,tile_y]
    @staticmethod
    def convert_map_to_pixel(tile_x,tile_y):
        tile_x -= camera_position[0];
        tile_y -= camera_position[1];
        return [
            32*tile_x - 32*tile_y + config.SCREENSIZE[0]/2,
            16*tile_x + 16*tile_y + config.SCREENSIZE[1]/2
        ]
    @staticmethod
    def convert_pixel_to_map(pixel_x,pixel_y):
        pixel_x -= config.SCREENSIZE[0]/2;
        pixel_y -= config.SCREENSIZE[1]/2;

        tile_x = util.rndint( (pixel_x+2*pixel_y) / 64.0 )
        tile_y = util.rndint( (2*pixel_y-pixel_x) / 64.0 )

        tile_x += camera_position[0]
        tile_y += camera_position[1]

        return [tile_x,tile_y]





