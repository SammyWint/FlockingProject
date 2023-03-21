
^^ Record of Changes and Modifications ^^

Date: 3/9
Who: Ermias
Changes: Increased GUI and pane size in SimulationGUI and xMAXRANGE in Circle,
        Increased number of circles in sim and made that number a final int in CircleModle (Only 40 for now),
        Fixed issue where some circles were frozen (Added an if statement to step() in Circle),
        Lastly, this README haha

Date: 3/16
Who: Ermias
Changes: Added method, avgDirection in CircleModel. This helper method calculates the average direction for
         calculating the average direction of every visible circle in the sim.

---------------------------------------------------------------------------------------------------------------

    * Help & Reminders *

^^ Code for New Branches ^^

git checkout -b newBranch
git push --set-upstream origin newBranch

^^ Code for Merging Finished Branches ^^

git checkout dev
git merge branchName
git add *
git push

^^ Current Plan (Loose) ^^

- Sam calculated average position (serparation) and might continue work on that
- Ermias will work on average direction (cohesion)