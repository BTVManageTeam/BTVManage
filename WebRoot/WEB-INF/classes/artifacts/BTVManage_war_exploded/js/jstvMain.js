
    //全局蒙版的添加
    jQuery.Mark = {          
    		show:function() {
    			$("#ajax_mask").css("display","block");
    			$("#ajax_mask_inner").css("display","block");       
    		},          
    		hide:function() {          
    			$("#ajax_mask").css("display","none");
    			$("#ajax_mask_inner").css("display","none");      
    		},
    		dialogshow:function() {
    			$("#ajax_mask").css("z-index","20");
    			$("#ajax_mask").css("display","block");    
    		},          
    		dialoghide:function() {          
    			$("#ajax_mask").css("display","none");   
    		}
    };
   