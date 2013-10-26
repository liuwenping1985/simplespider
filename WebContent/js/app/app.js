(function(exportObject,$){
	
	var ApplicationContext=App=function(){
		this.init.apply(this,arguments);
	};
	var fn = App.fn = App.prototype;
	fn.init=function(){
		
	};
	fn.extend=function(obj){
		for(var p in obj){
			this[p] = obj[p];
		}
	};
	App.include = function(obj){
		for(var p in obj){
			fn[p]= obj[p];
		}
		
	};
	exportObject.ApplicationContext = ApplicationContext;
	
})(window,jQuery);