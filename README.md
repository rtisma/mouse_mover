# mouse_mover


Simply `mvn clean package` from the root directory, 
and then run the `java -jar ./target/mouse_mover-1.0-SNAPSHOT-jar-with-dependencies.jar <durationSeconds>  <delayMs>  <resolution>  <exitOnCompletion>`. 
This will then move your mouse in a circle.
The exit criteria is when you move your mouse. For Windows users, after ensuring java 6+ is installed, copy the jar file to the desktop and create a `run.bat` file with the following text `java -jar mouse_mover-1.0-SNAPSHOT-jar-with-dependencies.jar -1 1000 0.3 false`, then simply double-click the .bat file to start the program.

## Parameters
1. DurationSeconds
This is the duration the program will move the mouse in seconds. When the value is < 0, it will run **infinitely**.

2. DelayMs
This is the delay between each mouse movement. A good value is 1000 ms (1 second).

3. Resolution
This decimal number represents the number of radians to move between each mouse movement. A good value is 0.3.

4. ExitOnCompletion
This is a boolean value (true/false) if true, once the program is done (either by timeout or by user mouse movement), the process running the program will end. If false, the process will idle even when the program is done.
