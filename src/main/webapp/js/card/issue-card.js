$(function (issuecard) {
    document.getElementById("tree").style.maxHeight=$(window).height()+'px';
    var department_code;
    var pk_staff;
    var index;


    //加载消费规则
    $.ajax({
        type: 'get',
        dataType:"json",
        url: getUrl()+'/Api/mealrule/retrieveAll' + "?" + Math.random(),
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data.success) {
                $.each(data.data,function(idx,obj){
                    $("#pk_meal_rule").append("<option value='"+obj.pk_meal_rule+"'>"+obj.meal_rule_name+"</option>");
                });
            } else {
                Notify('初始化消费规则失败'+ data.msg, 'top-right', '5000', 'danger', 'fa-times-circle', true);

            }
        },
        error: function (data) {
            Notify('连接错误', 'top-right', '5000', 'danger', 'fa-times-circle', true);

            onError();
        }
    });

    issuecard.initTreeView = function(){
        $.ajax({
            type: 'get',
            data:{company_code:"1"},
            dataType:"json",
            url: getUrl()+'/Api/dept/retrieveTree',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                if (data.success) {
                    var nodes = JSON.stringify(listToTree(data.data));
                    $('#tree').treeview({
                        data: nodes,
                        onhoverColor:'#5bc0de',
                        onNodeSelected: function(event, node) {
                            $("#btn_batchissue").removeAttr('disabled');
                            department_code = node.id;
                            issuecard.refresh();   //刷新表格
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

    issuecard.initTable = function(){
        $('#table').bootstrapTable({
            url:getUrl()+ '/Api/card/retrieve',         //请求后台的URL（*）
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
                    staff_code: $("#txt_search_staff_code").val(),
                    staff_name: $("#txt_search_name").val(),
                    department_code:department_code,
                    operator_type:"issue"
                };
                return temp;
            },//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            useCurrentPage:false,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'pk_staff',
                title: ''
            },{
                field: 'staff_code',
                title: '编码'
            }, {
                field: 'staff_name',
                title: '姓名'
            } ],
            onCheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_issue").removeAttr('disabled');
                }else{
                    $("#btn_issue").attr('disabled','disabled');
                }
            },
            onUncheck:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_issue").removeAttr('disabled');
                }else{
                    $("#btn_issue").attr('disabled','disabled');
                }
            },
            onCheckAll:function(){
                var selected_btn=$('#table').bootstrapTable("getSelections");
                if(selected_btn.length ==1){
                    $("#btn_issue").removeAttr('disabled');
                }else{
                    $("#btn_issue").attr('disabled','disabled');
                }
            },
            onUncheckAll:function(){
                $("#btn_issue").attr('disabled','disabled');
            }
        });
        $('#table').bootstrapTable('hideColumn', 'pk_staff');
    }

    issuecard.initButton = function(){
        //查询
        $("#btn_query").click(function(){
            department_code = "";
            issuecard.refresh();
        });

        //发卡
        $("#btn_issue").click(function(){

            $("#okBtn_One").show();
            $("#okBtn_Batch").hide();

            $.ajax({
                type: 'get',
                data:{offset:0,limit:100},
                dataType:"json",
                url:getUrl()+ '/Api/cardparam/retrieveall',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                        $("#costing").val(data.data.costing);
                        $("#deposit").val(data.data.deposit);
                        $("#effective_months").val(data.data.effective_months);
                        $("#password").val(data.data.password);

                    } else {
                        Notify('查询卡参数失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                },
                error: function (data) {
                    Notify('查询卡参数失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                }
            });
            var selected = $('#table').bootstrapTable("getSelections");
            $("#staff_code").val(selected[0].staff_code);
            $("#staff_name").val(selected[0].staff_name);
            var pk_merchant = selected[0].pk_merchant;console.log(pk_merchant)
            if( pk_merchant == '' || pk_merchant == null || pk_merchant == undefined || pk_merchant == '0'){
                Notify('请去人员档案设置当前人员所属商户', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                $('#okBtn_One').attr('disabled',true);
            }else  $('#okBtn_One').attr('disabled',false);

            pk_staff = selected[0].pk_staff;

            $("#info-modal").modal('show');
        });

        //读卡
        $("#readCard").click(function(){
            //调取硬件读取卡号
            var v;

            v = AxRead.RequestPhyNo( );
            if( v == 0 )
            {
                Notify('没有检测到卡或者发卡器', 'top-right', '5000', 'danger', 'fa-times-circle', true);
            }
            else
            {
                $("#card_code").val(v);
            }
            //$("#card_code").val("580258076");
        });

        //单个发卡确定
        $("#okBtn_One").click(function(){
            /*var pk_merchant = selected[0].pk_merchant;console.log(pk_merchant)
            if( pk_merchant == '' || pk_merchant == null || pk_merchant == undefined || pk_merchant == '0'){
                Notify('请去人员档案设置当前人员所属商户', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                $('#okBtn_One').attr('disabled',true);
            }else  $('#okBtn_One').attr('disabled',false);*/

            if($("#card_code").val() == ""){
                Notify('请填写卡号', 'top-right', '5000', 'info', 'fa-exclamation-circle', true);
                return;
            }

            var data = {
                card_code:$("#card_code").val(),
                pk_staff:pk_staff,
                staff_code:$("#staff_code").val(),
                staff_name:$("#staff_name").val(),
                card_costing:$("#costing").val()*100,
                card_deposit:$("#deposit").val()*100,
                effective_months:$("#effective_months").val(),
                password:$("#password").val(),
                pk_meal_rule:$("#pk_meal_rule").val(),
                operator:JSON.parse(localStorage.user).pk_user,
            };

            $.ajax({
                type: 'post',
                data:JSON.stringify(data),
                dataType:"json",
                url:getUrl()+ '/Api/card/issue',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify('发卡成功', 'top-right', '5000', 'primary', 'fa-times-circle', true);
                    } else {
                        Notify('发卡失败'+data.message, 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify('发卡失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
            $("#info-modal").modal('hide');

            issuecard.refresh();

        });

        //批量发卡确定
        $("#okBtn_Batch").click(function(){

            var currentData = $('#table').bootstrapTable("getData");
            var indextemp = index;

            if($("#card_code").val() == "" || $("#card_code").val() == null){
                Notify('请填写卡号', 'top-right', '5000', 'info', 'fa-exclamation-circle', true);
                return;
            }

            var data = {
                card_code:$("#card_code").val(),
                pk_staff:pk_staff,
                staff_code:$("#staff_code").val(),
                staff_name:$("#staff_name").val(),
                card_costing:$("#costing").val()*100,
                card_deposit:$("#deposit").val()*100,
                effective_months:$("#effective_months").val(),
                password:$("#password").val(),
                pk_meal_rule:$("#pk_meal_rule").val(),
                operator:JSON.parse(localStorage.user).pk_user,
            };

            $.ajax({
                type: 'post',
                data:JSON.stringify(data),
                dataType:"json",
                url:getUrl()+ '/Api/card/issue',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {
                        Notify($("#staff_name").val()+"发卡成功", 'top-right', '5000', 'primary', 'fa-times-circle', true);
                        indextemp = ++index;

                        if(currentData.length == indextemp){
                            Notify('本次发卡完毕', 'top-right', '5000', 'info', 'fa-times-circle', true);
                            $("#info-modal").modal('hide');
                            issuecard.refresh();
                            return;
                        }

                        pk_staff = currentData[indextemp].pk_staff;
                        $("#staff_code").val(currentData[indextemp].staff_code);
                        $("#staff_name").val(currentData[indextemp].staff_name);
                        var pk_merchant = currentData[indextemp].pk_merchant;console.log(pk_merchant)
                        if( pk_merchant == '' || pk_merchant == null || pk_merchant == undefined || pk_merchant == '0'){
                            Notify('请去人员档案设置当前人员所属商户', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                            $('#okBtn_Batch').attr('disabled',true);
                        }else {$('#okBtn_Batch').attr('disabled',false);}
                        $("#card_code").val("");
                    } else {
                        Notify($("#staff_name").val()+"发卡失败！", 'top-right', '5000', 'danger', 'fa-times-circle', true);
                    }
                },
                error: function (data) {
                    Notify($("#staff_name").val()+"发卡失败！", 'top-right', '5000', 'danger', 'fa-times-circle', true);
                }
            });
        });


        //批量发卡
        $("#btn_batchissue").click(function(){
            var selected = $('#table').bootstrapTable("getSelections");
            var pk_merchant = selected[0].pk_merchant;console.log(pk_merchant)
            if( pk_merchant == '' || pk_merchant == null || pk_merchant == undefined || pk_merchant == '0'){
                Notify('请去人员档案设置当前人员所属商户', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                $('#okBtn_Batch').attr('disabled',true);
            }else  $('#okBtn_Batch').attr('disabled',false);
            //重置计数器
            index = 0;

            if(department_code == null || department_code == ""){
                Notify('请选择要批量发卡的部门', 'top-right', '5000', 'default', 'fa-times-circle', true);
                return
            }

            var currentData = $('#table').bootstrapTable("getData");

            if(currentData.length == 0){
                Notify('未发现要发卡的人员', 'top-right', '5000', 'default', 'fa-times-circle', true);
                return;
            }

            $.ajax({
                type: 'get',
                data:{offset:0,limit:100},
                dataType:"json",
                url:getUrl()+ '/Api/cardparam/retrieveall',
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data.success) {

                        $("#costing").val(data.data.costing);
                        $("#deposit").val(data.data.deposit);
                        $("#effective_months").val(data.data.effective_months);
                        $("#password").val(data.data.password);

                    } else {
                        Notify('查询卡参数失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);

                    }
                },
                error: function (data) {
                    Notify('查询卡参数失败', 'top-right', '5000', 'danger', 'fa-times-circle', true);
                           }
            });

            $("#staff_code").val(currentData[0].staff_code);
            $("#staff_name").val(currentData[0].staff_name);
            pk_staff = currentData[0].pk_staff;

            $("#okBtn_One").hide();
            $("#okBtn_Batch").show();
            $("#info-modal").modal('show');

        });

        //取消
        $("#dismiss").click(function(){


        });
    }

    issuecard.refresh = function refresh(){
        var opt = {
            url:getUrl()+ "/Api/card/retrieve",
            silent: true,
            query:JSON.stringify({
                department_code:department_code,
                operator_type:"issue",
                limit:10,
                offset:0,
                type:1,
                level:2
            })
        };

        $("#table").bootstrapTable('refresh', opt);
    }

    //1.初始化左侧树
    issuecard.initTreeView();

    //2.初始化Table
    issuecard.initTable();

    //3.初始化Button的点击事件
    issuecard.initButton();

});
