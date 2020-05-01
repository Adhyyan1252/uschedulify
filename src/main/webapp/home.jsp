<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SChedulify | Home </title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/home.css">
        <script src="javascript/addclass.js"></script>
         <script>
    	function loadSched(){
	    		loadSavedCourses();
   		}
		</script>      
    </head>
    <body onload = "loadSched()">
        <header>
            <h1> <span class="highlight">sc</span>hedulify - <span class="highlight">hello</span> ${userName}!</h1>
        </header>
        <section>
            <div class="home-container">
                <div class="saved-sch" id = "savedsch">
                    <h1>saved schedules</h1>
                </div>
                <div class = "g-sch"><h1>generated schedules</h1> </div>
                <div class="create-sch">
                    <h1>create a schedule</h1>
                    <div class="add-a-class">
                        <h3>add a class</h3>
                        <form id="addClassForm">
                            <input id="addClassInput" type="text" placeholder="class name"><br>
                            <button type="button" onclick="addClass()">add class</button>
                        </form>
                    </div>
                    <ol id="addedClasses">
                    </ol>
                    <script src="javascript/addclass.js"></script>
                    <div class="gen-sch" >
                        <form method="POST" name="generate-schedule" onsubmit="return genCourses()">
                            <button type = "submit">generate schedule</button>
                            <button type = "button" onclick = "deleteClasses()">clear classes</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>