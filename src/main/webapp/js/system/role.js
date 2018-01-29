$(function (role) {

    var pk_role;
    var default_navigation;

    role.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/role/retrieve',         //请求后台的URL（*）
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
                    role_code: $("#query_code").val(),
                    role_name: $("#query_name").val(),
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
                field: 'pk_role',
                title: ''
            }, {
                field: 'role_code',
                title: '编码'
            }, {
                field: 'role_name',
                title: '名称'
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
        $('#table').bootstrapTable('hideColumn', 'pk_role');
    }

    role.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            role.refresh();
        });

        //新增
        $("#btn_add").click(function(){

            $("#role_code").val("");
            $("#role_name").val("");

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            pk_role = selected[0].pk_role;
            $("#role_code").val(selected[0].role_code);
            $("#role_name").val(selected[0].role_name);
            $("#mymodal-data").modal('show');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_role);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/role/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        role.refresh();

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

            var role_code = $("#role_code").val();
            var role_name = $("#role_name").val();

            if(role_code == "" || role_name == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                pk_role:pk_role,
                role_code:role_code,
                role_name:role_name,
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/role/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#mymodal-data").modal('hide');
                        role.refresh();

                    } else {
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_role = null;
        });

        //重置
        $("#btn_reset").click(function(){
            $("#query_code").val("");
            $("#query_name").val("");
            role.refresh();
        });

        function listTozTree(data){
            var result = [];
            $.each(data, function (n, e) {
                var cc = [];
                if (e.children_data != null) {
                    cc = listTozTree(e.children_data);
                }
                e.children = cc;
                result.push(convertItem(e));
            });
            return result;
        }

        function convertItem(o){
            o.id = o.treeId;
            o.name = o.treeText;
            return o;
        }

        $("#btn_jurisdiction").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");
            var params = {
                pk_role:selected[0].pk_role
            };



            //查询目录
            $.ajax({
                type: 'post',
                data:JSON.stringify(params),
                dataType:"json",
                url:getUrl()+ '/Api/index/loadRoleNavigation',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        console.log(data);
                        var setting = {
                            treeId:"zTree",
                            isSimpleData : true, //数据是否采用简单 Array 格式，默认false
                            treeNodeKey : "id", //在isSimpleData格式下，当前节点id属性
                            treeNodeParentKey : "pId", //在isSimpleData格式下，当前节点的父节点id属性
                            check:{
                                enable: true,
                                chkStyle: "checkbox",
                                chkboxType: { "Y": "ps", "N": "ps" }
                            }
                        };
                        $.fn.zTree.destroy($("#navigationMenu"));
                        $.fn.zTree.init($("#navigationMenu"), setting, listTozTree(data.data));
                        $("#myModal").modal('show');
                        default_navigation = $.fn.zTree.getZTreeObj("navigationMenu").getCheckedNodes(true);

                    } else {
                        Notify('初始化目录失败,'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                },
                error: function (data) {
                    Notify('服务器错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
        });

    }

    $("#okNavigationBtn").click(function(){

        var selected = $('#table').bootstrapTable("getSelections");

        var treeObj = $.fn.zTree.getZTreeObj("navigationMenu");
        var newnodes = treeObj.getCheckedNodes(true);

        var defaultnodesStr = [];
        var newnodeStr = [];

        //新增的权限
        var news = [];

        //去掉的权限
        var out = [];

        //判断权限是否发生变化
        $.each(default_navigation,function(idx,obj){
            defaultnodesStr.push(obj.id);
        });

        //新增的权限
        $.each(newnodes,function(idx,obj){
            newnodeStr.push(obj.id);
            if($.inArray(obj.id, defaultnodesStr) == -1){
                news.push(obj.id);
            }
        });

        //去掉的权限
        $.each(defaultnodesStr,function(idx,obj){
            if($.inArray(obj, newnodeStr) == -1){
                out.push(obj);
            }
        });

        if(news.length == 0 && out.length == 0){
            Notify('权限没有发生变化', 'top-right', '5000', 'info', 'fa-times-circle', true);
            return;
        }
        //console.log("新增的权限:"+news);
        //console.log("去掉的权限:"+out);
        if(news.length > 0){

            var params = [];

            $.each(news,function(idx,obj){
                params.push({
                    pk_role:selected[0].pk_role,
                    pk_navigation:obj
                });
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(params),
                dataType:"json",
                url: getUrl() + '/Api/jurisdiction/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                    } else {
                        Notify('失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('连接服务器错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
        }
        if(out.length > 0){
            var params = [];

            $.each(out,function(idx,obj){
                params.push({
                    pk_role:selected[0].pk_role,
                    pk_navigation:obj
                });
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(params),
                dataType:"json",
                url: window.getUrl() + '/Api/jurisdiction/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                    } else {
                        Notify('失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('连接服务器错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
        }
        $("#myModal").modal('hide');
    });

    role.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/role/retrieve",
            silent: true,
            query: JSON.stringify({
                role_code:$("#query_code").val(),
                role_name:$("#query_name").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }


    //.初始化Table
    role.initTable();

    //.初始化Button的点击事件
    role.initButton();

});

