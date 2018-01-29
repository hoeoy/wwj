$(function (device) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var pk_merchant;
    var pk_device;


    device.initTreeView = function () {
        $.ajax({
            type: 'get',
            dataType: "json",
            url:getUrl()+ '/Api/device/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onNodeSelected: function (event, node) {
                            pk_merchant = node.id;

                            $("#btn_add").removeAttr('disabled');

                            //刷新表格
                            device.refresh();

                        }
                    });
                } else {
                    alert("初始化树失败!:" + data.msg);
                }
            },
            error: function (data) {
                Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
            }
        });
    }

    device.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/device/retrieve',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset,  //页码
                    device_code: $("#txt_search_device_code").val(),
                    device_name: $("#txt_search_device_name").val(),
                    pk_merchant: pk_merchant,
                };
                return temp;
            },//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'pk_device',
                title: ''
            },{
                field: 'pk_device_time',
                title: ''
            },{
                field: 'device_code',
                title: '编码'
            }, {
                field: 'device_name',
                title: '名称'
            }, {
                field: 'device_ip',
                title: 'IP'
            }, {
                field: 'device_port',
                title: '端口号'
            }, {
                field: 'device_meal_type',
                title: '扣费规则'
            }, {
                field: 'be_control_time',
                title: '时间段控制'
            },{
                field: 'time_name',
                title: '时间段'
            }],
            onCheck:function(){
                console.log($('#table').bootstrapTable("getSelections").length);
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }
            },
            onUncheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_edit").attr('disabled','disabled');
                $("#btn_delete").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_device');
        $('#table').bootstrapTable('hideColumn', 'pk_device_time');
    }

    device.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            device.refresh();
            //alert($("#txt_search_sex").val())
        });

       //增加
        $("#btn_add").click(function(){

            if(pk_merchant == null){
                Notify('请选择商户', 'top-right', '5000', 'default', 'fa-times-circle', true);
                return;
            }
            $("#pk_device_time").html("");
            $.ajax({
                type: 'get',
                dataType: "json",
                url: getUrl() + '/Api/deviceTime/retrieveTree',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        var timeNodes = listToTree(data.data);

                        pk_device = selected[0].pk_device;
                        $.each(timeNodes, function (idx, obj) {

                            $("#pk_device_time").append("<option value='"+timeNodes[idx].pk_device_time+"'>"+timeNodes[idx].time_name+"</option>");

                        });
                    } else {
                        alert("初始化时间段失败!:" + data.msg);
                    }
                },
                error: function (data) {
                    Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

            $("#device_code").val("");
            $("#device_name").val("");
            $("#device_ip").val("");
            $("#device_port").val("");
            $("#device_meal_type").val("");
            $("#be_control_time").val("");
            pk_device = null;

            $("#mymodal-data").modal('show');

        });

        //修改
        $("#btn_edit").click(function(){
            $("#pk_device_time").html("");
            var selected = $('#table').bootstrapTable("getSelections");

            $.ajax({
                type: 'get',
                dataType: "json",
                url: getUrl() + '/Api/deviceTime/retrieveTree',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        var timeNodes = listToTree(data.data);

                        console.log(timeNodes);

                        $("#device_code").val(selected[0].device_code);
                        $("#device_name").val(selected[0].device_name);
                        $("#device_ip").val(selected[0].device_ip);
                        $("#device_port").val(selected[0].device_port);
                        $("#device_meal_type").val(selected[0].device_meal_type);
                        $("#be_control_time").val(selected[0].be_control_time);
                        $("#device_type").val(selected[0].device_type) ;
                        pk_device = selected[0].pk_device;
                        var pk_device_times = $("#pk_device_time").children();
                        $.each(timeNodes, function (idx, obj) {

                            if(timeNodes[idx].pk_device_time == selected.pk_device_time){
                                $("#pk_device_time").append("<option value='"+timeNodes[idx].pk_device_time+"' selected='selected'>"+timeNodes[idx].time_name+"</option>");
                            }else{
                                $("#pk_device_time").append("<option value='"+timeNodes[idx].pk_device_time+"'>"+timeNodes[idx].time_name+"</option>");
                            }
                        });
                        $("#mymodal-data").modal('show');
                    } else {
                        alert("初始化时间段失败!:" + data.msg);
                    }
                },
                error: function (data) {
                    Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });



        });

        $("#save").click(function(){

            var device_code = $("#device_code").val();
            var device_name = $("#device_name").val();
            var device_ip = $("#device_ip").val();
            var device_port = $("#device_port").val();
            var pk_device_time = $("#pk_device_time").val();pk_merchant
            var device_type = $("#device_type").val();//消费机类型

            if(device_code == null || device_code == "" || device_name == null || device_name == ""
            || device_ip == null || device_ip == "" || device_port == null || device_port == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'default', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                pk_device:pk_device,
                pk_device_time:pk_device_time,
                pk_merchant:pk_merchant,
                device_code:device_code,
                device_name:device_name,
                device_ip:device_ip,
                device_port:device_port,
                device_meal_type:$("#device_meal_type").val(),
                be_control_time:$("#be_control_time").val(),
                device_type:device_type//消费机类型
            }

            $.ajax({
                type: 'post',
                data: JSON.stringify(sendData),
                dataType: "json",
                url:getUrl()+ '/Api/device/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);

                        device.refresh();
                    } else {
                        Notify('保存失败'+ data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

            $("#mymodal-data").modal('hide');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var sendData = [];

            $.each(selected,function(idx,obj){
                sendData.push(obj.pk_device);
            });

            $.ajax({
                type: 'post',
                data: JSON.stringify(sendData),
                dataType: "json",
                url:getUrl()+ '/Api/device/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        device.refresh();
                    } else {
                        Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

    }

    device.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/device/retrieve",
            silent: true,
            query: JSON.stringify({
                pk_merchant: pk_merchant,
                device_code: $("#txt_search_device_code").val(),
                device_name: $("#txt_search_device_name").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    device.initTreeView();

    //2.初始化Table
    device.initTable();

    //3.初始化Button的点击事件
    device.initButton();

});
