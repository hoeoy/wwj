$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: '/Api/role/retrieve',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'pk_role',
                title: 'ID'
            }, {
                field: 'role_name',
                title: '角色名称'
            } ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            role_name: $("#txt_search_role_name").val(),
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件

        //新增
        $("#btn_add").click(function(){
            $("#role_name").val("");
            $("#pk_role").val("");
            $("#park-add-modal").modal('show');
        });

        //查询
        $("#btn_query").click(function(){
            alert($("#txt_search_role_name").val());
        });

        //保存
        $("#save").click(function(){
            var role_name = $.trim($("#role_name").val());
            var pk_role = $.trim($("#pk_role").val());
            if(role_name == null || role_name == ""){
                alert("请输入角色名称!");
                return;
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify({role_name:role_name,pk_role:pk_role}),
                dataType:"json",
                url:  '/Api/role/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if(data.success){
                        alert("保存成功!")
                    }else{
                        alert("保存失败!")
                    }
                },
                error: function (data) {
                    alert("保存失败!")
                }
            });

            $("#park-add-modal").modal('hide');

        });

        //编辑
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            if(selected.length == 0){
                alert("请选中要编辑的数据!");
                return;
            }

            if(selected.length > 1){
                alert("请选中一条!");
                return;
            }

            $("#role_name").val(selected[0].role_name);
            $("#pk_role").val(selected[0].pk_role);

            $("#park-add-modal").modal('show');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            if(selected.length == 0){
                alert("请选中要删除的数据!");
                return;
            }

            if(!confirm('确定要执行此操作吗?')){
                return;
            }


            var pks = [];
            $.each(selected,function(idx,obj){
                pks.push(obj.pk_role);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:  '/Api/role/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if(data.success){
                        alert("删除成功!")
                    }else{
                        alert("删除失败!")
                    }
                },
                error: function (data) {
                    alert("删除失败!")
                }
            });

        });

    };

    return oInit;
};