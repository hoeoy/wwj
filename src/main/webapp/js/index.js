$(function(){
	$(".treeview-menu>li").click(function(){
		var ul=$(this).parent().parent();
		ul.find('li').each(function(){
			$(this).removeClass('treeview-menu_active');
		})
		$(this).addClass('treeview-menu_active');
	})


	var user = JSON.parse(localStorage.user);

		$.ajax({
			type: 'post',
			data:JSON.stringify({pk_role:user.pk_role,user_code:user.user_code,pk_user:user.pk_user}),
			dataType:"json",
			url: getUrl() + '/Api/index/loadNavigation',
			contentType: "application/json;charset=UTF-8",
			success: function (data) {
				if (data.success) {

					$.each(data.data,function(idx,obj){

						//var lihtml = "<li class='treeview' id='"+obj.navigation_code+"'> <a href='#'><img src='"+obj.icon_path+"'> <span>"+obj.navigation_name+"</span> <span class='pull-right-container'> </span> </a>";
                        //
						//if(obj.children_data != null){
						//	lihtml = lihtml+ "<ul class='treeview-menu' style='display: none;'>";
						//	$.each(obj.children_data,function(idxsub,objsub){
						//		lihtml = lihtml + "<li><a href=\"javascript:indexNavigate('"+objsub.navigation_path+"')\"><i class='"+objsub.icon_path+"'></i> "+objsub.navigation_name+"</a></li>";
						//	});
						//	lihtml = lihtml + "</ul>";
						//}
						//lihtml = lihtml + "</li>";
						//$("#navigation").append(lihtml);

						var temp = "<li class='treeview'>"

						temp = temp + "<a href='#'><i class='"+obj.icon_path+"'style='font-size:16px'></i> <span class='left_tree_list_title'>&nbsp;"+obj.navigation_name+"</span></a>"

						if(obj.children_data != null){
							temp = temp + "<ul class='treeview-menu' style='display: none;'>";

							$.each(obj.children_data,function(idxsub,objsub){
								temp = temp + "<li><a href=\"javascript:indexNavigate('"+objsub.navigation_path+"');\"><span class='"+objsub.icon_path+"'></span>&nbsp;"+objsub.navigation_name+"</a></li>";
							});
							temp = temp + "</ul>";
						}

						temp = temp + "</li>"
						$("#navigation").append(temp);
					});


				} else {
					Notify('初始化目录失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
				}
			},
			error: function (data) {
				window.location.href = "login.html";
			}
		});


});
