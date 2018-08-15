setInterval(function(){
	var uri = "";
	$.ajax({
		type : 'POST',
		url : uri,
		cache : false,
		success : function(response) {
			
		},
		error : function(a, b, c) {
			window.location.href = window.location.href
		}
	});
},5000);