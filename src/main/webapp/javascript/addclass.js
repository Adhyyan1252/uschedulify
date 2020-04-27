var coursesraw = "";

function addClass() {
	var item = document.getElementById("addClassInput").value
	if(!validate(item)) {
		return
	}
	var text = document.createTextNode(item)
	var newItem = document.createElement("li")
	coursesraw = coursesraw + item + ",";
	newItem.appendChild(text)
	document.getElementById("addedClasses").appendChild(newItem)
}

function validate(item) {
	if(!item) {
		alert("Please input a class name.")
		return false
	}
	else {
		return true
	}
	
}

function deleteMap(index){
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET","DeleteServlet?ID=" + index, false);
	xhttp.send();
	window.location.href = "home.jsp";
}


function loadSavedCourses() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET","SavedServlet", false);
	xhttp.send();
	var arr = xhttp.responseText.trim().split("\n"); 
	if (xhttp.responseText.trim() == "") {
		document.getElementById("savedsch").innerHTML = "<h1>no saved schedules</h1>";
	}
	else {
		for(var i = 0; i < arr.length; i++){
			var newItem = document.createElement("BUTTON");
			newItem.setAttribute("type", "button");
			newItem.setAttribute("class", "button");
			newItem.setAttribute("style", "width:250px");
			newItem.setAttribute("onclick", "showMap('" + arr[i] + "', 'true')");
			newItem.innerHTML = arr[i];
			var removeItem = document.createElement("BUTTON");
			removeItem.setAttribute("type", "button");
			removeItem.setAttribute("class", "button");
			removeItem.setAttribute("style", "width:250px");
			removeItem.setAttribute("onclick", "deleteMap('" + arr[i] + "')");
			removeItem.innerHTML = "remove";
			var newDiv = document.createElement("DIV");
			newDiv.setAttribute("class", "newdiv");
			newDiv.appendChild(newItem);
			newDiv.appendChild(removeItem);
			document.getElementsByClassName("saved-sch")[0].appendChild(newDiv);		
		}
	}
	return false;
}

function genCourses() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET","ScheduleRequest?coursesraw=" + coursesraw,false);
	xhttp.send();
	
	var arr = xhttp.responseText.trim().split("\n"); 
	
	if (xhttp.responseText.trim() != "") {
		for(var i = 0; i < arr.length; i++){
			var newItem = document.createElement("BUTTON");
			newItem.setAttribute("type", "button");
			newItem.setAttribute("class", "button");
			newItem.setAttribute("style", "width:250px");
			newItem.setAttribute("onclick", "showMap('" + arr[i] + "', 'false')");
			newItem.innerHTML = arr[i];
			document.getElementsByClassName("g-sch")[0].appendChild(newItem);
			
		}
	} else {
		alert("No classes could be generated.");
	}
	return false;
}

function showMap( Index, param){
	window.open("calendar.jsp?ID="+Index + "&saved=" + param);
}

