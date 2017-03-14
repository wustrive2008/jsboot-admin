var Page = function(ibox_content_id) {
		this.first = '<li class="footable-page-arrow disabled"><a href="javascript:void(0);">«</a></li>';
		this.prev = '<li class="footable-page-arrow disabled"><a href="javascript:void(0);">‹</a></li>';
		this.next = '<li class="footable-page-arrow disabled"><a href="javascript:void(0);">›</a></li>';
		this.last = '<li class="footable-page-arrow disabled"><a href="javascript:void(0);">»</a></li>';
		this.disabled = function() {
			return this.firstPage() + this.next + this.last;
		};
		this.firstPage = function() {
			return this.first + this.prev + this.currentPage(1);
		};
		this.currentPage = function(i) {
			return '<li class="footable-page active"><a href="javascript:void(0);">'+i+'</a></li>';
		};
		this.activityPage = function(i) {
			return '<li class="footable-page"><a href="javascript:Page.prototype.seturl('+i+', \''+ibox_content_id+'\');">'+i+'</a></li>';
		};
		this.firstActivity = function(num) {
			return  '<li class="footable-page-arrow"><a href="javascript:Page.prototype.seturl(1, \''+ibox_content_id+'\');">«</a></li>' 
				+ '<li class="footable-page-arrow"><a href="javascript:Page.prototype.seturl('+(num - 1)+', \''+ibox_content_id+'\');">‹</a></li>'; 
		};
		this.lastDisabled = function() {
			return this.next + this.last;
		};
		this.lastActivity = function(num, maxpage) {
			return '<li class="footable-page-arrow"><a href="javascript:Page.prototype.seturl('+(num-0 +1)+', \''+ibox_content_id+'\');">›</a></li>' 
				+ '<li class="footable-page-arrow"><a href="javascript:Page.prototype.seturl('+maxpage+', \''+ibox_content_id+'\');">»</a></li>';
		};
		Page.prototype.seturl = function(num, ibox_content_id, form_obj) {
			var ibox_content_obj = $("#" + ibox_content_id);
			ibox_content_obj.find("input[name='pageNumber']").val(num);
			var params;
			if($("#" + ibox_content_id + "-form").length > 0) {
				params=$("#" + ibox_content_id + "-form").serialize();
			}else if(form_obj!=null&& form_obj.length > 0){
				params=form_obj.serialize();
			}else if(ibox_content_obj.find("form").length > 0) {
				params=ibox_content_obj.find("form").serialize();
			}else if(ibox_content_obj.parents("form").length > 0) {
				params=ibox_content_obj.parents("form").serialize();
			}
		    $.get(ibox_content_obj.attr('url'),params,function(data){
		    	ibox_content_obj.empty();
		    	ibox_content_obj.html(data);
			});	
		}
}	
function setpage(num, maxpage, ibox_content_id){
		var obj = $("#" + ibox_content_id);
		var page = new Page(ibox_content_id);
        var maxpage=maxpage-0;
        var pageHTML= new Array();
        var strn=1;
        var endn=5;
        if(num==1){
        	if(maxpage==num){
        		pageHTML.push(page.disabled());
        	}
        	else{
        		endn=10;
                if(endn>maxpage){
                    endn=maxpage-0+1;
                }
                pageHTML.push(page.firstPage());
                for(i=2;i<endn;i++){
                	 pageHTML.push(page.activityPage(i));
                }
                if(!maxpage){
                	pageHTML.push(page.lastDisabled());
                } else {
                	pageHTML.push(page.lastActivity(num, maxpage));
                }
        	}
        }
        if(num<maxpage&&num>1){
        	pageHTML.push(page.firstActivity(num));
            strn=num-4;
            endn=num-0+5;
            if((num-4)<1){
                strn=1;
                endn=10-0;
                if(endn>(maxpage-0)){
                    endn=maxpage-0+1;
                }
            }

            if(endn>(maxpage-0+1)){
                endn=maxpage-0+1;
                strn=maxpage-8;
                if(strn<1){strn=1;}
            }

            for(i=strn;i<endn;i++){
                if(i==num){
                	pageHTML.push(page.currentPage(i));
                }
                else{
                    pageHTML.push(page.activityPage(i));
                }
            }
            pageHTML.push(page.lastActivity(num, maxpage));
        }
        
        if(num==maxpage && num != 1){
            strn=maxpage-8;
            if(strn<1){strn=1;}
            endn=maxpage-0+1;
            pageHTML.push(page.firstActivity(num));
            for(i=strn;i<endn;i++){
            	if(i==num){
                	pageHTML.push(page.currentPage(i));
                }
                else{
                    pageHTML.push(page.activityPage(i));
                }
            }
            pageHTML.push(page.lastDisabled());
        }
        // 查找 box-page clsss 子节点
        obj = obj.find('.box-page');
        obj.html(pageHTML.join(' '));
        var setpageobj = obj.find("a[href!='javascript:void(0);']");
        if (setpageobj.length) {
            setpageobj.bind("click", function () {
                var path = window.contextPath + '/res/skin/img/spinner.gif';
                $(this).html("<img src='"+path+"'>");
            });
        }
}