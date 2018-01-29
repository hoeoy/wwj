$(function (tasklog) {

    tasklog.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/task/retrievelogs',         //请求后台的URL（*）
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
                    start_ts: $("#start_ts").val(),
                    end_ts: $("#end_ts").val(),
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
                field: 'pk_task_log',
                title: ''
            }, {
                field: 'log_ts',
                title: '同步时间'
            }, {
                field: 'result',
                title: '结果'
            },{
                field: 'memo',
                title: '信息'
            }],
            onCheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }
            },
            onUncheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                    $("#btn_jurisdiction").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_edit").removeAttr('disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }else if(selected_btn.length>1){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                    $("#btn_jurisdiction").removeAttr('disabled');
                }else if(selected_btn.length==0){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                    $("#btn_jurisdiction").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_edit").attr('disabled','disabled');
                $("#btn_delete").attr('disabled','disabled');
                $("#btn_jurisdiction").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_task_log');

    };

    tasklog.initButton = function () {

        $("#btn_query").click(function(){
            tasklog.refresh();
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_task_log);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/task/deletelog',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        tasklog.refresh();

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


    tasklog.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/task/retrievelogs",
            silent: true,
            query: JSON.stringify({
                start_ts:$("#start_ts").val(),
                end_ts:$("#end_ts").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }


    //.初始化Table
    tasklog.initTable();

    //.初始化Button的点击事件
    tasklog.initButton();

});

