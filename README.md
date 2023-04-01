README.md
Authors: Sam Winter, Adnan Adan, Ermias Cham

This is an implementation of a flocking behavior using Circles.

The project contains:
BoundingBox.java - Creates the bounds for circles to flock within
Circle.java - Representation of a circle within the JFrame
CircleModel.java - Models a collection of circles within a JFrame
Controller.java - The controller class for circles
Flocking Project.pdf - A pdf version of the project plans and who is doing what
Point.java - Helper Class to store xy coords
Simulation.java - The driver class for simulation 
SimulationGUI.java - Creates the GUI application

How to run:
- Run the application by running Simulation.java
- Enter a the number of circles at the top 2-100
- Enter the speed for the circles from 1-5
- Press set-up
- Press run
- The circles should show a flocking behavior


---------------------------------------------------------------------------------------------------------------

    * Help & Reminders *

- ^^ Code for New Branches ^^

git checkout -b newBranch
git push --set-upstream origin newBranch

- ^^ Code for Merging Finished Branches ^^

git checkout dev
git merge branchName
git add *
git push

- ^^ Current Plan (Loose) ^^

- Sam calculated average position (serparation) and might continue work on that
- Ermias will work on average direction (cohesion)