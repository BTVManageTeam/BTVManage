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
					shtml+=" tabIndex='"+settings.row+"' "
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
				shtml+=" tabIndex='"+settings.row+"' "
				shtml+="style='margin: 0px; height: 100px; width: 600px;'>"+value+"</textarea>";
				shtml+="<span class='Validform_checktip'></span>";
				return shtml;
			};
			var thumbnailhtml=function(settings){};
			var posterhtml=function(settings){}
			var blockdiv=$('<div></div>').addClass("control-group").attr("id",settings._id.$oid);
			var blocklabel=$('<label></label>').addClass("control-label").html(settings.fields_name);
			var embeddeddiv=$('<div></div>').addClass("controls");
				var functionname=settings.type+"html";
				var divtr=$("tr").has("#"+$(this).attr("id"));
				var prevtr=divtr.prev();
				var prevtd=prevtr.find("td:nth-child(2)");			
				if(settings.alias=="suoluetu"){}else if(settings.alias=="haibao"){
                   }else{
					var show_html=eval(functionname+"(settings)");
					var before_li="";
					if(settings.notnull==0){
						before_li='<li data-row="'+settings.row+'" data-col="'+settings.col+'" data-sizex="'+settings.sizex+'" data-sizey="'+settings.sizey+'" data-id="'+settings._id.$oid+'"><label class="labnocursor">'+settings.name+'：</label>';
					}else{
						before_li='<li data-row="'+settings.row+'" data-col="'+settings.col+'" data-sizex="'+settings.sizex+'" data-sizey="'+settings.sizey+'" data-id="'+settings._id.$oid+'"><label class="labnocursor"><label style="color: red; padding-right: 5px; " >*</label>'+settings.name+'：</label>';
					}
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
			$(".gridster ul").find("")
			
			function initHtml(option,param){
				for(var i=0;i<option.length;i++){			
					$("#adddiv").GenerateHtml(option[i],param);
				};
			};
		  
		}
		$.fn.InitGenerateHtml.loadposter=function (){};
		$.fn.InitGenerateHtml.hoverposterimage=function (){}
		//鼠标移动到图片上面显示删除按钮
		  $.fn.InitGenerateHtml.deleteposterimage=function (o,id){}
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
  		
  				if(data.status=="success"){
  					$.scojs_message("编辑编目信息成功!", $.scojs_message.TYPE_OK);
  				}else{
  					$.scojs_message("编辑编目信息失败!", $.scojs_message.TYPE_ERROR);
  				}
  				giveupAndRefresh();
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
  				if(data.status=="success"){
  					$.scojs_message("编辑编目信息成功!", $.scojs_message.TYPE_OK);
  				}else{
  					$.scojs_message("编辑编目信息失败!", $.scojs_message.TYPE_ERROR);
  				}
  				 giveup();
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
	function selectThumbnailSystem(o){};
	
	//缩略图系统截图
	function thumbnailMoveDiv(o,id){}
	
	/**
	 * 返回并更新
	 */
	function giveupAndRefresh(){
		$('#content').css('display','block');
		$('#contenttwo').remove();
		var tempCatalogue = $("#catalogue").html();
		if("" != tempCatalogue && undefined != tempCatalogue){
			searchNew('catalogue');
		}else{
			var tempCMS = $("#cms").html();
			if("" != tempCMS && undefined != tempCMS){
				searchNew('cms');
			}
			var tempCheck = $("#checkPendId").html();
			if("" != tempCheck && undefined != tempCheck){
				searchNew('checkPendId');
			}
		}
		
	}
	