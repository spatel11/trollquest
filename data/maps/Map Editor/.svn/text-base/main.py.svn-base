import pygame
from pygame.locals import *
import sys, os, traceback
##if sys.platform == 'win32' or sys.platform == 'win64':
##    os.environ['SDL_VIDEO_CENTERED'] = '1'
pygame.display.init()
pygame.font.init()

from config import *
from Map import *
from Sidebar import *
from SidebarLeft import *
from SidebarRight import *

icon = pygame.Surface((1,1)); icon.set_alpha(0); pygame.display.set_icon(icon)
pygame.display.set_caption("Map Editor for TrollQuest - Ian Mallett - 2011")
Surface = pygame.display.set_mode(SCREENSIZE,RESIZABLE)

pygame.key.set_repeat(300,30)

camera_position = [0,0]
mouse_position = [0,0]

fullscreen = False

def quit():
    pygame.quit(); sys.exit()
def GetInput():
    global selected_tile, fullscreen
    key = pygame.key.get_pressed()
    mpos = pygame.mouse.get_pos()
    map_set_mouse_position(mpos)
    for event in pygame.event.get():
        if sidebar_left.process(mpos,key,event): continue
        if   event.type == QUIT: quit()
        elif event.type == KEYDOWN:
            if   event.key == K_ESCAPE: quit()
            elif event.key == K_f:
                fullscreen = not fullscreen
                if fullscreen:
                    resize(pygame.display.list_modes()[0])
                else:
                    resize(ORIGNAL_SIZE)
                
            if Map.tile_selected!=None:
                if   event.key == K_a:
                    Map.tile_selected.names.append(sidebar_left.get_tile_name())
                elif event.key == K_r:
                    Map.tile_selected.names.append(sidebar_left.get_tile_name_random())
        elif event.type == MOUSEBUTTONDOWN:
            Map.tile_selected = None
            coord = map_get_mouse_coordinate()
            set_camera_position(coord)
            for row in current_map.tiles:
                for tile in row:
                    if tile.position_map[0]==coord[0] and tile.position_map[1]==coord[1]:
                        tile.selected = not tile.selected
                        if tile.selected:
                            Map.tile_selected = tile
                    else:
                        tile.selected = False
        elif event.type == VIDEORESIZE:
            resize(event.size)
            
def resize(new_size):
    global Surface
    SCREENSIZE[0] = new_size[0]
    SCREENSIZE[1] = new_size[1]
    if fullscreen:
        Surface = pygame.display.set_mode(SCREENSIZE,RESIZABLE|FULLSCREEN)
    else:
        Surface = pygame.display.set_mode(SCREENSIZE,RESIZABLE)
    Sidebar.init()
def set_camera_position(new_camera_position):
    global camera_position
    if camera_position != new_camera_position:
        camera_position = list(new_camera_position)
        map_set_camera_position(camera_position)
        return True
    return False
def move_camera_position(dx,dy):
    set_camera_position([camera_position[0]+dx,camera_position[1]+dy])

def Draw():
    Surface.fill((0,0,0))
    current_map.draw(Surface)

    sidebar_left.draw(Surface)

    sidebar_right.draw(Surface)

    pygame.display.flip()
    
def main():
    global current_map, sidebar_left, sidebar_right
    Sidebar.init()
    current_map = Map(30,30)
    sidebar_left = SidebarLeft()
    sidebar_right = SidebarRight()
    
    Clock = pygame.time.Clock()
    while True:
        GetInput()
        Draw()
        Clock.tick(30)
if __name__ == '__main__':
    try:
        main()
    except Exception, e:
        tb = sys.exc_info()[2]
        traceback.print_exception(e.__class__, e, tb)
        pygame.quit()
        raw_input()
        sys.exit()
