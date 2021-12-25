# Craftyly

Craftyly is an app made to be a personalized space for artists and their ideas.

## Description
The main features include the ability to save prompts from a prompt generator and a notes section.
As well, the user can choose from a list of colour themes to customize their interface.
The app uses Firebase Firestore and Authentication SDK as a backend.
Note: if the app is frozen on the splash screen, try reinstalling.

**Splash:**
Detects if the user is logged in, and sends user to sign-in screen if not logged in.

**Sign-in:**
Contains a google sign-in button and an anonymous sign-in button. 

**Light bulb message dialog:**
If it is the user's first time using the app each day, a supportive message shows up
 as a dialog to remind users to take care of themselves.

**Home/Prompt generator:**
Includes a stack of cards that displays all prompts. Prompts come from a collection existing in
 the Firestore database. Prompts displayed can be filtered by category (eg. characters, environments).
  Users can swipe right to save the prompt as "accepted". Swipe left to save the prompt as "rejected". 
  A full prompt history is displayed underneath the card stack.

**Notes:**
Users can view their accepted and rejected prompts here. They can also create their own quick notes. 
Swiping left will allow the user to delete items (both for prompts and for notes).

**Settings:**
Users can set a colour scheme for their app interface. This also changes 
the design of the lightbulbs shown throughout the app.
A shop is set up with information about subscription plans, however purchasing anything 
currently is not implemented.
The about section displays information about our journey and acknowledges those we worked with.
Lastly, clicking sign out will sign out the user and bring them back to the sign-in screen.
 

## Acknowledgements

Team Craftyly: Michelle Xu and Tiffany Trinh
Mentor: Skye Kim
Prototype testers: Aileen K (UX/UI Designer), Nayun K. (UI Designer), Yeojin (Registered Nurse)

April 2021 - Submitted for the Technovation competition.
July 2021 - Revisited and added more in consideration of an actual release of the app.
