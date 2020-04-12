// added a class list
function addClass() {
	var item = document.getElementById("addClassInput").value
	
	if(!validate(item)) {
		return
	}
	
	var text = document.createTextNode(item)
	var newItem = document.createElement("li")
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