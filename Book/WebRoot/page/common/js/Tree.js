var Tree=new function(){
	this._url="";                //用于请求数据的服务器页面地址
	this._openMark="-";          // 目录节点处于展开状态时的标识
	this._closeMark="+";         //目录节点处于关闭状态时的标识
	this._itemMark=".";          // 非目录节点标识
	this._initld="treelnit";     //树形目录初始div标识
	this._rootData="根目录";      //根节点文字信息
	this._rootId="0";           //根节点ID
	this._boxSuffix="_childrenBox";    //子节点容器后缀
	this.folderType="folder";          //目录节点类型变量
	this.itemType="item";              //非目录节点类型变量
	//初始化根节点
	this.init=function(){
	  // document.getElementById("tree").style.display="";
       document.getElementById("treelnit").innerHTML="";
	   var initNode=document.getElementById("treelnit");       //获取初始div
	   var _node=document.createElement("div");                //创建新div作为根节点
	  // var elementValue=document.getElementById("elementValue"); 
	   _node.id=this._rootId;
	   _node.innerHTML=this.createltemHTML(this._rootId,this.folderType,this._rootData);
	   initNode.appendChild(_node);                             //将根节点加入初始div
	}
	//获取给定节点的子节点
	this.getChildren=function(_parentld,_level){
		//获取页面子节点容器box
		var childBox=document.getElementById(_parentld+this._boxSuffix);
		//如果子节点容器已存在，则直接设置显示状态，否则从服务器获取子节点信息
		if(childBox){
			var isHidden=(childBox.style.display=="none");       //判断当前状态是否隐藏
			//隐藏则显示，如果显示则变为隐藏
			childBox.style.display=isHidden?"":"none";
			
			//根据子节点的显示状态修改父节点标识
			var _parentNode=document.getElementById(_parentld);
			_parentNode.firstChild.innerHTML=isHidden?this._openMark:this._closeMark;
		}else{                        
			var xmlHttp=this.createXmlHttp();                    //创建XmlHttpRequest对象
			xmlHttp.onreadystatechange=function(){
				if(xmlHttp.readyState==4){
					if (xmlHttp.status == 200){
						//调用addChildren函数生成子节点
						Status.setStatusShow(false);
			            Request.showStatus = true;
						Tree.addChildren(_parentld,xmlHttp.responseXML);
					}
				}
			} 
			xmlHttp.open("GET",this._url+_parentld,true);
			xmlHttp.send(null);
		}
	}
	
	//根据获取的xmlTree信息，设置指定节点的子节点
	this.addChildren=function(_parentld,_data){
		var _parentNode=document.getElementById(_parentld) ;  //获取父节点
		_parentNode.firstChild.innerHTML=this._openMark ;     //设置节点前标记为目录展开形式
		//创建一个容器，成为box,用于存放所有子节点
		var _nodeBox=document.createElement("div");
		//容器的id规则为：在父节点id后加固定后缀
		_nodeBox.id=_parentld+this._boxSuffix;
		_nodeBox.className="box";                             //样式名称为box, div.box样式会对此节点生效
		_parentNode.appendChild(_nodeBox);                    //将子节点box放入父节点中
		
		//获取所有item节点
		var _children=_data.getElementsByTagName("root")[0].childNodes;
		
		var _child=null;       //声明_child变量用于保存每个子节点
		var _childType=null;   //声明_childType变量用于保存每个子节点类型
		for(var i=0;i<_children.length;i++){
		   _child=_children[i];
		   _node=document.createElement("div");                //每个子节点对应一个新div
		   _node.id=_child.getAttribute("id");                 //节点的id值就是获取数据中的id属性值
		    //目录节点只需传递id，节点类型，节点数据
		    _node.innerHTML=this.createltemHTML(_node.id,_childType,_child.firstChild.data);
			_nodeBox.appendChild(_node);                       //将创建好的节点加入子节点box中
		}
		_nodeBox.style.cssText="border-left:0px solid #ccc;margin-left:0px;margin-top:5px;padding-left:10px";	
	}
	
	//创建节点的页面片断
	this.createltemHTML=function(itemld,itemType,itemData){
		   //目录节点的class属性以folder开头，并且onclick事件调用Tree.getChildren函数
		   return '<span class="folderMark" onclick="Tree.getChildren('+itemld+')">'+this._closeMark+'</span>'+'<span class="folder"><input type="checkbox" id="cbxItem'+itemld+'" name="cbxItemName" /><a id="a'+itemld+'" href="javascript:void(0);" onclick="Tree.getChildren('+itemld+');">'+itemData+'</a></span>';
	}
	
	//单击叶子节点后的动作，目前只是弹出对话框，可修改为链接到具体的页面
	this.clickltem=function(_link){
	   alert("当前节点可以链接到页面"+_link+"。");
	}
	
	//用于创建XMLHttpRequest对象
	this.createXmlHttp=function(){
	   var xmlHttp=null;
	   //根据window.XmlHttpRequest对象是否存在使用不同的创建方式
	   if(window.XMLHttpRequest){
		   xmlHttp=new XMLHttpRequest();       //FireFox,Opera等浏览器支持的创建方式
	   }else{
	 	   xmlHttp=new ActiveXObject("Microsoft.XMLHTTP"); //IE浏览器支持的创建方式
	   }
	   return xmlHttp;
	}
	
	this.makeSure =function(){
	    var input = document.getElementsByTagName("input");
	    var count = 0;
	    var elementId=document.getElementById("elementId");
	  //  var elementHtml=document.getElementById("elementValue");
	    var r=document.getElementsByName("cbxItemName");
	    var checkIdValues="";
	    var checkHtmlValues="-99";
              for(var i = 0; i < input.length; i ++){ 
                  if(input[i].type == "checkbox" && input[i].id.indexOf("cbxItem") != -1){ 
                      if (input[i].checked){ //这个地方是获取你选定了的的checkbox的Value
                        if(checkHtmlValues=="-99"){
                           var checkValue=input[i].id;
                           var idValue=checkValue.substring(7);
                           var spanValue=document.getElementById("a"+idValue).innerHTML;
                           checkHtmlValues=spanValue;
                           checkIdValues=idValue;
                        }else {
                           var checkValue=input[i].id;
                           var idValue=checkValue.substring(7);
                           var spanValue=document.getElementById("a"+idValue).innerHTML;
                           checkHtmlValues=checkHtmlValues+","+spanValue;
                           checkIdValues=checkIdValues+","+idValue;
                        }
                        count ++; 
                      }                
                   }          
              }    
                          
              if(count == 0){               
               alert("请选择您要进行操作的数据！");
               return false;
              }
              elementId.value=checkIdValues;
              elementHtml.value=checkHtmlValues;
	    this.closeTree();
	}
	this.closeTree=function (){
	    document.getElementById("treelnit").innerHTML="";
	    document.getElementById("tree").style.display="none";
	}
}