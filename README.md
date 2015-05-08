# Bluetooth_App2
CSE 2102(Kyle Heitman, Jianxing Ke, Arif Prabawa, Christopher Cyriac)This is the second repository for the application because the first one's HEAD file got corrupted and we were unable to fix it. 

##User Profiles for Commits
-Requiem3(Kyle Heitman)

-Kjr21362(Jianxing Ke)

##Summary of Application
The main idea behind the application was to create an app that would use Bluetooth meshnetworking to establish a chat room and let people connect and send messages to each other.

##Current Progress of Application
Currently the application allows two devices to connect via Bluetooth and send messages. We have most of the threading classes completed for expanding into multiple devices but we ran out of time before we could implement it. The framework is there, but it needs a little bit more work to get to a point where it can work.

##Directions to Run
Currently you can no longer run the entire application by using tests and a virtual device in Eclipse, it just became way too complicated and hard to maintain. You will need two Android phones and the app installed on both phones. Next follow these steps:

-Enable Bluetooth on both phones

-Start the app on both phones and click yes when it requests to enable discovery

-On one of the phones click scan to search for devices in the area with Bluetooth discoverability enabled.

-Click on the name of the phone that you want to connect to on the list that pops up. Wait about 4 seconds while it connects and a text label in the upper right should say "Connected2". If it says "cfs" then you hit a bug(described below in the bugs section).

-Once it is connected you can type the message you want to send on either phone into the text line and hit send.

##Bugs
- If you start the application without Bluetooth enabled it will crash

-Once you connect you cannot reconnect to the same device if you close the app. This is a massive bug but we ran out of time before we could fix it. The way our multithreading runs it does not account or check for whether a Bluetooth socket currently exists between the two devices. It just goes to create a new Bluetooth socket and then you cannot connect to the device.

-Does not work on some of the really cheap Android phones anymore after an API update. We all bought some really cheap $20 burner phones and it worked on them for awhile. Then LG did an API update on just their phones and it no longer works on specific ones, but should work on regular phones because it works on my Galaxy S4.

##How it Works
Both phones are running the same version of the application with the same UUID. While the phone is not connected to another phone it is acting as the server and listening for incoming socket connection attempts. When one of the phones scans for available devices and then attempts to connect to one it acts as the client. The client sends a request to the server which then accepts it if the UUID is the same. Once the connection is accepted it creates a socket which then both phones can communicate over. Once you have the socket created the rest is fairly easy. This whole process runs in its own thread and using a state machine to determine which part it is doing(nothing, listening, connecting, or managing a connection). The rest of the app runs in the main Android UI thread.

##Location of Important Things
- All diagrams/pictures are either located in this top section or in screenshots. We have all the UML/Sequence diagrams in here somewhere, either from our project or reference project.
- 
