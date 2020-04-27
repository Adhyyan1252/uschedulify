// homepage js
var coursesraw = "";

function addClass() {
	var item = document.getElementById("addClassInput").value
	
	if(!validate(item)) {
		return
	}
	
	var text = document.createTextNode(item)
	var newItem = document.createElement("li")
	coursesraw = coursesraw + item + ",";
	alert(coursesraw);
	newItem.appendChild(text)
	document.getElementById("addedClasses").appendChild(newItem)
}

function validate(item) {
	
	if(!item) {
		alert("please input a class name")
		return false
	}
	else {
		return true
	}
	
}

function loadSavedCourses() {
	var xhttp = new XMLHttpRequest();
	alert(coursesraw);
	xhttp.open("GET","ScheduleRequest?coursesraw=" + coursesraw,false);
	xhttp.send();
	
	var arr = xhttp.responseText.trim().split("\n"); 
	if (arr.length == 0) { alert("NO SAVED SCHEDULES"); }
	alert(arr.length);
	for( var i = 0; i < arr.length; i++){
		var newItem = document.createElement("BUTTON");
		newItem.setAttribute("type", "button");
		newItem.setAttribute("class", "button");
		newItem.setAttribute("style", "width:250px");
		newItem.setAttribute("onclick", "showMap('" + arr[i] + "')");
		newItem.innerHTML = arr[i];
		document.getElementsByClassName("saved-sch")[0].appendChild(newItem);
		
	}
	alert(xhttp.responseText+" " + arr);
	
	
	return false;
}

function genCourses() {
	var xhttp = new XMLHttpRequest();
	alert(coursesraw);
	xhttp.open("GET","ScheduleRequest?coursesraw=" + coursesraw,false);
	xhttp.send();
	
	var arr = xhttp.responseText.trim().split("\n"); 
	
	alert(arr.length);
	for( var i = 0; i < arr.length; i++){
		var newItem = document.createElement("BUTTON");
		newItem.setAttribute("type", "button");
		newItem.setAttribute("class", "button");
		newItem.setAttribute("style", "width:250px");
		newItem.setAttribute("onclick", "showMap('" + arr[i] + "')");
		newItem.innerHTML = arr[i];
		document.getElementsByClassName("g-sch")[0].appendChild(newItem);
		
	}
	alert(xhttp.responseText+" " + arr);
	
	
	return false;
}

function showMap( Index){
	window.open("calendar.jsp?ID="+Index);
}

