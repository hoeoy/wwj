$(function (mealrule) {

    var pk_meal_rule;

    mealrule.initTable = function () {
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/mealrule/retrieve',         //请求后台的URL（*）
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
                field: 'pk_meal_rule',
                title: ''
            }, {
                field: 'meal_rule_code',
                title: '编码'
            }, {
                field: 'meal_rule_name',
                title: '名称'
            },{
                field: 'frequency_day',
                title: '日限次'
            },{
                field: 'money_day',
                title: '日限额'
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
        $('#table').bootstrapTable('hideColumn', 'pk_meal_rule');
    }

    mealrule.initButton = function () {

        //新增
        $("#btn_add").click(function(){

            $("#meal_rule_code").val("");
            $("#meal_rule_name").val("");
            $("#frequency_day").val("");
            $("#money_day").val("");
            $("#frequency_time").val("");
            $("#money_time").val("");

            $("#mymodal-data").modal('show');
        });

        //修改
        $("#btn_edit").click(function(){

            var selected = $('#table').bootstrapTable("getSelections");
            pk_meal_rule = selected[0].pk_meal_rule;
            $("#meal_rule_code").val(selected[0].meal_rule_code);
            $("#meal_rule_name").val(selected[0].meal_rule_name);
            $("#frequency_day").val(selected[0].frequency_day);
            $("#money_day").val(selected[0].money_day);
            $("#frequency_time").val(selected[0].frequency_time);
            $("#money_time").val(selected[0].money_time);
            $("#mymodal-data").modal('show');
        });

        //删除
        $("#btn_delete").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");

            var pks = [];

            $.each(selected,function(idx,obj){
                pks.push(obj.pk_meal_rule);
            });

            $.ajax({
                type: 'post',
                data:JSON.stringify(pks),
                dataType:"json",
                url:getUrl()+ '/Api/mealrule/delete',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('删除成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        mealrule.refresh();

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

            var meal_rule_code = $("#meal_rule_code").val();
            var meal_rule_name = $("#meal_rule_name").val();
            var frequency_day = $("#frequency_day").val();
            var money_day = $("#money_day").val();
            var frequency_time = $("#frequency_time").val();
            var money_time = $("#money_time").val();

            if(meal_rule_code == "" || meal_rule_code == "" || frequency_day=="" || money_day == "" || frequency_time == "" || money_time == ""){
                Notify('请填写完整信息', 'top-right', '5000', 'info', 'fa-times-circle', true);
                return;
            }

            var sendData = {
                pk_meal_rule:pk_meal_rule,
                meal_rule_code:meal_rule_code,
                meal_rule_name:meal_rule_name,
                frequency_day:frequency_day,
                money_day:money_day*100,
                frequency_time:frequency_time,
                money_time:money_time*100,
            }

            $.ajax({
                type: 'post',
                data:JSON.stringify(sendData),
                dataType:"json",
                url:getUrl()+ '/Api/mealrule/save',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('保存成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        $("#mymodal-data").modal('hide');
                        mealrule.refresh();

                    } else {
                        Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('保存失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            pk_meal_rule = null;
        });



    }

    mealrule.refresh = function refresh() {
        var opt = {
            url:getUrl()+"/Api/mealrule/retrieve",
            silent: true,
            query: JSON.stringify({
                limit: 10,
                offset: 0,
                type: 1,
                level: 2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }


    //.初始化Table
    mealrule.initTable();

    //.初始化Button的点击事件
    mealrule.initButton();

});

