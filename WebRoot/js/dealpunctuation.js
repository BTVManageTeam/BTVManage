function ChineseToEnglish(txt) {
	var ChineseInterpunction = [  "，"];
	var EnglishInterpunction = [ ","];
	for ( var j = 0; j < ChineseInterpunction.length; j++) {
		var reg = new RegExp(ChineseInterpunction[j], "g");
		txt = txt.replace(reg, EnglishInterpunction[j]);
	}
	return txt;
}

function FullToDBC(objStr){ 
    var Str = objStr; 
    var DBCStr = ""; 
    Str = ChineseToEnglish(Str); 
    if(/.*[\u4e00-\u9fa5]+.*$/.test(Str)){        
        alert("含有汉字！");        
    } 
    for(var i = 0; i < Str.length; i++){ 
            var c = Str.charCodeAt(i); 
            if(c == 12288){ 
                    DBCStr += String.fromCharCode(32); 
                    continue; 
            } 
            if(c > 65280 && c < 65375){ 
                    DBCStr += String.fromCharCode(c - 65248); 
                    continue; 
            } 
            DBCStr += String.fromCharCode(c); 
    } 
    objStr = DBCStr; 
    return objStr;
} 