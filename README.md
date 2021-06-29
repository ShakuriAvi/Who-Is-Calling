# Who-Is-Calling
Implemented "ME" app for android platform, with firebase, Animation, notifications, services, etc.
The application works as follows: When a user installs the application, he receives a request for access to his contacts,
in order to take the contacts and save them in a database (i using in Firebase).
In the first time that user connection to my App, The user enters his name and numbers and after he confirm the permmision to take his contacts,
each contact add to database with the name of user.
Eventually a kind of network is created that each user gives information and receives information back.
There are different types of options in the app that I created:
  <summary> When a number calls you, you are notified of the number and its name.</summary>
  <summary> You can search for a specific cell phone number and know its name.</summary>
  <summary> You can see what people are calling you on their phone.</summary>
</br>
<!-- <img src="https://user-images.githubusercontent.com/65177459/108538582-a1a6b100-72e7-11eb-98ce-c2ab55f74157.jpg" width="350" height="500"> -->

When the application is opened for the first time, a username and the user's cell phone number must be added.
If the user has already entered his details, the application opens a page with the application icon in the center. 
The icon moves with the help of animation which shakes and moves the icon in a circular rotation.

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

When the user is on the names page, he is presented with all the names he is called by in the contacts in the database

<img src="https://user-images.githubusercontent.com/65177459/123824297-95220d00-d906-11eb-9413-3e2643373ec3.png" width="350" height="500">

# click on show Button:

The user can check who contacts that called him in the different names, by click on the show button.

<img src="https://user-images.githubusercontent.com/65177459/123828263-00211300-d90a-11eb-8945-0b35c1b8f71b.png" width="350" height="500">

