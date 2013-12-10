/**
 * 盘点计划中使用
 */
var ids="";//仓位IDS
	var names="";//仓位名称
	function getDepotId(id){
		$.ajax({ 
            type: "post", 
            url: "/inven_plan/toAlldepots.c", 
            dataType: "json", 
            data:{"id":id},
            async:false,
            success: function (data) {
            		for(var i=0;i<data.length;i++){
            		ids+=data[i].depotId+"`";	
            		names+=data[i].depotName+"、";	
            		}
            		ids=ids.substring(0,ids.length-1);
            		names=names.substring(0,names.length-1);
            }, 
            error: function (XMLHttpRequest, textStatus, errorThrown) { 
            } 
		});
	}
	function tj(id, position) {
		getDepotId(id);
		window.parent.document.getElementById(back_depotid).value = ids;
		window.parent.document.getElementById(back_depotname).value = names;
		$.pdialog.closeCurrent();
	}
	TreeMenu._url = '/createTree.c';
	TreeMenu.getChildren('0', '0', '', '1', 'depot', 'findDepot');