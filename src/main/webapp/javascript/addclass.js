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

function genCourses() {
	var xhttp = new XMLHttpRequest();
	alert(coursesraw);
	xhttp.open("GET","ScheduleRequest?coursesraw=" + coursesraw,false);
	xhttp.send();
	var arr = xhttp.responseText.split("\n"); 
	for( var i = 0; i < arr.length; i++){
		var newItem = document.createElement("BUTTON");
		newItem.setAttribute("type", "button");
		newItem.setAttribute("onclick", "" )
		document.getElementsByClassName("g-sch")[0].appendChild(newItem);
		
	}
	
	return false;
}

function showSchedule(var ID){
	window.open("ScheduleR", "_self")
}