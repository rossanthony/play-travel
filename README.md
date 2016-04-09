## Play Travel

### An On-Line Travel Reservation System

Coursework submission for Birkbeck MSc module, [Component Based Software Development](https://www.dcs.bbk.ac.uk/study-with-us/modules/component-based-software-development/).

#### Backend Stack
- [Play Framework](https://www.playframework.com/) 2.4
- [Slick plugin for Play](https://github.com/playframework/play-slick) 1.0.1 (Slick ver 3.0.1)
- [Silhouette JWT Authentication](http://silhouette.mohiva.com/docs/config-authenticators#jwtauthenticator)

#### Build Tools 
- [Scala build tool (SBT)](http://www.scala-sbt.org/index.html)
- [Gulp](http://gulpjs.com/)

#### Frontend
- [AngularJS](https://angularjs.org/)
- [Material Design Data Table](https://github.com/daniel-nagy/md-data-table)

#### Progress

Unfortunately despite having devoted around two solid weeks to this project (spread out over several months) I have been unable to meet all of the requirements. Being relatively new to Scala and completely new to Play + Slick, I found it particular challenging to get all of the required elements of the coursework completed. The parts I have managed to get working are as follows:

- User sign-up and login (for online travellers)
- Login for admin users (reservation managers)
- Dashboard for reservation managers; with list view of flights, ability to delete + create flights
- Flight searching with some of the required filters in place

#### Things outstanding

- Frontend booking of flights and viewing of previous bookings. Models for bookings and tickets are in place, however I did not have time to complete the frontend part for this.
- Admin screens for viewing bookings, showing how many seats are sold per flight etc.
- Write unit tests

#### Particular challenges with Slick

It has been a difficult and somewhat frustrating experience at times dealing with Slick and its API for building SQL queries. Dealing with joins and filtering I found particularly troublesome at first and the documentation on the Slick website is a bit sketchy in parts. 

I ended up getting help on Stackoverflow for a few issues:
 
- [Mapping a sequence of results from Slick monadic join to Json](http://stackoverflow.com/questions/36391027/mapping-a-sequence-of-results-from-slick-monadic-join-to-json)
- [Dynamic query with optional where clauses using Slick 3](http://stackoverflow.com/questions/36246763/dynamic-query-with-optional-where-clauses-using-slick-3)
- [Scala Slick groupBy without aggregation](http://stackoverflow.com/questions/36520451/scala-slick-groupby-without-aggregation)

Resolving these issues among others, caused significant setbacks in terms of the timescale.   

#### Conclusion / outcomes

While it is disappointing to have fallen short in getting everything in the spec done on time, nevertheless I feel it has been a rewarding and beneficial experience. It has certainly sparked a keen interest in the potential of Play + Slick and I plan to continue with this build in my own time. While it may have been easier and more feasible to get all (or closer to all) of the requirements met if using JEE (e.g Spring Roo) instead, however I set myself the challenge of attempting it with Scala, Play + Slick and persisted with this goal, setbacks aside. 

It was only when I had already gone too far down this road that it became increasingly evident that I was running behind schedule and would likely be unable to complete all parts of the spec. So, while in some respects I am the victim of the age-old [sunk costs fallacy](http://www.lifehack.org/articles/communication/how-the-sunk-cost-fallacy-makes-you-act-stupid.html), I still standby my decision for the reasons already mentioned. I hope that those examining this coursework will take these points on board when considering the final mark.
