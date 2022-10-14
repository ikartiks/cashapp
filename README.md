# Cash app demo

Hi !, I am Kartik. here are a a few things I would like you to know before reviewing the project

#### Architectural approach
The app uses MVVM with jetpack coroutines and is modularised at architectural level.The project is modularised into 2 layers - api and feature or app layer.

#### Tradeoffs
I have based this project on top of my existing repo, to save setup time and trouble. The base repo is a project maintened by me solely over the years and I can share its codebase if required. (If i had to start from scratch i would not do all this but i already had a good solution in place so just thought of using it rather than going through the trouble of writing from scratch)

I was also told by recruiter that a dark mode is a plus, however I have not implemented it as I would urge you to just download my personal app from playstore which has it already. There are many things I would have done differently, eg logging, caching responses in api etc had this been a real project. ( Real reason - I wanted to get this done asap as I did not want to spoil my weekend plans :smile:	 )

## ! [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
Just clone the repo from github into android studio and you should be good to go :smile:

You can mock, all the three api responses - failure, success and empty state from settings by clicking on nav icon. :smirk:

#### Other
Unit tests are added as required

I have also recently worked on Jetpack Compose and could have done this project using it. 

## Tech
- [Kotin] - base code
- [Coroutines] - anything async
- [Retrofit] -network layers
- [Lotties] - great UI animations for modern  apps