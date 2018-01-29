$(function (sync) {
    var pk_task_config;

    sync.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/task/retrieveconfigs',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
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
                field: 'pk_task_config',
                title: ''
            }, {
                field: 'url',
                title: '数据库地址'
            }, {
                field: 'user',
                title: '用户名'
            }, {
                field: 'password',
                title: '密码'
            }, {
                field: 'frequency',
                title: '同步间隔'
            }, {
                field: 'company_code',
                title: '公司编码'
            }],
            onCheck:function(){
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
        $('#table').bootstrapTable('hideColumn', 'pk_task_config');
    }

    sync.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            sync.refresh();
        });

        //新增
        $("#btn_add").click(function(){

            pk_task_config = "";
            $("#url").val("");
            $("#user").val("");
            $("#password").val("");
            $("#frequency").val("");
            $("#company_code").val("");

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            pk_task_config = selected[0].pk_task_config;

            $("#user").val(selected[0].user);;
            $("#url").val(selected[0].url);;
            $("#password").val(selected[0].password);
            $("#frequency").val(selected[0].frequency);
            $("#company_code").val(selected[0].company_code);

            $("#mymodal-data").modal('show');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_task_config);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/task/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        sync.refresh();

                    } else {
                        Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

        //保存
        $("#save").click(function(){

            var user = $("#user").val();
            var url = $("#url").val();
            var password = $("#password").val();
            var frequency = $("#frequency").val();
            var company_code = $("#company_code").val();


            if(url == "" || frequency == ""||company_code == ""||user == ""||password == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                pk_task_config:pk_task_config,
                url:url,
                frequency:frequency,
                company_code:company_code,
                user:user,
                password:password
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/task/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#addSynModal").modal('hide');
                        sync.refresh();

                    } else {
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_task_config = null;
        });

        $("#btn_refresh").click(function(){
            sync.refresh();
        });

    }


    sync.refresh = function refresh(){
        var opt = {
            url:getUrl()+"/Api/task/retrieveconfigs",
            silent: true,
            query: JSON.stringify({
                limit: 10,
                offset: 0,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //.初始化Table
    sync.initTable();

    //.初始化Button的点击事件
    sync.initButton();

});

