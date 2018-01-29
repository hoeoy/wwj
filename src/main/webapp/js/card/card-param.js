$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    $('#table').bootstrapTable('hideColumn', 'pk_card_param');

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/cardparam/retrieve',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
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
            //showColumns: true,                  //是否显示所有的列
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
                field: 'pk_card_param',
                title: 'ID',
            },{
                field: 'name',
                title: '名称',
            },{
                field: 'costing',
                title: '卡成本',
            }, {
                field: 'deposit',
                title: '卡押金'
            }, {
                field: 'effective_months',
                title: '有效月份'
            },{
                field: 'password',
                title: '默认密码'
            }
            ],
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
           /* onClickRow:function(){
                var selected_btn = $('#table').bootstrapTable("getSelections");
                console.log(selected_btn.length);
                if(selected_btn.length+1 ==2){
                   $("#btn_edit").removeAttr('disabled');
                   $("#btn_delete").removeAttr('disabled');
                }else if(selected_btn.length+1>2){
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").removeAttr('disabled');
                }else{
                    $("#btn_edit").attr('disabled','disabled');
                    $("#btn_delete").attr('disabled','disabled');
                }


            }*/
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    function refresh(){
        var opt = {
            url: getUrl()+"/Api/cardparam/retrieve",
            silent: true,
            query:JSON.stringify({
                limit:10,
                offset:0,
                type:1,
                level:2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    oInit.Init = function () {
        //初始化页面上面的按钮事件

        //新增
        $("#btn_add").click(function(){
            $("#deposit").val("");
            $("#costing").val("");
            $("#effective_months").val("");
            $("#password").val("");
            $("#pk_card_param").val("");
            $("#name").val("");
            $("#park-add-modal").modal('show');
        });

        //查询
        $("#btn_query").click(function(){
            refresh();
        });

        //保存
        $("#save").click(function(){
            var deposit = $.trim($("#deposit").val());
            var costing = $.trim($("#costing").val());
            var effective_months = $.trim($("#effective_months").val());
            var password = $.trim($("#password").val());
            var pk_card_param = $.trim($("#pk_card_param").val());
            var name = $.trim($("#name").val());
            if(deposit == null || deposit == "" || costing == null || costing == "" || effective_months == null || effective_months == ""　|| password == null || password == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                return;
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify(
                    {
                        deposit:deposit*100,
                        costing:costing*100,
                        effective_months:effective_months,
                        password:password,
                        pk_card_param:pk_card_param,
                        name:name,
                    }
                ),
                dataType:"json",
                url: getUrl()+ '/Api/cardparam/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if(data.success){
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        refresh();
                    }else{
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $(".text-warn").hide();
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

            $("#deposit").val(selected[0].deposit);
            $("#costing").val(selected[0].costing);
            $("#effective_months").val(selected[0].effective_months);
            $("#password").val(selected[0].password);
            $("#pk_card_param").val(selected[0].pk_card_param);
            $("#name").val(selected[0].name);

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
                pks.push(obj.pk_card_param);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url: getUrl()+ '/Api/cardparam/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if(data.success){
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-check-circle', true);
                        refresh();
                    }else{
                        Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('删除失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });

        });

    };

    return oInit;
};
//部门树高度 背景色
(function(){
    setTimeout(function(){
        $('#tree').css({minHeight:deptTreeHeight,background:'#fff'});
    },500)
})();