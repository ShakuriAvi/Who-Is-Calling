# Who-Is-Calling
Implemented "ME" app for android platform, with firebase, Animation, notifications, services, Permissions etc.</br>
The application works as follows:</br>
When a user installs the application and open in first time, he enters his name and numbers.</br>
Thereafter The user receives a request for access to his contacts (the request is permission for read his contacts from phone),</br>
in order to take the contacts and save them in a database (I used in Firebase).
After he confirm the permmision to take his contacts,</br>
each contact from user's phone add to database with the name of user.</br>
Eventually a kind of network is created that each user gives information and receives information back.</br></br>
There are different types of options in the app:
  <summary> When the user receives a phone call, he is notified of the person who called him, by receiving information from the database</br> By the service, we can receive notifications even when the app is closed.</summary>
  <summary> You can search for a specific cell phone number and get the owner name.</summary>
  <summary> You can see how people are call you on their phone.</summary>
</br>
<!-- <img src="https://user-images.githubusercontent.com/65177459/108538582-a1a6b100-72e7-11eb-98ce-c2ab55f74157.jpg" width="350" height="500"> -->

# Register Page: 

When the application is opened for the first time, a username and the user's cell phone number must be added.
If the user has already entered his details, the application opens a page with the application icon in the center. 
The icon moves with the help of animation which shakes and moves the icon in a circular rotation.


<img src="https://user-images.githubusercontent.com/65177459/123913911-73b53580-d987-11eb-908f-273a59e0c60d.png" width="350" height="500">

# Search Page: 

When the application opens after registration page or after loading the start page,
the search page screen opens and the user can search by cell phone number for the name of the contact with the number he wrote.
The user can switch between this page to the "Names" page using the bar below.

<img src="https://user-images.githubusercontent.com/65177459/123824875-15487280-d907-11eb-9365-1f4107ca7433.png" width="350" height="500">

# Contact Not found

When a required cell phone number does not appear in the database, a message is displayed that the contact has not been found.

<img src="https://user-images.githubusercontent.com/65177459/123824300-96ebd080-d906-11eb-97ed-e32b186cab2d.png" width="350" height="500">

# Contact found

The message displayed when the contact is in the database.

<img src="https://user-images.githubusercontent.com/65177459/123824310-98b59400-d906-11eb-962e-d0e94b00a1e7.png" width="350" height="500">

# Names Page:

When the user is on the names page, he is presented with all the names he is called by in the contacts in the database.

<img src="https://user-images.githubusercontent.com/65177459/123824297-95220d00-d906-11eb-9413-3e2643373ec3.png" width="350" height="500">

# click on show Button:

The user can check who contacts that called him in the different names, by click on the show button.

<img src="https://user-images.githubusercontent.com/65177459/123828263-00211300-d90a-11eb-8945-0b35c1b8f71b.png" width="350" height="500">

# Notification

The user receives two notification, first notification that the app run in the background phone and a second notification display who called him.

<img src="https://user-images.githubusercontent.com/65177459/123913889-6c8e2780-d987-11eb-8f56-ed60d6782177.png" width="350" height="500">
