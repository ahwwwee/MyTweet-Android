# android myTweet, Java #


### What is this repository for? ###

* This repository is for an android application mimicking twitter. 
* The application has both a local database (SQLite), and is consuming the exposed api on the web myTweet (connected to mLabs)
* users can tweet, with images.
* users can also follow other users and timeline is populated dependent on who the user is following. 
* a user can delete tweets locally, I didn't give users the capability to delete from online database.
* The application is written in java and xml.
* There is an automatic refresh that runs in the background, set by a field in the user model, this can be edited on the settings page.

### How do I get set up? ###

* The application can be started by running it on the emulator through android studio.
* Alternatively, the application can be installed on an android device and run on it.
* 
* A user can sign up to the application, this information is sent up to the node api. 
* Once signed up, a user is brought to the log in page, this user is now in the local database and can be used again.
* Any user on the database can be signed in on the application, once it is online. 
* Once a user logs in this will be the user retained in the local database.
* upon log in, the user is brought to the global timeline. they can add a tweet, go to the settings, or go to the list of users.
* the Tweet, when sent, is added through the consumed api, and is added to the mLabs db. 
* In the settings, the username and password setting are not setting anything. Toasts will show that it is being accessed.
* In the users list a user can be followed or unfollowed, this will effect what is seen on the timeline.