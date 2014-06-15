Fortune Cookie Service with Twilio
===============
A small twilio demo app to provide a fortune cookie like phone service. It
demonstrates how an app can use TwiML to direct caller's dial operation and how
to send SMS using the Twilio Java SDK.

What you need
==============
Sign up a twilio account. Get your account sid and auth token ready.

How to build
==============
After "git clone" the repository. You need to modify the "application.conf" file
under "src/main/resources" to match your twilio settings. Then build the app
using maven:

%mvn package

It will build the app as a war file. Just deploy the war file to any servlet
container such as TOMCAT or Jetty. (Many PaaS companies already provide services
to deploy your war app, you don't even have to set up your own servlet container.)

If you have set your application.conf right, the app is ready to rock and roll.

Twilio Setup
=============
Once you deploy your app and have a publicly available url. Go to your twilio
account and set the voice url to your app url. Any incoming call to your twilio
phone number will be wired to your app. Make a call to your twilio phone
number and be amazed.


