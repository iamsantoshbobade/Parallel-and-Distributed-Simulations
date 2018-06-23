#__author__ = 'santosh'


background_image1_filename = 'blackbg.jpg'
image_ball = "ballnew.png"

import pygame
from pygame.locals import *
from sys import exit


pygame.init()

screen = pygame.display.set_mode((640, 480), 20, 0)

background1 = pygame.image.load(background_image1_filename).convert()
sprite1 = pygame.image.load(image_ball).convert()

clock = pygame.time.Clock()

# x coordinate of our sprite
x1 = 200.
y1 = 200.


# Speed in pixels per second
speedx = 100.
speedy = 100.

screen.blit(background1, (0,0))
pygame.display.update()
pygame.time.delay(10)

while True:
    for event in pygame.event.get():
        if event.type == QUIT:
            exit()

    pygame.display.update()
    screen.blit(background1, (0,0))

    screen.blit(sprite1, (x1, y1))
    time_passed = clock.tick(30)
    time_passed_seconds = time_passed / 1000.0

    distance_movedx = time_passed_seconds * speedx
    x1 += distance_movedx

    distance_movedy = time_passed_seconds * speedy
    y1 += distance_movedy

    if x1 > 600.:
        speedx = speedx * -1
    if y1 < 0:
        speedy = speedy * -1

    if y1 > 440.:
        speedy = speedy * -1


    if x1 < 0.:
        speedx = speedx * -1

    pygame.display.update()



