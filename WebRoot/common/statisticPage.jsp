<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="zcz_page" class="pagination alternate fr">
              <ul class="page_ul">
             
                <li id="zcz_page_li1" num="first" ><a href="javascript:void(0)">首页</a></li>
				<li id="zcz_page_li2" num="up"><a href="javascript:void(0)">上一页</a></li>              
                <li id="zcz_page_li8" num="next"><a href="javascript:void(0)">下一页</a></li>
				<li id="zcz_page_li9" num="last"><a href="javascript:void(0)">尾页</a></li>		
              </ul>
               <div class="page_total">
               <span >共${page.pageCount}页/共${page.totalNum}条</span>
               	 跳到第  <input type="text" id="pageChangeNum" value="${page.currentPage}" onKeyUp="$.Page.pageChange(this)"> 页
                <button  class="btn-mini btn-info" onclick="$.Page.submit()">GO</button>
               </div>
              <input type="hidden" id="pageCount" name="pageCount" value="${page.pageCount}"/>
</div>
<script>
$(document).ready(function(){
	var li1=$("#zcz_page_li1");
	var li2=$("#zcz_page_li2");
	var li8=$("#zcz_page_li8");
	var li9=$("#zcz_page_li9");
	var currentPage=$("#currentPage");
	//初始化分页的方法，首先取得总页数
	var totalnum=parseInt($("#pageCount").val());
	if(totalnum<1)totalnum=1;
	li1.addClass("disabled");
	li2.addClass("disabled");
	if(totalnum==1){
		li8.addClass("disabled");
		li9.addClass("disabled");
	}
	shownum=totalnum;
	if(shownum>5)shownum=5;
	var pagehtml="";
	for(var i=1;i<=shownum;i++){
		pagehtml+="<li num="+i+" class='zcz_page_li'><a href='javascript:void(0)'>"+i+"</a></li>";
	}
	li2.after(pagehtml);
	li2.next().addClass("active");

	//初始化当前页面
	init();
	//初始化排列行的图标
	initorder();
	//添加每个按钮的点击事件
	$("#zcz_page ul li").on("click", function(){
		var linum=$(this).attr("num");
		var li=$(".zcz_page_li");
		if(linum=="first"){
			currentPage.val(1);
		}else if(linum=="last"){
			currentPage.val(totalnum);	
		}else if(linum=="up"){
			pagenum=currentPage.val();
			if(pagenum==1)pagenum=2;
			currentPage.val(pagenum-1);	
		}else if(linum=="next"){
			pagenum=currentPage.val();
			if(pagenum==totalnum)pagenum=totalnum-1;
			if(pagenum<=totalnum)
			currentPage.val(parseInt(pagenum)+parseInt(1));	
		}else{
			pagenum=currentPage.val();
			currentPage.val(linum);
		}
		if($(this).attr("class")!="disabled"){
			selform();
		}
	});

	function init(){
		var linum=parseInt($("#currentPage").val());
		var li=$(".zcz_page_li");
		//var totalnum=$("#pageCount").val();
		//alert(totalnum);
		//currentPage.val(linum);
			if(linum<=totalnum-2 && linum>3 &&linum<=totalnum){
				for(var i=1;i<=li.size();i++){
					$(li.get(i-1)).attr("num",linum-3+i);
					$(li.get(i-1)).find("a").html(linum-3+i);
				}
			}
			if(linum==totalnum-1 && linum>3 &&linum<=totalnum){
				for(var i=1;i<=li.size();i++){
					$(li.get(i-1)).attr("num",linum-4+i);
					$(li.get(i-1)).find("a").html(linum-4+i);
				}
			}
			if(linum==totalnum && linum>4 &&linum<=totalnum){
				for(var i=1;i<=li.size();i++){
					$(li.get(i-1)).attr("num",linum-5+i);
					$(li.get(i-1)).find("a").html(linum-5+i);
				}
			}
			if(linum==2 && totalnum>3 &&linum<=totalnum){
				for(var i=1;i<=li.size();i++){
					$(li.get(i-1)).attr("num",linum-2+i);
					$(li.get(i-1)).find("a").html(linum-2+i);
				}
			}
			if(linum==(totalnum-1) && totalnum>4 &&linum<=totalnum){
				for(var i=1;i<=li.size();i++){
					$(li.get(i-1)).attr("num",linum-4+i);
					$(li.get(i-1)).find("a").html(linum-4+i);
				}
			}
			if(linum>totalnum){
				var num=5;
				if(linum<5)num=linum;
				for(var i=1;i<=num;i++){ 
					$(li.get(i-1)).attr("num",linum-num+i);
					$(li.get(i-1)).find("a").html(linum-num+i);
				}
			}
			


			//判断显示那一页
			pagenum=parseInt(currentPage.val());
			if(pagenum==0)pagenum=1;
			li.removeClass("active");
			if(pagenum==1 && totalnum!=1){
				li1.addClass("disabled");
				li2.addClass("disabled");
				li8.removeClass("disabled");
				li9.removeClass("disabled");
			}else if(pagenum==totalnum && totalnum!=1){
				li8.addClass("disabled");
				li9.addClass("disabled");
				li1.removeClass("disabled");
				li2.removeClass("disabled");
			}else if(pagenum==totalnum && totalnum==1){
				li8.addClass("disabled");
				li9.addClass("disabled");
				li1.addClass("disabled");
				li2.addClass("disabled");
			}else if(pagenum>totalnum){
				li8.addClass("disabled");
				li9.addClass("disabled");
				li1.removeClass("disabled");
				li2.removeClass("disabled");
			}else{
				li8.removeClass("disabled");
				li9.removeClass("disabled");
				li1.removeClass("disabled");
				li2.removeClass("disabled");
				}
			for(var i=1;i<=li.size();i++){
				if($(li.get(i-1)).attr("num")==pagenum)
					$(li.get(i-1)).addClass("active");
				
			}
		}

	function initorder(){
		var id=$("#order").val();
		var orderBy=$("#orderBy").val();
		if(id!="" && id!=null){
			if(orderBy=="asc"){
				$("#"+id).find("i").removeClass("icon-caret-right").addClass("icon-caret-down");
			}
			if(orderBy=="desc"){
				$("#"+id).find("i").removeClass("icon-caret-right").addClass("icon-caret-up");
			}
		}
	}

});

jQuery.Page = {
		pageChange:function(o){
			var $o=$(o);
			var reg =/^(-|\+)?\d+$/;
			var numpage=$o.val();
			var tatalpage=$("#pageCount").val();
			if(reg.test(numpage)){
				var pagenum=$o.val();
				if(parseInt(numpage)>parseInt(tatalpage)){$o.val(tatalpage);pagenum=tatalpage;}
				if(numpage==0){$o.val("1");pagenum=1;}				
				$("#currentPage").val(pagenum);
			}else{
				$o.val("");
			}
			
		},
		submit:function(){
			if($("#pageChangeNum").val()==""){
				$.scojs_message('跳转页面不能为空!', $.scojs_message.TYPE_ERROR);
			}else{
				selform();
			}
			
		}
};
function selform(){
	$.Mark.show();
	$('#vmsform').ajaxSubmit(function(data){
		$.Mark.hide();
    	$(".channel-table").html(data);
    });
}
</script>