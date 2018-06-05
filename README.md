# Mayterm 2018: Monstercat visualizer 
Back in middle school, I spent a lot of my time listening to electronic music (mostly dubstep) while working on projects. There was this particular channel on YouTube called [Monstercat][1] that I really liked, partially because they produced good music at the time, but also their iconic [spectrum visualizer][2] which was used in most of their videos.

So, for my mayterm project this year, I decided to recreate that visualizer in Java.

It should be noted that there is still a lot missing from the visualizer (this entire project is still a work in progress), such as correct spectrum movement, the dynamic background, and other smaller animations, but it does the core of what I wanted it to do: Read and play back music, change color based on set genre, display the title, artist, and cover art of the track, and even use a spectrum visualizer. Again, I realize that the movement is not right compared to that of the original visualizer, but I am working on fixing that by using an [FFT algorithm][3] instead of the built in JavaFX algorithm.

In the meantime, you can [download and run the visualizer yourself][4]!

You will need a JRE (**J**ava **R**untime **E**nvironment) as a prerequisite though, but I’ll leave a link to that download page.

**Notice for Windows 10 users** *(And potentially other windows users)*: There are currently known issues with the visualizer, such as its extremely slow, and there's a problem with the database, so genres will not be stored as a result. Know that I am working on resolving these issues, so stay tuned!

## Downloads
[The application itself][4]

and...

### JREs

|   macOS   |  Windows  |   Linux   |
|:---------:|:---------:|:---------:|
| [64bit][5] | [64bit][6] | [64bit][7] |
|    ---    | [32bit][8] | [32bit][9] |



## Controls
List of current controls:
* `O` - Opens a file
* `P` - Pause / unpause a track
* `ESC` - Quits the application


The rest of these commands have to be executed while a track is playing:
* `Up arrow` - Increase volume
* `Down arrow` - Decrease volume
* `Right arrow` - Skip the current track
* `.` (period) or `,` (comma) - Rotate between genres



## Things to come

There are a few things to expect in the very near future, such as:
* Database fix for windows (sorry windows users)
* Accurate spectrum movement that more closely mimics that of the original Monstercat one
* Settings and Database management page
* The ability to restart tracks (and more track control in general)

Once those are done however, I’ll see about working on other things with this project, such as more animations, better screen optimization, and even more visualizer options? *Cough cough wink wink*



## Credits and thanks

This project is largely inspired by [vis.js][10], a similar project done by Max Roncace (@caseif). His [JS project][10] was a **huge** inspiration for everything in this visualizer, and I cannot give enough props to him. Be sure to [go check out what other projects he has done][11]!


[1]: https://www.youtube.com/user/MonstercatMedia/ "Here’s the channel"
[2]: https://www.youtube.com/watch?v=RhzJGZ5lzgM "Here’s an example of what I mean"
[3]: https://en.wikipedia.org/wiki/Fast_Fourier_transform
[4]: https://github.com/jeffrypig23/Mayterm/blob/master/Release.jar?raw=true "Download"
[5]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[6]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[7]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[8]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[9]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[10]: https://github.com/caseif/vis.js "Monstercat visualizer in JavaScript and three.js."
[11]: https://github.com/caseif
