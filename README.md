# Friends' Events

This is a Facebook app that lists the events your friends have been invited to. Before FB Graph API 2.0, one could pull in much more information from friends' profiles than is possible with new apps. This app is built around an FQL call that's, paraphrased, SELECT * FROM events WHERE eid IN (SELECT eid FROM event_member WHERE start_time > today AND uid IN friends). The changes to the API mean that from April 2015 only events from others using the app will appear.

It is written with GWT and is entirely client-side. 

The app is live at: [apps.facebook.com/SortonsEvents/](https://apps.facebook.com/SortonsEvents/)

To run it locally:

```
git clone https://github.com/BrianHenryIE/FriendsEvents.git
cd FriendsEvents
mvn gwt:run
```

You'll need to point dev.sortons.ie to 127.0.0.1 in your hosts file because the FB app id is tied to particular domains.   

** Brian: fix dependencies on your other GitHub projects 