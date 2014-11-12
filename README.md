litupandroid
============

An Android game similar to wackamole, but with a 5 by 5 grids and dots. The entire project has been detailed out below, from start to finish.

__Author__: _Karanbir Randhawa_ (via karanbirrandhawa and smogi accounts).

![Main Menu](screenshots/Screenshot_menu)
![Game](screenshots/Screenshot_game)
![Game Menu](screenshots/Screenshot_menu)
![Main Menu](screenshots/Screenshot_menu)

# About the Game

The game was a great exercise in polishing my knowledge of the Android and a pretty fun way to spend some time. Originally it was to be a small hackathon project but since I didn't end up going I decided to dive into it at home on my spare time and have some fun with it. When I first started the project I had several features in my head that I knew I wanted done:

1. A Game Screen that consisted of a field of dots that would light up one by one until no dots remained or the time limit ran out.
2. To have the dots light up at changing speeds. I.e. 10 seconds in the dots start to light up faster, even faster 30 seconds in, etc.
3. The ability to pause, restart and quit the game.
4. The ability to record  the top ten scores ever  achieved on the phone.

At first I thought it'd be way too easy. These were some pretty small features and I was confident that I knew enough Java to code each of them. To top it off, I'd made an Android app before - a flash card app that showed the user a set of cards from a static library. It didn't have the finesse of an Android game but it had things like password protection for certain cards, crisp animations between cards that were flipped, etc. It turned out though that developing an Android game is far different from making an ordinary Android app.

# The Anatomy of an Android Game

The first time I really got stuck during this project was after I'd finished creating the main menu and different activities for each screen. I realized I wasn't really sure how exactly the structure of a game really worked. Where would I place my logic? Would it be possible, or even smart, to run the game thread seperately from the Activity/UI thread? What were the parts of the Android SDK that would help me do so? I did some research and thought about, ultimately deciding that there were to be two main components of the "game" part of my application.

* _The Game Loop_ or the central controller with which my logic would be centered in. It would run seperately from the UI thread through the support of `CountDownTimer` and `Runnable`. When the time came to interact with the UI, it would use Android's `runOnUiThread`.
* _The Activities_ or the frontend component. The user interacts with this area. For example, the tapping gesture that you would make on a dot would be including with this. Ideally the game loop would interact with this through `runOnUiThread` as specified above.

## The Game 

My initial definition for the game loop was pretty simple for this game since there were very few objectives. 

1. We will check to see if any dots are available for lighting up. If not, game will finish. 
2. A random dot on the field will light up.
3. The user will click on any dots and they will light down back to normal. 

Step three I was uncertain of how to approach because the more that I thought about it the more I realized that it wouldn't really be part of the loop per se but rather run alongside the loop. The loop would continue to occur and the user would tap the dots, which would toggle them off somehow. The logic for how was specified in the central game controller.

### GameController.java

As specified above there were several requirements for the game when starting. To reiterate:

1. Dots will light up randomly. 
2. The speed at which the dots will light up will change
3. Once there are no dots left or the counter has finished, the game is done. 

With that knowledge in mind, I tackled each issue one by one.

#### Lighting Up the Dots

The dots were all stored in a boolean array of length 25. The entry would start off as false if the dot was turned off and become true as it was lit up. The process to a dot lighting up would thus be:

* Search array to make sure there is at least one false entry.  Meaning at least one dot is not lit. If not, the game is finished.
* Randomly pick from these entries. 
* Light up the entry and turn its index to true. 

This entire task was stored into the `run()` method of a `Runnable` object created on the GameController's instantiation.

#### The Speed at Which Dots Were Lit

In order to deal with the situation of the speeds I created an inner class within `GameController` called `GameCountDownTimer` that extended Android's `CountDownTimer` that would end the game if the counter had worn off and run the light up runnable to light up random dots. 

`GameCountDownTimer` followed the same logic as `CountDownTimer`, that is when it would be initialized with a time span to run for and an interval to execute the task at. For example if you gave it a time span of 15 seconds and gave it an interval of 5 seconds it would run three times as shown below:

00:15 - Timer is created
00:10 - Task Runs 
00:05 - Task Runs 
00:00 - Task Runs. Timer is finished. 

Using that logic I set up a method `setTimerDetails()` that would take the current number of seconds passed and give it a certain time span and interval accordingly. For example, for the first 10 that had passed it would tell the loop to execute the light up dots runnable every 2 seconds. After that it would execute the runnable every 1 second for a certain amount of time. And so on. 

#### Ending the Game

As the majority of the logic was done by this point the final step was fairly easy. By having a second `CountDownTimer` object that would decrement a counter every second. For example, the counter would start at 60 and every second it would decrease by one becoming 59... 58... and so on. Once it reached zero a method known as `endGame()` would be called (described further below).

I also created three methods, `pauseGame()`, `resumeGame()` and `endGame()` which would be called during each of their respective game stages. Pausing the game would pop up a small menu and freeze the timer, storing the current value that it was at. Resuming the game would restart the timer but with the appropriate values that it left off at so the speed would not jump, slow and the time would be consistent. Ending the game would call `pauseGame()` and also create the ending game screen, showing the user's score and adding it to the high scores if it was high enough.

## User Logic

The user logic revolved mainly around setting an `onClickListener` to each of the buttons. The buttons would change in color whenever specified by the game loop. I realized quickly that the easiest way to deal with the view was to extend the view to get my custom dot shape. I called this class `DotView.java` and its method `toggleLight()` would be called whenever the onClickListener was called.  There were three main Fragments contained withing the main GameActivity:

1. `GameFragment` which contained the field of Dots as well as attached each of the listeners. 
2. `PauseGameFragment` which would be called whenever the user pressed to halt the game. It popped up a menu contained the option to restart or quit the game as well.  
3. `CompleteGameFragment` which would pop up once the game was completed. It would tell the user what their score was and give them an option to reply. A

### HighScoreManager.java

The `CompleteGameFragment` would get an instance of `HighScoreManager`, a class that would grab the top ten scores as an integer array, and add the new score to the array, if it was high enough. My method to storing scores was the `SharedPreferences` class, a quick and easy solution. I created a SharedPreference file called `highScores` and assigned a value to a key that would represent the position for the score. I.e. for a score that was third place, it would check the list of scores, update the key `score3` and shift every entry lower than third place down. The last entry would be removed from the list.

# The End

Android is a pretty neat platform and surprisingly easy to get a hang off. Game development is interesting and maybe if I had time I would add some more interesting elements to the game. Something along the lines of having and using items that would do things like freeze the clock or temporarily slow it down. Maybe even a bomb to take out a whole bunch of the dots in one area. It was a neat little side project though even if I don't take it any farther.
