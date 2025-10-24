# Activity 5: Reflection

<strong>How did you implement CRUD using SQLite?</strong>
<p>
  - I implemented CRUD (Create, Read, Update, Delete) operations using the 
  SQLiteOpenHelper class in Android. I created a custom helper class 
  NotesDbHelper that manages database creation and versioning. Each note’s 
  data — like title, content, and date, is stored in an SQLite table. Using 
  SQLiteDatabase, I wrote functions for inserting new notes, querying all notes,
  updating specific notes by ID, and deleting them.
</p>

<strong>What challenges did you face in maintaining data persistence?</strong>
<p>
  - One challenge was ensuring that data remained consistent after updates or 
deletions. Sometimes, changes weren’t immediately reflected in the UI, so I 
had to refresh the RecyclerView after each database operation. I also had to 
handle app restarts making sure that all saved notes reloaded correctly from 
the database.
</p>

<strong>How could you improve performance or UI design in future versions?</strong>
<p>
  - For performance, I could migrate from SQLite to Room Database, which offers 
  cleaner code, compile-time checks, and better lifecycle management. I could 
  also add background threads using Coroutines for database access to prevent 
  UI freezing. In terms of UI, I plan to enhance the design using Material Design 
  components, animations when adding or deleting notes.
</p>
