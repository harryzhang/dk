/****************　数组扩展 ********************/

Array.prototype.add = function(item) {
	this.push(item);
}

Array.prototype.addRange = function(items) {
	var length = items.length;

	if (length != 0) {
		for (var index = 0; index < length; index++) {
			this.push(items[index]);
		}
	}
}

Array.prototype.clear = function() {
	if (this.length > 0) {
		this.splice(0, this.length);
	}
}

Array.prototype.isEmpty = function() {
	if (this.length == 0)
		return true;
	else
		return false;
}

Array.prototype.clone = function() {
	var clonedArray = [];
	var length = this.length;

	for (var index = 0; index < length; index++) {
		clonedArray[index] = this[index];
	}

	return clonedArray;
}

Array.prototype.contains = function(item) {
	var index = this.indexOf(item);
	return (index >= 0);
}

Array.prototype.dequeue = function() {
	return this.shift();
}

Array.prototype.indexOf = function(item) {
	var length = this.length;

	if (length != 0) {
		for (var index = 0; index < length; index++) {
			if (this[index] == item) {
				return index;
			}
		}
	}

	return -1;
}

Array.prototype.insert = function(index, item) {
	this.splice(index, 0, item);
}

Array.prototype.joinstr = function(str) {
	var new_arr = new Array(this.length);
	for (var i = 0; i < this.length; i++) {
		new_arr[i] = this[i] + str
	}
	return new_arr;
}

Array.prototype.queue = function(item) {
	this.push(item);
}

Array.prototype.remove = function(item) {
	var index = this.indexOf(item);

	if (index >= 0) {
		this.splice(index, 1);
	}
}

Array.prototype.removeAt = function(index) {
	this.splice(index, 1);
}
