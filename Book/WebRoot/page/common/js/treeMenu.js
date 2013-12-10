var TreeMenu=new function()
{   
    this._url=null;

    document.write('<style type="text/css">');
    document.write('.treeCheckBox{height:11px; width:11px;vertical-align:middle}'); 
    document.write('.treeImg{cursor:pointer;vertical-align:text-bottom;margin-right:1px}');
    document.write('</style>');

	this.icon=new Array();
	this.icon["home1"]='/page/common/img/tree/home1.gif';
	this.icon["home2"]='/page/common/img/tree/home2.gif';
	this.icon["org1"]='/page/common/img/tree/org1.gif';
	this.icon["org2"]='/page/common/img/tree/org2.gif';
	this.icon["dept1"]='/page/common/img/tree/dept1.gif';
	this.icon["dept2"]='/page/common/img/tree/dept2.gif';	
	this.icon["member"]='/page/common/img/child.gif'; 
     
    ///////////////////////////////
	this.getChildren=function(_parentId,checkbox,nodeCode,_asynchronous,unique,statement,hasChildren)
	{
		  if (this.alreadyGetChildren(unique))
		  {
		    var childContainer=document.getElementById(unique+"_subContainer");
		    if (childContainer)
		    {
		        childContainer.style.display=(childContainer.style.display=="none")?"":"none";
		        var _parentNode=document.getElementById(unique);
		        if (_parentNode.firstChild && _parentNode.firstChild.tagName=="IMG")
		        {
		            _parentNode.firstChild.src=(childContainer.style.display=="none")?this.icon["home1"]:this.icon["home2"];
		        }
		    }
		    return;
		}
		
		var processRequest=function(obj)
		{
		    TreeMenu.addChildren(_parentId,checkbox,nodeCode,_asynchronous,statement,obj.responseXML,unique);         
		}
		Request.send(this._url+"?parentId="+_parentId+"&checkbox="+checkbox+"&nodeCode="+nodeCode+"&asynchronous="+_asynchronous+"&statement="+statement,"",processRequest);
	}	
    /////////////////////////////////
	this.addChildren=function(_parentId,checkbox,nodeCode,_asynchronous,statement,_data,unique)
	{   
	    if (this.alreadyGetChildren(unique))
	    {	     
	        return;
	    }
	    var _parentNode=document.getElementById(unique);
		
		if(_parentNode==null)return;
	    if (_parentNode.firstChild && _parentNode.firstChild.tagName=="IMG")
	    {
	        _parentNode.firstChild.src=this.icon["home2"];
	    }	    	       
	    _nodeContainer=document.createElement("div");
		_nodeContainer.id=unique+"_subContainer";
		_parentNode.appendChild(_nodeContainer);
		if(_data.getElementsByTagName("root").length==0)return ;
		var _children=_data.getElementsByTagName("root")[0].childNodes;
		var _child=null;
		var _point=this;
		//var strHasChildren='';
		var strParentId='';
		var strUnique='';
		var strHasChildren = '';
		var strStatement='';
		var strCheckbox='';
		var strAsynchronous='';
		var strNodeCode = '';
		//alert(_children.length);
		for(var i=0; i<_children.length; i++)
		{
			_child=_children[i];				
			_node=document.createElement("div");
			if (i!=_children.length-1)
			{
			    _node.style.cssText="padding-bottom:5px";
			}			
			_node.innerHTML="";
			strUnique=_child.getAttribute("unique");
			strParentId=_child.getAttribute("parentId");
			strHasChildren=_child.getAttribute("hasChildren");
			strStatement=_child.getAttribute("statement");
			strCheckbox=_child.getAttribute("checkbox");
			strAsynchronous=_child.getAttribute("asynchronous");
			strNodeCode=_child.getAttribute("nodeCode");
			_node.id=strUnique;
			if(strHasChildren =='0'||strAsynchronous=='0')//没有子节点
			{
			    _node.innerHTML+='<img class="treeImg" onclick="" src="'+this.icon["home2"]+'"/>';
			    if(checkbox=="1"){
			    	_node.innerHTML+='<input type="checkbox" id="cbxItem'+strUnique+'" name="cbxItemName" />'
			    }
			    if(strAsynchronous=='1'){
			    	_node.innerHTML+='<span style="cursor:pointer;line-height:16px;height:16px;a:link{text-decoration: none}" name="treeText" onclick="treeNodeChoosed(this);"> <a href="#"  onclick="'
    					+'tj'+"(\'"+strUnique+"\'"+','+"\'"+_child.firstChild.data+"\'"+','+"\'"+strNodeCode+"\'"+')">'+_child.firstChild.data+'</a>'+'</span>';			    				
			    }else{
			    _node.innerHTML+='<span style="line-height:16px;height:16px;a:link{text-decoration: none}" name="treeText" onclick="treeNodeChoosed(this);"> '+_child.firstChild.data+'</span>';
			    }			    
			}else{
				 _node.innerHTML+='<img class="treeImg" onclick="TreeMenu.getChildren('+"\'"+strParentId+"\'"+','+"\'"+strCheckbox+"\'"+','+"\'"+strNodeCode+"\'"+','+"\'"+strAsynchronous+"\'"+','+"\'"+strUnique+"\'"+','+"\'"+statement+"\'"+')" src="'+this.icon["home1"]+'"/>';
				if(checkbox=="1"){
			    	_node.innerHTML+='<input type="checkbox" id="cbxItem'+strUnique+'" name="cbxItemName" />'
			    }
			     if(strAsynchronous=='1'){
			    	_node.innerHTML+='<span style="cursor:pointer;line-height:16px;height:16px;a:link{text-decoration: none}" name="treeText" onclick="treeNodeChoosed(this);"> <a href="#"  onclick="'
    					+'tj'+"(\'"+strUnique+"\'"+','+"\'"+_child.firstChild.data+"\'"+','+"\'"+strNodeCode+"\'"+')">'+_child.firstChild.data+'</a>'+'</span>';			    				
			    }else{
			    _node.innerHTML+='<span style="line-height:16px;height:16px;a:link{text-decoration: none}" name="treeText" onclick="treeNodeChoosed(this);"> '+_child.firstChild.data+'</span>';	
			    }
			}
			_nodeContainer.appendChild(_node);					
		}
		_nodeContainer.style.cssText="border-left:0px solid #ccc;margin-left:0px;margin-top:5px;padding-left:10px";	    
	}
	
	/////////////////////////////////
	this.alreadyGetChildren=function(_nodeId)
	{ 
	    var obj=document.getElementById(_nodeId+"_subContainer");
	    if (obj)
	    {	       
	        return true;               
	    }
	    return false;	    
	}	
}

////////////////////////////////
function treeNodeChoosed(_obj)
{
    var choosedColor="lightblue";
    var unChoosedColor="";

    if (_obj.style.backgroundColor==choosedColor)
    {
        _obj.style.backgroundColor=unChoosedColor;           
    }
    else
    {
        var allNodeText=document.getElementsByTagName("SPAN");
        for (var i=0; i<allNodeText.length; i++)
        {
            allNodeText[i].style.backgroundColor=unChoosedColor;
        }
        _obj.style.backgroundColor=choosedColor;
    }    
}