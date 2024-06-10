Storify is an android app to share and view stories, equipped by a location feature to see where each stories are located. 
The features are:

1. Authentication Page
- Login page (email and password)
- Registration page (name, email, password).
- Password is hidden.
- Custom View in the form of EditText for the password. If the password is less than 8 characters, displays an error message directly in EditText without having to move the form or click a button first.
- Store session data and tokens in preferences to manage this flow : If user have logged in, go straight to the main page. If not, user will go to the login page.
- Log out feature

2. Story List
- Displays a list of stories from the API using Paging 3. The list includes username, photo, and description (short version).
- A detailed view appears when one of the story items is pressed, which includes the username, photo, and the full description of the story.
- Displays the list of stories using Paging 3

3. Add Story
- "Add story" button is on the story list page. When clicked, the add story page appears.
- The add story page has these inputs: photo file, story description, location. When clicking on the location, it will lead to the "add location" page where a map appears and there are 2 buttons: "current location" and "marked location".
- "Share" button to upload the story. After the button is clicked and the upload process is successful, it will return to the story list page.

4. Animations
- The animations are on the login, register, error, and successful page.

5. Map
- "View Map" button in the story list page to display the map page.
- The map page shows a map that has all of the stories that have a location.

Demo Video: https://drive.google.com/file/d/1-rBWmclccVVOL3RAP3Pdy4VXJsqb652i/view?usp=sharing
