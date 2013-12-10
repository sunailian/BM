/**
 * Theme Plugins
 * @author ZhangHuihua@msn.com
 * @translator 16530202@qq.com
 */
(function($){//以下代码请结合首页#themeList元素结构加以理解
	$.fn.extend({ //在首页面以如下形式调用：$("#themeList").theme({themeBase:"DWZ/themes"});
		theme: function(options){
			var op = $.extend({themeBase:"themes"}, options); //设定默认的相对于首页的主题base路径为themes,可被传入的值覆盖
			var _themeHref = op.themeBase + "/#theme#/style.css"; //定义主题href变量，值构造为DWZ/themes/#theme#/style.css(假定传入的主题路径为DWZ/theme)
			return this.each(function(){ 
				var jThemeLi = $(this).find(">li[theme]"); //获取主题容器#themeList下包含theme属性的li元素
				var setTheme = function(themeName){//根据给定主题字符串设定主题
					$("head").find("link[href$='style.css']").attr("href", _themeHref.replace("#theme#", themeName)); //将主题样式文件的href值动态替换掉
					jThemeLi.find(">div").removeClass("selected"); //移除选中样式
					jThemeLi.filter("[theme="+themeName+"]").find(">div").addClass("selected"); //给当前点击项设定选中样式
					
					if ($.isFunction($.cookie)) $.cookie("dwz_theme", themeName);//将当前主题缓存到cookie
				}
				
				jThemeLi.each(function(index){ //循环包含theme属性的li元素
					var $this = $(this);
					var themeName = $this.attr("theme"); //获取theme属性值
					$this.addClass(themeName).click(function(){ //为当前li添加class属性,值同theme属性值.如<li theme="green"></li>,会变成<li class="green" theme="green"></li>
						setTheme(themeName); //为li的绑定点击事件,设定主题
					});
				});
					
				if ($.isFunction($.cookie)){
					var themeName = $.cookie("dwz_theme");
					if (themeName) {
						setTheme(themeName);//从cookie里取得主题字符串名，设定主题
					}
				}
				
			});
		}
	});
})(jQuery);
