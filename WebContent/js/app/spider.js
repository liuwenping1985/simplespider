(function(exportedObj,$){
	
	var grapUrl = "api/start.do",fetchUrl="api/fetch.do",parseUrl="api/parse.do";
	
	ApplicationContext.include({
		start:function(strUrl){
			$.post(grapUrl,{"url":strUrl},function(msg){
				alert("操作结果:"+msg);
			});
		},
		fetch:function(cont){
			$.post(fetchUrl,{url:this.parseUrls[0]},function(msg){
				$(cont).empty();
				$(cont).html(msg);
			});
		},
		__getParseUrl:function(){
			
			return $(this.__url__).val();
		},
		parse:function(){
			var ctx = this;
			$.post(parseUrl,{num:1},function(data){
				window.console.log(data);
				ctx.parseUrls = data;
			});
		},
		applyView:function(param){
			var ctx = this;
			if(param.fireBtn){
				$(param.fireBtn).click(function(){
					ctx.start(ctx.__getParseUrl());
				});
			}
			if(param.urlInpt){
				
				ctx.__url__ = param.urlInpt;
			}
			if(param.fetchResult){
				$(param.fetchResult).click(function(){
					ctx.fetch(param.fetchResultContent);
				});
			}
			if(param.parseResult){
				$(param.parseResult).click(function(){
					ctx.parse();
				});
			}
		}
		
		
	});
	
	exportedObj.spider = new ApplicationContext();
	
	
	
	
	
})(window,jQuery);