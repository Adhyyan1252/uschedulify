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
	alert(xhttp.responseText);
	return false;
}