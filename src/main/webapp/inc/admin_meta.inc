<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="/static/css/admin/admin.css" media="screen" />
<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/admin/admin.js"></script>
<script type="text/javascript" src="/static/js/admin-all.js"></script>
<script>
$(function(){
    admin.index.init();
    $('#map').click(function(){
        Xwb.use('MgrDlg',{
            dlgcfg:{
                title:'后台管理导航',
                cs : ' win-fixed map '
            },
            modeUrl:Xwb.request.mkUrl('mgr/admin','map'),
            actiontrig : function(e){
                if(e.get('e') == 'reload'){
                    var arr = e.get('data').split('|');
                    //admin.index.selectMainMenu(Number(arr[0]),arr.length == 2 ? Number(arr[1]):null );
                    //this.dlg.close();
                    location.href="?_="+Math.random()+"#"+arr.join(',');
                }
            },
            afterDisplay:function(){
                this.dlg.jq().css({'marginLeft':'0px','marginTop':'0px'});
                this.dlg.center();
            }
        });
    });
});
</script>
<style>
#header h1 {
    background: url("/static/logo72.jpg") no-repeat scroll 0 0 transparent;
    height: 72px;
    left: 18px;
    position: absolute;
    text-indent: -999em;
    top: 1px;
    width: 215px;
}
</style>
