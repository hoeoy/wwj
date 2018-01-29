var endtimes;

$(function(allowanceCard){
	// var department_code;
	var batch_code;// 定义全局批次号，用于点击的时候，加载下面的明细列表;
	//初始化部门树
	 allowanceCard.initTreeView = function () {
	        $.ajax({
	            type: 'get',
	            data: {company_code: "1"},
	            dataType: "json",
	            url:getUrl()+ '/Api/dept/retrieveTree',
	            contentType: "application/json;charset=UTF-8",
	            success: function (data) {
	                if (data.success) {
	                    var nodes = JSON.stringify(listToTree(data.data));
	                    $('#left-tree').treeview({
	                        data: nodes,
	                        onhoverColor:'#5bc0de',
	                        onNodeSelected: function (event, node) {
	                        	$.ajax({
                                    type: 'get',
                                    url: getUrl()+"/Api/AllowanceCard/retrieve" + "?" + Math.random(),
                                    data: {department_code: node.id, operator_type: "charge", offset: 0, limit: 9999},
                                    dataType: "json",
                                    contentType: "application/json;charset=UTF-8",
                                    success: function (data) {
                                        $("#left_psns ul").html("");
                                        $.each(data.rows, function (idx, obj) {
                                            $("#left_psns ul").append("<li class='list-group-item'><a href='#'><span  data-pk-staff='" + obj.pk_staff + "' data-pk-card='" + obj.pk_card + "' data-card-code='" + obj.card_code + "' data-staff-code='" + obj.staff_code + "'>" + obj.staff_code +"&nbsp;"+ obj.staff_name +"</span><span style='margin-left: 15px;'>" + filter(obj.def1) + "</span></a></li>");
                                        });

                                    },
                                    error: function (data) {
										Notify('获取数据失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                                    }
                                });
	                        },
	                        onNodeUnselected: function (event, node){
	                            department_code = '';
	                        }
	                    });
	                } else {
	                    alert("初始化部门树失败!:" + data.msg);
	                }
	            },
	            error: function (data) {
	                alert("初始化部门树失败!");
	                onError();
	            }
	        });
	    }
	 //下拉框
//加载头部表格

    function filter(val) {
        if(val){
            console.log('已限制')
            return '已限制';
        }else return '';

    }
allowanceCard.initTable = function () {
	        $('#maintable').bootstrapTable({
	            url: getUrl()+'/Api/AllowanceCard/queryMainData',         //请求后台的URL（*）
	            method: 'get',                      //请求方式（*）
	            toolbar: '#toolbar1',                //工具按钮用哪个容器
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: false,                   //是否显示分页（*）
	            sortable: false,                     //是否启用排序
	            sortOrder: "asc",                   //排序方式
	            queryParams: function (params) {
	                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	                    limit: 9999,   //页面大小
	                    offset: params.offset,  //页码
	                    start_time: $("#start_time").val(),//起始时间
	                    end_time:endtimes ,//结束时间
	                    state:$("#status").val(),
	                };
	                return temp;
	            },//传递参数（*）
	            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber: 1,                       //初始化加载第一页，默认第一页
	            pageSize: 10,                       //每页的记录行数（*）
	            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            strictSearch: false,
	            showColumns: false,                  //是否显示所有的列
	            showRefresh: true,                  //是否显示刷新按钮
	            minimumCountColumns: 2,             //最少允许的列数
	            clickToSelect: false,                //是否启用点击选中行
	            height: 380,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	            //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
	            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            columns: [{
	                checkbox: true
	            },{
	                field: 'pk_allowance_num',
	                title: ''
	            },{
	                field: 'allowance_num_code',
	                title: '批次补贴号',
	                formatter: function(value, row, index){return '<a class="pk_allow_code_check" id="'+row.pk_allowance_num+'" href ="javascript:;">'+value+'</a>';}
	            },{
	                field: 'staff_name',
	                title: '操作员'
	            },{
	                field: 'ts',
	                title: '登记时间'
	            },{
	                field: 'stateName',
	                title: '状态'
	            }, {
	                field: 'memo',
	                title: '备注'
	            }],
	            //选择行加载下面的明细表格
	            /*onClickCell: function (field, value, row, $element) {
	            	alert(field);
	            	alert(value);
	           // onClickRow: function (item, $element) {
	                //var selected_btn = $('#maintable').bootstrapTable("getSelections");
	                batch_code = item.pk_allowance_num;
	                console.log(item.pk_allowance_num);
	                allowanceCard.initDetailTable();
	                allowanceCard.rerefreshDetail();
	                //console.log(item.pk_allowance_num);
	            }*/
	            onCheck: function () {
	                var selected_btn = $('#maintable').bootstrapTable("getSelections");
	                if (selected_btn.length >= 1) {
	                    $("#subsidyProvide").removeAttr('disabled');
	                    $("#subsidyDelete").removeAttr('disabled');
	                } else {
	                    $("#subsidyProvide").attr('disabled', 'disabled');
	                    $("#subsidyDelete").attr('disabled', 'disabled');
	                }
	            },
	            onUncheck: function () {
	                var selected_btn = $('#maintable').bootstrapTable("getSelections");
	                if (selected_btn.length >= 1) {
	                    $("#subsidyProvide").removeAttr('disabled');
	                    $("#subsidyDelete").removeAttr('disabled');
	                }else{
	                    $("#subsidyProvide").attr('disabled', 'disabled');
	                    $("#subsidyDelete").attr('disabled', 'disabled');
	                }
	            },
	            onCheckAll: function () {
	                $("#subsidyProvide").removeAttr('disabled');
	                $("#subsidyDelete").removeAttr('disabled');
	            },
	            onUncheckAll: function () {
	                $("#subsidyProvide").attr('disabled', 'disabled');
	                $("#subsidyDelete").attr('disabled', 'disabled');
	            }
	        });
	      //点击批次号加载明细表方法
	        $("#maintable").on("click",".pk_allow_code_check",function(){
                subsidyVue.isShow = true;
                $(".details-modal").modal('show');
	         	batch_code = $(this).prop("id");//把pk_allowance_num放在A标签上，所以取属性值即可取得
                subsidyVue.query();
	             //allowanceCard.initDetailTable();//初始化明细表
	             //allowanceCard.rerefreshDetail();//刷新明细表
	         });
	        
	        $('#maintable').bootstrapTable('hideColumn', 'pk_allowance_num');
}
//加载明细表格
allowanceCard.initDetailTable = function () {
    $('#detailtable').bootstrapTable({
        url: getUrl()+'/Api/AllowanceCard/queryDeatilData',         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                pk_allowance_num:batch_code//批次号
            };
            return temp;
        },//传递参数（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [{
            checkbox: false
        },/* {
            field: 'pk_allowance',
            title: ''
        },*/ {
            field: 'allowance_num_code',
            title: '批次补贴号'
        }, {
            field: 'staff_code',
            title: '人员编码'
        }, {
            field: 'staff_name',
            title: '姓名'
        }, {
            field: 'money_allowance',
            title: '补贴金额'
        }, {
            field: 'allowanceTypeName',
            title: '补贴类型'
        },
        {
            field: 'statename',
            title: '是否发放'
        },
        {
            field: 'operator',
            title: '操作员'
        },
        {
            field: 'ts',
            title: '操作时间'
        }]
    });
    $('#detailtable').bootstrapTable('hideColumn', 'pk_allowance');
}
//刷新明细表格方法
allowanceCard.rerefreshDetail= function refresh() {
    var opt = {           //Api/AllowanceCard/queryDeatilData
            url:getUrl()+ "/Api/AllowanceCard/queryDeatilData",
            silent: true,

            query: JSON.stringify({
                //department_code: department_code,
                //operator_type: "charge",
            	pk_allowance_num:batch_code,//批次号
                limit: 9999,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#detailtable").bootstrapTable('refresh', opt);
    }
//查询是调用的刷新表格方法
allowanceCard.refresh = function refresh() {
    var opt = {
        url:getUrl()+ "/Api/AllowanceCard/queryMainData",
        silent: true,
        query: JSON.stringify({
            //department_code: department_code,
            //operator_type: "charge",
        	start_time: $("#start_time").val(),//起始时间
            end_time: endtimes,//结束时间
            limit: 999,
            offset: 0,
            type: 1,
            level: 2
        })
    };

    $("#maintable").bootstrapTable('refresh', opt);


}

allowanceCard.initButton = function () {
	//补贴发放
	$("#subsidyProvide").click(function(){
		 $('#subsidyProvide').attr('disabled',true);
		 var selected_values = $('#maintable').bootstrapTable("getSelections");
		 var isSendNum =  0;//统计已发放的
		 var isNoSendNum = 0;//统计未发放的
		 var ids = "";
		 for(i in selected_values){
			 //alert(selected_values[i].state);
			 if(selected_values[i].state==2){
				 isSendNum++;
			 }else{
				 isNoSendNum++;
				 ids += ","+selected_values[i].pk_allowance_num;
			 }
			 
		 }
		 var isConfirm = false;
		 if(isSendNum>0){
			alert("您选择的记录中包含"+isSendNum+"条已发放的记录,请重新选择");
			 return;
		 }
		 /*if(isConfirm){//如果选择是，直接返回，重新选择
			 return;
		 }*/
		 if(ids.length>0){
			 ids = ids.substr(1);
		 }else{
			 //alert("成功发放0条");
			 Notify("成功发放0条", 'top-right', '5000', 'primary', 'fa-circle-o', true);

			 return;
		 }
		 //alert(ids);
		 $.ajax({
	        cache : true,
	        type: 'post',
	        url: getUrl()+'/Api/AllowanceCard/butieSend',
	       // contentType: "application/json;charset=UTF-8",
	        dataType: "json",
	        data: {"ids":ids,operator:JSON.parse(localStorage.user).pk_user},
	        success: function (data) {
	            if (data.success) {
	               // alert("补贴发放成功");
					Notify("补贴发放成功", 'top-right', '5000', 'primary', 'fa-circle-o', true);
					allowanceCard.refresh();
	                allowanceCard.rerefreshDetail();
	            } else {
					Notify("补贴发放失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
	            }
                $('#subsidyProvide').attr('disabled',false);
	        },
	        error: function (data) {
                $('#subsidyProvide').attr('disabled',false);
				Notify("补贴发放失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);

	        }
	    });
	});
	
	 
	//删除补贴
	$("#subsidyDelete").click(function(){
		//alert("hhhh");
		 var selected_values = $('#maintable').bootstrapTable("getSelections");
		 //alert(selected_values.length);
		 var ids = "";
		 var failNum=0;
		 //拼接选中行的pk_allowance_num，用,分割开
		 for(i in selected_values){
			 if(selected_values[i].stateName=="已发放"){
				 failNum++;
			 }else{
				 ids += ","+selected_values[i].pk_allowance_num;
			 }
		 }
		 if(ids.length>0){
			 ids = ids.substr(1);
		 }
		 //alert(ids);
		 
		 $.ajax({
	        cache : true,
	        type: 'post',
	        url: getUrl()+'/Api/AllowanceCard/deleteRecord',
	       // contentType: "application/json;charset=UTF-8",
	        dataType: "json",
	        data: {"ids":ids},
	        success: function (data) {
	            if (data.success) {
	                //alert("成功删除"+data.message+"条记录，失败删除"+(selected_values.length-data.message)+"条记录");
					Notify("成功删除"+data.message+"条记录，失败删除"+(selected_values.length-data.message)+"条记录", 'top-right', '5000', 'primary', 'fa-circle-o', true);

					allowanceCard.refresh();
	            } else {
	               // alert("成功删除0条记录，失败删除"+(selected_values.length)+"条记录");
					Notify("成功删除0条记录，失败删除"+(selected_values.length)+"条记录", 'top-right', '5000', 'primary', 'fa-circle-o', true);

				}
	        },
	        error: function (data) {
				Notify("成功删除0条记录，失败删除"+(selected_values.length)+"条记录", 'top-right', '5000', 'primary', 'fa-circle-o', true);
				//alert("成功删除0条记录，失败删除"+(selected_values.length)+"条记录");
	        }
	    });
	});

	 //查询
    $("#btn_query").click(function () {
    	//alert("s");

		if($("#end_time").val()==""){
			endtimes="";
		}else{
			var t1=parseInt($("#end_time").val().substr($("#end_time").val().length-2,2))+1;
			if(t1<10){
				t1='0'+t1;
			}
			var t2=$("#end_time").val().substr(0,$("#end_time").val().length-2);
			endtimes=t2+t1;
		}
		console.log($("#start_time").val(),endtimes);
    	allowanceCard.refresh();
    });
    //右边列表选中事件
	$("#right_psns ul").on('click', 'li', function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
        //$(this).siblings().removeClass("selected");
    });
	//左边列表选中事件
    $("#left_psns ul").on('click', 'li', function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
        //$(this).siblings().removeClass("selected");
    });


	//往右移动一个事件
	$("#move_right_one").click(function () {
		var leftitems = $("#left_psns ul li");
		$.each(leftitems, function (idx, obj) {
			if ($(obj).hasClass("selected")) {
				var right_psns = $("#right_psns ul li");
				var flag = true;
				$.each(right_psns, function (idx1, obj1) {
					if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
						flag = false;
					}
				});
				if (flag) {
					$("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
					$(obj).remove();
					//$(obj).removeClass("selected");
				} else {
					if (confirm("人员已经存在，确认重复添加？")) {
						$("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
						$(obj).removeClass("selected");
					}
				}
			}
		});
		//$("#right_psns ul").children().removeClass("selected");
	});
	//往右边移动全部事件
	$("#move_right_all").click(function () {
		var leftitems = $("#left_psns ul");
		var right_psns = $("#right_psns ul li");
		var flag = true;
		$.each(leftitems.children(), function (idx, obj) {
			$.each(right_psns, function (idx1, obj1) {
				if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
					flag = false;
				}
			});
		});

		if (flag) {
			$("#right_psns ul").append($(leftitems).html());
			$("#right_psns ul").children().removeClass("selected");
			$("#left_psns ul").html("");
		} else {
			if (confirm("存在重复人员,确认添加？")) {
				$("#right_psns ul").append($(leftitems).html());
				$("#right_psns ul").children().removeClass("selected");
			}
		}

	});
	//往左边移动一个事件
	$("#move_left_one").click(function () {
		var right_psns = $("#right_psns ul li");
		$.each(right_psns, function (idx, obj) {
			if ($(obj).hasClass("selected")) {
				var leftitems = $("#left_psns ul li");
				var flag = true;
				$.each(leftitems, function (idx1, obj1) {
					if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
						flag = false;
					}
				});
				if (flag) {
					$("#left_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
					$(obj).remove();
					//$(obj).removeClass("selected");
				} else {
					if (confirm("人员已经存在，确认重复添加？")) {
						$("#right_psns ul").append("<li class='list-group-item'>" + $(obj).html() + "</li>");
						$(obj).removeClass("selected");
					}
				}
			}
		});
		// $("#left_psns ul").children().removeClass("selected");

		/*  var rightitems = $("#right_psns ul li");
		 $.each(rightitems, function (idx, obj) {
		 if ($(obj).hasClass("selected")) {
		 $(obj).remove();
		 }
		 });*/
	});
	//往左边移动全部事件
	$("#move_left_all").click(function () {
		var leftitems = $("#left_psns ul li");
		var right_psns = $("#right_psns ul");
		var flag = true;
		$.each(right_psns.children(), function (idx, obj) {
			$.each(leftitems, function (idx1, obj1) {
				if ($(obj).children().children().attr("data-pk-staff") == $(obj1).children().children().attr("data-pk-staff")) {
					flag = false;
				}
			});
		});

		if (flag) {
			$("#left_psns ul").append($(right_psns).html());
			$("#left_psns ul").children().removeClass("selected");
			$("#right_psns ul").html("");
		} else {

		}

	});



	//点击导入按钮
     $("#upload").click(function(){
    	 //alert(JSON.parse(localStorage.user).pk_user);
    	 $("#operator").val(JSON.parse(localStorage.user).pk_user);
    	 var form = $("#commentForm")[0];
		 var formData = new FormData(form);//1.FormData//dom对象;
         $("#upload").attr({disabled:true});
    	 $.ajax({  
    	        url:getUrl()+'/Api/AllowanceCard/ULE',
    	        contentType: false, //3.必须false才会避开jQuery对 formdata 的默认处理, XMLHttpRequest会对 formdata 进行正确的处理 
	       		processData: false, //4.必须false才会自动加上正确的Content-Type
	       		type:"post",
    	        data:formData,
    	        dataType:'json',                           //服务器返回的格式
    	        success:function(data){                   //服务器响应成功时的处理函数  
    	        	if(data.success){
						Notify("文件导入成功,"+data.message, 'top-right', '5000', 'primary', 'fa-circle-o', true);
						$("#uploadExcelModal").modal('hide');
						allowanceCard.refresh();
                        $("#upload").attr({disabled:false});
    	        	}else{
                                subsidyVue.errMsg = data.errCode ;
                                subsidyVue.errMsgContent = data.operateCode;
                                $('.tip-modal').modal('show');
                        $("#upload").attr({disabled:false});
						//Notify("文件导入失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
			 }
    	        	
    	           
    	        },  
    	        error:function(data){ //服务器响应失败时的处理函数
					Notify("文件导入失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
				}
    	        });  
    	 
     });
     //点击取消按钮的时候刷新主表
     $("#pldismiss").click(function(){
    	 allowanceCard.refresh();
     });

//点击充值按钮的事件
     $("#plokBtn").click(function(){
         var count = $('#right_psns .list-group .list-group-item').length;
         var plchargemoney = $("#plchargemoney").val();
         var info = "<p style='font-size: 20px;margin-top: 15px;text-align: center'>总共登记" + "<span style='margin: 15px;color: red;'>" + count + "</span>"  + "笔补贴,共" + "<span style='margin: 15px;color: red;'>" + plchargemoney * count + "</span>" + "元</p>";
         var isChecked = $("#clear_subdify").is(":checked");
         console.log(isChecked);
         // var isChecked = $("#clear_subdify").val();
         if(plchargemoney == "" || plchargemoney == 0 ){
             //alert("充值金额错误");
             Notify("充值金额错误", 'top-right', '5000', 'danger', 'fa-times-circle', true);
             return;
         }
         $('#info').html(info);
         $('#confirmModal').modal('show')
	 })
     $("#confirm").click(function(){

         var plchargemoney = $("#plchargemoney").val();
         var isChecked = $("#clear_subdify").is(":checked");
         console.log(isChecked);

         var batchChargeItems = $("#right_psns ul").children();
         if(batchChargeItems.length == 0){
             Notify('请选择人员!', 'top-right', '5000', 'info', 'fa-exclamation-circle', true);
             return;
         }

         var params = [];

         $.each(batchChargeItems,function(idx,obj){
             var param = {
                 charge_money:plchargemoney,
                 isChecked:isChecked,//判断是否清零
                 pk_staff:$(obj).children().children().attr("data-pk-staff"),
                 pk_card:$(obj).children().children().attr("data-pk-card"),
                 card_code:$(obj).children().children().attr("data-card-code"),
                 staff_code:$(obj).children().children().attr("data-staff-code"),
                 charge_type:"0",
                 operator:JSON.parse(localStorage.user).pk_user,
             };
             params.push(param);
         });
         $("#confirm").attr('disabled',true);
         $.ajax({
             cache : true,
             type: 'post',
             url: getUrl()+'/Api/AllowanceCard/butiecharge',
             contentType: "application/json;charset=UTF-8",
             dataType: "json",
             data: JSON.stringify(params),
             success: function (data) {
				 console.log(data);
                 if (data.success) {
					 Notify("充值成功", 'top-right', '5000', 'primary', 'fa-circle-o', true);
                     $('#confirmModal').modal("hide");
                     $('#subsidyRModal').modal("hide"); $("#confirm").attr('disabled',false);
					 allowanceCard.refresh();
                     //chargecard.refresh();
                 } else {

					 Notify("充值失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
                 }
             },
             error: function (data) {
				 console.log(data);
				 Notify("充值失败", 'top-right', '5000', 'danger', 'fa-times-circle', true);
             }
         });

         $("#plcharge").modal('hide');

     });
}
//vue代码，可以去掉
    var subsidyVue=new Vue({
        el:'#all_content',
        data: {
            selected:'',
            status: [
                { text: '全部', value: '' },
                { text: '已发放', value: 2 },
                { text: '未发放', value: 0 }
            ],
			startTime:new Date(new Date(new Date()).getTime() - new Date().getDate() * 86400000 + 86400000).Format("yyyy-MM-dd"),
			endTime:(new Date()).Format("yyyy-MM-dd"),money_allowance:'',
            errMsg:'',
            errMsgContent:'',
            isShow:false,tableData:[],
            pagination:{currentPage:1,pageSizes:[20,30],pageSize:20,total:0},
        },
        methods:{
            showExcelErrMsg:function(){
                var  p='<html><head><title>错误详情</title></head><body><div>'+ this.errMsgContent +'</div></body></html>';
                var  w=window.open('about:blank');
                w.document.write(p);
            },
            init:function(){
               /* laydate({
                    key: '1989-10-14',
                    elem: '#start_time', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                    //  event: 'focus' //响应事件。如果没有传入event，则按照默认的click
                });
                laydate({
                    elem: '#end_time', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                    //  event: 'focus' //响应事件。如果没有传入event，则按照默认的click
                });*/
            },
            checkSubsidy:function(e){
                alert(e.currentTarget.outerText);
            },
            subsidyRegister:function(){
                //$("#subsidyRModal").modal('show');
                $('#subsidyRModal').modal({backdrop: 'static', keyboard: false});
            },
            inputExcel:function(){
                $("#uploadExcelModal").modal('show');
            },
            changeCurrtentPage:function (val) {
                this.pagination.currentPage = val;this.query();
            },
            indexMethod:function(index){
                return index + 1;
            },//
            query:function () {
            	this.querySum();
                this.tableData = [];
                var data =  {
                    pk_allowance_num:batch_code,//批次号
                    pageSize: this.pagination.pageSize,
                    pageNo: this.pagination.currentPage,
                    /*  type: 1,
                      level: 2*/
                };
                var _this = this;

                $.ajax({
                    url:getUrl()+ '/Api/AllowanceCard/newqueryDeatilData',
                    type:'GET',
                    dataType: "json",
                    contentType:"application/x-www-form-urlencoded",
                    data:data,
                    success: function (data) {
                        _this.tableData = data.data.list;
                        _this.pagination.total = data.data.total;
                    },
                    error:function(){

                    }
                });

            },
            querySum:function () {
                var _this = this;
                $.ajax({
                    url:getUrl()+ '/Api/AllowanceCard/queryDeatilDataSum',
                    type:'GET',
                    dataType: "json",
                    contentType:"application/x-www-form-urlencoded",
                    data:{pk_allowance_num:batch_code},//批次号,
                    success: function (data) {
                        _this.money_allowance = data.data[0].money_allowance;
                    },
                    error:function(){

                    }
                });

            },
        },
    });

	subsidyVue.init();
	//1.初始化左侧树
	allowanceCard.initTreeView();
	//2.初始化按钮
	allowanceCard.initButton();
	//3初始化表格,后台还没写好，暂时注释，后台写好打开测试表格功能
	allowanceCard.initTable();
})
