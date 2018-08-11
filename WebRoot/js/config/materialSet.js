!(function($) { 
		 $.fn.GenerateHtml = function(option,param) {
			 settings=$.extend({},{				 	
					type:'input',//表示类型
	 				name:'无', //表示显示的中文名称
	 				defaultval:'',//表示默认值
	 				alias:'',//表示字段的英文名称
	 				limit:'',//表示限制字数
	 				row:'',
	 				col:'',
	 				sizex:'',
	 				sizey:'',
	 				value:'',
	 				inputtype:'' //表示input的类型
	 			},option);
			var html=function(settings){
				return "<input class='span5' type='text'/>";
			};
			var inputhtml=function(settings){
				var value=settings.defaultval;
				if(param){
					value=settings.value;
				}
				if(value==null)value="";
				var shtml="<input  type='text' value='"+value+"' name='VMSCS."+settings._id.$oid+"' ";
					if(settings.inputtype=="int"){
						shtml+=" datatype='n1-"+settings.limit+"' "
					}else{
						if(settings.notnull==0){
							shtml+=" ignore='ignore' "
						}
						if(settings.limit!=null){
							shtml+=" datatype='*1-"+settings.limit+"' "
						}
					}
					shtml+="/>"
					shtml+="<span class='Validform_checktip'></span>";
				return shtml;
			};
			var datehtml=function(settings){
				var value=settings.defaultval;
				if(param){
					value=settings.value;
				}
				if(value==null)value="";
				var shtml="<div class='input-append date form_date'  data-date-format='yyyy-mm-dd' data-link-field='"+settings.alias+"' data-link-format='yyyy-mm-dd'>";
					shtml+="<input size='16' style='width:152px'  type='text' value='"+value+"' readonly>";
					shtml+="<span class='add-on'><i class='icon-remove'></i></span>";
					shtml+="<span class='add-on'><i class='icon-th'></i></span>";
					shtml+="</div><input name='VMSCS."+settings._id.$oid+"' value='"+value+"' type='text' readonly style='width:0px;padding-left:0;padding-right:0' id='"+settings.alias+"'  ";
							if(settings.notnull!=0){
								shtml+=" datatype='*' "
							}
							shtml+="/>";
					shtml+="<span class='Validform_checktip'></span>";
				return shtml;
			}
			var listboxhtml=function(settings){
				var _option=settings.defaultval;
				var shtml="<select name='VMSCS."+settings._id.$oid+"' datatype='*' nullmsg='该选项已被更改,请重选!'>";
						if(settings.defaultval!=null && settings.defaultval!="" && _option.indexOf(settings.defaultval)==-1){
							shtml+="<option value='' >"+settings.defaultval+"</option>"
						}
						optionval=_option.split(",");
						for(var i=0;i<optionval.length;i++){
							if(optionval[i]==settings.value){
								shtml+="<option value="+optionval[i]+" selected>"+optionval[i]+"</option>"
							}else{
								shtml+="<option value="+optionval[i]+" >"+optionval[i]+"</option>"
							}
							
						}
	            	shtml+="</select>";
				return shtml;
			};
			var checkboxhtml=function(settings){
				var _option=settings.defaultval;
				var shtml="";
	               optionval=_option.split(",");

					for(var i=0;i<optionval.length;i++){
						var value=settings.value;
						if(value==null){value=""}
						if(settings.defaultval==null){
							value="";
						}
						if(value.indexOf(optionval[i])!=-1){
							shtml+="<label><input type='checkbox' name='VMSCS."+settings._id.$oid+"' value="+optionval[i]+"   checked='checked'  />" + optionval[i]+"</label>"
						}else{
							shtml+="<label><input type='checkbox' name='VMSCS."+settings._id.$oid+"' value="+optionval[i]+" />" + optionval[i]+"</label>"
						}
					}
				return shtml;
			};
			var textareahtml=function(settings){
				var _option=settings.defaultval;
				var value=settings.defaultval;
				if(param){
					value=settings.value;
				}
				if(value==null)value="";
				var shtml="";
				shtml="<textarea name='VMSCS."+settings._id.$oid+"' ";
						if(settings.notnull==0){
							shtml+=" ignore='ignore' "
						}
						if(settings.limit!=null){
							shtml+=" datatype='*1-"+settings.limit+"' "
						}
				shtml+="style='margin: 0px; height: 100px; width: 600px;'>"+value+"</textarea>";
				shtml+="<span class='Validform_checktip'></span>";
				return shtml;
			};
			var thumbnailhtml=function(settings){
				var shtml="";
					shtml+="<div class='cata_title isdeletetr'><p>缩略图</p></div>";
					shtml+="<div id='thumbnailhidden' class='cata_upload thumbnailcss isdeletetr' style='position:relative;display:none'>";
					shtml+="<input type='file' name='thumbnailfile' id='thumbnailuploadify' />";
					shtml+="<div id='mark-loaddingimage' class='mark-loaddingimage'></div>";
					
					if(settings.value=="" || settings.value==null){
						
						shtml+="<div id='loaddingimage' sid='' class='uploadify-div'>";
						shtml+="<img id='image' sid='' src='"+ctx+"/images/loadingImage.png' name=''></img>";
						$("#thumbnailId").val("");
					}else{
						imageval=settings.value;
						shtml+="<div id='loaddingimage' sid='"+imageval.fileid+"' class='uploadify-div'>";
						shtml+="<img  id='image'  sid='"+imageval.id+"' src='"+imageval.httpwan+"' name='"+imageval.name+"'  type='"+imageval.src+"'></img>";
						$("#thumbnailId").val(imageval.id);
					}
					shtml+="</div>";
					
					shtml+="<div id='thumbnaildiv' class='image_grid' style='width:100%;" +
							"max-height:355px;margin-top:30px;overflow-y:auto;overflow-x:hidden;background:url("+ctx+"/js/catalog/img//bg.png);display:none;'>";
					shtml+="<ul id='thumbnailul' class='portfolio_list da-thumbs'>";
					
					shtml+="</ul>";
					shtml+="</div>";
					
					shtml+="</div>";
					return shtml;
			};
			var posterhtml=function(settings){
				var shtml="";
					shtml+="<div class='cata_title isdeletetr'><p>海报</p></div>";
					shtml+="<div id='posterhidden' class='cata_upload postercss isdeletetr' style='position:relative;display:none'>";
					shtml+="<input type='file' name='posterfile' id='posteruploadify' />";
					shtml+="<div id='poster-loaddingimage' class='poster-loaddingimage'></div>";
					shtml+="<div id='ThumbPicdiv' >";					
					shtml+="<img src='"+ctx+"/images/poster_left.png' id='btnPrev' class='FlLeft poster_left'/>";
					shtml+="<div class='jCarouselLite FlLeft'><ul id='ThumbPic'>";
					if(settings.value=="" || settings.value==null){
						shtml+="<li><div class='divinli'><img name='no' sid='' src='"+ctx+"/images/loadingImage.png' /></div></li>";		
					}else{
						posterval=settings.value;
						for(var i=0;i<posterval.length;i++){
							postervalid=posterval[i];
							shtml+="<li><div class='divinli'><div sid='"+postervalid.id+"' class='divindiv'></div><img id='posterimage' sid ='"+postervalid.id+"' src='"+postervalid.httpwan+"' name='"+postervalid.name+"'/></div></li>";										    					
						}
					
					}
					shtml+="</ul><div class='Clearer'></div></div>";
					shtml+="<img src='"+ctx+"/images/poster_right.png' id='btnNext' class='FlLeft poster_right' /><div class='Clearer'></div>";
					shtml+="</div></div>";
				return shtml;
			}
			var blockdiv=$('<div></div>').addClass("control-group").attr("id",settings._id.$oid);
			var blocklabel=$('<label></label>').addClass("control-label").html(settings.fields_name);
			var embeddeddiv=$('<div></div>').addClass("controls");
				var functionname=settings.type+"html";
				var divtr=$("tr").has("#"+$(this).attr("id"));
				var prevtr=divtr.prev();
				var prevtd=prevtr.find("td:nth-child(2)");			
				if(settings.alias=="suoluetu"){
					functionname="thumbnailhtml";
					$("#addhtml_thumbnail").css("display","block");
					$("#addhtml_thumbnail").html(eval(functionname+"(settings)"));
				}else if(settings.alias=="haibao"){
					functionname="posterhtml";
					$("#addhtml_poster").css("display","block");
					$("#addhtml_poster").html(eval(functionname+"(settings)"));
					//让海报居中的方法
	    			var hiimg=$("#ThumbPic li img");
	    			for(var i=0;i<hiimg.size();i++){
	    				var himg=$(hiimg.get(i));
	    				himg.parent().css({"margin-top":((240-himg.height())/2)+"px"});
	    			}
	    			
	    			//为初始化后的鼠标删除添加事件
	    			$.fn.InitGenerateHtml.hoverposterimage();
				}else{
					var show_html=eval(functionname+"(settings)");
					var before_li='<li data-row="'+settings.row+'" data-col="'+settings.col+'" data-sizex="'+settings.sizex+'" data-sizey="'+settings.sizey+'" data-id="'+settings._id.$oid+'"><label class="labnocursor">'+settings.name+'：</label>';
					var after_li='</li>';
					var html=before_li+show_html+after_li;
					$("#templateUl").append(html);					
				}
		 }
	})(jQuery);
	
	(function($){
		$.fn.InitGenerateHtml=function(sjson,update){
			//循环加载页面内容
			var option=sjson;
			//console.log(option);
			//初始化页面内容 隐藏缩略图和海报 制空信息
			$("#addhtml_thumbnail").css("display","none");
			$("#addhtml_poster").css("display","none");
			$("#templateUl").html("");
			initHtml(option,update);
			//执行页面调整
			CatalogInit();
			$(".gridster ul").gridster({
				widget_margins : [ 5, 5 ],
				widget_base_dimensions : [ 480, 40 ],
				draggable: {
		           handle: 'header'
		        }
			});
			function initHtml(option,param){
				for(var i=0;i<option.length;i++){			
					$("#adddiv").GenerateHtml(option[i],param);
					//缩略图和海报的加载
					if(option[i].alias=="suoluetu"){
						$("#thumbnailhidden").fadeIn(1500);
						loadthumbnail();
						//缩略图片id的赋值						
						var id= $("#loaddingimage").find("img").attr("sid");;
						$("#thumbnailId").val(id);
					}else if(option[i].alias=="haibao"){
						
						setTimeout(function(){
							var hiimg=$("#ThumbPic li img");
		    				for(var i=0;i<hiimg.size();i++){
		    					var himg=$(hiimg.get(i));
		    					himg.parent().css({"margin-top":((240-himg.height())/2)+"px"});
		    				}
	    				},800);
						
						//计算上传海报的图片id集合
	    				var imgList= $("#ThumbPic").find("img");
	    				var posterList="";
					    for ( var img = 0; img < imgList.length; img++) {
						var sid = $(imgList.get(img)).attr("sid");
						var name = $(imgList.get(img)).attr("name");
						var src = $(imgList.get(img)).attr("src");
						if (name != "no") {
							var poster = sid + "_" + name + "_" + src;
							if (img == 0) {
								posterList += poster;
							} else {
								posterList += "," + poster;
							}
						}
					}
	    				$("#posterList").val(posterList);		
						$("#posterhidden").fadeIn(1500);
						loadposterupload();
						$.fn.InitGenerateHtml.loadposter();
					}			
				};
			};
			
			function loadthumbnail(){
				var sid=$('#image').attr("sid");
		    	 $("#thumbnailuploadify").uploadify({
		    		 	'debug':false,
		    			'swf' : ctx+'/js/upload/uploadify3.1/uploadify.swf',
		    			'uploader' : ctx+'/upload/pictureUpload/?sid='+sid,
		    			'fileObjName' : 'thumbnailfile',
		    			'queueID' : 'fileQueue',
		    			'fileSizeLimit' : 2147483648,
		    			'auto' : true,
		    			'multi' : false,
		    			'width' : 130,
		    			'height' : 32,
		    			'fileTypeExts' : '*.jpg;*.png;*.jpeg;*.bmp;*.gif;',
		    			'buttonImage' : ctx+'/js/upload/uploadify3.1/img/uploadbtn.png',
		    			'progressData':'speed',
		    			'preventCaching':true,
		    			'successTimeout':99999,
		    			'removeTimeout':'1',
		    			'removeCompleted':true,
		    			'removeTimeout':3, 
		    			'onUploadStart':function(file){
		    					var uploadsid=$("#loaddingimage").attr("sid");
		    					//传到后台的参数
		    					var catalogueId=$("#catalogueId").val();
		    					$("#thumbnailuploadify").uploadify("settings", "formData",{ 'catalogueId':catalogueId,'picture_type':'E655FD05952846A884217BBDEDB8EA01','uploadsid':uploadsid });
		    					$("#loaddingimage img").addClass("markImg-opacity");
		    					var left=$("#loaddingimage").position().left;
		    					var top=$("#loaddingimage").position().top;
		    					var imagewidth=$("#loaddingimage img").width();
		    					var imageheight=$("#loaddingimage img").height();
		    					$("#mark-loaddingimage").css("display","block");
		    					$("#mark-loaddingimage").css({"width":imagewidth,"height":imageheight,"line-height":imageheight+"px","left":20,"top":43});
		    				},
		    			'onUploadProgress':function(a,b,c,d,e){
			    				var tPercentagenum=parseInt(parseFloat(b)/parseFloat(e)*100);
			    				if(tPercentagenum>100){tPercentagenum=100}
			    				$("#mark-loaddingimage").html(tPercentagenum+"%")
		    				},
		    			'onUploadSuccess':function(a,b,c){
		    				b= eval("("+b+")");		    				
		    				$("#mark-loaddingimage").html("0%");
		    				$("#mark-loaddingimage").css("display","none");
		    				$("#delthumbnailId").val(b.id);
		    				$("#thumbnailId").val("0");
		    				$("#thumbnailName").val(b.name);
		    				$("#thumbnailSrc").val(b.saveHttpPath);
		    				$("#loaddingimage").html("<img  id='image' sid='0' style='max-width:240px;max-height:180px' name='"+b.name+"' src='"+b.saveHttpPath+"' type='2' />")}
		         });
		     };
		     
		     
		     
		     function loadposterupload(){
		    	 var sid=$('#posterimage').attr("sid");
		    	 var markimage=false;
		    	 $("#posteruploadify").uploadify({
		    		 	'debug':false,
		    			'swf' : ctx+'/js/upload/uploadify3.1/uploadify.swf',
		    			'uploader' : ctx+'/upload/pictureUpload/?sid='+sid,
		    			'fileObjName' : 'posterfile',
		    			'queueID' : false,
		    			'fileSizeLimit' : 2147483648,
		    			'auto' : true,
		    			'multi' : true,
		    			'width' : 130,
		    			'height' : 32,
		    			'fileTypeExts' : '*.jpg;*.png;*.jpeg;*.bmp;*.gif;',
		    			'buttonImage' : ctx+'/js/upload/uploadify3.1/img/uploadbtn.png',
		    			'progressData':'speed',
		    			'preventCaching':true,
		    			'successTimeout':99999,
		    			//'removeCompleted':false,
		    			'onUploadStart':function(file){
		    				//console.log("onUploadStart开始上传");
		    				var catalogueId=$("#catalogueId").val();
		    					$("#posteruploadify").uploadify("settings", "formData",{ 'catalogueId':catalogueId,'picture_type':'DA499816208A4040977DD90B838D8710' });
		    				addposterimage();
		    				$.fn.InitGenerateHtml.loadposter();
		    			},
		    			'onUploadProgress':function(a,b,c,d,e){
		    				var Percentagenum=parseInt(parseFloat(d)/parseFloat(e)*100);
		    				if(Percentagenum>100){Percentagenum=100}
		    				$("#poster-loaddingimage").html(Percentagenum+"%")
		    				//console.log("a:"+a+"b："+b+"c："+c+"d："+d+"e："+e);
		    				//console.log(d==e);
		    				if(d>=e){
		    					markimage=true;	
		    				}
		    			},
		    			'onUploadSuccess':function(a,b,c){
		  
		    				b= eval("("+b+")");
		    				var $img=$($("#ThumbPic li").get(0)).find("img");
		    				if(markimage){
		   		        	 	$img.removeClass("markImg-opacity");
			    				$("#poster-loaddingimage").html("0%");
			    				$("#poster-loaddingimage").css("display","none");
		    				}
		    				//console.log("onUploadSuccess:"+"a:"+a+"b："+b+"c："+c);
		    				$img.removeClass("markImg-opacity");
		    				if($("#ThumbPic li").size()==1){
		    					$img.before("<div sid='' class='divindiv'></div>")
		    				}
		    				$img.attr("sid","0");
		    				$img.attr("name",b.name);
		    				$img.attr("src",b.saveHttpPath);
		    				//$img.parent().css({"margin-top":((240-$img.height())/2)+"px"});
		    				//alert($("#ThumbPic li img:hidden").size());
		    				setTimeout(function(){
		    					//alert($("#"+b.id).attr("src"));
		    					var himg=$("#"+b.id);
		    					himg.parent().css({"margin-top":((240-himg.height())/2)+"px"});
		    					himg.fadeIn("fast");
			    				/*var hiimg=$("#ThumbPic li img:hidden");
			    				for(var i=0;i<hiimg.size();i++){
			    					console.log("onUploadSuccess:循环显示图片"+i);
			    					var himg=$(hiimg.get(i));
			    					himg.parent().css({"margin-top":((240-himg.height())/2)+"px"});
			    					himg.fadeIn("slow");
			    				}*/
		    				},1000);
		    				if(markimage){
		    					$("#ThumbPic li img").css({"opacity":100});
		    				}
		    				$.fn.InitGenerateHtml.hoverposterimage();
		    				//计算上传海报的图片id集合
		    				var imgList= $("#ThumbPic").find("img");
		    				var posterList="";
								for ( var img = 0; img < imgList.length; img++) {
										var sid = $(imgList.get(img)).attr(
												"sid");
										var name = $(imgList.get(img)).attr(
												"name");
										var src = $(imgList.get(img)).attr(
												"src");
										
											var poster = sid + "_" + name + "_"
													+ src;
											if (img == 0) {
												posterList += poster;
											} else {
												posterList += "," + poster;
											}
										}
								
		    				$("#posterList").val(posterList);
		    			}
		         });
		     };

		   function addposterimage(){
		        	 var addposterimagehtml=$("#ThumbPicdiv").html();
		        	 $("#ThumbPicdiv").html("")
		        	 $("#ThumbPicdiv").prepend(addposterimagehtml);
		        	 var imgagename=$($("#ThumbPic li").get(0)).find("img").attr("src");
		        	 imgagename=imgagename.substring(imgagename.lastIndexOf("/")+1,imgagename.length);
		        	 if(imgagename!="loadingImage.png"){
		        		 $("#ThumbPic").prepend("<li><div class='divinli'><div sid='' class='divindiv'></div><img style='width:200px; max-height:240px' src='"+ctx+"/images/loadingImage.png'/></div></li>");
		        	 };
		        	 var $img=$($("#ThumbPic li").get(0)).find("img");
		        	 $img.addClass("markImg-opacity");
					 $img.parent().css({"margin-top":((240-$img.height())/2)+"px"});
		        	// alert($img.offset().left);
 					var imagewidth=$img.width()-2;
 					var imageheight=$img.height();
 					$("#poster-loaddingimage").css("display","block");
 					$("#poster-loaddingimage").css({"width":imagewidth,"height":imageheight,"line-height":imageheight+"px","left":34,"top":89});
		         };
		  
		}
		$.fn.InitGenerateHtml.loadposter=function (){
			 //判断页面中海报显示的数量
				var screenwidth=$(window).width();
				var shownum=$("#ThumbPic li").size();
				if(screenwidth>1480){
					if(shownum>5)shownum=5;
				}else if(screenwidth>1250){
					if(shownum>4)shownum=4;
				}else if(screenwidth>1050 && screenwidth<=1250){
					if(shownum>3)shownum=3;
				}else{
					shownum=2;
				}
	    		$(".jCarouselLite").jCarouselLite({
	    			btnNext: "#btnNext",
	    			btnPrev: "#btnPrev",
	    			//mouseWheel: true,
	    			scroll: 1,
	    			circular:false,
	    			speed: 240,
	    			visible:shownum
	    		});	    		
	    	};
		$.fn.InitGenerateHtml.hoverposterimage=function (){
			  /**添加对于鼠标移动事件**/
				$("#ThumbPic li .divinli").hover(function(){
					var o=$(this);
					var odiv=o.find("div");
					odiv.fadeIn("slow");
					odiv.css({"left":"175px","top":o.find("img").position().top+5+"px"});
				},function(){
					var odiv=$(this).find("div");
					odiv.fadeOut("slow");
				});
				/**添加对于鼠标移动事件**/
				$("#ThumbPic li .divindiv").on("click",function(){
					$.fn.InitGenerateHtml.deleteposterimage(this,$(this).attr("sid"));
				});
		  }
		//鼠标移动到图片上面显示删除按钮
		  $.fn.InitGenerateHtml.deleteposterimage=function (o,id){
			  $.Mark.show();
			  $(o).parent().parent().remove();
			  if($("#ThumbPic li").size()<1){
					$("#ThumbPic").prepend("<li><div class='divinli'><div sid='' class='divindiv'></div><img style='width:200px; max-height:240px' src='"+ctx+"/images/loadingImage.png' /></div></li>");
					var $img=$($("#ThumbPic li").get(0)).find("img");
					$img.parent().css({"margin-top":((240-$img.height())/2)+"px"});
			  }
				var addposterimagehtml=$("#ThumbPicdiv").html();
	        	$("#ThumbPicdiv").html("")
	        	$("#ThumbPicdiv").prepend(addposterimagehtml);
				$.fn.InitGenerateHtml.loadposter();
				$.fn.InitGenerateHtml.hoverposterimage();
				$.Mark.hide();
				var delId=$("#delposterId").val();
				if(delId!=""){
					delId+=","+id;
				}else{
					delId=id;
				}
			    $("#delposterId").val(delId);
			}
	})(jQuery);
	
	//添加编目信息页面初始化的方法
	function CatalogInit(){
		$(".form_date").datetimepicker({
		    todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2
		});
  		$("#registeredform").Validform({
  			tiptype:4,
  			ajaxPost:true,
  			beforeCheck:function(curform){
  				if($("#template_id_select").val()==""){
  					$.scojs_message("编目模板不能为空!", $.scojs_message.TYPE_ERROR);
  					return false;
  				}
  			},
  			callback:function(data){
  				search('materialset');
  				$("#content").css("display","block");
  				$("#contenttwo").remove();
  				//selectform();
  				$.Mark.hide();
  			}
  		});
  		$("#registeredformNF").Validform({
  			tiptype:4,
  			ajaxPost:true,
  			beforeCheck:function(curform){
  				if($("#template_id_select").val()==""){
  					$.scojs_message("编目模板不能为空!", $.scojs_message.TYPE_ERROR);
  					return false;
  				}
  				if($("#dtp_input2").val()>$("#dtp_input3").val() && $("#dtp_input3").val()!=""){
  					$.scojs_message("授权开始时间不能大于授权结束时间!", $.scojs_message.TYPE_ERROR);
  					return false;
  				}
  			},
  			callback:function(data){
  				search('materialset');
  				$("#content").css("display","block");
  				$("#contenttwo").remove();
  				selectform();
  				$.Mark.hide();
  			}
  		});
  		
  	};
	//模板变更时执行的方法
	function selectTemplate(o){
		var upselect=$("#template_id").val();
		var nextselect="";
		nextselect=$(o).val();
		var templateIsExist=$("#templateIsExist").val();
		
		if(templateIsExist=="" || templateIsExist==null){
			var templateId=$(o).val();
			if(templateId=="id"){
		  		$(".isdeletetr").remove();
		  	}else{
				changetemplate($(o).val());
		  	}
		}else{		
			var smessage="更改编目模板会清除以前数据,是否继续?";
			var confirm = $.scojs_confirm({
				  content:smessage,
				  action: function(){
					  confirm.destroy();
					  $.Mark.show();
					  var materialid=$("#materialid").val();
					  var CatalogTableName=$("#templateNameId").val();
					  $.ajax({
							type: "POST",
							url: ctx+"/material/deteleCatalog/",
							data:"CatalogTableName="+CatalogTableName+"&materialid="+materialid,
							async:false,
							success: function(sjson){
								allchangepage(nextselect,materialid);
								$.Mark.hide();
							}
					 }); 
					 
				  }
				});
			$(o).val(upselect);
			confirm.show();
			
		}
    };
	//整体替换该页面的方法
	function allchangepage(nextselect,materialid){
		$.ajax({
			type: "POST",
			url: ctx+"/material/addCatalog/",
			data:"template_id="+nextselect+"&id="+materialid,
			success: function(data){
				$("#contenttwo").html(data);
			}
	 	});
	};
	//变更模板执行的方法
	function changetemplate(nextselect){
		
		$.Mark.show();
		var templateId=nextselect;
	  	$("#template_id").val(templateId);
	  	var materialid=$("#materialid").val();
	  
	  		$.ajax({
				type: "POST",
				url: ctx+"/material/selectTemplateById/",
				data:"templateId="+templateId+"&materialid="+materialid,
				success: function(sjson){
					$("#templateNameId").val(sjson.tableName);
					$("#createTableid").val(sjson.createTableId);
					$(".isdeletetr").remove();
					if(sjson.tableName!=null){
						$("#adddiv").InitGenerateHtml(sjson.list);	
					}
					//给页面添加验证信息
					CatalogInit();
					$.Mark.hide();
				}
		    });

	};
	
	//选择系统缩略图 根据素材的id查询出所有的打点图片展示
	function selectThumbnailSystem(o){
		if($(o).html()=="选择系统截图"){
			$(o).html("关闭");
			var cataid=$("#catalogueId").val();
			$.ajax({
				type: "POST",
				url: ctx+"/material/selectThumbnailSystem/",
				data:"cataid="+cataid,
				success: function(sjson){
					//根据返回的信息循环输出打点图片
					
					var sLiHtml="";
					for(var i=0;i<sjson.length;i++){
						//alert(sjson[i].httpUrl);
						sLiHtml+="<li>";
						sLiHtml+="<div class='imgdiv'>";
						sLiHtml+="<img style='max-height:150px;' src='"+sjson[i].httpUrl+"' alt='img' sid='"+sjson[i].id+"' type='"+sjson[i].type+"'>";
						sLiHtml+="</div>";
						sLiHtml+="<article class='da-animate da-slideFromRight' style='display: block;'>";
						sLiHtml+="<div class='title' ><span>"+sjson[i].text+"</span></div>";
						sLiHtml+="<div class='button' >";
						sLiHtml+="<em style='cursor:pointer;' sid='"+sjson[i].localUrl+"' onclick='thumbnailMoveDiv(this)'>设为缩略图</em>";
						//sLiHtml+="<span class='link_post' sid='"+sjson[i].localUrl+"' onclick='thumbnailMoveDiv(this)'></span>";
						sLiHtml+="</div>";
						sLiHtml+="</article>";
						sLiHtml+="</li>";
					}
					if(sjson.length==0){
						sLiHtml+="<strong style='font-size:16px'></br>系统截图为空</strong>";
					}
				   $("#thumbnailul").html(sLiHtml);
				   $('ul.da-thumbs > li').hoverdir();
				   $("#thumbnaildiv").slideDown("slow");
				}
		    });
		}else{
			$(o).html("选择系统截图");
			$("#thumbnaildiv").slideUp("slow");
		}
		
	};
	
	//缩略图系统截图
	function thumbnailMoveDiv(o,id){
		
		var $o=$(o).parent().parent();
		var top=$("#loaddingimage").offset().top;
		var left=$("#loaddingimage").offset().left;
		var width=$o.width();
		var height=$o.height();
		var imgsrc=$o.parent().find("img").attr("src");
		var sid=$o.parent().find("img").attr("sid");
		var type=$o.parent().find("img").attr("type");
		var oldtype=$("#image").attr("type");
		var oldsid=$("#image").attr("sid");
		alert(oldsid);
		var $shtml=$("<div style='width:"+width+";height:"+height+"' class='movedivclass'><img src='"+imgsrc+"'></div>");
		$shtml.prependTo($("body"));
		var stop=$o.offset().top;
		var sleft=$o.offset().left;
		var num=(stop+sleft-top-left);
		$shtml.css({"left":sleft+"px",top:stop+"px"});
		$shtml.animate({left:(left)+"px",top:(top)+"px"}, num,"linear",function(){
			$("#loaddingimage").find("img").attr("src",imgsrc);
			$("#loaddingimage").find("img").attr("sid",sid);
			$("#loaddingimage").find("img").attr("type",type);
			$shtml.remove();
			$("#thumbnaildiv").slideUp("slow");
			//开始进行数据库的存储
		    var id= $("#image").attr("sid");
		    var name= $("#image").attr("name");
		    var src= $("#image").attr("src");
		    if(oldtype=='2'){
		    	$("#delthumbnailId").val(oldsid);
		    }else{
		    	$("#delthumbnailId").val("");
		    }
			$("#thumbnailId").val(id);
			$("#thumbnailName").val(name);
			$("#thumbnailSrc").val(src);
			}
		);
	}
	