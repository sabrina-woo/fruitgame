# My Personal Project: Falling Fruit Game

## What will the application do?

The application I will create is a simple game that consists of a **single playable character** that can move left 
and right across the screen. The user's goal is to **collect falling fruit** that moves vertically down the 
screen, and **avoid harmful falling objects** falling vertically down the screen. Upon **hitting a harmful object, 
the game will end**.


## Who will use it?

This game is geared towards **younger children** who would be best able to enjoy the simplistic nature of the game, 
but of course, the game can be played by anyone.

## Why is this project of interest to you?

- Ever since my first coding class, I would find myself interested in how the video games that I play are coded. By 
creating this simplistic game, I can explore the mechanisms that I have been curious about.
- I would like to create something visual, and I believe that creating a simplistic game with this concept 
would allow for a wide range of flexibility/**creative liberty in rendering the final product**.
- As I enjoy playing video games, being able to play the final product would be highly satisfying.

## User Stories

- As a user, I want to be able to control the direction of my character.
- As a user, I want to be able to be able to add an arbitrary number of fruit to the basket by hitting the fruit.
- As a user, I want to encounter an arbitrary number of enemies and fruit throughout my game.
- As a user, I want my score to be continuously updated throughout the game.
- As a user, I want to be able to view my score and see it increase when I collect every new fruit throughout the game.
- As a user, I want the game to end when I hit an enemy.
- As a user, I want to be able to view my final score at the end of the game.
- As a user, I want the option to save my game.
- As a user, I want to option to load a saved game.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by ending the game, then selecting
the add fruit or remove fruit buttons
- You can generate the second required action related to adding Xs to a Y by ending the game, then selecting the 
remove top half button (which will filter out the fruit from the top half of the screen)
- You can locate my visual component by ending the game (component will pop up automatically)
- You can save the state of my application by pressing the top arrow key anytime throughout the game, or by selecting 
the save button when the game over screen pops up (when saved this way, if the game is reloaded, game is set to not over)
- You can reload the state of my application by selecting the yes when prompted when starting the application.

# Phase 4: Task 3
There are many ways I could refactor/improve my application. To start, I would add an abstract class (perhaps called 
something like falling object) that would be 
extended by both the Enemy and Fruit classes. These classes share a lot of similar behaviour; they both have two fields, 
X position and Y position, and they share similar behaviour (both spawning on the same Y value, and both moving down one
Y position every tick). In doing this, I would be able to have much less duplicate code. I would also make Fruit an 
abstract class. This would make it quite simple to create different types of fruit which would all extend this abstract 
fruit class, which I think would make my application much more visually appealing. 

Another change I would make is potentially merging the GamePanel class and EndScreen class. Currently, I have a method 
in my main (FruitGame) class that checks if the game is over every tick, and repaints the game to the EndScreen if the 
game is over. However, rather than making a new class for the EndScreen, I could just remove Graphic objects from the 
GamePanel JPanel and add the EndScreen JPanel to within the GamePanel class. This would be viable since they share many 
of the same fields. 

Another change I would like to make is to refactor my code so that my GamePanel class does not take in FruitGame as 
a field. I only require using the fruitGame field once in the GamePanel class, and I feel like it would be possible to 
remove this association.



