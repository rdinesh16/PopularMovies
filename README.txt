*******************************************
Steps for Updating your TheMovieDB API KEY:
*******************************************

Please generate an API key for your ID in https://www.themoviedb.org/

Once you get your API key, update it in the Resources(Res) -> Values -> strings.xml file.
The file can also be found under <Project_basePath>\PopularMovies\app\src\main\res\values\strings.xml

Updating strings.xml :
---------------------- 
   Inside the strings.xml, the following entry will be present.

	<string name="API_KEY">PLEASE_PROVIDE_API_KEY</string>

   Please insert your API key replacing "PLEASE_PROVIDE_API_KEY" shown above. Dont put any spaces.

The app will show data on the UI once you put the correct API key. Else, an error message will be displayed.


