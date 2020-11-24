#

This is a mobile application that was developed for Android platforms using Java. This was a school assignment and the requirements for the project can be found below.

--------------------------------------------------------------INTRODUCTION------------------------------------------------------------

As a competent mobile application developer, your understanding of mobile application structure and design will help you to develop applications to meet customer requirements. The following project to develop a student scheduler/student progress tracking application, will help you to apply these skills in a familiar, real-world scenario. This task will allow you to demonstrate your ability to apply the skills learned in the course.

You will develop a multiple-screen mobile application for WGU students to track their terms, courses associated with each term, and assessment(s) associated with each course. The application will allow students to enter, edit, and delete term, course, and assessment data. It should provide summary and detailed views of courses for each term and provide alerts for upcoming performance and objective assessments. This application will use a SQLite database.

---------------------------REQUIRED-INPUT-------------------------------

Each of the following needs to be input into the application:
  • terms, including the following:
     the term title (e.g., Term 1, Term 2)
     the start date
     the end date
  • courses, including the following:
     the course title
     the start date
     the anticipated end date
     the status (e.g., in progress, completed, dropped, plan to take)
     the course mentor name(s), phone number(s), and email address(es)
     objective and performance assessments associated with each course
  • the ability to add optional notes for each course
  • the ability to set alerts or notifications for the start and end date of each course
  • the ability to set alerts for goal dates for each objective and performance assessment

---------------------------REQUIRED-OUTPUT-------------------------------

Each of the following needs to be displayed by the application on a separate screen:
  • the navigation panel
  • a list of terms
  • a detailed view of each term, including the following:
     the title (e.g., Term 1, Term 2)
     the start date
     the end date
  • a list of courses for each term
  • a detailed view of each course, including the following:
     the course title
     the start date
     the anticipated end date
     assessments
  • a list of performance and objective assessments for a selected course
  • a detailed view of each objective and performance assessment, including the following:
     the due date for a selected course
     notes for the selected course
     sharing features (e.g., email, SMS)
 
 ------------------------------------------------------------------------REQUIREMENTS---------------------------------------------------------------------------
 
 A. Create an Android (version 4.0 or higher) mobile application with the following functional requirements:
  Include the following information for each term:
  • the term title (e.g., Term 1, Term 2, Spring Term)
  • the start date
  • the end date
  Include features that allow the user to add as many terms as needed.
  Implement validation so that a term cannot be deleted if courses are assigned to it.
  Include features that allow the user to do the following for each term:
    a. add as many courses as needed
    b. display a list of courses associated with each term
    c. display a detailed view of each term, including the following information:
    • the term title (e.g., Term 1, Term 2, Spring Term)
    • the start date
    • the end date
  Include the following details for each course:
    • the course title
    • the start date
    • the anticipated end date
    • the status (in progress, completed, dropped, plan to take)
    • the course mentor names, phone numbers, and e-mail addresses
  Include features that allow the user to do the following for each course:
    a. add as many assessments as needed
    b. add a minimum of one optional note per course
    c. enter, edit, and delete course information
    d. display optional notes
    e. display a detailed view of the course, including the due date
    f. set alerts for the start and end date, that will trigger when the application is not running
    g. share notes via a sharing feature (either e-mail or SMS)
  Include features that allow the user to do the following for each assessment:
    a. add performance and objective assessments for each course, including the titles and due dates of the assessments
    b. enter, edit, and delete assessment information
    c. set alerts for goal dates, that will trigger when the application is not running
B. Design the following screen layouts, including appropriate GUI (graphical user interface) elements (e.g., navigation, input, and information) for each layout:
  • home screen
  • list of terms
  • list of courses
  • list of assessments
  • detailed course view
  • detailed term view
  • detailed assessment view
C. Create a scheduler and progress tracking application as part of your mobile application from part A.
Note: This can be the implementation of part A.
Include the following implementation requirements in the application from part C. Be sure your application is a minimum version 4.0 mobile application:
  • an ArrayList
  • an intent
  • navigation capability between multiple screens using activity
  • three activities
  • events (e.g., a click event)
  • the ability to work in portrait and landscape layout
  • interactive capability (e.g., the ability to accept and act upon user input)
  • a database to store and retrieve application data
  • an application title and an icon
  • notifications or alerts
  • the use of both declarative and programmatic methods to create a user interface
  Include the following interface requirements in the application from part C:
  • the ability to scroll vertically
  • an action bar
  • two layouts
  • input controls
  • buttons




