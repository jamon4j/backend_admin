(function(list){
	// open debug mode
	window.__debug = true;
	var jsbase,base,cssbase;
	  var scripts = document.getElementsByTagName('SCRIPT');
	  for(var i=0,len=scripts.length;i<len;i++){
	    var src = scripts[i].src;
	    if(src){
	      var idx = src.lastIndexOf("/admin-all.js");
	      if(idx>=0){
	        base = src.substring(0, idx);
	        base = base.substring(0, base.lastIndexOf('/')) + '/';
	        cssbase = base + 'css/';
	        jsbase = base + 'js/'
	        break;
	      }
	    }
	  }
	  if(!base)
	    throw 'base path not found.';
	    
	  XWB_BASE = base;
	
  var s = [];
           
  for(var i=0,len=list.length;i<len;i++){
    // mark some debug info
    if(window.__debug && window.onscriptloadcallback) s.push('<script type="text/javascript">if(window.onscriptloadcallback) onscriptloadcallback("'+list[i]+'");</script>');
    s.push('<script charset="utf-8" type="text/javascript" src="'+list[i]+'"></script>');
  }
  document.write(s.join(''));
})([
'/js/frame/base/xwbapp.js',
'/js/frame/mod/xwbrequestapi.js',
'/js/frame/base/jqext.js',
'/js/frame/admin/template.js',
'/js/frame/base/selectionholder.js',
'/js/frame/base/actionmgr.js',
'/js/frame/base/validation.js',
'/js/frame/base/shortlink.js',
'/js/frame/ui/base.js',
'/js/frame/ui/contextmgr.js',
'/js/frame/ui/layer.js',
'/js/frame/ui/dlg.js',
'/js/frame/mod/validators.js',
'/js/frame/admin/mgr.js',
'/js/frame/admin/xwb-admin.js',
'/js/frame/admin/adminux.js'
]);