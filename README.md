# android myTweet, Java #


### What is this repository for? ###

* This repository is for an android application mimicking twitter. 
* The application has both a local database (SQLite), and is consuming the exposed api on the web myTweet (connected to mLabs)
* users can tweet, with images, viewable from the phone or on the web myTweet. 
* only images taken on the device are viewable.
* users can also follow other users and timeline is populated dependent on who the user is following. 
* a user can delete tweets locally, I didn't give users the capability to delete from online database.
* The application is written in java and xml.

### How do I get set up? ###

* The application can be started by running it on the emulator through android studio.
* Alternatively, the application can be installed on an android device and run on it.
* 
* To sign into the application a user must sign up to the application on the device. 
* Once signed up, a user is brought to the log in page, this user is now in the local database and can be used again.
* upon log in, the user is brought to the global timeline. they can add a tweet, go to the settings, or go to the list of users.
* the Tweet, when sent, is added through the consumed api, and is added to the mLabs db. 
* In the settings, the username and password setting are not setting anything. Toasts will show that it is being accessed.
* In the users list a user can be followed or unfollowed, this will effect what is seen on the timeline.