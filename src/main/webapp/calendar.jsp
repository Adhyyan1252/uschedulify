<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script>document.getElementsByTagName("html")[0].className += " js";</script>
  <link rel="stylesheet" href="css/calendarCSS.css">
  <title>Schedule Template | CodyHouse</title>
</head>
<body>
  <header class="cd-main-header text-center flex flex-column flex-center">
    <h1 class="text-xl">USChedulify| SChedule</h1>
  </header>
<script>
	window.onload = function showMap(){
		var ID  = "<%= request.getParameter("ID") %>";
		var xhttp = new XMLHttpRequest();
		
		xhttp.open("GET", "CalendarServlet?ID="+ID, false);
		xhttp.send();
		console.log(xhttp.responseText);
		var sched = JSON.parse(xhttp.responseText);
		console.log(sched);
		readSchedule(sched);
		
	}
	
	function readSchedule(schedule){
		const sections = schedule["sections"];
		for( var i = 0; i<sections.length; i++){
			const className = sections[i]["classname"];
			const majorName = sections[i]["majorname"];
			const timings = sections[i]["timing"];
			console.log(className);
			for( var j = 0;timings!=null&&j<timings.length; j++){
				console.log("hello");
				const start = timings[j]["start"];
				const end = timings[j]["end"];
				var tempLI = document.createElement("LI");
				var newItem = document.createElement("a");
				var newEM = document.createElement("em");
				newEM.setAttribute("class" , "cd-schedule__name");
				newItem.setAttribute("data-start", start["hour"]+":" +start["min"]);
				newItem.setAttribute("data-end", end["hour"]+":" +end["min"]);
				newItem.setAttribute("data-event", "event-1");
				newItem.setAttribute("href", "#0");
				tempLI.setAttribute("class", "cd-schedule__event");
				newEM.innerHTML = majorName + className;
				newItem.appendChild(newEM);
				tempLI.appendChild(newItem);
				document.getElementById("ulday"+start["day"]).appendChild(tempLI);
				console.log(tempLI);
			}
			
		}
		
	}
	
</script>

  <div class="cd-schedule cd-schedule--loading margin-top-lg margin-bottom-lg js-cd-schedule">
    <div class="cd-schedule__timeline">
      <ul>
        <li><span>09:00</span></li>
        <li><span>09:30</span></li>
        <li><span>10:00</span></li>
        <li><span>10:30</span></li>
        <li><span>11:00</span></li>
        <li><span>11:30</span></li>
        <li><span>12:00</span></li>
        <li><span>12:30</span></li>
        <li><span>13:00</span></li>
        <li><span>13:30</span></li>
        <li><span>14:00</span></li>
        <li><span>14:30</span></li>
        <li><span>15:00</span></li>
        <li><span>15:30</span></li>
        <li><span>16:00</span></li>
        <li><span>16:30</span></li>
        <li><span>17:00</span></li>
        <li><span>17:30</span></li>
        <li><span>18:00</span></li>
      </ul>
    </div> <!-- .cd-schedule__timeline -->
  
    <div class="cd-schedule__events">
    <ul ></ul>
      <ul>
        <li class="cd-schedule__group">
          <div class="cd-schedule__top-info" id = "day0"><span>Monday</span></div>
          <ul id = "ulday0"> </ul>
        </li>
        <li class="cd-schedule__group">
           <div class="cd-schedule__top-info" id = "day1"><span>Tuesday</span></div>
           	<ul id = "ulday1"></ul>
        </li>
        <li class="cd-schedule__group">
           <div class="cd-schedule__top-info" id = "day2"><span>Wednesday</span></div>
           <ul id = "ulday2"></ul>
        </li>
        <li class="cd-schedule__group">
           <div class="cd-schedule__top-info" id = "day3"><span>Thursday</span></div>
           <ul id = "ulday3"></ul>
        </li>
        <li class="cd-schedule__group">
           <div class="cd-schedule__top-info" id = "day4"><span>Friday</span></div>
           <ul id = "ulday4"></ul>
        </li>
      </ul>
    </div>
  
    <div class="cd-schedule-modal">
      <header class="cd-schedule-modal__header">
        <div class="cd-schedule-modal__content">
          <span class="cd-schedule-modal__date"></span>
          <h3 class="cd-schedule-modal__name"></h3>
        </div>
  
        <div class="cd-schedule-modal__header-bg"></div>
      </header>
  
      <div class="cd-schedule-modal__body">
        <div class="cd-schedule-modal__event-info"></div>
        <div class="cd-schedule-modal__body-bg"></div>
      </div>
  
      <a href="#0" class="cd-schedule-modal__close text-replace">Close</a>
    </div>
  
    <div class="cd-schedule__cover-layer"></div>
  </div> <!-- .cd-schedule -->

  <script src="javascript/util.js"></script> <!-- util functions included in the CodyHouse framework -->
  <script src="javascript/main.js"></script>
</body>
</html>