$(document).ready(function () {
	// 页面导航a标签form表单历史数据提交
	$('.page-heading').find('a[data-toggle="button"]').on('click', function() {
		var $form = $('#history_form');
		$form.attr('action', $(this).attr("href"));
		$form.submit();
	});
	
	// 回到顶部按钮 class l-top
	$('.l-top').click(function (e) {
	  e.preventDefault();
	  $('html, body').animate({scrollTop: 0}, 800);
	});
	
	// 图片预加载  $.preloadImages('img/hover-on.png', 'img/hover-off.png');
	$.preloadImages = function () {
	  for (var i = 0; i < arguments.length; i++) {
	    $('<img>').attr('src', arguments[i]);
	  }
	};
	
	// 修复默认加载图片 这里需修改默认图片地址
	$('img').on('error', function () {
		  if(!$(this).hasClass('broken-image')) {
//		    $(this).prop('src', 'img/broken.png').addClass('broken-image');
		    $(this).prop('src', '').addClass('broken-image');
		  }
	});
	
	$('#admin_role_select').on('change', function() {
		window.location.href = window.contextPath + '/manage/resource/rolechange/' + $(this).children('option:selected').val(); 
	})
});

//图片上传  
function editorUpImg(file, editor, $editable){  
    var filename = false;  
    try{  
    filename = file['name'];  
    } catch(e){  
        filename = false;  
    }  
    if(!filename){  
        $(".note-alarm").remove();  
    }  
      
    //以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误  
    data = new FormData();  
    data.append("imageFile", file);  
    data.append("key",filename); //唯一性参数  
  
    $.ajax({  
    	data: data,  
    	type: "POST",  
    	url: window.contextPath + "/manage/fileUpload/editor/img",  
    	cache: false,  
    	contentType: false,  
    	processData: false,  
    	success: function(r) {
    		if(r.state==1){  
    			editor.insertImage($editable, r.content.data.url);
    		}else{  
    			toastr.error(r.message, '上传图片失败');  
        	}  
    	},  
    	error:function(){  
    		toastr.error('插入图片上传服务器失败', '上传图片失败'); 
    		return;  
        },
        dataType:'json'
  });
 }

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}

Array.prototype.unique = function() {
	 var result = [], hash = {}, arr = this;
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	return result;
}