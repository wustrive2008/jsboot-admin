(function($){  
    //备份jquery的ajax方法  
    var _ajax = $.ajax;  
    //重写jquery的ajax方法  
    $.ajax = function(opt){  
        //备份opt中error和success方法  
        var fn = {  
            error:function(XMLHttpRequest, textStatus, errorThrown){},  
            success:function(data, textStatus){}  
        }  
        if(opt.error){  
            fn.error = opt.error;  
        }  
        if(opt.success){  
            fn.success = opt.success;  
        }  
        //扩展增强处理  
        var _opt = $.extend(opt,{  
        	beforeSend: function() {
        		// loading 不处理
        		Pace.start();
        	},
            error:function(XMLHttpRequest, textStatus, errorThrown){ 
            	Pace.stop();
            	//错误方法增强处理  
            	toastr.error('服务器内部错误，请联系运维人员。', '操作失败');
                fn.error(XMLHttpRequest, textStatus, errorThrown);  
            },  
            success:function(data, textStatus){
            	Pace.stop();
            	try {
            		doSuccess(data);
            	} catch(e){
            		
            	}
            	function doSuccess(dataJson) {
            		if(!dataJson || !dataJson.state){ return;}
            		if(dataJson.state == 403) {
            			// 服务器拒绝请求。跳转登录页面
            			location.href = window.contextPath + "/manage/home";
            		} else if(dataJson.state == 500) {
            			toastr.error('服务器内部错误，请联系运维人员。', '操作失败');
            		}
            	}
                //成功回调方法增强处理  
                fn.success(data, textStatus);  
            }  
        });  
        return _ajax(_opt);  
    };  
})(jQuery);