$(function (merchant) {

    var pk_merchant;
    var default_navigation;

    merchant.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/merchant/retrieve',         //请求后台的URL（*）
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
                    merchant_code: $("#query_code").val(),
                    merchant_name: $("#query_name").val(),
                    merchant_phone: $("#query_phone").val(),
                    merchant_psn: $("#query_psn").val(),
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
                field: 'pk_merchant',
                title: ''
            }, {
                field: 'merchant_code',
                title: '编码'
            }, {
                field: 'merchant_name',
                title: '名称'
            },{
                field: 'merchant_psn',
                title: '联系人'
            },{
                field: 'merchant_phone',
                title: '联系电话'
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
        $('#table').bootstrapTable('hideColumn', 'pk_merchant');
    }

    merchant.initButton = function () {
        //查询
        $("#btn_query").click(function () {
            merchant.refresh();
        });

        //新增
        $("#btn_add").click(function(){

            $("#merchant_code").val("");
            $("#merchant_name").val("");
            $("#merchant_psn").val("");
            $("#merchant_phone").val("");

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            pk_merchant = selected[0].pk_merchant;
            $("#merchant_code").val(selected[0].merchant_code);
            $("#merchant_name").val(selected[0].merchant_name);
            $("#merchant_psn").val(selected[0].merchant_psn);
            $("#merchant_phone").val(selected[0].merchant_phone);
            $("#mymodal-data").modal('show');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_merchant);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/merchant/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        merchant.refresh();

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

            var merchant_code = $("#merchant_code").val();
            var merchant_name = $("#merchant_name").val();
            var merchant_psn = $("#merchant_psn").val();
            var merchant_phone = $("#merchant_phone").val();

            if(merchant_code == "" || merchant_name == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
            }

            var sendData = {
                pk_merchant:pk_merchant,
                merchant_code:merchant_code,
                merchant_name:merchant_name,
                merchant_psn:merchant_psn,
                merchant_phone:merchant_phone,
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/merchant/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#mymodal-data").modal('hide');
                        merchant.refresh();

                    } else {

                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_merchant = null;
        });

        //重置
        $("#btn_reset").click(function(){
            $("#query_code").val("");
            $("#query_name").val("");
            $("#query_psn").val("");
            $("#query_phone").val("");
            merchant.refresh();
        });


    }

    merchant.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/merchant/retrieve",
            silent: true,
            query: JSON.stringify({
                merchant_code:$("#query_code").val(),
                merchant_name:$("#query_name").val(),
                merchant_psn:$("#query_psn").val(),
                merchant_phone:$("#query_phone").val(),
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }


    //.初始化Table
    merchant.initTable();

    //.初始化Button的点击事件
    merchant.initButton();

});

